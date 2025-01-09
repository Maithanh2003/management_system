package management_system.service;

import lombok.AllArgsConstructor;
import management_system.config.user.SystemUserDetails;
import management_system.domain.dto.TaskDTO;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService implements ITaskService {
    @Autowired
    private final TaskRepository taskRepository;
    @Autowired
    private final ProjectRepository projectRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ModelMapper modelMapper;

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
        if (taskRepository.existsByCode(request.getCode()))
            throw new IllegalArgumentException("Code already exists, please choose a different code.");
        newTask.setCreatedAt(LocalDate.now());
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userPrincipal = (SystemUserDetails) authentication.getPrincipal();
        newTask.setCreatedBy(userPrincipal.getEmail());
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
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userPrincipal = (SystemUserDetails) authentication.getPrincipal();
        task.setUpdatedBy(userPrincipal.getEmail());
        taskRepository.save(task);
    }

    @Override
    public Task updateTask(UpdateTaskRequest request, Long taskId) {
        Task task =  taskRepository.findById(taskId).orElseThrow(
                ()-> new ResourceNotFoundException("task not found")
        );
        task.setUpdatedBy("system");
        task.setUpdatedAt(LocalDate.now());
        task.setCode(request.getCode());
        task.setName(request.getName());
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userPrincipal = (SystemUserDetails) authentication.getPrincipal();
        task.setUpdatedBy(userPrincipal.getEmail());
        return taskRepository.save(task);
    }
    @Override
    public TaskDTO convertToDto(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }
    @Override
    public Task convertToEntity(TaskDTO taskDto) {
        return modelMapper.map(taskDto, Task.class);
    }
}

