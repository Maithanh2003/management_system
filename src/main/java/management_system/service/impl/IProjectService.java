package management_system.service.impl;

import management_system.domain.dto.ProjectDTO;
import management_system.payload.UpdateProjectRequest;
import management_system.payload.ProjectRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IProjectService {
    List<ProjectDTO> getAllProject();
    ProjectDTO getProjectById(Long taskId);
    List<ProjectDTO> getProjectByUserId (Long userId);
    ProjectDTO createProject(ProjectRequest request);
    ProjectDTO updateProject(UpdateProjectRequest request, Long projectId);
    void deleteProject (Long projectId);

}
