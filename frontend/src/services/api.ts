import axios from 'axios';
import type { Preventiva, PreventivaStats } from '../types';

const api = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

export const preventivaService = {
  getAll: async (): Promise<Preventiva[]> => {
    const response = await api.get<Preventiva[]>('/preventivas');
    return response.data;
  },

  getById: async (id: number): Promise<Preventiva> => {
    const response = await api.get<Preventiva>(`/preventivas/${id}`);
    return response.data;
  },

  create: async (preventiva: Omit<Preventiva, 'id'>): Promise<Preventiva> => {
    const response = await api.post<Preventiva>('/preventivas', preventiva);
    return response.data;
  },

  update: async (id: number, preventiva: Omit<Preventiva, 'id'>): Promise<Preventiva> => {
    const response = await api.put<Preventiva>(`/preventivas/${id}`, preventiva);
    return response.data;
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/preventivas/${id}`);
  },

  search: async (termo: string): Promise<Preventiva[]> => {
    const response = await api.get<Preventiva[]>('/preventivas/search', {
      params: { q: termo },
    });
    return response.data;
  },

  filter: async (params: {
    fabricante?: string;
    modelo?: string;
    item?: string;
    partNumber?: string;
  }): Promise<Preventiva[]> => {
    const response = await api.get<Preventiva[]>('/preventivas/filter', { params });
    return response.data;
  },

  getStats: async (): Promise<PreventivaStats> => {
    const response = await api.get<PreventivaStats>('/preventivas/stats');
    return response.data;
  },

  getFabricantes: async (): Promise<string[]> => {
    const response = await api.get<string[]>('/preventivas/fabricantes');
    return response.data;
  },

  getModelos: async (): Promise<string[]> => {
    const response = await api.get<string[]>('/preventivas/modelos');
    return response.data;
  },

  getItens: async (): Promise<string[]> => {
    const response = await api.get<string[]>('/preventivas/itens');
    return response.data;
  },
};

export default api;
