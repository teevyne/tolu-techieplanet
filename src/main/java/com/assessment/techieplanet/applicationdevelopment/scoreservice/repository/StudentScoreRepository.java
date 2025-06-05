package com.assessment.techieplanet.applicationdevelopment.scoreservice.repository;

import com.assessment.techieplanet.applicationdevelopment.scoreservice.entity.StudentScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentScoreRepository extends JpaRepository<StudentScore, Long>, JpaSpecificationExecutor<StudentScore> {
}
