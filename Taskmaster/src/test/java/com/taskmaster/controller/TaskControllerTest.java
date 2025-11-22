package com.taskmaster.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmaster.dto.TaskDTO;
import com.taskmaster.model.Task;
import com.taskmaster.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private TaskService taskService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void criar_ComDadosValidos_DeveRetornar201() throws Exception {
        TaskDTO dto = new TaskDTO();
        dto.setTitulo("Nova Tarefa");
        dto.setDescricao("Descrição");
        dto.setCategoria("Teste");
        dto.setDataLimite(LocalDate.now().plusDays(1));
        
        Task task = new Task();
        task.setId(1L);
        task.setTitulo("Nova Tarefa");
        task.setDescricao("Descrição");
        task.setCategoria("Teste");
        task.setDataLimite(LocalDate.now().plusDays(1));
        
        when(taskService.criarTarefa(any(TaskDTO.class))).thenReturn(task);
        
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Nova Tarefa"));
    }
    
    @Test
    void criar_ComDadosInvalidos_DeveRetornar400() throws Exception {
        TaskDTO dto = new TaskDTO();
        
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void listar_DeveRetornar200() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setTitulo("Tarefa");
        task.setCategoria("Teste");
        task.setDataLimite(LocalDate.now().plusDays(1));
        
        Page<Task> page = new PageImpl<>(Arrays.asList(task), PageRequest.of(0, 10), 1);
        
        when(taskService.listarTarefas(any())).thenReturn(page);
        
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(1));
    }
    
    @Test
    void buscarPorId_ComIdExistente_DeveRetornar200() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setTitulo("Tarefa");
        task.setCategoria("Teste");
        task.setDataLimite(LocalDate.now().plusDays(1));
        
        when(taskService.buscarPorId(1L)).thenReturn(task);
        
        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
    
    @Test
    void excluir_ComIdExistente_DeveRetornar204() throws Exception {
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());
    }
}

