import { useState, useEffect, useMemo } from 'react';
import { 
  Search, Filter, Plus, RefreshCw, Edit2, Trash2, X, Check, 
  AlertCircle, Wrench, Factory, Package, Clock, ChevronUp, ChevronDown
} from 'lucide-react';
import { preventivaService } from './services/api';
import type { Preventiva, PreventivaStats, SortDirection } from './types';

function App() {
  const [preventivas, setPreventivas] = useState<Preventiva[]>([]);
  const [stats, setStats] = useState<PreventivaStats | null>(null);
  const [fabricantes, setFabricantes] = useState<string[]>([]);
  const [modelos, setModelos] = useState<string[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  
  // Filters
  const [searchTerm, setSearchTerm] = useState('');
  const [filterFabricante, setFilterFabricante] = useState('');
  const [filterModelo, setFilterModelo] = useState('');
  const [showFilters, setShowFilters] = useState(true);
  
  // Sort
  const [sortField, setSortField] = useState<keyof Preventiva | ''>('');
  const [sortDirection, setSortDirection] = useState<SortDirection>('asc');
  
  // Modal
  const [showModal, setShowModal] = useState(false);
  const [editingItem, setEditingItem] = useState<Preventiva | null>(null);
  const [formData, setFormData] = useState<Partial<Preventiva>>({});
  
  // Confirm Dialog
  const [showConfirm, setShowConfirm] = useState(false);
  const [deleteId, setDeleteId] = useState<number | null>(null);
  
  // Toast
  const [toast, setToast] = useState<{ message: string; type: 'success' | 'error' } | null>(null);

  useEffect(() => {
    loadData();
  }, []);

  useEffect(() => {
    if (toast) {
      const timer = setTimeout(() => setToast(null), 3000);
      return () => clearTimeout(timer);
    }
  }, [toast]);

  const loadData = async () => {
    try {
      setLoading(true);
      const [preventivasData, statsData, fabricantesData, modelosData] = await Promise.all([
        preventivaService.getAll(),
        preventivaService.getStats(),
        preventivaService.getFabricantes(),
        preventivaService.getModelos(),
      ]);
      setPreventivas(preventivasData);
      setStats(statsData);
      setFabricantes(fabricantesData);
      setModelos(modelosData);
      setError(null);
    } catch (err) {
      setError('Erro ao carregar dados');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const filteredData = useMemo(() => {
    let result = [...preventivas];
    
    if (searchTerm) {
      const term = searchTerm.toLowerCase();
      result = result.filter(p => 
        p.modelo.toLowerCase().includes(term) ||
        p.fabricante.toLowerCase().includes(term) ||
        p.itemDescricao.toLowerCase().includes(term) ||
        (p.partNumber && p.partNumber.toLowerCase().includes(term))
      );
    }
    
    if (filterFabricante) {
      result = result.filter(p => p.fabricante === filterFabricante);
    }
    
    if (filterModelo) {
      result = result.filter(p => p.modelo === filterModelo);
    }
    
    if (sortField) {
      result.sort((a, b) => {
        const aVal = a[sortField] ?? '';
        const bVal = b[sortField] ?? '';
        const comparison = String(aVal).localeCompare(String(bVal));
        return sortDirection === 'asc' ? comparison : -comparison;
      });
    }
    
    return result;
  }, [preventivas, searchTerm, filterFabricante, filterModelo, sortField, sortDirection]);

  const handleSort = (field: keyof Preventiva) => {
    if (sortField === field) {
      setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc');
    } else {
      setSortField(field);
      setSortDirection('asc');
    }
  };

  const handleEdit = (item: Preventiva) => {
    setEditingItem(item);
    setFormData(item);
    setShowModal(true);
  };

  const handleCreate = () => {
    setEditingItem(null);
    setFormData({
      modelo: '',
      fabricante: '',
      itemDescricao: '',
      partNumber: '',
      codSap: '',
      qtd250h: '',
      qtd500h: '',
      qtd750h: '',
      qtd1000h: '',
      horasMo250h: '',
      horasMo500h: '',
      horasMo750h: '',
      horasMo1000h: '',
    });
    setShowModal(true);
  };

  const handleSave = async () => {
    try {
      if (editingItem) {
        await preventivaService.update(editingItem.id, formData as Omit<Preventiva, 'id'>);
        setToast({ message: 'Item atualizado com sucesso!', type: 'success' });
      } else {
        await preventivaService.create(formData as Omit<Preventiva, 'id'>);
        setToast({ message: 'Item criado com sucesso!', type: 'success' });
      }
      setShowModal(false);
      loadData();
    } catch (err) {
      setToast({ message: 'Erro ao salvar item', type: 'error' });
    }
  };

  const handleDelete = async () => {
    if (deleteId) {
      try {
        await preventivaService.delete(deleteId);
        setToast({ message: 'Item excluído com sucesso!', type: 'success' });
        loadData();
      } catch (err) {
        setToast({ message: 'Erro ao excluir item', type: 'error' });
      }
    }
    setShowConfirm(false);
    setDeleteId(null);
  };

  const confirmDelete = (id: number) => {
    setDeleteId(id);
    setShowConfirm(true);
  };

  const getFabricanteColor = (fab: string) => {
    const colors: Record<string, string> = {
      'JLG': 'bg-orange-100 text-orange-800',
      'GENIE': 'bg-blue-100 text-blue-800',
      'SKYJACK': 'bg-red-100 text-red-800',
      'HAULOTTE': 'bg-green-100 text-green-800',
    };
    return colors[fab] || 'bg-gray-100 text-gray-800';
  };

  const SortIcon = ({ field }: { field: keyof Preventiva }) => {
    if (sortField !== field) return <ChevronUp className="w-4 h-4 opacity-30" />;
    return sortDirection === 'asc' 
      ? <ChevronUp className="w-4 h-4" /> 
      : <ChevronDown className="w-4 h-4" />;
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 to-slate-100">
      {/* Header */}
      <header className="bg-white border-b border-slate-200 sticky top-0 z-40">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 bg-gradient-to-br from-blue-600 to-indigo-600 rounded-xl flex items-center justify-center">
                <Wrench className="w-6 h-6 text-white" />
              </div>
              <div>
                <h1 className="text-xl font-bold text-slate-900">PreventivaDB</h1>
                <p className="text-xs text-slate-500">Sistema de Manutenção Preventiva</p>
              </div>
            </div>
            <div className="flex items-center gap-3">
              <button
                onClick={loadData}
                className="flex items-center gap-2 px-4 py-2 text-slate-600 hover:text-slate-900 hover:bg-slate-100 rounded-lg transition-colors"
              >
                <RefreshCw className={`w-4 h-4 ${loading ? 'animate-spin' : ''}`} />
                Atualizar
              </button>
              <button
                onClick={handleCreate}
                className="flex items-center gap-2 px-4 py-2 bg-gradient-to-r from-blue-600 to-indigo-600 text-white rounded-lg hover:from-blue-700 hover:to-indigo-700 transition-all shadow-sm"
              >
                <Plus className="w-4 h-4" />
                Novo Item
              </button>
            </div>
          </div>
        </div>
      </header>

      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Stats Cards */}
        {stats && (
          <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
            <div className="bg-white rounded-2xl p-6 shadow-sm border border-slate-200">
              <div className="flex items-center gap-4">
                <div className="w-12 h-12 bg-blue-100 rounded-xl flex items-center justify-center">
                  <Package className="w-6 h-6 text-blue-600" />
                </div>
                <div>
                  <p className="text-sm text-slate-500">Total de Registros</p>
                  <p className="text-2xl font-bold text-slate-900">{stats.totalRegistros}</p>
                </div>
              </div>
            </div>
            <div className="bg-white rounded-2xl p-6 shadow-sm border border-slate-200">
              <div className="flex items-center gap-4">
                <div className="w-12 h-12 bg-indigo-100 rounded-xl flex items-center justify-center">
                  <Wrench className="w-6 h-6 text-indigo-600" />
                </div>
                <div>
                  <p className="text-sm text-slate-500">Modelos</p>
                  <p className="text-2xl font-bold text-slate-900">{stats.totalModelos}</p>
                </div>
              </div>
            </div>
            <div className="bg-white rounded-2xl p-6 shadow-sm border border-slate-200">
              <div className="flex items-center gap-4">
                <div className="w-12 h-12 bg-green-100 rounded-xl flex items-center justify-center">
                  <Factory className="w-6 h-6 text-green-600" />
                </div>
                <div>
                  <p className="text-sm text-slate-500">Fabricantes</p>
                  <p className="text-2xl font-bold text-slate-900">{stats.totalFabricantes}</p>
                </div>
              </div>
            </div>
            <div className="bg-white rounded-2xl p-6 shadow-sm border border-slate-200">
              <div className="flex items-center gap-4">
                <div className="w-12 h-12 bg-amber-100 rounded-xl flex items-center justify-center">
                  <Clock className="w-6 h-6 text-amber-600" />
                </div>
                <div>
                  <p className="text-sm text-slate-500">Itens de Manutenção</p>
                  <p className="text-2xl font-bold text-slate-900">{stats.totalItens}</p>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* Search and Filters */}
        <div className="bg-white rounded-2xl shadow-sm border border-slate-200 mb-6">
          <div className="p-4 border-b border-slate-200">
            <div className="flex items-center gap-4">
              <div className="flex-1 relative">
                <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-slate-400" />
                <input
                  type="text"
                  placeholder="Buscar por modelo, fabricante, item ou part number..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="w-full pl-10 pr-4 py-2.5 border border-slate-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                />
                {searchTerm && (
                  <button
                    onClick={() => setSearchTerm('')}
                    className="absolute right-3 top-1/2 -translate-y-1/2 text-slate-400 hover:text-slate-600"
                  >
                    <X className="w-4 h-4" />
                  </button>
                )}
              </div>
              <button
                onClick={() => setShowFilters(!showFilters)}
                className={`flex items-center gap-2 px-4 py-2.5 rounded-xl border transition-colors ${
                  showFilters ? 'bg-blue-50 border-blue-200 text-blue-700' : 'border-slate-200 text-slate-600 hover:bg-slate-50'
                }`}
              >
                <Filter className="w-4 h-4" />
                Filtros
              </button>
            </div>
          </div>
          
          {showFilters && (
            <div className="p-4 bg-slate-50 grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Fabricante</label>
                <select
                  value={filterFabricante}
                  onChange={(e) => setFilterFabricante(e.target.value)}
                  className="w-full px-3 py-2 border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  <option value="">Todos</option>
                  {fabricantes.map(fab => (
                    <option key={fab} value={fab}>{fab}</option>
                  ))}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Modelo</label>
                <select
                  value={filterModelo}
                  onChange={(e) => setFilterModelo(e.target.value)}
                  className="w-full px-3 py-2 border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  <option value="">Todos</option>
                  {modelos.map(mod => (
                    <option key={mod} value={mod}>{mod}</option>
                  ))}
                </select>
              </div>
            </div>
          )}
        </div>

        {/* Data Table */}
        <div className="bg-white rounded-2xl shadow-sm border border-slate-200 overflow-hidden">
          {loading ? (
            <div className="flex items-center justify-center py-20">
              <RefreshCw className="w-8 h-8 text-blue-600 animate-spin" />
            </div>
          ) : error ? (
            <div className="flex items-center justify-center py-20 text-red-500">
              <AlertCircle className="w-6 h-6 mr-2" />
              {error}
            </div>
          ) : (
            <div className="overflow-x-auto">
              <table className="w-full">
                <thead className="bg-slate-50 border-b border-slate-200">
                  <tr>
                    <th className="px-4 py-3 text-left">
                      <button onClick={() => handleSort('id')} className="flex items-center gap-1 text-xs font-semibold text-slate-600 uppercase tracking-wider">
                        ID <SortIcon field="id" />
                      </button>
                    </th>
                    <th className="px-4 py-3 text-left">
                      <button onClick={() => handleSort('modelo')} className="flex items-center gap-1 text-xs font-semibold text-slate-600 uppercase tracking-wider">
                        Modelo <SortIcon field="modelo" />
                      </button>
                    </th>
                    <th className="px-4 py-3 text-left">
                      <button onClick={() => handleSort('fabricante')} className="flex items-center gap-1 text-xs font-semibold text-slate-600 uppercase tracking-wider">
                        Fabricante <SortIcon field="fabricante" />
                      </button>
                    </th>
                    <th className="px-4 py-3 text-left">
                      <button onClick={() => handleSort('itemDescricao')} className="flex items-center gap-1 text-xs font-semibold text-slate-600 uppercase tracking-wider">
                        Item <SortIcon field="itemDescricao" />
                      </button>
                    </th>
                    <th className="px-4 py-3 text-left">
                      <button onClick={() => handleSort('partNumber')} className="flex items-center gap-1 text-xs font-semibold text-slate-600 uppercase tracking-wider">
                        Part Number <SortIcon field="partNumber" />
                      </button>
                    </th>
                    <th className="px-4 py-3 text-center text-xs font-semibold text-slate-600 uppercase tracking-wider">250h</th>
                    <th className="px-4 py-3 text-center text-xs font-semibold text-slate-600 uppercase tracking-wider">500h</th>
                    <th className="px-4 py-3 text-center text-xs font-semibold text-slate-600 uppercase tracking-wider">750h</th>
                    <th className="px-4 py-3 text-center text-xs font-semibold text-slate-600 uppercase tracking-wider">1000h</th>
                    <th className="px-4 py-3 text-center text-xs font-semibold text-slate-600 uppercase tracking-wider">Ações</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-slate-100">
                  {filteredData.map((item) => (
                    <tr key={item.id} className="hover:bg-slate-50 transition-colors">
                      <td className="px-4 py-3 text-sm text-slate-500">#{item.id}</td>
                      <td className="px-4 py-3">
                        <span className="font-medium text-slate-900">{item.modelo}</span>
                      </td>
                      <td className="px-4 py-3">
                        <span className={`inline-flex px-2.5 py-1 rounded-full text-xs font-medium ${getFabricanteColor(item.fabricante)}`}>
                          {item.fabricante}
                        </span>
                      </td>
                      <td className="px-4 py-3">
                        <span className="text-sm text-slate-700">{item.itemDescricao}</span>
                      </td>
                      <td className="px-4 py-3">
                        <span className="text-sm font-mono text-slate-600">{item.partNumber || '-'}</span>
                      </td>
                      <td className="px-4 py-3 text-center">
                        <span className={`text-sm ${item.qtd250h ? 'text-slate-900 font-medium' : 'text-slate-400'}`}>
                          {item.qtd250h || '-'}
                        </span>
                      </td>
                      <td className="px-4 py-3 text-center">
                        <span className={`text-sm ${item.qtd500h ? 'text-slate-900 font-medium' : 'text-slate-400'}`}>
                          {item.qtd500h || '-'}
                        </span>
                      </td>
                      <td className="px-4 py-3 text-center">
                        <span className={`text-sm ${item.qtd750h ? 'text-slate-900 font-medium' : 'text-slate-400'}`}>
                          {item.qtd750h || '-'}
                        </span>
                      </td>
                      <td className="px-4 py-3 text-center">
                        <span className={`text-sm ${item.qtd1000h ? 'text-slate-900 font-medium' : 'text-slate-400'}`}>
                          {item.qtd1000h || '-'}
                        </span>
                      </td>
                      <td className="px-4 py-3">
                        <div className="flex items-center justify-center gap-1">
                          <button
                            onClick={() => handleEdit(item)}
                            className="p-1.5 text-slate-400 hover:text-blue-600 hover:bg-blue-50 rounded-lg transition-colors"
                            title="Editar"
                          >
                            <Edit2 className="w-4 h-4" />
                          </button>
                          <button
                            onClick={() => confirmDelete(item.id)}
                            className="p-1.5 text-slate-400 hover:text-red-600 hover:bg-red-50 rounded-lg transition-colors"
                            title="Excluir"
                          >
                            <Trash2 className="w-4 h-4" />
                          </button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
          
          <div className="px-4 py-3 bg-slate-50 border-t border-slate-200 text-sm text-slate-600">
            Mostrando {filteredData.length} de {preventivas.length} registros
          </div>
        </div>
      </main>

      {/* Footer */}
      <footer className="border-t border-slate-200 bg-white mt-12">
        <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
          <div className="flex flex-col sm:flex-row items-center justify-between gap-4">
            <p className="text-sm text-slate-500">
              © 2026 PreventivaDB. Sistema de Manutenção Preventiva de Plataformas.
            </p>
            <div className="flex items-center gap-4">
              <a href="#" className="text-sm text-slate-500 hover:text-slate-700">Documentação</a>
              <a href="#" className="text-sm text-slate-500 hover:text-slate-700">Suporte</a>
              <a href="#" className="text-sm text-slate-500 hover:text-slate-700">API</a>
            </div>
          </div>
        </div>
      </footer>

      {/* Modal */}
      {showModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl shadow-xl max-w-2xl w-full max-h-[90vh] overflow-y-auto">
            <div className="p-6 border-b border-slate-200">
              <div className="flex items-center justify-between">
                <h2 className="text-xl font-semibold text-slate-900">
                  {editingItem ? 'Editar Item' : 'Novo Item'}
                </h2>
                <button onClick={() => setShowModal(false)} className="text-slate-400 hover:text-slate-600">
                  <X className="w-5 h-5" />
                </button>
              </div>
            </div>
            <div className="p-6 space-y-4">
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-slate-700 mb-1">Modelo *</label>
                  <input
                    type="text"
                    value={formData.modelo || ''}
                    onChange={(e) => setFormData({ ...formData, modelo: e.target.value })}
                    className="w-full px-3 py-2 border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-slate-700 mb-1">Fabricante *</label>
                  <select
                    value={formData.fabricante || ''}
                    onChange={(e) => setFormData({ ...formData, fabricante: e.target.value })}
                    className="w-full px-3 py-2 border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                    <option value="">Selecione...</option>
                    {fabricantes.map(fab => (
                      <option key={fab} value={fab}>{fab}</option>
                    ))}
                  </select>
                </div>
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Item de Manutenção *</label>
                <input
                  type="text"
                  value={formData.itemDescricao || ''}
                  onChange={(e) => setFormData({ ...formData, itemDescricao: e.target.value })}
                  className="w-full px-3 py-2 border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-slate-700 mb-1">Part Number</label>
                  <input
                    type="text"
                    value={formData.partNumber || ''}
                    onChange={(e) => setFormData({ ...formData, partNumber: e.target.value })}
                    className="w-full px-3 py-2 border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-slate-700 mb-1">Código SAP</label>
                  <input
                    type="text"
                    value={formData.codSap || ''}
                    onChange={(e) => setFormData({ ...formData, codSap: e.target.value })}
                    className="w-full px-3 py-2 border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
              </div>
              <div className="grid grid-cols-4 gap-4">
                <div>
                  <label className="block text-sm font-medium text-slate-700 mb-1">Qtd 250h</label>
                  <input
                    type="text"
                    value={formData.qtd250h || ''}
                    onChange={(e) => setFormData({ ...formData, qtd250h: e.target.value })}
                    className="w-full px-3 py-2 border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-slate-700 mb-1">Qtd 500h</label>
                  <input
                    type="text"
                    value={formData.qtd500h || ''}
                    onChange={(e) => setFormData({ ...formData, qtd500h: e.target.value })}
                    className="w-full px-3 py-2 border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-slate-700 mb-1">Qtd 750h</label>
                  <input
                    type="text"
                    value={formData.qtd750h || ''}
                    onChange={(e) => setFormData({ ...formData, qtd750h: e.target.value })}
                    className="w-full px-3 py-2 border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-slate-700 mb-1">Qtd 1000h</label>
                  <input
                    type="text"
                    value={formData.qtd1000h || ''}
                    onChange={(e) => setFormData({ ...formData, qtd1000h: e.target.value })}
                    className="w-full px-3 py-2 border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
              </div>
            </div>
            <div className="p-6 border-t border-slate-200 flex justify-end gap-3">
              <button
                onClick={() => setShowModal(false)}
                className="px-4 py-2 text-slate-600 hover:bg-slate-100 rounded-lg transition-colors"
              >
                Cancelar
              </button>
              <button
                onClick={handleSave}
                className="px-4 py-2 bg-gradient-to-r from-blue-600 to-indigo-600 text-white rounded-lg hover:from-blue-700 hover:to-indigo-700 transition-all flex items-center gap-2"
              >
                <Check className="w-4 h-4" />
                {editingItem ? 'Salvar' : 'Criar'}
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Confirm Dialog */}
      {showConfirm && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl shadow-xl max-w-md w-full p-6">
            <div className="flex items-center gap-4 mb-4">
              <div className="w-12 h-12 bg-red-100 rounded-full flex items-center justify-center">
                <AlertCircle className="w-6 h-6 text-red-600" />
              </div>
              <div>
                <h3 className="text-lg font-semibold text-slate-900">Confirmar Exclusão</h3>
                <p className="text-sm text-slate-500">Esta ação não pode ser desfeita.</p>
              </div>
            </div>
            <div className="flex justify-end gap-3">
              <button
                onClick={() => setShowConfirm(false)}
                className="px-4 py-2 text-slate-600 hover:bg-slate-100 rounded-lg transition-colors"
              >
                Cancelar
              </button>
              <button
                onClick={handleDelete}
                className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 transition-colors"
              >
                Excluir
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Toast */}
      {toast && (
        <div className={`fixed bottom-4 right-4 px-4 py-3 rounded-lg shadow-lg flex items-center gap-2 z-50 ${
          toast.type === 'success' ? 'bg-green-600 text-white' : 'bg-red-600 text-white'
        }`}>
          {toast.type === 'success' ? <Check className="w-5 h-5" /> : <AlertCircle className="w-5 h-5" />}
          {toast.message}
        </div>
      )}
    </div>
  );
}

export default App;
