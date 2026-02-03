import { CheckCircle, XCircle, AlertCircle, Info, X } from 'lucide-react';
import { useEffect } from 'react';

export type ToastType = 'success' | 'error' | 'warning' | 'info';

interface ToastProps {
  message: string;
  type: ToastType;
  isVisible: boolean;
  onClose: () => void;
  duration?: number;
}

export function Toast({ message, type, isVisible, onClose, duration = 4000 }: ToastProps) {
  useEffect(() => {
    if (isVisible && duration > 0) {
      const timer = setTimeout(onClose, duration);
      return () => clearTimeout(timer);
    }
  }, [isVisible, duration, onClose]);

  if (!isVisible) return null;

  const styles = {
    success: {
      bg: 'bg-emerald-50 border-emerald-200',
      icon: 'text-emerald-500',
      text: 'text-emerald-800',
      Icon: CheckCircle,
    },
    error: {
      bg: 'bg-red-50 border-red-200',
      icon: 'text-red-500',
      text: 'text-red-800',
      Icon: XCircle,
    },
    warning: {
      bg: 'bg-amber-50 border-amber-200',
      icon: 'text-amber-500',
      text: 'text-amber-800',
      Icon: AlertCircle,
    },
    info: {
      bg: 'bg-blue-50 border-blue-200',
      icon: 'text-blue-500',
      text: 'text-blue-800',
      Icon: Info,
    },
  };

  const { bg, icon, text, Icon } = styles[type];

  return (
    <div className="fixed bottom-6 right-6 z-50 animate-slideIn">
      <div className={`flex items-center gap-3 rounded-xl border ${bg} px-4 py-3 shadow-lg`}>
        <Icon className={`h-5 w-5 flex-shrink-0 ${icon}`} />
        <p className={`text-sm font-medium ${text}`}>{message}</p>
        <button
          onClick={onClose}
          className={`ml-2 inline-flex h-6 w-6 items-center justify-center rounded-lg ${text} hover:bg-white/50 transition-colors`}
        >
          <X className="h-4 w-4" />
        </button>
      </div>
    </div>
  );
}
