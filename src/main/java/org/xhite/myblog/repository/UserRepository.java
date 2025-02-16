package org.xhite.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xhite.myblog.model.AppUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}
