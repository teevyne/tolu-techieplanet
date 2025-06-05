package com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentScoreReportDto {

    private String studentName;
    private double meanScore;
    private double medianScore;
    private double modeScore;
    private Map<String, Integer> subjectScores;
}
