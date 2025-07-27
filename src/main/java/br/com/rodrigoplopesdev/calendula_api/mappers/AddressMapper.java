package br.com.rodrigoplopesdev.calendula_api.mappers;

import br.com.rodrigoplopesdev.calendula_api.dtos.AddressDTO;
import br.com.rodrigoplopesdev.calendula_api.models.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDTO toDTO(Address address);
    Address toEntity(AddressDTO dto);
}
