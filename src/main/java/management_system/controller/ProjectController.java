package management_system.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import management_system.domain.dto.ProjectDTO;
import management_system.domain.entity.Project;
import management_system.payload.ProjectRequest;
import management_system.payload.UpdateProjectRequest;
import management_system.response.ApiResponse;
import management_system.service.impl.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private IProjectService projectService;
    @GetMapping
    public ApiResponse<List<ProjectDTO>> getAllProjects() {
        List<Project> projects = projectService.getAllProject();
        return ApiResponse.<List<ProjectDTO>>builder()
                .message("danh sach cac project")
                .result(projects.stream().map(project -> projectService.convertToDto(project)).toList())
                .build();
    }

    @GetMapping("/{projectId}")
    public ApiResponse<ProjectDTO> getProjectById(@PathVariable Long projectId) {
        Project project = projectService.getProjectById(projectId);
        return ApiResponse.<ProjectDTO>builder()
                .message("thong tin project")
                .result(projectService.convertToDto(project))
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<ProjectDTO>> getProjectsByUserId(@PathVariable Long userId) {
        List<Project> projects = projectService.getProjectByUserId(userId);
        return ApiResponse.<List<ProjectDTO>>builder()
                .message("danh sach cac project theo user")
                .result(projects.stream().map(project -> projectService.convertToDto(project)).toList())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<ProjectDTO> createProject(@Valid @RequestBody ProjectRequest request){
        Project project = projectService.createProject(request);
        return ApiResponse.<ProjectDTO>builder()
                .message("tao moi project thanh cong")
                .result(projectService.convertToDto(project))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{projectId}")
    public ApiResponse<ProjectDTO> updateProject(@RequestBody UpdateProjectRequest request, @PathVariable Long projectId) {
        Project updatedProject = projectService.updateProject(request, projectId);
        return ApiResponse.<ProjectDTO>builder()
                .message("cap nhat thong tin project thanh cong")
                .result(projectService.convertToDto(updatedProject))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            return ApiResponse.<Void>builder()
                    .message("Project deleted successfully")
                    .code(1000)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Void>builder()
                    .message("error")
                    .code(404)
                    .build();
        }
    }
}
