package com.plataformas.crud.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "preventivas")
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

    public Preventiva() {
    }

    public Preventiva(
            Long id,
            String modelo,
            String fabricante,
            String itemDescricao,
            String partNumber,
            String codSap,
            String qtd250h,
            String qtd500h,
            String qtd750h,
            String qtd1000h,
            String horasMo250h,
            String horasMo500h,
            String horasMo750h,
            String horasMo1000h
    ) {
        this.id = id;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.itemDescricao = itemDescricao;
        this.partNumber = partNumber;
        this.codSap = codSap;
        this.qtd250h = qtd250h;
        this.qtd500h = qtd500h;
        this.qtd750h = qtd750h;
        this.qtd1000h = qtd1000h;
        this.horasMo250h = horasMo250h;
        this.horasMo500h = horasMo500h;
        this.horasMo750h = horasMo750h;
        this.horasMo1000h = horasMo1000h;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getItemDescricao() {
        return itemDescricao;
    }

    public void setItemDescricao(String itemDescricao) {
        this.itemDescricao = itemDescricao;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getCodSap() {
        return codSap;
    }

    public void setCodSap(String codSap) {
        this.codSap = codSap;
    }

    public String getQtd250h() {
        return qtd250h;
    }

    public void setQtd250h(String qtd250h) {
        this.qtd250h = qtd250h;
    }

    public String getQtd500h() {
        return qtd500h;
    }

    public void setQtd500h(String qtd500h) {
        this.qtd500h = qtd500h;
    }

    public String getQtd750h() {
        return qtd750h;
    }

    public void setQtd750h(String qtd750h) {
        this.qtd750h = qtd750h;
    }

    public String getQtd1000h() {
        return qtd1000h;
    }

    public void setQtd1000h(String qtd1000h) {
        this.qtd1000h = qtd1000h;
    }

    public String getHorasMo250h() {
        return horasMo250h;
    }

    public void setHorasMo250h(String horasMo250h) {
        this.horasMo250h = horasMo250h;
    }

    public String getHorasMo500h() {
        return horasMo500h;
    }

    public void setHorasMo500h(String horasMo500h) {
        this.horasMo500h = horasMo500h;
    }

    public String getHorasMo750h() {
        return horasMo750h;
    }

    public void setHorasMo750h(String horasMo750h) {
        this.horasMo750h = horasMo750h;
    }

    public String getHorasMo1000h() {
        return horasMo1000h;
    }

    public void setHorasMo1000h(String horasMo1000h) {
        this.horasMo1000h = horasMo1000h;
    }
}
