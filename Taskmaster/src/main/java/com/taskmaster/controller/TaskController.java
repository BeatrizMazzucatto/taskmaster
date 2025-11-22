package com.taskmaster.controller;

import com.taskmaster.dto.TaskDTO;
import com.taskmaster.model.Task;
import com.taskmaster.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks", description = "API para gerenciamento de tarefas")
@CrossOrigin(origins = "*")
public class TaskController {
    
    private final TaskService taskService;
    
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @PostMapping
    @Operation(summary = "Criar uma nova tarefa", description = "Cria uma nova tarefa com título, descrição, categoria e data limite")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso",
                content = @Content(schema = @Schema(implementation = Task.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Task> criar(@RequestBody @Valid TaskDTO dto) {
        Task task = taskService.criarTarefa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma tarefa", description = "Atualiza completamente uma tarefa existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso",
                content = @Content(schema = @Schema(implementation = Task.class))),
        @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Task> atualizar(
            @Parameter(description = "ID da tarefa a ser atualizada") @PathVariable Long id,
            @RequestBody @Valid TaskDTO dto) {
        Task task = taskService.atualizarTarefa(id, dto);
        return ResponseEntity.ok(task);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma tarefa", description = "Remove uma tarefa do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tarefa excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da tarefa a ser excluída") @PathVariable Long id) {
        taskService.excluirTarefa(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    @Operation(summary = "Listar tarefas", description = "Lista tarefas com paginação e ordenação. Suporta filtro opcional por categoria via parâmetro query. Retorna metadados de paginação quando não filtra.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso")
    })
    public ResponseEntity<?> listar(
            @Parameter(description = "Número da página (começa em 0)") 
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") 
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenação (ex: dataLimite,asc ou titulo,desc)") 
            @RequestParam(defaultValue = "dataLimite,asc") String sort,
            @Parameter(description = "Categoria para filtrar tarefas (opcional). Se fornecido, retorna apenas tarefas desta categoria sem paginação.") 
            @RequestParam(required = false) String categoria) {
        
        if (categoria != null && !categoria.isEmpty()) {
            List<Task> tasks = taskService.filtrarPorCategoria(categoria);
            return ResponseEntity.ok(tasks);
        }
        
        Page<Task> tasksPage = taskService.listarTarefas(page, size, sort);
        return ResponseEntity.ok(tasksPage);
    }
    
    @GetMapping("/filtrar")
    @Operation(summary = "Filtrar tarefas por categoria (alternativo)", description = "Endpoint alternativo para filtrar tarefas por categoria específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tarefas filtradas retornada com sucesso")
    })
    public ResponseEntity<List<Task>> filtrar(
            @Parameter(description = "Categoria para filtrar tarefas") 
            @RequestParam String categoria) {
        
        List<Task> tasks = taskService.filtrarPorCategoria(categoria);
        return ResponseEntity.ok(tasks);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar tarefa por ID", description = "Retorna os detalhes de uma tarefa específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarefa encontrada",
                content = @Content(schema = @Schema(implementation = Task.class))),
        @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    public ResponseEntity<Task> buscarPorId(
            @Parameter(description = "ID da tarefa") @PathVariable Long id) {
        Task task = taskService.buscarPorId(id);
        return ResponseEntity.ok(task);
    }
}

