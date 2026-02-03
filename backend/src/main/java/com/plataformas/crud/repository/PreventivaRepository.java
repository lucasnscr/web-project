package com.plataformas.crud.repository;

import com.plataformas.crud.model.Preventiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreventivaRepository extends JpaRepository<Preventiva, Long> {
    
    List<Preventiva> findByFabricanteIgnoreCase(String fabricante);
    
    List<Preventiva> findByModeloContainingIgnoreCase(String modelo);
    
    List<Preventiva> findByItemDescricaoContainingIgnoreCase(String itemDescricao);
    
    @Query("SELECT DISTINCT p.fabricante FROM Preventiva p ORDER BY p.fabricante")
    List<String> findDistinctFabricantes();
    
    @Query("SELECT DISTINCT p.modelo FROM Preventiva p ORDER BY p.modelo")
    List<String> findDistinctModelos();
    
    @Query("SELECT DISTINCT p.itemDescricao FROM Preventiva p ORDER BY p.itemDescricao")
    List<String> findDistinctItens();
    
    @Query("SELECT p FROM Preventiva p WHERE " +
           "(:fabricante IS NULL OR p.fabricante = :fabricante) AND " +
           "(:modelo IS NULL OR p.modelo LIKE %:modelo%) AND " +
           "(:item IS NULL OR p.itemDescricao LIKE %:item%) AND " +
           "(:partNumber IS NULL OR p.partNumber LIKE %:partNumber%)")
    List<Preventiva> findByFilters(
            @Param("fabricante") String fabricante,
            @Param("modelo") String modelo,
            @Param("item") String item,
            @Param("partNumber") String partNumber
    );
    
    @Query("SELECT p FROM Preventiva p WHERE " +
           "LOWER(p.modelo) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(p.fabricante) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(p.itemDescricao) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(p.partNumber) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Preventiva> searchByTermo(@Param("termo") String termo);
    
    @Query("SELECT COUNT(DISTINCT p.modelo) FROM Preventiva p")
    long countDistinctModelos();
    
    @Query("SELECT COUNT(DISTINCT p.fabricante) FROM Preventiva p")
    long countDistinctFabricantes();
    
    @Query("SELECT COUNT(DISTINCT p.itemDescricao) FROM Preventiva p")
    long countDistinctItens();
}
