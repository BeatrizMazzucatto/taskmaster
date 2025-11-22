package com.taskmaster.service;

import com.taskmaster.dto.TaskDTO;
import com.taskmaster.exception.ResourceNotFoundException;
import com.taskmaster.model.Task;
import com.taskmaster.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    public Task criarTarefa(TaskDTO dto) {
        validarDataLimite(dto.getDataLimite());
        
        Task task = convertToEntity(dto);
        return taskRepository.save(task);
    }
    
    public Task atualizarTarefa(Long id, TaskDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com ID: " + id));
        
        validarDataLimite(dto.getDataLimite());
        
        task.setTitulo(dto.getTitulo());
        task.setDescricao(dto.getDescricao());
        task.setCategoria(dto.getCategoria());
        task.setDataLimite(dto.getDataLimite());
        
        return taskRepository.save(task);
    }
    
    public void excluirTarefa(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tarefa não encontrada com ID: " + id);
        }
        taskRepository.deleteById(id);
    }
    
    public Page<Task> listarTarefas(int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc") 
                ? Sort.Direction.DESC 
                : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return taskRepository.findAll(pageable);
    }
    
    public List<Task> filtrarPorCategoria(String categoria) {
        return taskRepository.findByCategoria(categoria);
    }
    
    public Task buscarPorId(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com ID: " + id));
    }
    
    private void validarDataLimite(LocalDate dataLimite) {
        if (dataLimite != null && dataLimite.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data limite não pode ser no passado");
        }
    }
    
    public Task convertToEntity(TaskDTO dto) {
        Task task = new Task();
        task.setTitulo(dto.getTitulo());
        task.setDescricao(dto.getDescricao());
        task.setCategoria(dto.getCategoria());
        task.setDataLimite(dto.getDataLimite());
        return task;
    }
    
    public TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setTitulo(task.getTitulo());
        dto.setDescricao(task.getDescricao());
        dto.setCategoria(task.getCategoria());
        dto.setDataLimite(task.getDataLimite());
        return dto;
    }
}

