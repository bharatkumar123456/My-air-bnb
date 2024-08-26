package com.blogapp12.repository;

import com.blogapp12.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);

    boolean existsByuserName(String userName);

   User  findByUserName(String userName);
}
