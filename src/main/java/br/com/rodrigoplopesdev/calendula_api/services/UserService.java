package br.com.rodrigoplopesdev.calendula_api.services;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateUserDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ListUserDTO;

import br.com.rodrigoplopesdev.calendula_api.models.User;
import br.com.rodrigoplopesdev.calendula_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;


    public ListUserDTO create(CreateUserDTO data){
        User user = new User(data);

        var saved = this.userRepository.save(user);

        return new ListUserDTO(saved);
    }

}
