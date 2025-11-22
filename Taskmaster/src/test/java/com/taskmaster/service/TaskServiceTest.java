package com.taskmaster.service;

import com.taskmaster.dto.TaskDTO;
import com.taskmaster.exception.ResourceNotFoundException;
import com.taskmaster.model.Task;
import com.taskmaster.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    
    @Mock
    private TaskRepository taskRepository;
    
    @InjectMocks
    private TaskService taskService;
    
    private TaskDTO taskDTO;
    private Task task;
    
    @BeforeEach
    void setUp() {
        taskDTO = new TaskDTO();
        taskDTO.setTitulo("Tarefa de Teste");
        taskDTO.setDescricao("Descrição da tarefa");
        taskDTO.setCategoria("Teste");
        taskDTO.setDataLimite(LocalDate.now().plusDays(1));
        
        task = new Task();
        task.setId(1L);
        task.setTitulo("Tarefa de Teste");
        task.setDescricao("Descrição da tarefa");
        task.setCategoria("Teste");
        task.setDataLimite(LocalDate.now().plusDays(1));
    }
    
    @Test
    void criarTarefa_ComDadosValidos_DeveRetornarTarefaCriada() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        
        Task resultado = taskService.criarTarefa(taskDTO);
        
        assertNotNull(resultado);
        assertEquals("Tarefa de Teste", resultado.getTitulo());
        verify(taskRepository, times(1)).save(any(Task.class));
    }
    
    @Test
    void criarTarefa_ComDataNoPassado_DeveLancarExcecao() {
        taskDTO.setDataLimite(LocalDate.now().minusDays(1));
        
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.criarTarefa(taskDTO);
        });
        
        verify(taskRepository, never()).save(any(Task.class));
    }
    
    @Test
    void atualizarTarefa_ComIdExistente_DeveRetornarTarefaAtualizada() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        
        Task resultado = taskService.atualizarTarefa(1L, taskDTO);
        
        assertNotNull(resultado);
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }
    
    @Test
    void atualizarTarefa_ComIdInexistente_DeveLancarExcecao() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.atualizarTarefa(999L, taskDTO);
        });
        
        verify(taskRepository, never()).save(any(Task.class));
    }
    
    @Test
    void excluirTarefa_ComIdExistente_DeveExcluirComSucesso() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        
        taskService.excluirTarefa(1L);
        
        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
    
    @Test
    void excluirTarefa_ComIdInexistente_DeveLancarExcecao() {
        when(taskRepository.existsById(999L)).thenReturn(false);
        
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.excluirTarefa(999L);
        });
        
        verify(taskRepository, never()).deleteById(anyLong());
    }
    
    @Test
    void listarTarefas_DeveRetornarPaginaDeTarefas() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Task> page = new PageImpl<>(Arrays.asList(task));
        
        when(taskRepository.findAll(pageable)).thenReturn(page);
        
        Page<Task> resultado = taskService.listarTarefas(pageable);
        
        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(taskRepository, times(1)).findAll(pageable);
    }
    
    @Test
    void filtrarPorCategoria_DeveRetornarTarefasDaCategoria() {
        List<Task> tasks = Arrays.asList(task);
        
        when(taskRepository.findByCategoria("Teste")).thenReturn(tasks);
        
        List<Task> resultado = taskService.filtrarPorCategoria("Teste");
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(taskRepository, times(1)).findByCategoria("Teste");
    }
}

