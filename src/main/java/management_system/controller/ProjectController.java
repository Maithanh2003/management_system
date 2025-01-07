package management_system.controller;

import lombok.AllArgsConstructor;
import management_system.domain.entity.Project;
import management_system.payload.ProjectRequest;
import management_system.payload.UpdateProjectRequest;
import management_system.response.ApiResponse;
import management_system.service.impl.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private IProjectService projectService;
    @GetMapping
    public ApiResponse<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProject();
        return ApiResponse.<List<Project>>builder()
                .message("danh sach cac project")
                .result(projects)
                .build();
    }

    @GetMapping("/{projectId}")
    public ApiResponse<Project> getProjectById(@PathVariable Long projectId) {
        Project project = projectService.getProjectById(projectId);
        return ApiResponse.<Project>builder()
                .message("thong tin project")
                .result(project)
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<Project>> getProjectsByUserId(@PathVariable Long userId) {
        List<Project> projects = projectService.getProjectByUserId(userId);
        return ApiResponse.<List<Project>>builder()
                .message("danh sach cac project theo user")
                .result(projects)
                .build();
    }

    @PostMapping
    public ApiResponse<Project> createProject(@RequestBody ProjectRequest request){
        Project project = projectService.createProject(request);
        return ApiResponse.<Project>builder()
                .message("tao moi project thanh cong")
                .result(project)
                .build();
    }

    // Sửa thông tin project
    @PutMapping("/{projectId}")
    public ApiResponse<Project> updateProject(@RequestBody UpdateProjectRequest request, @PathVariable Long projectId) {
        Project updatedProject = projectService.updateProject(request, projectId);
        return ApiResponse.<Project>builder()
                .message("cap nhat thong tin project thanh cong")
                .result(updatedProject)
                .build();
    }

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
