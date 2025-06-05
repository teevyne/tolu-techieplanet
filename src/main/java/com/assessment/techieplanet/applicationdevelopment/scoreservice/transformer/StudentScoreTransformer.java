package com.assessment.techieplanet.applicationdevelopment.scoreservice.transformer;

import com.assessment.techieplanet.applicationdevelopment.scoreservice.entity.StudentScore;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.model.StudentScoreModel;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.service.StudentScoreService;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.dto.FilterDto;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.dto.StudentScoreReportDto;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.request.StudentScoreRequest;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentScoreTransformer {

    private final StudentScoreService service;

    public CustomResponse getScoresReport(FilterDto filter, Pageable pageable) {
        Page<StudentScoreReportDto> studentScoresReport = service.getStudentScoresReport(filter, pageable);

        return CustomResponse.builder()
            .code(HttpStatus.OK.value())
            .message("Fetched report for students")
            .status(true)
            .data(studentScoresReport)
            .build();
    }

    public CustomResponse addScore(List<StudentScoreRequest> requests) {

        validateScore(requests);
        List<UUID> uuids = buildAndSaveScores(requests);

        return CustomResponse.builder()
            .code(HttpStatus.CREATED.value())
            .message("Added scores to the database")
            .status(true)
            .data(uuids)
            .build();
    }

    private List<UUID> buildAndSaveScores(List<StudentScoreRequest> requests) {
        List<StudentScore> scoreList = requests.stream()
            .map(StudentScoreModel::toScoreModel)
            .map(StudentScore::toStudentScoreEntity)
            .toList();

        return service.addScores(scoreList);
    }

    private void validateScore(List<StudentScoreRequest> requests) {
        requests.forEach(req ->
            req.getScores().forEach((subject, score) -> {
                if (score == null || score < 0 || score > 100) {
                    throw new IllegalArgumentException(String.format(
                        "Invalid score '%s' in subject '%s' for student '%s'. Score must be between 0 and 100.",
                        score, subject, req.getStudentName()
                    ));
                }
            })
        );
    }
}
