package management_system.controller;

import lombok.AllArgsConstructor;
import management_system.domain.entity.Task;
import management_system.domain.entity.User;
import management_system.payload.CreateTaskRequest;
import management_system.payload.UpdateTaskRequest;
import management_system.payload.UpdateUserRequest;
import management_system.response.ApiResponse;
import management_system.payload.AddUserTask;
import management_system.service.impl.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private final ITaskService taskService;

    @GetMapping
    public ApiResponse<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTask();
        return ApiResponse.<List<Task>>builder()
                .message("danh sach cac task cua he thong")
                .result(tasks)
                .build();
    }

    @GetMapping("/{taskId}")
    public ApiResponse<Task> getTaskById(@PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId);
        return ApiResponse.<Task>builder()
                .message("thong tin task theo id")
                .result(task)
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<Task>> getTasksByUserId(@PathVariable Long userId) {
        List<Task> tasks = taskService.getTaskByUserId(userId);
        return ApiResponse.<List<Task>>builder()
                .message("danh sach cac task theo user ID")
                .result(tasks)
                .build();
    }

    @GetMapping("/project/{projectId}")
    public ApiResponse<List<Task>> getTasksByProjectId(@PathVariable Long projectId) {
        List<Task> tasks = taskService.getTaskByProjectId(projectId);
        return ApiResponse.<List<Task>>builder()
                .message("danh sach cac task theo project id")
                .result(tasks)
                .build();
    }
    @PostMapping
    public ApiResponse<Task> createTask(@RequestBody CreateTaskRequest request) {
        Task task = taskService.createTask(request);
        return ApiResponse.<Task>builder()
                .message("tao moi 1 task thanh cong")
                .result(task)
                .build();
    }

    @PostMapping("/{taskId}/assign-user")
    public ApiResponse<Task> addUserToTask(@RequestBody AddUserTask request, @PathVariable Long taskId) {
        Task updatedTask = taskService.addUserTask(request, taskId);
        return ApiResponse.<Task>builder()
                .message("User đã được dang ki vào task")
                .result(updatedTask)
                .build();
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ApiResponse.<Void>builder()
                    .message("task deleted successfully")
                    .code(1000)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Void>builder()
                    .message("error")
                    .code(404)
                    .build();
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<Task> updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest request) {
        try {
            Task updatedTask = taskService.updateTask(request, id);
            return ApiResponse.<Task>builder()
                    .result(updatedTask)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Task>builder()
                    .result(null)
                    .message("error")
                    .code(404)
                    .build();
        }
    }
}

