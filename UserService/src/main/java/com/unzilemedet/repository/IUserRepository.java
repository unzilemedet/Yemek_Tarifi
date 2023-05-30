package com.unzilemedet.repository;

import com.unzilemedet.repository.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends MongoRepository<User,String> {
    Optional<User> findOptionalByAuthId(Long authId);
    Optional<User> findOptionalByUsernameIgnoreCase(String username);
}

