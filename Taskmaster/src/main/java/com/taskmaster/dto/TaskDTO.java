package com.taskmaster.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class TaskDTO {
    
    @NotBlank(message = "O título é obrigatório")
    @Size(max = 200, message = "O título deve ter no máximo 200 caracteres")
    private String titulo;
    
    @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres")
    private String descricao;
    
    @NotBlank(message = "A categoria é obrigatória")
    @Size(max = 50, message = "A categoria deve ter no máximo 50 caracteres")
    private String categoria;
    
    @NotNull(message = "A data limite é obrigatória")
    private LocalDate dataLimite;
    
    public TaskDTO() {
    }
    
    public TaskDTO(String titulo, String descricao, String categoria, LocalDate dataLimite) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.dataLimite = dataLimite;
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

