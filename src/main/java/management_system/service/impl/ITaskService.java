package management_system.service.impl;

import management_system.domain.entity.Task;
import management_system.payload.AddUserTask;
import management_system.payload.CreateTaskRequest;
import management_system.payload.UpdateTaskRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITaskService {
    List<Task> getAllTask();
    Task getTaskById(Long taskId);
    List<Task> getTaskByUserId (Long userId);
    List<Task> getTaskByProjectId (Long projectId);
    Task createTask (CreateTaskRequest request);
    Task addUserTask (AddUserTask request, Long taskId);
    void deleteTask (Long taskId);

    Task updateTask(UpdateTaskRequest request, Long taskId);
}

