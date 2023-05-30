package com.unzilemedet.repository;

import com.unzilemedet.repository.entity.Auth;
import com.unzilemedet.repository.entity.enums.ERole;
import io.lettuce.core.ScanIterator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth,Long> {
    Optional<Auth> findOptionalByUsername(String username);
    Optional<Auth> findOptionalByEmailAndPassword(String email, String password);

    List<Auth> findByRole(ERole role);

    Optional<Auth> findOptionalByEmail(String email);
}
