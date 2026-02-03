import { Database, Factory, Layers, TrendingUp } from 'lucide-react';
import type { PlataformaStats } from '../types';

interface StatsCardsProps {
  stats: PlataformaStats | null;
  loading: boolean;
}

export function StatsCards({ stats, loading }: StatsCardsProps) {
  const cards = [
    {
      title: 'Total de Plataformas',
      value: stats?.total || 0,
      icon: Database,
      color: 'from-blue-500 to-blue-600',
      bgColor: 'bg-blue-50',
      textColor: 'text-blue-600',
    },
    {
      title: 'Categorias',
      value: stats?.categorias || 0,
      icon: Layers,
      color: 'from-purple-500 to-purple-600',
      bgColor: 'bg-purple-50',
      textColor: 'text-purple-600',
    },
    {
      title: 'Fabricantes',
      value: stats?.fabricantes || 0,
      icon: Factory,
      color: 'from-emerald-500 to-emerald-600',
      bgColor: 'bg-emerald-50',
      textColor: 'text-emerald-600',
    },
    {
      title: 'Crescimento',
      value: '+12%',
      icon: TrendingUp,
      color: 'from-orange-500 to-orange-600',
      bgColor: 'bg-orange-50',
      textColor: 'text-orange-600',
      isPercentage: true,
    },
  ];

  if (loading) {
    return (
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 lg:gap-6">
        {[...Array(4)].map((_, i) => (
          <div key={i} className="rounded-2xl border border-slate-200 bg-white p-6 animate-pulse">
            <div className="h-12 w-12 rounded-xl bg-slate-200 mb-4"></div>
            <div className="h-4 w-24 bg-slate-200 rounded mb-2"></div>
            <div className="h-8 w-16 bg-slate-200 rounded"></div>
          </div>
        ))}
      </div>
    );
  }

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 lg:gap-6">
      {cards.map((card, index) => (
        <div
          key={index}
          className="group relative overflow-hidden rounded-2xl border border-slate-200 bg-white p-6 shadow-sm transition-all duration-300 hover:shadow-lg hover:border-slate-300"
        >
          {/* Background gradient on hover */}
          <div className={`absolute inset-0 bg-gradient-to-br ${card.color} opacity-0 group-hover:opacity-5 transition-opacity duration-300`}></div>
          
          <div className="relative">
            <div className={`inline-flex h-12 w-12 items-center justify-center rounded-xl ${card.bgColor} mb-4`}>
              <card.icon className={`h-6 w-6 ${card.textColor}`} />
            </div>
            
            <p className="text-sm font-medium text-slate-500 mb-1">{card.title}</p>
            <p className="text-3xl font-bold text-slate-900">
              {card.isPercentage ? card.value : card.value.toLocaleString('pt-BR')}
            </p>
          </div>
        </div>
      ))}
    </div>
  );
}
