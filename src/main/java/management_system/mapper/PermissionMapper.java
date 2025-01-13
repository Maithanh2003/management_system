package management_system.mapper;

import lombok.RequiredArgsConstructor;
import management_system.domain.dto.PermissionDTO;
import management_system.domain.entity.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PermissionMapper {
    @Autowired
    private final ModelMapper modelMapper;
    // Map Permission entity to PermissionDTO
    public PermissionDTO toPermissionDTO(Permission permission) {
        return modelMapper.map(permission, PermissionDTO.class);
    }

    // Map list of Permission entities to list of PermissionDTOs
    public List<PermissionDTO> toPermissionDTOs(List<Permission> permissions) {
        return permissions.stream()
                .map(this::toPermissionDTO)
                .collect(Collectors.toList());
    }
}
