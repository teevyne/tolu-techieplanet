package com.assessment.techieplanet.applicationdevelopment.scoreservice.model;

import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.request.StudentScoreRequest;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentScoreModel {

    private String studentName;
    private Map<String, Integer> scores;

    public static StudentScoreModel toScoreModel(StudentScoreRequest request) {
        return StudentScoreModel.builder()
            .studentName(request.getStudentName())
            .scores(request.getScores())
            .build();
    }
}
