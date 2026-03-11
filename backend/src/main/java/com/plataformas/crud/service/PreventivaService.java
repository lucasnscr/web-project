package com.plataformas.crud.service;

import com.plataformas.crud.model.Preventiva;
import com.plataformas.crud.repository.PreventivaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class PreventivaService {

    private final PreventivaRepository preventivaRepository;

    public PreventivaService(PreventivaRepository preventivaRepository) {
        this.preventivaRepository = preventivaRepository;
    }

    public List<Preventiva> findAll() {
        return preventivaRepository.findAll();
    }
    
    public Optional<Preventiva> findById(Long id) {
        return preventivaRepository.findById(id);
    }
    
    public Preventiva save(Preventiva preventiva) {
        return preventivaRepository.save(preventiva);
    }
    
    public Preventiva update(Long id, Preventiva preventiva) {
        return preventivaRepository.findById(id)
                .map(existing -> {
                    existing.setModelo(preventiva.getModelo());
                    existing.setFabricante(preventiva.getFabricante());
                    existing.setItemDescricao(preventiva.getItemDescricao());
                    existing.setPartNumber(preventiva.getPartNumber());
                    existing.setCodSap(preventiva.getCodSap());
                    existing.setQtd250h(preventiva.getQtd250h());
                    existing.setQtd500h(preventiva.getQtd500h());
                    existing.setQtd750h(preventiva.getQtd750h());
                    existing.setQtd1000h(preventiva.getQtd1000h());
                    existing.setHorasMo250h(preventiva.getHorasMo250h());
                    existing.setHorasMo500h(preventiva.getHorasMo500h());
                    existing.setHorasMo750h(preventiva.getHorasMo750h());
                    existing.setHorasMo1000h(preventiva.getHorasMo1000h());
                    return preventivaRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Preventiva não encontrada com id: " + id));
    }
    
    public void delete(Long id) {
        preventivaRepository.deleteById(id);
    }
    
    public List<Preventiva> findByFabricante(String fabricante) {
        return preventivaRepository.findByFabricanteIgnoreCase(fabricante);
    }
    
    public List<Preventiva> findByModelo(String modelo) {
        return preventivaRepository.findByModeloContainingIgnoreCase(modelo);
    }
    
    public List<Preventiva> search(String termo) {
        return preventivaRepository.searchByTermo(termo);
    }
    
    public List<Preventiva> filter(String fabricante, String modelo, String item, String partNumber) {
        return preventivaRepository.findByFilters(
                fabricante != null && !fabricante.isEmpty() ? fabricante : null,
                modelo != null && !modelo.isEmpty() ? modelo : null,
                item != null && !item.isEmpty() ? item : null,
                partNumber != null && !partNumber.isEmpty() ? partNumber : null
        );
    }
    
    public List<String> getFabricantes() {
        return preventivaRepository.findDistinctFabricantes();
    }
    
    public List<String> getModelos() {
        return preventivaRepository.findDistinctModelos();
    }
    
    public List<String> getItens() {
        return preventivaRepository.findDistinctItens();
    }
    
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRegistros", preventivaRepository.count());
        stats.put("totalModelos", preventivaRepository.countDistinctModelos());
        stats.put("totalFabricantes", preventivaRepository.countDistinctFabricantes());
        stats.put("totalItens", preventivaRepository.countDistinctItens());
        return stats;
    }
}
