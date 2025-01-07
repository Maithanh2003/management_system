package management_system.service.impl;

import management_system.domain.entity.Project;
import management_system.payload.UpdateProjectRequest;
import management_system.payload.ProjectRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IProjectService {
    List<Project> getAllProject();
    Project getProjectById(Long taskId);
    List<Project> getProjectByUserId (Long userId);
    Project createProject(ProjectRequest request);
    Project updateProject(UpdateProjectRequest request, Long projectId);
    void deleteProject (Long projectId);
//    List<User> getUserByProjectId (Long projectId);
//    List<User> getUserByTaskId (Long taskId);
}
