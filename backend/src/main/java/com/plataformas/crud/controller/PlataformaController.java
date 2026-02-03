package com.plataformas.crud.controller;

import com.plataformas.crud.model.Plataforma;
import com.plataformas.crud.service.PlataformaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plataformas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Plataformas", description = "API para gerenciamento de plataformas elevatórias")
public class PlataformaController {

    private final PlataformaService service;

    @GetMapping
    @Operation(summary = "Listar todas as plataformas")
    public ResponseEntity<List<Plataforma>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar plataforma por ID")
    public ResponseEntity<Plataforma> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar nova plataforma")
    public ResponseEntity<Plataforma> create(@Valid @RequestBody Plataforma plataforma) {
        Plataforma saved = service.save(plataforma);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar plataforma existente")
    public ResponseEntity<Plataforma> update(@PathVariable Long id, 
                                              @Valid @RequestBody Plataforma plataforma) {
        try {
            Plataforma updated = service.update(id, plataforma);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir plataforma")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar plataformas por termo")
    public ResponseEntity<List<Plataforma>> search(@RequestParam String termo) {
        return ResponseEntity.ok(service.search(termo));
    }

    @GetMapping("/filter")
    @Operation(summary = "Filtrar plataformas por múltiplos critérios")
    public ResponseEntity<List<Plataforma>> filter(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String fabricante,
            @RequestParam(required = false) String tipoAlimentacao,
            @RequestParam(required = false) String tipoSolo,
            @RequestParam(required = false) Double alturaMin,
            @RequestParam(required = false) Double alturaMax) {
        return ResponseEntity.ok(service.findByFilters(categoria, fabricante, 
                tipoAlimentacao, tipoSolo, alturaMin, alturaMax));
    }

    @GetMapping("/categorias")
    @Operation(summary = "Listar todas as categorias")
    public ResponseEntity<List<String>> getCategorias() {
        return ResponseEntity.ok(service.getAllCategorias());
    }

    @GetMapping("/fabricantes")
    @Operation(summary = "Listar todos os fabricantes")
    public ResponseEntity<List<String>> getFabricantes() {
        return ResponseEntity.ok(service.getAllFabricantes());
    }

    @GetMapping("/tipos-alimentacao")
    @Operation(summary = "Listar todos os tipos de alimentação")
    public ResponseEntity<List<String>> getTiposAlimentacao() {
        return ResponseEntity.ok(service.getAllTiposAlimentacao());
    }

    @GetMapping("/tipos-solo")
    @Operation(summary = "Listar todos os tipos de solo")
    public ResponseEntity<List<String>> getTiposSolo() {
        return ResponseEntity.ok(service.getAllTiposSolo());
    }

    @GetMapping("/stats")
    @Operation(summary = "Obter estatísticas das plataformas")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", service.count());
        stats.put("categorias", service.getAllCategorias().size());
        stats.put("fabricantes", service.getAllFabricantes().size());
        return ResponseEntity.ok(stats);
    }
}
