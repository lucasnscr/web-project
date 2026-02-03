export interface Preventiva {
  id: number;
  modelo: string;
  fabricante: string;
  itemDescricao: string;
  partNumber: string | null;
  codSap: string | null;
  qtd250h: string | null;
  qtd500h: string | null;
  qtd750h: string | null;
  qtd1000h: string | null;
  horasMo250h: string | null;
  horasMo500h: string | null;
  horasMo750h: string | null;
  horasMo1000h: string | null;
}

export interface PreventivaStats {
  totalRegistros: number;
  totalModelos: number;
  totalFabricantes: number;
  totalItens: number;
}

export interface FilterState {
  search: string;
  fabricante: string;
  modelo: string;
  item: string;
}

export type SortDirection = 'asc' | 'desc';

export interface SortState {
  field: keyof Preventiva | '';
  direction: SortDirection;
}
