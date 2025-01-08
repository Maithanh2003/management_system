package management_system.service;

import lombok.AllArgsConstructor;
import management_system.domain.dto.ProjectDTO;
import management_system.domain.entity.Project;
import management_system.domain.repository.ProjectRepository;
import management_system.exception.ResourceNotFoundException;
import management_system.payload.UpdateProjectRequest;
import management_system.payload.ProjectRequest;
import management_system.service.impl.IProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService implements IProjectService {
    @Autowired
    private final ProjectRepository projectRepository;
    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(
                () -> new ResourceNotFoundException("khong tim thay project theo id "+ projectId)
        );
    }

    @Override
    public List<Project> getProjectByUserId(Long userId) {
        return projectRepository.findByUsers_Id(userId).orElseThrow(
                () -> new ResourceNotFoundException("khong tim thay user theo id "+ userId)
        );
    }

    @Override
    public Project createProject(ProjectRequest request) {
        Project project = new Project();
        project.setCode(request.getCode());
        project.setName(request.getName());
        project.setCreatedAt(LocalDate.now());
        project.setCreatedBy("system");
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(UpdateProjectRequest request, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new ResourceNotFoundException("khong tim thay project theo id "+ projectId)
        );
        project.setName(request.getName());
        project.setCode(request.getCode());
        project.setUpdatedAt(LocalDate.now());
        project.setUpdatedBy("system");
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long projectId) {
        Project project =  projectRepository.findById(projectId).orElseThrow(
                ()-> new ResourceNotFoundException("role not found")
        );
        project.markAsDeleted();
        projectRepository.save(project);
    }

    @Override
    public ProjectDTO convertToDto(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }
    @Override
    public Project convertToEntity(ProjectDTO projectDto) {
        return modelMapper.map(projectDto, Project.class);
    }
}
