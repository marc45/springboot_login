package org.dimatech.repository;

import org.dimatech.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
    Optional<User> findById(String id);
    void delete(User deleted);
}