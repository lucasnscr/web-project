import { X, Save, Loader2 } from 'lucide-react';
import { useState, useEffect } from 'react';
import type { Plataforma } from '../types';
import { plataformaService } from '../services/api';

interface PlataformaModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSave: (plataforma: Plataforma) => void;
  plataforma?: Plataforma | null;
}

const initialFormData: Plataforma = {
  categoria: '',
  tipoAlimentacao: '',
  tipoSolo: '',
  descricao: '',
  alturaTrabalho: 0,
  modelo: '',
  fabricante: '',
};

export function PlataformaModal({ isOpen, onClose, onSave, plataforma }: PlataformaModalProps) {
  const [formData, setFormData] = useState<Plataforma>(initialFormData);
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [categorias, setCategorias] = useState<string[]>([]);
  const [fabricantes, setFabricantes] = useState<string[]>([]);
  const [tiposAlimentacao, setTiposAlimentacao] = useState<string[]>([]);
  const [tiposSolo, setTiposSolo] = useState<string[]>([]);

  const isEditing = !!plataforma?.id;

  useEffect(() => {
    if (plataforma) {
      setFormData(plataforma);
    } else {
      setFormData(initialFormData);
    }
    setErrors({});
  }, [plataforma, isOpen]);

  useEffect(() => {
    const loadOptions = async () => {
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
        console.error('Erro ao carregar opções:', error);
      }
    };
    if (isOpen) {
      loadOptions();
    }
  }, [isOpen]);

  const validate = (): boolean => {
    const newErrors: Record<string, string> = {};

    if (!formData.categoria.trim()) newErrors.categoria = 'Categoria é obrigatória';
    if (!formData.tipoAlimentacao.trim()) newErrors.tipoAlimentacao = 'Tipo de alimentação é obrigatório';
    if (!formData.tipoSolo.trim()) newErrors.tipoSolo = 'Tipo de solo é obrigatório';
    if (!formData.descricao.trim()) newErrors.descricao = 'Descrição é obrigatória';
    if (!formData.alturaTrabalho || formData.alturaTrabalho <= 0) newErrors.alturaTrabalho = 'Altura deve ser maior que zero';
    if (!formData.modelo.trim()) newErrors.modelo = 'Modelo é obrigatório';
    if (!formData.fabricante.trim()) newErrors.fabricante = 'Fabricante é obrigatório';

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!validate()) return;

    setLoading(true);
    try {
      await onSave(formData);
      onClose();
    } catch (error) {
      console.error('Erro ao salvar:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (field: keyof Plataforma, value: string | number) => {
    setFormData(prev => ({ ...prev, [field]: value }));
    if (errors[field]) {
      setErrors(prev => ({ ...prev, [field]: '' }));
    }
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/50 backdrop-blur-sm animate-fadeIn">
      <div className="w-full max-w-2xl rounded-2xl bg-white shadow-2xl animate-slideIn">
        {/* Header */}
        <div className="flex items-center justify-between border-b border-slate-200 px-6 py-4">
          <div>
            <h2 className="text-xl font-bold text-slate-900">
              {isEditing ? 'Editar Plataforma' : 'Nova Plataforma'}
            </h2>
            <p className="text-sm text-slate-500 mt-0.5">
              {isEditing ? 'Atualize os dados da plataforma' : 'Preencha os dados para cadastrar'}
            </p>
          </div>
          <button
            onClick={onClose}
            className="inline-flex h-10 w-10 items-center justify-center rounded-lg text-slate-400 hover:bg-slate-100 hover:text-slate-600 transition-colors"
          >
            <X className="h-5 w-5" />
          </button>
        </div>

        {/* Form */}
        <form onSubmit={handleSubmit} className="p-6">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
            {/* Categoria */}
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1.5">
                Categoria <span className="text-red-500">*</span>
              </label>
              <select
                value={formData.categoria}
                onChange={(e) => handleChange('categoria', e.target.value)}
                className={`w-full rounded-lg border px-4 py-2.5 text-sm transition-all focus:outline-none focus:ring-2 ${
                  errors.categoria
                    ? 'border-red-300 focus:border-red-500 focus:ring-red-500/20'
                    : 'border-slate-300 focus:border-blue-500 focus:ring-blue-500/20'
                }`}
              >
                <option value="">Selecione...</option>
                {categorias.map((cat) => (
                  <option key={cat} value={cat}>{cat}</option>
                ))}
                <option value="__custom__">Outra categoria...</option>
              </select>
              {formData.categoria === '__custom__' && (
                <input
                  type="text"
                  placeholder="Digite a nova categoria"
                  onChange={(e) => handleChange('categoria', e.target.value)}
                  className="mt-2 w-full rounded-lg border border-slate-300 px-4 py-2.5 text-sm focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500/20"
                />
              )}
              {errors.categoria && <p className="mt-1 text-xs text-red-600">{errors.categoria}</p>}
            </div>

            {/* Fabricante */}
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1.5">
                Fabricante <span className="text-red-500">*</span>
              </label>
              <select
                value={formData.fabricante}
                onChange={(e) => handleChange('fabricante', e.target.value)}
                className={`w-full rounded-lg border px-4 py-2.5 text-sm transition-all focus:outline-none focus:ring-2 ${
                  errors.fabricante
                    ? 'border-red-300 focus:border-red-500 focus:ring-red-500/20'
                    : 'border-slate-300 focus:border-blue-500 focus:ring-blue-500/20'
                }`}
              >
                <option value="">Selecione...</option>
                {fabricantes.map((fab) => (
                  <option key={fab} value={fab}>{fab}</option>
                ))}
                <option value="__custom__">Outro fabricante...</option>
              </select>
              {formData.fabricante === '__custom__' && (
                <input
                  type="text"
                  placeholder="Digite o novo fabricante"
                  onChange={(e) => handleChange('fabricante', e.target.value)}
                  className="mt-2 w-full rounded-lg border border-slate-300 px-4 py-2.5 text-sm focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500/20"
                />
              )}
              {errors.fabricante && <p className="mt-1 text-xs text-red-600">{errors.fabricante}</p>}
            </div>

            {/* Tipo Alimentação */}
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1.5">
                Tipo de Alimentação <span className="text-red-500">*</span>
              </label>
              <select
                value={formData.tipoAlimentacao}
                onChange={(e) => handleChange('tipoAlimentacao', e.target.value)}
                className={`w-full rounded-lg border px-4 py-2.5 text-sm transition-all focus:outline-none focus:ring-2 ${
                  errors.tipoAlimentacao
                    ? 'border-red-300 focus:border-red-500 focus:ring-red-500/20'
                    : 'border-slate-300 focus:border-blue-500 focus:ring-blue-500/20'
                }`}
              >
                <option value="">Selecione...</option>
                {tiposAlimentacao.map((tipo) => (
                  <option key={tipo} value={tipo}>{tipo}</option>
                ))}
              </select>
              {errors.tipoAlimentacao && <p className="mt-1 text-xs text-red-600">{errors.tipoAlimentacao}</p>}
            </div>

            {/* Tipo Solo */}
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1.5">
                Tipo de Solo <span className="text-red-500">*</span>
              </label>
              <select
                value={formData.tipoSolo}
                onChange={(e) => handleChange('tipoSolo', e.target.value)}
                className={`w-full rounded-lg border px-4 py-2.5 text-sm transition-all focus:outline-none focus:ring-2 ${
                  errors.tipoSolo
                    ? 'border-red-300 focus:border-red-500 focus:ring-red-500/20'
                    : 'border-slate-300 focus:border-blue-500 focus:ring-blue-500/20'
                }`}
              >
                <option value="">Selecione...</option>
                {tiposSolo.map((solo) => (
                  <option key={solo} value={solo}>{solo}</option>
                ))}
              </select>
              {errors.tipoSolo && <p className="mt-1 text-xs text-red-600">{errors.tipoSolo}</p>}
            </div>

            {/* Modelo */}
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1.5">
                Modelo <span className="text-red-500">*</span>
              </label>
              <input
                type="text"
                value={formData.modelo}
                onChange={(e) => handleChange('modelo', e.target.value)}
                placeholder="Ex: GS-1330"
                className={`w-full rounded-lg border px-4 py-2.5 text-sm transition-all focus:outline-none focus:ring-2 ${
                  errors.modelo
                    ? 'border-red-300 focus:border-red-500 focus:ring-red-500/20'
                    : 'border-slate-300 focus:border-blue-500 focus:ring-blue-500/20'
                }`}
              />
              {errors.modelo && <p className="mt-1 text-xs text-red-600">{errors.modelo}</p>}
            </div>

            {/* Altura de Trabalho */}
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1.5">
                Altura de Trabalho (m) <span className="text-red-500">*</span>
              </label>
              <input
                type="number"
                step="0.01"
                min="0"
                value={formData.alturaTrabalho || ''}
                onChange={(e) => handleChange('alturaTrabalho', parseFloat(e.target.value) || 0)}
                placeholder="Ex: 10.5"
                className={`w-full rounded-lg border px-4 py-2.5 text-sm transition-all focus:outline-none focus:ring-2 ${
                  errors.alturaTrabalho
                    ? 'border-red-300 focus:border-red-500 focus:ring-red-500/20'
                    : 'border-slate-300 focus:border-blue-500 focus:ring-blue-500/20'
                }`}
              />
              {errors.alturaTrabalho && <p className="mt-1 text-xs text-red-600">{errors.alturaTrabalho}</p>}
            </div>

            {/* Descrição - Full Width */}
            <div className="md:col-span-2">
              <label className="block text-sm font-medium text-slate-700 mb-1.5">
                Descrição <span className="text-red-500">*</span>
              </label>
              <input
                type="text"
                value={formData.descricao}
                onChange={(e) => handleChange('descricao', e.target.value)}
                placeholder="Ex: Plataforma Tesoura Elétrica de 19 pés"
                className={`w-full rounded-lg border px-4 py-2.5 text-sm transition-all focus:outline-none focus:ring-2 ${
                  errors.descricao
                    ? 'border-red-300 focus:border-red-500 focus:ring-red-500/20'
                    : 'border-slate-300 focus:border-blue-500 focus:ring-blue-500/20'
                }`}
              />
              {errors.descricao && <p className="mt-1 text-xs text-red-600">{errors.descricao}</p>}
            </div>
          </div>

          {/* Actions */}
          <div className="flex items-center justify-end gap-3 mt-8 pt-6 border-t border-slate-200">
            <button
              type="button"
              onClick={onClose}
              className="inline-flex items-center justify-center gap-2 rounded-lg border border-slate-300 bg-white px-5 py-2.5 text-sm font-medium text-slate-700 transition-all hover:bg-slate-50 focus:outline-none focus:ring-2 focus:ring-slate-500 focus:ring-offset-2"
            >
              Cancelar
            </button>
            <button
              type="submit"
              disabled={loading}
              className="inline-flex items-center justify-center gap-2 rounded-lg bg-blue-600 px-5 py-2.5 text-sm font-medium text-white transition-all hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {loading ? (
                <>
                  <Loader2 className="h-4 w-4 animate-spin" />
                  Salvando...
                </>
              ) : (
                <>
                  <Save className="h-4 w-4" />
                  {isEditing ? 'Atualizar' : 'Cadastrar'}
                </>
              )}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
