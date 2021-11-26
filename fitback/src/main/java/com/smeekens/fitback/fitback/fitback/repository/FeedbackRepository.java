package com.smeekens.fitback.fitback.fitback.repository;

import com.smeekens.fitback.fitback.fitback.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
