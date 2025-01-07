package management_system.service;

import lombok.AllArgsConstructor;
import management_system.domain.entity.Project;
import management_system.domain.entity.Task;
import management_system.domain.entity.User;
import management_system.domain.repository.ProjectRepository;
import management_system.domain.repository.TaskRepository;
import management_system.domain.repository.UserRepository;
import management_system.exception.ResourceNotFoundException;
import management_system.payload.CreateTaskRequest;
import management_system.payload.AddUserTask;
import management_system.payload.UpdateTaskRequest;
import management_system.service.impl.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class TaskService implements ITaskService {
    @Autowired
    private final TaskRepository taskRepository;
    @Autowired
    private final ProjectRepository projectRepository;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("not found task by id" + taskId)
        );
    }

    @Override
    public List<Task> getTaskByUserId(Long userId) {
        return taskRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("task not found by user by id" + userId)
        );
    }

    @Override
    public List<Task> getTaskByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId).orElseThrow(
                () -> new ResourceNotFoundException("task not found by user by id" + projectId)
        );
    }

    @Override
    public Task createTask(CreateTaskRequest request) {
        Task newTask = new Task();
        newTask.setCreated_at(LocalDate.now());
        newTask.setCreated_by("system");
        newTask.setName(request.getName());
        newTask.setCode(request.getCode());
        Project project = projectRepository.findByName(request.getProject());
        newTask.setProject(project);
        return taskRepository.save(newTask);
    }


    @Override
    public Task addUserTask(AddUserTask request, Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("khong tim thay task")
        );
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("khong tim thay user theo id" + request.getUserId())
        );
        task.setUser(user);

        Project project = task.getProject();
        if (project == null) {
            throw new ResourceNotFoundException("Task không được liên kết với bất kỳ project nào");
        }

        if (!project.getUsers().contains(user)) {
            project.getUsers().add(user); // Thêm User vào Project
            projectRepository.save(project); // Cập nhật bảng user_project
        }

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long taskId) {
        Task task =  taskRepository.findById(taskId).orElseThrow(
                ()-> new ResourceNotFoundException("task not found")
        );
        task.markAsDeleted();
        taskRepository.save(task);
    }

    @Override
    public Task updateTask(UpdateTaskRequest request, Long taskId) {
        Task task =  taskRepository.findById(taskId).orElseThrow(
                ()-> new ResourceNotFoundException("task not found")
        );
        task.setUpdated_by("system");
        task.setUpdated_at(LocalDate.now());
        task.setCode(request.getCode());
        task.setName(request.getName());
        return taskRepository.save(task);
    }
}

