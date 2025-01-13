package management_system.service;

import lombok.AllArgsConstructor;
import management_system.config.user.SystemUserDetails;
import management_system.domain.dto.ProjectDTO;
import management_system.domain.entity.Project;
import management_system.domain.repository.ProjectRepository;
import management_system.exception.ResourceNotFoundException;
import management_system.mapper.ProjectMapper;
import management_system.payload.UpdateProjectRequest;
import management_system.payload.ProjectRequest;
import management_system.service.impl.IProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService implements IProjectService {
    @Autowired
    private final ProjectRepository projectRepository;
    @Autowired
    private final ProjectMapper projectMapper;

    @Override
    public List<ProjectDTO> getAllProject() {
        return projectMapper.toDTOList(projectRepository.findAll());
    }

    @Override
    public ProjectDTO getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("khong tim thay project theo id " + projectId));
        return projectMapper.toDTO(project);
    }

    @Override
    public List<ProjectDTO> getProjectByUserId(Long userId) {
        List<Project> projects = projectRepository.findByUsers_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("khong tim thay user theo id " + userId));
        return projectMapper.toDTOList(projects);
    }

    @Override
    public ProjectDTO createProject(ProjectRequest request) {
        Project project = new Project();
        if (projectRepository.existsByCode(request.getCode()))
            throw new IllegalArgumentException("Code already exists, please choose a different code.");
        Project newProject = projectMapper.toEntity(request);
        newProject.setCreatedAt(LocalDate.now());
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userPrincipal = (SystemUserDetails) authentication.getPrincipal();
        newProject.setCreatedBy(userPrincipal.getEmail());
        projectRepository.save(newProject);
        return projectMapper.toDTO(newProject);
    }

    @Override
    public ProjectDTO updateProject(UpdateProjectRequest request, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new ResourceNotFoundException("khong tim thay project theo id "+ projectId)
        );
        Project updatedProject = projectMapper.toEntity(request, project);
        project.setUpdatedAt(LocalDate.now());
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userPrincipal = (SystemUserDetails) authentication.getPrincipal();
        updatedProject.setUpdatedBy(userPrincipal.getEmail());
        projectRepository.save(updatedProject);
        return projectMapper.toDTO(updatedProject);
    }

    @Override
    public void deleteProject(Long projectId) {
        Project project =  projectRepository.findById(projectId).orElseThrow(
                ()-> new ResourceNotFoundException("role not found")
        );
        project.markAsDeleted();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userPrincipal = (SystemUserDetails) authentication.getPrincipal();
        project.setUpdatedBy(userPrincipal.getEmail());
        projectRepository.save(project);
    }
}
