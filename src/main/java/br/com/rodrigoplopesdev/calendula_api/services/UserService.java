package br.com.rodrigoplopesdev.calendula_api.services;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateUserDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ListUserDTO;

import br.com.rodrigoplopesdev.calendula_api.models.User;
import br.com.rodrigoplopesdev.calendula_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public ListUserDTO create(CreateUserDTO data){
        User user = new User(data);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var saved = this.userRepository.save(user);

        return new ListUserDTO(saved);
    }

}
