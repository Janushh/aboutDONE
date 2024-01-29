package com.medical.api.repository;

import com.medical.api.entities.UserQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserQueryRepository  extends JpaRepository<UserQuery, Long> {
    List<UserQuery> findByUsername(String username);
}
