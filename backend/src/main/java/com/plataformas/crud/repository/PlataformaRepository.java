package com.plataformas.crud.repository;

import com.plataformas.crud.model.Plataforma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlataformaRepository extends JpaRepository<Plataforma, Long> {

    List<Plataforma> findByCategoria(String categoria);

    List<Plataforma> findByFabricante(String fabricante);

    List<Plataforma> findByTipoAlimentacao(String tipoAlimentacao);

    List<Plataforma> findByTipoSolo(String tipoSolo);

    List<Plataforma> findByAlturaTrabalhoGreaterThanEqual(Double altura);

    List<Plataforma> findByAlturaTrabalhoLessThanEqual(Double altura);

    List<Plataforma> findByAlturaTrabalhoIsBetween(Double alturaMin, Double alturaMax);

    @Query("SELECT DISTINCT p.categoria FROM Plataforma p ORDER BY p.categoria")
    List<String> findAllCategorias();

    @Query("SELECT DISTINCT p.fabricante FROM Plataforma p ORDER BY p.fabricante")
    List<String> findAllFabricantes();

    @Query("SELECT DISTINCT p.tipoAlimentacao FROM Plataforma p ORDER BY p.tipoAlimentacao")
    List<String> findAllTiposAlimentacao();

    @Query("SELECT DISTINCT p.tipoSolo FROM Plataforma p ORDER BY p.tipoSolo")
    List<String> findAllTiposSolo();

    @Query("SELECT p FROM Plataforma p WHERE " +
           "(:categoria IS NULL OR p.categoria = :categoria) AND " +
           "(:fabricante IS NULL OR p.fabricante = :fabricante) AND " +
           "(:tipoAlimentacao IS NULL OR p.tipoAlimentacao = :tipoAlimentacao) AND " +
           "(:tipoSolo IS NULL OR p.tipoSolo = :tipoSolo) AND " +
           "(:alturaMin IS NULL OR p.alturaTrabalho >= :alturaMin) AND " +
           "(:alturaMax IS NULL OR p.alturaTrabalho <= :alturaMax)")
    List<Plataforma> findByFilters(
            @Param("categoria") String categoria,
            @Param("fabricante") String fabricante,
            @Param("tipoAlimentacao") String tipoAlimentacao,
            @Param("tipoSolo") String tipoSolo,
            @Param("alturaMin") Double alturaMin,
            @Param("alturaMax") Double alturaMax
    );

    @Query("SELECT p FROM Plataforma p WHERE " +
           "LOWER(p.descricao) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(p.modelo) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(p.fabricante) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(p.categoria) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Plataforma> searchByTermo(@Param("termo") String termo);
}
