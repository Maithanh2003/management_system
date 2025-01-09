package management_system.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import management_system.domain.constant.ResponseConstants;
import management_system.domain.dto.TaskDTO;
import management_system.domain.entity.Task;
import management_system.payload.CreateTaskRequest;
import management_system.payload.UpdateTaskRequest;
import management_system.response.ApiResponse;
import management_system.payload.AddUserTask;
import management_system.service.impl.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private final ITaskService taskService;

    @GetMapping
    public ApiResponse<List<TaskDTO>> getAllTasks() {
        List<Task> tasks = taskService.getAllTask();
        return ApiResponse.<List<TaskDTO>>builder()
                .message("danh sach cac task cua he thong")
                .result(tasks.stream().map(task -> taskService.convertToDto(task)).toList())
                .build();
    }

    @GetMapping("/{taskId}")
    public ApiResponse<TaskDTO> getTaskById(@PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId);
        return ApiResponse.<TaskDTO>builder()
                .message("thong tin task theo id")
                .result(taskService.convertToDto(task))
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<TaskDTO>> getTasksByUserId(@PathVariable Long userId) {
        List<Task> tasks = taskService.getTaskByUserId(userId);
        return ApiResponse.<List<TaskDTO>>builder()
                .message("danh sach cac task theo user ID")
                .result(tasks.stream().map(task -> taskService.convertToDto(task)).toList())
                .build();
    }

    @GetMapping("/project/{projectId}")
    public ApiResponse<List<TaskDTO>> getTasksByProjectId(@PathVariable Long projectId) {
        List<Task> tasks = taskService.getTaskByProjectId(projectId);
        return ApiResponse.<List<TaskDTO>>builder()
                .message("danh sach cac task theo project id")
                .result(tasks.stream().map(task -> taskService.convertToDto(task)).toList())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<TaskDTO> createTask(@Valid @RequestBody CreateTaskRequest request) {
        Task task = taskService.createTask(request);
        return ApiResponse.<TaskDTO>builder()
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
                .result(taskService.convertToDto(task))
                .build();
    }

    @PostAuthorize("hasRole('ADMIN') || returnObject.result.userId == authentication.principal.id")
    @PostMapping("/{taskId}/assign-user")
    public ApiResponse<TaskDTO> addUserToTask(@RequestBody AddUserTask request, @PathVariable Long taskId) {
        Task updatedTask = taskService.addUserTask(request, taskId);
        return ApiResponse.<TaskDTO>builder()
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
                .result(taskService.convertToDto(updatedTask))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ApiResponse.<Void>builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.SUCCESS_MESSAGE)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Void>builder()
                    .code(ResponseConstants.ERROR_CODE)
                    .message(ResponseConstants.ERROR_MESSAGE)
                    .build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<TaskDTO> updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest request) {
        try {
            Task updatedTask = taskService.updateTask(request, id);
            return ApiResponse.<TaskDTO>builder()
                    .result(taskService.convertToDto(updatedTask))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<TaskDTO>builder()
                    .result(null)
                    .code(ResponseConstants.ERROR_CODE)
                    .message(ResponseConstants.ERROR_MESSAGE)
                    .build();
        }
    }
}

