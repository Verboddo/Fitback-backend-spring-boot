package com.smeekens.fitback.fitback.fitback.repository;

import com.smeekens.fitback.fitback.fitback.models.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, Long> {
}
