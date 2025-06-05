package com.assessment.techieplanet.applicationdevelopment.scoreservice.controller;

import com.assessment.techieplanet.applicationdevelopment.scoreservice.transformer.StudentScoreTransformer;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.dto.FilterDto;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.request.StudentScoreRequest;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.response.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scores/")
@RequiredArgsConstructor
public class StudentScoreController {

    private final StudentScoreTransformer transformer;

    @PostMapping
    public ResponseEntity<CustomResponse> saveScore(@Valid @RequestBody List<StudentScoreRequest> request) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(transformer.addScore(request));
    }

    @GetMapping("report")
    public ResponseEntity<CustomResponse> getReport(
            @ModelAttribute FilterDto filter,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok(transformer.getScoresReport(filter, pageable));
    }
}
