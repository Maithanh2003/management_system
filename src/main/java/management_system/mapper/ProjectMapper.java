package management_system.mapper;

import lombok.RequiredArgsConstructor;
import management_system.domain.dto.ProjectDTO;
import management_system.domain.entity.Project;
import management_system.payload.ProjectRequest;
import management_system.payload.UpdateProjectRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectMapper {
    @Autowired
    private final ModelMapper modelMapper;
    public ProjectDTO toDTO(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }

    public Project toEntity(ProjectRequest request) {
        return modelMapper.map(request, Project.class);
    }

    public Project toEntity(UpdateProjectRequest request, Project existingProject) {
        modelMapper.map(request, existingProject);
        return existingProject;
    }

    public List<ProjectDTO> toDTOList(List<Project> projects) {
        return projects.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
