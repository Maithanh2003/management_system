package management_system.service;

import lombok.RequiredArgsConstructor;
import management_system.config.user.SystemUserDetails;
import management_system.domain.dto.FileDTO;
import management_system.domain.dto.ProjectDTO;
import management_system.domain.entity.File;
import management_system.domain.repository.FileRepository;
import management_system.service.impl.IFileservice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService implements IFileservice {
    @Autowired
    private final FileRepository fileRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public FileDTO uploadFile(FileDTO fileDTO) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userPrincipal = (SystemUserDetails) authentication.getPrincipal();
        File file = File.builder()
                .type(fileDTO.getType())
                .fileName(fileDTO.getFileName())
                .uploadedAt(LocalDate.now())
                .uploadedBy(userPrincipal.getEmail())
                .isDeleted(0)
                .build();

        File savedFile = fileRepository.save(file);
        return convertToDTO(savedFile);
    }

    @Override
    public List<FileDTO> getAllFiles() {
        return fileRepository.findByIsDeleted(0).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFile(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
        file.markAsDeleted();
        fileRepository.save(file);
    }

    private FileDTO convertToDTO(File file) {
        return modelMapper.map(file, FileDTO.class);
    }

}
