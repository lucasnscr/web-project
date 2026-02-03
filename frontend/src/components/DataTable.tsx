import { Edit2, Trash2, ChevronUp, ChevronDown, ChevronsUpDown } from 'lucide-react';
import { useState, useMemo } from 'react';
import type { Plataforma } from '../types';

interface DataTableProps {
  data: Plataforma[];
  loading: boolean;
  onEdit: (plataforma: Plataforma) => void;
  onDelete: (id: number) => void;
}

type SortField = keyof Plataforma;
type SortDirection = 'asc' | 'desc';

export function DataTable({ data, loading, onEdit, onDelete }: DataTableProps) {
  const [sortField, setSortField] = useState<SortField>('id');
  const [sortDirection, setSortDirection] = useState<SortDirection>('asc');

  const handleSort = (field: SortField) => {
    if (sortField === field) {
      setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc');
    } else {
      setSortField(field);
      setSortDirection('asc');
    }
  };

  const sortedData = useMemo(() => {
    return [...data].sort((a, b) => {
      const aValue = a[sortField];
      const bValue = b[sortField];

      if (aValue === undefined || aValue === null) return 1;
      if (bValue === undefined || bValue === null) return -1;

      if (typeof aValue === 'number' && typeof bValue === 'number') {
        return sortDirection === 'asc' ? aValue - bValue : bValue - aValue;
      }

      const aStr = String(aValue).toLowerCase();
      const bStr = String(bValue).toLowerCase();
      
      if (sortDirection === 'asc') {
        return aStr.localeCompare(bStr, 'pt-BR');
      }
      return bStr.localeCompare(aStr, 'pt-BR');
    });
  }, [data, sortField, sortDirection]);

  const SortIcon = ({ field }: { field: SortField }) => {
    if (sortField !== field) {
      return <ChevronsUpDown className="h-4 w-4 text-slate-400" />;
    }
    return sortDirection === 'asc' 
      ? <ChevronUp className="h-4 w-4 text-blue-600" />
      : <ChevronDown className="h-4 w-4 text-blue-600" />;
  };

  const getCategoryBadgeColor = (categoria: string) => {
    if (categoria.includes('Elétrica')) return 'bg-blue-100 text-blue-800';
    if (categoria.includes('Diesel')) return 'bg-amber-100 text-amber-800';
    if (categoria.includes('Híbrida')) return 'bg-green-100 text-green-800';
    return 'bg-slate-100 text-slate-800';
  };

  const getFabricanteBadgeColor = (fabricante: string) => {
    const colors: Record<string, string> = {
      'GENIE': 'bg-blue-100 text-blue-800',
      'JLG': 'bg-orange-100 text-orange-800',
      'SKYJACK': 'bg-red-100 text-red-800',
      'HAULOTTE': 'bg-purple-100 text-purple-800',
      'DINGLI': 'bg-green-100 text-green-800',
      'MANITOU': 'bg-cyan-100 text-cyan-800',
    };
    return colors[fabricante] || 'bg-slate-100 text-slate-800';
  };

  if (loading) {
    return (
      <div className="overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm">
        <div className="p-8 text-center">
          <div className="inline-flex items-center gap-3">
            <div className="h-6 w-6 animate-spin rounded-full border-2 border-blue-600 border-t-transparent"></div>
            <span className="text-slate-600">Carregando plataformas...</span>
          </div>
        </div>
      </div>
    );
  }

  if (data.length === 0) {
    return (
      <div className="overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm">
        <div className="p-12 text-center">
          <div className="mx-auto h-16 w-16 rounded-full bg-slate-100 flex items-center justify-center mb-4">
            <svg className="h-8 w-8 text-slate-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.5} d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4" />
            </svg>
          </div>
          <h3 className="text-lg font-semibold text-slate-900 mb-1">Nenhuma plataforma encontrada</h3>
          <p className="text-slate-500">Tente ajustar os filtros ou adicione uma nova plataforma.</p>
        </div>
      </div>
    );
  }

  return (
    <div className="overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm">
      <div className="overflow-x-auto">
        <table className="w-full">
          <thead>
            <tr className="border-b border-slate-200 bg-slate-50/50">
              <th className="px-4 py-4 text-left">
                <button
                  onClick={() => handleSort('id')}
                  className="inline-flex items-center gap-1 text-xs font-semibold uppercase tracking-wider text-slate-600 hover:text-slate-900"
                >
                  ID <SortIcon field="id" />
                </button>
              </th>
              <th className="px-4 py-4 text-left">
                <button
                  onClick={() => handleSort('categoria')}
                  className="inline-flex items-center gap-1 text-xs font-semibold uppercase tracking-wider text-slate-600 hover:text-slate-900"
                >
                  Categoria <SortIcon field="categoria" />
                </button>
              </th>
              <th className="px-4 py-4 text-left">
                <button
                  onClick={() => handleSort('descricao')}
                  className="inline-flex items-center gap-1 text-xs font-semibold uppercase tracking-wider text-slate-600 hover:text-slate-900"
                >
                  Descrição <SortIcon field="descricao" />
                </button>
              </th>
              <th className="px-4 py-4 text-left">
                <button
                  onClick={() => handleSort('alturaTrabalho')}
                  className="inline-flex items-center gap-1 text-xs font-semibold uppercase tracking-wider text-slate-600 hover:text-slate-900"
                >
                  Altura (m) <SortIcon field="alturaTrabalho" />
                </button>
              </th>
              <th className="px-4 py-4 text-left">
                <button
                  onClick={() => handleSort('modelo')}
                  className="inline-flex items-center gap-1 text-xs font-semibold uppercase tracking-wider text-slate-600 hover:text-slate-900"
                >
                  Modelo <SortIcon field="modelo" />
                </button>
              </th>
              <th className="px-4 py-4 text-left">
                <button
                  onClick={() => handleSort('fabricante')}
                  className="inline-flex items-center gap-1 text-xs font-semibold uppercase tracking-wider text-slate-600 hover:text-slate-900"
                >
                  Fabricante <SortIcon field="fabricante" />
                </button>
              </th>
              <th className="px-4 py-4 text-right">
                <span className="text-xs font-semibold uppercase tracking-wider text-slate-600">
                  Ações
                </span>
              </th>
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-100">
            {sortedData.map((plataforma) => (
              <tr
                key={plataforma.id}
                className="group transition-colors hover:bg-slate-50/50"
              >
                <td className="px-4 py-4">
                  <span className="text-sm font-medium text-slate-500">#{plataforma.id}</span>
                </td>
                <td className="px-4 py-4">
                  <span className={`inline-flex items-center rounded-full px-2.5 py-1 text-xs font-medium ${getCategoryBadgeColor(plataforma.categoria)}`}>
                    {plataforma.categoria}
                  </span>
                </td>
                <td className="px-4 py-4">
                  <div className="max-w-xs">
                    <p className="text-sm font-medium text-slate-900 truncate">{plataforma.descricao}</p>
                    <p className="text-xs text-slate-500 mt-0.5">
                      {plataforma.tipoAlimentacao} • {plataforma.tipoSolo}
                    </p>
                  </div>
                </td>
                <td className="px-4 py-4">
                  <span className="inline-flex items-center rounded-lg bg-slate-100 px-2.5 py-1 text-sm font-semibold text-slate-700">
                    {plataforma.alturaTrabalho.toFixed(2)} m
                  </span>
                </td>
                <td className="px-4 py-4">
                  <span className="text-sm font-medium text-slate-900">{plataforma.modelo}</span>
                </td>
                <td className="px-4 py-4">
                  <span className={`inline-flex items-center rounded-full px-2.5 py-1 text-xs font-medium ${getFabricanteBadgeColor(plataforma.fabricante)}`}>
                    {plataforma.fabricante}
                  </span>
                </td>
                <td className="px-4 py-4">
                  <div className="flex items-center justify-end gap-2 opacity-0 group-hover:opacity-100 transition-opacity">
                    <button
                      onClick={() => onEdit(plataforma)}
                      className="inline-flex h-8 w-8 items-center justify-center rounded-lg text-slate-500 hover:bg-blue-50 hover:text-blue-600 transition-colors"
                      title="Editar"
                    >
                      <Edit2 className="h-4 w-4" />
                    </button>
                    <button
                      onClick={() => plataforma.id && onDelete(plataforma.id)}
                      className="inline-flex h-8 w-8 items-center justify-center rounded-lg text-slate-500 hover:bg-red-50 hover:text-red-600 transition-colors"
                      title="Excluir"
                    >
                      <Trash2 className="h-4 w-4" />
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      
      {/* Footer */}
      <div className="border-t border-slate-200 bg-slate-50/50 px-4 py-3">
        <p className="text-sm text-slate-600">
          Mostrando <span className="font-semibold text-slate-900">{sortedData.length}</span> plataformas
        </p>
      </div>
    </div>
  );
}
