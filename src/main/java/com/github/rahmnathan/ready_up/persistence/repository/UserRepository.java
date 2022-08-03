package com.github.rahmnathan.ready_up.persistence.repository;

import com.github.rahmnathan.ready_up.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUserId(String userId);
    Set<User> findUsersByUserIdIn(Set<String> userIds);
}
