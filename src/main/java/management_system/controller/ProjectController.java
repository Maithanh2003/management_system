package management_system.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import management_system.domain.constant.ResponseConstants;
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
        List<ProjectDTO> projects = projectService.getAllProject();
        return ApiResponse.<List<ProjectDTO>>builder()
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
                .result(projects)
                .build();
    }

    @GetMapping("/{projectId}")
    public ApiResponse<ProjectDTO> getProjectById(@PathVariable Long projectId) {
        ProjectDTO project = projectService.getProjectById(projectId);
        return ApiResponse.<ProjectDTO>builder()
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
                .result(project)
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<ProjectDTO>> getProjectsByUserId(@PathVariable Long userId) {
        List<ProjectDTO> projects = projectService.getProjectByUserId(userId);
        return ApiResponse.<List<ProjectDTO>>builder()
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
                .result(projects)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<ProjectDTO> createProject(@Valid @RequestBody ProjectRequest request){
        ProjectDTO project = projectService.createProject(request);
        return ApiResponse.<ProjectDTO>builder()
                .message("tao moi project thanh cong")
                .code(ResponseConstants.SUCCESS_CODE)
                .result(project)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{projectId}")
    public ApiResponse<ProjectDTO> updateProject(@RequestBody UpdateProjectRequest request, @PathVariable Long projectId) {
        ProjectDTO updatedProject = projectService.updateProject(request, projectId);
        return ApiResponse.<ProjectDTO>builder()
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
                .result(updatedProject)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            return ApiResponse.<Void>builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.SUCCESS_MESSAGE)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Void>builder()
                    .code(ResponseConstants.ERROR_CODE)
                    .message(ResponseConstants.ERROR_MESSAGE)
                    .build();
        }
    }
}
