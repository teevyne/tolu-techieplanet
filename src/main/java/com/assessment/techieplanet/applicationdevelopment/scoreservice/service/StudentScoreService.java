package com.assessment.techieplanet.applicationdevelopment.scoreservice.service;

import com.assessment.techieplanet.applicationdevelopment.scoreservice.entity.StudentScore;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.dto.FilterDto;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.dto.StudentScoreReportDto;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.response.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface StudentScoreService {
    Double calculateMean(Map<String, Integer> scores);

    Double calculateMedian(Map<String, Integer> scores);

    Integer calculateMode(Map<String, Integer> scores);

    Page<StudentScore> loadStudentScores(FilterDto filter, Pageable pageable);

    Page<StudentScoreReportDto> getStudentScoresReport(FilterDto filter, Pageable pageable);

    List<UUID> addScores(List<StudentScore> scoreList);
}
