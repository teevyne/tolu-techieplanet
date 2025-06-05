package com.assessment.techieplanet.applicationdevelopment.scoreservice.service.impl;

import com.assessment.techieplanet.applicationdevelopment.scoreservice.entity.StudentScore;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.repository.StudentScoreRepository;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.repository.StudentScoreSpecification;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.service.StudentScoreService;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.dto.FilterDto;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.dto.StudentScoreReportDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentScoreServiceImpl implements StudentScoreService {

    private final StudentScoreRepository repository;

    @Override
    public Double calculateMean(Map<String, Integer> scores) {
        return scores.values().stream().mapToInt(i -> i).average().orElse(0);
    }

    @Override
    public Double calculateMedian(Map<String, Integer> scores) {
        List<Integer> sorted = scores.values().stream().sorted().toList();
        int n = sorted.size();
        return n % 2 == 0 ?
            (sorted.get(n / 2 - 1) + sorted.get(n / 2)) / 2.0 :
            sorted.get(n / 2).doubleValue();
    }

    @Override
    public Integer calculateMode(Map<String, Integer> scores) {
        return scores.values().stream()
            .collect(Collectors.groupingBy(i -> i, Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey).orElse(null);
    }

    @Override
    public Page<StudentScore> loadStudentScores(FilterDto filter, Pageable pageable) {
        return repository.findAll(StudentScoreSpecification.build(filter), pageable);
    }

    @Override
    public Page<StudentScoreReportDto> getStudentScoresReport(FilterDto filter, Pageable pageable) {

        return new PageImpl<>(loadStudentScores(filter, pageable).stream()
            .map(score -> StudentScoreReportDto.builder()
                .studentName(score.getStudentName())
                .meanScore(calculateMean(score.getScores()))
                .medianScore(calculateMedian(score.getScores()))
                .modeScore(calculateMode(score.getScores()))
                .subjectScores(score.getScores())
                .build())
            .toList());
    }

    @Override
    public List<UUID> addScores(List<StudentScore> scoreList) {

        List<StudentScore> savedScores = repository.saveAll(scoreList);
        return savedScores.stream().map(StudentScore::getUuid).toList();
    }
}