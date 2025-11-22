package com.taskmaster.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String titulo;
    
    @Column(length = 1000)
    private String descricao;
    
    @Column(nullable = false, length = 50)
    private String categoria;
    
    @Column(nullable = false)
    private LocalDate dataLimite;
    
    public Task() {
    }
    
    public Task(String titulo, String descricao, String categoria, LocalDate dataLimite) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.dataLimite = dataLimite;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public LocalDate getDataLimite() {
        return dataLimite;
    }
    
    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }
}

