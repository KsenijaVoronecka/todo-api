package com.ksenija.todo_api.dto;

import com.ksenija.todo_api.model.Task;
import com.ksenija.todo_api.model.TaskStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskMapper {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static Task toEntity(TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus() != null
                ? request.getStatus()
                : TaskStatus.TODO);
        if (request.getDeadline() != null) {
            task.setDeadline(LocalDateTime.parse(
                    request.getDeadline(), FORMATTER));
        }
        return task;
    }

    public static TaskResponse toResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        if (task.getDeadline() != null) {
            response.setDeadline(
                    task.getDeadline().format(FORMATTER));
        }
        if (task.getCreatedAt() != null) {
            response.setCreatedAt(
                    task.getCreatedAt().format(FORMATTER));
        }
        return response;
    }
}
