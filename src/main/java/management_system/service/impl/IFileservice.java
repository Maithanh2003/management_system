package management_system.service.impl;

import management_system.domain.dto.FileDTO;

import java.util.List;

public interface IFileservice {

    FileDTO uploadFile(FileDTO fileDTO);

    List<FileDTO> getAllFiles();

    void deleteFile(Long id);
}
