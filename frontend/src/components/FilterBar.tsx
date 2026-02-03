import { Search, Filter, X, ChevronDown } from 'lucide-react';
import { useState, useEffect } from 'react';
import type { FilterOptions } from '../types';
import { plataformaService } from '../services/api';

interface FilterBarProps {
  searchTerm: string;
  onSearchChange: (term: string) => void;
  filters: FilterOptions;
  onFilterChange: (filters: FilterOptions) => void;
  onClearFilters: () => void;
}

export function FilterBar({
  searchTerm,
  onSearchChange,
  filters,
  onFilterChange,
  onClearFilters,
}: FilterBarProps) {
  const [showFilters, setShowFilters] = useState(false);
  const [categorias, setCategorias] = useState<string[]>([]);
  const [fabricantes, setFabricantes] = useState<string[]>([]);
  const [tiposAlimentacao, setTiposAlimentacao] = useState<string[]>([]);
  const [tiposSolo, setTiposSolo] = useState<string[]>([]);

  useEffect(() => {
    const loadFilterOptions = async () => {
      try {
        const [cats, fabs, tipos, solos] = await Promise.all([
          plataformaService.getCategorias(),
          plataformaService.getFabricantes(),
          plataformaService.getTiposAlimentacao(),
          plataformaService.getTiposSolo(),
        ]);
        setCategorias(cats);
        setFabricantes(fabs);
        setTiposAlimentacao(tipos);
        setTiposSolo(solos);
      } catch (error) {
        console.error('Erro ao carregar opções de filtro:', error);
      }
    };
    loadFilterOptions();
  }, []);

  const hasActiveFilters = Object.values(filters).some(v => v !== undefined && v !== '');

  return (
    <div className="space-y-4">
      {/* Search and Filter Toggle */}
      <div className="flex flex-col sm:flex-row gap-3">
        {/* Search Input */}
        <div className="relative flex-1">
          <Search className="absolute left-4 top-1/2 h-5 w-5 -translate-y-1/2 text-slate-400" />
          <input
            type="text"
            placeholder="Buscar por modelo, fabricante, descrição..."
            value={searchTerm}
            onChange={(e) => onSearchChange(e.target.value)}
            className="w-full rounded-xl border border-slate-300 bg-white py-3 pl-12 pr-4 text-sm text-slate-900 placeholder-slate-400 shadow-sm transition-all focus:border-blue-500 focus:outline-none focus:ring-4 focus:ring-blue-500/10"
          />
          {searchTerm && (
            <button
              onClick={() => onSearchChange('')}
              className="absolute right-4 top-1/2 -translate-y-1/2 text-slate-400 hover:text-slate-600"
            >
              <X className="h-5 w-5" />
            </button>
          )}
        </div>

        {/* Filter Toggle Button */}
        <button
          onClick={() => setShowFilters(!showFilters)}
          className={`inline-flex items-center gap-2 rounded-xl border px-5 py-3 text-sm font-medium transition-all ${
            showFilters || hasActiveFilters
              ? 'border-blue-500 bg-blue-50 text-blue-700'
              : 'border-slate-300 bg-white text-slate-700 hover:bg-slate-50'
          }`}
        >
          <Filter className="h-5 w-5" />
          Filtros
          {hasActiveFilters && (
            <span className="flex h-5 w-5 items-center justify-center rounded-full bg-blue-600 text-xs text-white">
              {Object.values(filters).filter(v => v !== undefined && v !== '').length}
            </span>
          )}
          <ChevronDown className={`h-4 w-4 transition-transform ${showFilters ? 'rotate-180' : ''}`} />
        </button>
      </div>

      {/* Filter Panel */}
      {showFilters && (
        <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm animate-fadeIn">
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-6 gap-4">
            {/* Categoria */}
            <div>
              <label className="block text-xs font-semibold text-slate-600 uppercase tracking-wider mb-2">
                Categoria
              </label>
              <select
                value={filters.categoria || ''}
                onChange={(e) => onFilterChange({ ...filters, categoria: e.target.value || undefined })}
                className="w-full rounded-lg border border-slate-300 bg-white px-3 py-2.5 text-sm text-slate-900 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500/20"
              >
                <option value="">Todas</option>
                {categorias.map((cat) => (
                  <option key={cat} value={cat}>{cat}</option>
                ))}
              </select>
            </div>

            {/* Fabricante */}
            <div>
              <label className="block text-xs font-semibold text-slate-600 uppercase tracking-wider mb-2">
                Fabricante
              </label>
              <select
                value={filters.fabricante || ''}
                onChange={(e) => onFilterChange({ ...filters, fabricante: e.target.value || undefined })}
                className="w-full rounded-lg border border-slate-300 bg-white px-3 py-2.5 text-sm text-slate-900 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500/20"
              >
                <option value="">Todos</option>
                {fabricantes.map((fab) => (
                  <option key={fab} value={fab}>{fab}</option>
                ))}
              </select>
            </div>

            {/* Tipo Alimentação */}
            <div>
              <label className="block text-xs font-semibold text-slate-600 uppercase tracking-wider mb-2">
                Alimentação
              </label>
              <select
                value={filters.tipoAlimentacao || ''}
                onChange={(e) => onFilterChange({ ...filters, tipoAlimentacao: e.target.value || undefined })}
                className="w-full rounded-lg border border-slate-300 bg-white px-3 py-2.5 text-sm text-slate-900 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500/20"
              >
                <option value="">Todos</option>
                {tiposAlimentacao.map((tipo) => (
                  <option key={tipo} value={tipo}>{tipo}</option>
                ))}
              </select>
            </div>

            {/* Tipo Solo */}
            <div>
              <label className="block text-xs font-semibold text-slate-600 uppercase tracking-wider mb-2">
                Tipo de Solo
              </label>
              <select
                value={filters.tipoSolo || ''}
                onChange={(e) => onFilterChange({ ...filters, tipoSolo: e.target.value || undefined })}
                className="w-full rounded-lg border border-slate-300 bg-white px-3 py-2.5 text-sm text-slate-900 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500/20"
              >
                <option value="">Todos</option>
                {tiposSolo.map((solo) => (
                  <option key={solo} value={solo}>{solo}</option>
                ))}
              </select>
            </div>

            {/* Altura Mínima */}
            <div>
              <label className="block text-xs font-semibold text-slate-600 uppercase tracking-wider mb-2">
                Altura Mín (m)
              </label>
              <input
                type="number"
                step="0.1"
                min="0"
                placeholder="0"
                value={filters.alturaMin || ''}
                onChange={(e) => onFilterChange({ ...filters, alturaMin: e.target.value ? parseFloat(e.target.value) : undefined })}
                className="w-full rounded-lg border border-slate-300 bg-white px-3 py-2.5 text-sm text-slate-900 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500/20"
              />
            </div>

            {/* Altura Máxima */}
            <div>
              <label className="block text-xs font-semibold text-slate-600 uppercase tracking-wider mb-2">
                Altura Máx (m)
              </label>
              <input
                type="number"
                step="0.1"
                min="0"
                placeholder="60"
                value={filters.alturaMax || ''}
                onChange={(e) => onFilterChange({ ...filters, alturaMax: e.target.value ? parseFloat(e.target.value) : undefined })}
                className="w-full rounded-lg border border-slate-300 bg-white px-3 py-2.5 text-sm text-slate-900 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500/20"
              />
            </div>
          </div>

          {/* Clear Filters */}
          {hasActiveFilters && (
            <div className="mt-4 pt-4 border-t border-slate-200">
              <button
                onClick={onClearFilters}
                className="inline-flex items-center gap-2 text-sm font-medium text-red-600 hover:text-red-700"
              >
                <X className="h-4 w-4" />
                Limpar todos os filtros
              </button>
            </div>
          )}
        </div>
      )}
    </div>
  );
}
