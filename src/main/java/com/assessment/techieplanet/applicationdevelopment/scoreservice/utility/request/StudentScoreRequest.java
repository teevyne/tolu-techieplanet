package com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentScoreRequest {

    @NotBlank
    private String studentName;

    @Size(min = 5, max = 5)
    private Map<String, Integer> scores;
}