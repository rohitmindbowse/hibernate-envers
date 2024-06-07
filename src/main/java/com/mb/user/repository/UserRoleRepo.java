package com.mb.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mb.user.entity.UserRole;

@Repository
public interface UserRoleRepo   extends JpaRepository<UserRole, Long> {

}
