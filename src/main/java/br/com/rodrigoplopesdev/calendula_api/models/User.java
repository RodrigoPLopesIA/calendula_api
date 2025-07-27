package br.com.rodrigoplopesdev.calendula_api.models;


import br.com.rodrigoplopesdev.calendula_api.dtos.CreateUserDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ListUserDTO;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;


    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public User(CreateUserDTO data) {
        this.setFirstName(data.firstName());
        this.setLastName(data.lastName());
        this.setEmail(data.email());
        this.setPassword(data.password());
    }
    public User(ListUserDTO data) {
        this.setId(data.id());
        this.setFirstName(data.firstName());
        this.setLastName(data.lastName());
        this.setEmail(data.email());
        this.setPassword(data.password());
    }

    public User(String id, String firstName, String lastName, String email, String password) {
        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
    }

    public User(String firstName, String lastName, String email, String password) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
    }
}
