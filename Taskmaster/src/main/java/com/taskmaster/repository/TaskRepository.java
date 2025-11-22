package com.taskmaster.repository;

import com.taskmaster.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    Page<Task> findAll(Pageable pageable);
    
    List<Task> findByCategoria(String categoria);
    
    Page<Task> findByCategoria(String categoria, Pageable pageable);
}

