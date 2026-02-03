package com.plataformas.crud.service;

import com.plataformas.crud.model.Plataforma;
import com.plataformas.crud.repository.PlataformaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlataformaService {

    private final PlataformaRepository repository;

    public List<Plataforma> findAll() {
        return repository.findAll();
    }

    public Optional<Plataforma> findById(Long id) {
        return repository.findById(id);
    }

    public Plataforma save(Plataforma plataforma) {
        return repository.save(plataforma);
    }

    public Plataforma update(Long id, Plataforma plataforma) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setCategoria(plataforma.getCategoria());
                    existing.setTipoAlimentacao(plataforma.getTipoAlimentacao());
                    existing.setTipoSolo(plataforma.getTipoSolo());
                    existing.setDescricao(plataforma.getDescricao());
                    existing.setAlturaTrabalho(plataforma.getAlturaTrabalho());
                    existing.setModelo(plataforma.getModelo());
                    existing.setFabricante(plataforma.getFabricante());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Plataforma não encontrada com id: " + id));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Plataforma não encontrada com id: " + id);
        }
        repository.deleteById(id);
    }

    public List<Plataforma> findByCategoria(String categoria) {
        return repository.findByCategoria(categoria);
    }

    public List<Plataforma> findByFabricante(String fabricante) {
        return repository.findByFabricante(fabricante);
    }

    public List<Plataforma> findByTipoAlimentacao(String tipoAlimentacao) {
        return repository.findByTipoAlimentacao(tipoAlimentacao);
    }

    public List<Plataforma> findByTipoSolo(String tipoSolo) {
        return repository.findByTipoSolo(tipoSolo);
    }

    public List<Plataforma> findByFilters(String categoria, String fabricante, 
                                          String tipoAlimentacao, String tipoSolo,
                                          Double alturaMin, Double alturaMax) {
        return repository.findByFilters(categoria, fabricante, tipoAlimentacao, 
                                        tipoSolo, alturaMin, alturaMax);
    }

    public List<Plataforma> search(String termo) {
        return repository.searchByTermo(termo);
    }

    public List<String> getAllCategorias() {
        return repository.findAllCategorias();
    }

    public List<String> getAllFabricantes() {
        return repository.findAllFabricantes();
    }

    public List<String> getAllTiposAlimentacao() {
        return repository.findAllTiposAlimentacao();
    }

    public List<String> getAllTiposSolo() {
        return repository.findAllTiposSolo();
    }

    public long count() {
        return repository.count();
    }
}
