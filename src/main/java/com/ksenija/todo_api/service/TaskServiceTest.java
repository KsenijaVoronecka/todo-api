package com.ksenija.todo_api.service;

import com.ksenija.todo_api.exception.TaskNotFoundException;
import com.ksenija.todo_api.model.Task;
import com.ksenija.todo_api.model.TaskStatus;
import com.ksenija.todo_api.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Test task");
        task.setStatus(TaskStatus.TODO);
    }

    @Test
    void getAll_shouldReturnAllTasks_whenNoStatusFilter() {
        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<Task> result = taskService.getAll(null);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test task");
        verify(taskRepository).findAll();
    }

    @Test
    void getAll_shouldReturnFilteredTasks_whenStatusProvided() {
        when(taskRepository.findByStatus(TaskStatus.TODO))
                .thenReturn(List.of(task));

        List<Task> result = taskService.getAll(TaskStatus.TODO);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo(TaskStatus.TODO);
        verify(taskRepository).findByStatus(TaskStatus.TODO);
    }

    @Test
    void getById_shouldReturnTask_whenTaskExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.getById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test task");
    }

    @Test
    void getById_shouldThrowException_whenTaskNotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getById(99L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void create_shouldSaveAndReturnTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.create(task);

        assertThat(result.getTitle()).isEqualTo("Test task");
        verify(taskRepository).save(task);
    }

    @Test
    void update_shouldUpdateFields_whenTaskExists() {
        Task updated = new Task();
        updated.setTitle("Updated title");
        updated.setStatus(TaskStatus.IN_PROGRESS);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.update(1L, updated);

        verify(taskRepository).findById(1L);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void delete_shouldCallDeleteById() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.delete(1L);

        verify(taskRepository).deleteById(1L);
    }
}
