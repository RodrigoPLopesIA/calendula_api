package br.com.rodrigoplopesdev.calendula_api.repositories;

import br.com.rodrigoplopesdev.calendula_api.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<String, User> {
}
