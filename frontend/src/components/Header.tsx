import { Layers, Menu, X } from 'lucide-react';
import { useState } from 'react';

export function Header() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  return (
    <header className="sticky top-0 z-40 border-b border-slate-200 bg-white/80 backdrop-blur-lg">
      <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
        <div className="flex h-16 items-center justify-between">
          {/* Logo */}
          <div className="flex items-center gap-3">
            <div className="flex h-10 w-10 items-center justify-center rounded-xl bg-gradient-to-br from-blue-600 to-indigo-600 shadow-lg shadow-blue-500/25">
              <Layers className="h-5 w-5 text-white" />
            </div>
            <div>
              <h1 className="text-lg font-bold text-slate-900">PlataformasDB</h1>
              <p className="text-xs text-slate-500">Sistema de Gestão</p>
            </div>
          </div>

          {/* Desktop Navigation */}
          <nav className="hidden md:flex items-center gap-6">
            <a href="#" className="text-sm font-medium text-blue-600">Dashboard</a>
            <a href="#" className="text-sm font-medium text-slate-600 hover:text-slate-900 transition-colors">Plataformas</a>
            <a href="#" className="text-sm font-medium text-slate-600 hover:text-slate-900 transition-colors">Relatórios</a>
          </nav>

          {/* User Menu */}
          <div className="hidden md:flex items-center gap-4">
            <div className="flex items-center gap-3">
              <div className="h-9 w-9 rounded-full bg-gradient-to-br from-blue-500 to-indigo-500 flex items-center justify-center text-white font-medium text-sm">
                AD
              </div>
              <div className="text-right">
                <p className="text-sm font-medium text-slate-900">Admin</p>
                <p className="text-xs text-slate-500">Administrador</p>
              </div>
            </div>
          </div>

          {/* Mobile menu button */}
          <button
            className="md:hidden p-2 rounded-lg text-slate-600 hover:bg-slate-100"
            onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
          >
            {mobileMenuOpen ? <X className="h-6 w-6" /> : <Menu className="h-6 w-6" />}
          </button>
        </div>
      </div>

      {/* Mobile menu */}
      {mobileMenuOpen && (
        <div className="md:hidden border-t border-slate-200 bg-white animate-fadeIn">
          <div className="px-4 py-4 space-y-3">
            <a href="#" className="block px-3 py-2 rounded-lg text-sm font-medium text-blue-600 bg-blue-50">Dashboard</a>
            <a href="#" className="block px-3 py-2 rounded-lg text-sm font-medium text-slate-600 hover:bg-slate-50">Plataformas</a>
            <a href="#" className="block px-3 py-2 rounded-lg text-sm font-medium text-slate-600 hover:bg-slate-50">Relatórios</a>
          </div>
        </div>
      )}
    </header>
  );
}
