package com.example.repositories;

import com.example.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    @Override
    @RestResource(exported = false)
    User save(User s);
}
