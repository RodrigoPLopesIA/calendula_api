package br.com.rodrigoplopesdev.calendula_api.dtos;

import br.com.rodrigoplopesdev.calendula_api.models.User;

public record ListUserDTO(
        String id,
        String firstName,
        String lastName,
        String email
) {

    public ListUserDTO(User user) {
        this(user.getId(), user.getFirstName(),user.getLastName(), user.getEmail());
    }
}
