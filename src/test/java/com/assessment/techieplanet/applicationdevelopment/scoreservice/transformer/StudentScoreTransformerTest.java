package com.assessment.techieplanet.applicationdevelopment.scoreservice.transformer;

import com.assessment.techieplanet.applicationdevelopment.scoreservice.service.StudentScoreService;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.dto.FilterDto;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.dto.StudentScoreReportDto;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.request.StudentScoreRequest;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.response.CustomResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentScoreTransformerTest {

    @InjectMocks
    private StudentScoreTransformer transformer;

    @Mock
    private StudentScoreService service;

    @Test
    void testGetScoresReport_ReturnsReportPage() {

        FilterDto filter = new FilterDto();
        Pageable pageable = PageRequest.of(0, 2);

        Page<StudentScoreReportDto> mockPage = new PageImpl<>(
            List.of(StudentScoreReportDto.builder().studentName("John").build())
        );

        when(service.getStudentScoresReport(filter, pageable)).thenReturn(mockPage);
        CustomResponse response = transformer.getScoresReport(filter, pageable);

        assertEquals(200, response.getCode());
        assertEquals("Fetched report for students", response.getMessage());
        assertTrue(response.isStatus());
        assertEquals(mockPage, response.getData());
    }

    @Test
    void testAddScore_WithValidRequests_ReturnsSuccessResponse() {

        List<StudentScoreRequest> requests = List.of(
            new StudentScoreRequest("John Doe", Map.of("Math", 85, "Science", 90)),
            new StudentScoreRequest("Jane Smith", Map.of("English", 92, "History", 88))
        );

        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        List<UUID> expectedUuids = List.of(uuid1, uuid2);

        when(service.addScores(any(List.class))).thenReturn(expectedUuids);

        CustomResponse result = transformer.addScore(requests);

        assertEquals(HttpStatus.CREATED.value(), result.getCode());
        assertEquals("Added scores to the database", result.getMessage());
        assertTrue(result.isStatus());
        assertEquals(expectedUuids, result.getData());

        verify(service, times(1)).addScores(any(List.class));
    }

    @Test
    void testAddScore_WithEmptyRequests_ReturnsSuccessResponseWithEmptyData() {

        List<StudentScoreRequest> emptyRequests = List.of();
        List<UUID> emptyUuids = List.of();

        when(service.addScores(any(List.class))).thenReturn(emptyUuids);

        CustomResponse result = transformer.addScore(emptyRequests);

        assertEquals(HttpStatus.CREATED.value(), result.getCode());
        assertEquals("Added scores to the database", result.getMessage());
        assertTrue(result.isStatus());
        assertEquals(emptyUuids, result.getData());

        verify(service, times(1)).addScores(any(List.class));
    }

    @Test
    void testAddScore_WithSingleRequest_ReturnsSuccessResponse() {

        List<StudentScoreRequest> singleRequest = List.of(
            new StudentScoreRequest("Test Student", Map.of("Subject", 75))
        );

        UUID expectedUuid = UUID.randomUUID();
        List<UUID> expectedUuids = List.of(expectedUuid);

        when(service.addScores(any(List.class))).thenReturn(expectedUuids);

        CustomResponse result = transformer.addScore(singleRequest);

        assertEquals(HttpStatus.CREATED.value(), result.getCode());
        assertEquals("Added scores to the database", result.getMessage());
        assertTrue(result.isStatus());
        assertEquals(expectedUuids, result.getData());

        verify(service, times(1)).addScores(any(List.class));
    }
}