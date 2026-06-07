package com.ksenija.todo_api.controller;

import com.ksenija.todo_api.model.TaskStatus;
import com.ksenija.todo_api.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.ksenija.todo_api.dto.TaskMapper;
import com.ksenija.todo_api.dto.TaskRequest;
import com.ksenija.todo_api.dto.TaskResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<TaskResponse> getAll(
            @RequestParam(required = false) TaskStatus status) {
        return taskService.getAll(status)
                .stream()
                .map(TaskMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TaskResponse getById(@PathVariable Long id) {
        return TaskMapper.toResponse(taskService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@RequestBody @Valid TaskRequest request) {
        return TaskMapper.toResponse(
                taskService.create(TaskMapper.toEntity(request)));
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id,
                               @RequestBody @Valid TaskRequest request) {
        return TaskMapper.toResponse(
                taskService.update(id, TaskMapper.toEntity(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}