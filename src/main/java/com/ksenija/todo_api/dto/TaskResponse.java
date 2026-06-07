package com.ksenija.todo_api.dto;

import com.ksenija.todo_api.model.TaskStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private String deadline;
    private String createdAt;
}
