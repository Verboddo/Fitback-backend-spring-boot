package com.smeekens.fitback.fitback.fitback.repository;

import com.smeekens.fitback.fitback.fitback.models.ERole;
import com.smeekens.fitback.fitback.fitback.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
