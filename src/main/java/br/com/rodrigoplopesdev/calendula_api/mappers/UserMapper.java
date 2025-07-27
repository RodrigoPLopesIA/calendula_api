package br.com.rodrigoplopesdev.calendula_api.mappers;

import br.com.rodrigoplopesdev.calendula_api.dtos.AddressDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.CreateUserDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ListUserDTO;
import br.com.rodrigoplopesdev.calendula_api.models.Address;
import br.com.rodrigoplopesdev.calendula_api.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserDTO dto);
    User toEntity(ListUserDTO dto);
    ListUserDTO toDTO(User user);
}
