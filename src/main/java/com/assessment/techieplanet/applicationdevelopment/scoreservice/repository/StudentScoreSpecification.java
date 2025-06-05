package com.assessment.techieplanet.applicationdevelopment.scoreservice.repository;

import com.assessment.techieplanet.applicationdevelopment.scoreservice.entity.StudentScore;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.dto.FilterDto;
import org.springframework.data.jpa.domain.Specification;

public class StudentScoreSpecification {

    public static Specification<StudentScore> build(FilterDto filter) {
        return Specification.where(hasStudentName(filter));
    }

    public static Specification<StudentScore> hasStudentName(FilterDto filterDto) {
        return (root, query, criteriaBuilder) ->
            filterDto.getStudentName() == null ? null :
                criteriaBuilder.like(criteriaBuilder.lower(
                    root.get("studentName")), "%" + filterDto.getStudentName().toLowerCase() + "%");
    }
}
