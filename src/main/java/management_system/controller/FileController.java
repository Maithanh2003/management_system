package management_system.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management_system.domain.constant.ResponseConstants;
import management_system.domain.dto.FileDTO;
import management_system.domain.entity.File;
import management_system.domain.entity.Permission;
import management_system.domain.repository.FileRepository;
import management_system.response.ApiResponse;
import management_system.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
@Slf4j
public class FileController {
    @Autowired
    private FileService fileService;
    @PostMapping("/upload")
    public ApiResponse<FileDTO> uploadFile(@RequestBody FileDTO fileDTO) {
        FileDTO file =  fileService.uploadFile(fileDTO);
        return ApiResponse.<FileDTO>builder()
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
                .result(file)
                .build();
    }

    @GetMapping
    public ApiResponse<List<FileDTO>> getAllFiles() {
        List<FileDTO> files =  fileService.getAllFiles();
        return ApiResponse.<List<FileDTO>>builder()
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
                .result(files)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFile(@PathVariable Long id) {
        try {
            fileService.deleteFile(id);
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
