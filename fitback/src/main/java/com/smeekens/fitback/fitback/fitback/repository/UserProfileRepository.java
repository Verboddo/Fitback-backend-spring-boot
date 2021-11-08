package com.smeekens.fitback.fitback.fitback.repository;

import com.smeekens.fitback.fitback.fitback.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
