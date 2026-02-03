package com.plataformas.crud.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "preventivas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Preventiva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String modelo;
    
    @Column(nullable = false)
    private String fabricante;
    
    @Column(name = "item_descricao", nullable = false)
    private String itemDescricao;
    
    @Column(name = "part_number")
    private String partNumber;
    
    @Column(name = "cod_sap")
    private String codSap;
    
    @Column(name = "qtd_250h")
    private String qtd250h;
    
    @Column(name = "qtd_500h")
    private String qtd500h;
    
    @Column(name = "qtd_750h")
    private String qtd750h;
    
    @Column(name = "qtd_1000h")
    private String qtd1000h;
    
    @Column(name = "horas_mo_250h")
    private String horasMo250h;
    
    @Column(name = "horas_mo_500h")
    private String horasMo500h;
    
    @Column(name = "horas_mo_750h")
    private String horasMo750h;
    
    @Column(name = "horas_mo_1000h")
    private String horasMo1000h;
}
