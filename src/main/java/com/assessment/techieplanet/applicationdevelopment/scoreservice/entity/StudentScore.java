package com.assessment.techieplanet.applicationdevelopment.scoreservice.entity;

import com.assessment.techieplanet.applicationdevelopment.scoreservice.model.StudentScoreModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "student_scores")
public class StudentScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid;

    @Column(nullable = false)
    private String studentName;

    @ElementCollection
    @MapKeyColumn(name = "subject")
    @Column(name = "scores")
    private Map<String, Integer> scores = new HashMap<>();

    public static StudentScore toStudentScoreEntity(StudentScoreModel model) {
        return StudentScore.builder()
            .scores(model.getScores())
            .uuid(UUID.randomUUID())
            .studentName(model.getStudentName())
            .build();
    }
}

