package com.plataformas.crud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "plataformas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Categoria é obrigatória")
    @Column(nullable = false)
    private String categoria;

    @NotBlank(message = "Tipo de alimentação é obrigatório")
    @Column(name = "tipo_alimentacao", nullable = false)
    private String tipoAlimentacao;

    @NotBlank(message = "Tipo de solo é obrigatório")
    @Column(name = "tipo_solo", nullable = false)
    private String tipoSolo;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(nullable = false, length = 500)
    private String descricao;

    @NotNull(message = "Altura de trabalho é obrigatória")
    @Positive(message = "Altura de trabalho deve ser positiva")
    @Column(name = "altura_trabalho", nullable = false)
    private Double alturaTrabalho;

    @NotBlank(message = "Modelo é obrigatório")
    @Column(nullable = false)
    private String modelo;

    @NotBlank(message = "Fabricante é obrigatório")
    @Column(nullable = false)
    private String fabricante;
}
