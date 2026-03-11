package com.plataformas.crud.controller;

import com.plataformas.crud.model.Preventiva;
import com.plataformas.crud.service.PreventivaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/preventivas")
@CrossOrigin(origins = "*")
public class PreventivaController {

    private final PreventivaService preventivaService;

    public PreventivaController(PreventivaService preventivaService) {
        this.preventivaService = preventivaService;
    }

    @GetMapping
    public ResponseEntity<List<Preventiva>> getAll() {
        return ResponseEntity.ok(preventivaService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Preventiva> getById(@PathVariable Long id) {
        return preventivaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Preventiva> create(@RequestBody Preventiva preventiva) {
        Preventiva saved = preventivaService.save(preventiva);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Preventiva> update(@PathVariable Long id, @RequestBody Preventiva preventiva) {
        try {
            Preventiva updated = preventivaService.update(id, preventiva);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        preventivaService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Preventiva>> search(@RequestParam String q) {
        return ResponseEntity.ok(preventivaService.search(q));
    }
    
    @GetMapping("/filter")
    public ResponseEntity<List<Preventiva>> filter(
            @RequestParam(required = false) String fabricante,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) String item,
            @RequestParam(required = false) String partNumber) {
        return ResponseEntity.ok(preventivaService.filter(fabricante, modelo, item, partNumber));
    }
    
    @GetMapping("/fabricantes")
    public ResponseEntity<List<String>> getFabricantes() {
        return ResponseEntity.ok(preventivaService.getFabricantes());
    }
    
    @GetMapping("/modelos")
    public ResponseEntity<List<String>> getModelos() {
        return ResponseEntity.ok(preventivaService.getModelos());
    }
    
    @GetMapping("/itens")
    public ResponseEntity<List<String>> getItens() {
        return ResponseEntity.ok(preventivaService.getItens());
    }
    
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(preventivaService.getStats());
    }
}
