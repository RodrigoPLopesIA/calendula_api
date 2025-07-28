package br.com.rodrigoplopesdev.calendula_api.repositories;

import br.com.rodrigoplopesdev.calendula_api.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByEmail(String email);

    UserDetails findByEmail(String username);
}
