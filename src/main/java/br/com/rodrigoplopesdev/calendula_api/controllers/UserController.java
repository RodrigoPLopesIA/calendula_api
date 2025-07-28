package br.com.rodrigoplopesdev.calendula_api.controllers;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateUserDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ListUserDTO;
import br.com.rodrigoplopesdev.calendula_api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ListUserDTO> register(@Valid @RequestBody CreateUserDTO data) {
        URI uri = UriComponentsBuilder.fromUriString("/login").buildAndExpand().toUri();
        return ResponseEntity.created(uri).body(this.userService.create(data));
    }
}
