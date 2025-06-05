package com.assessment.techieplanet.applicationdevelopment.scoreservice.service.impl;

import com.assessment.techieplanet.applicationdevelopment.scoreservice.entity.StudentScore;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.repository.StudentScoreRepository;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.repository.StudentScoreSpecification;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.dto.FilterDto;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.dto.StudentScoreReportDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentScoreServiceImplTest {

    @InjectMocks
    private StudentScoreServiceImpl scoreService;

    @Mock
    private StudentScoreRepository repository;

    FilterDto filterDto = new FilterDto();

    @Test
    void testCalculateMean_EmptyMap() {
        Map<String, Integer> scores = new HashMap<>();
        assertEquals(0.0, scoreService.calculateMean(scores));
    }

    @Test
    void testCalculateMean_SingleScore() {
        Map<String, Integer> scores = Map.of("Math", 80);
        assertEquals(80.0, scoreService.calculateMean(scores));
    }

    @Test
    void testCalculateMean_MultipleScores() {
        Map<String, Integer> scores = Map.of("Math", 70, "Eng", 90, "Sci", 80);
        assertEquals(80.0, scoreService.calculateMean(scores));
    }

    @Test
    void testCalculateMedian_EmptyMap() {
        Map<String, Integer> scores = new HashMap<>();
        assertThrows(IndexOutOfBoundsException.class, () -> scoreService.calculateMedian(scores));
    }

    @Test
    void testCalculateMedian_SingleScore() {
        Map<String, Integer> scores = Map.of("Math", 70);
        assertEquals(70.0, scoreService.calculateMedian(scores));
    }

    @Test
    void testCalculateMedian_EvenScores() {
        Map<String, Integer> scores = Map.of("Math", 60, "Eng", 80);
        assertEquals(70.0, scoreService.calculateMedian(scores));
    }

    @Test
    void testCalculateMedian_OddScores() {
        Map<String, Integer> scores = Map.of("Math", 60, "Eng", 80, "Sci", 70);
        assertEquals(70.0, scoreService.calculateMedian(scores));
    }

    @Test
    void testCalculateMode_EmptyMap() {
        Map<String, Integer> scores = new HashMap<>();
        assertNull(scoreService.calculateMode(scores));
    }

    @Test
    void testCalculateMode_SingleScore() {
        Map<String, Integer> scores = Map.of("Maths", 90);
        assertEquals(90, scoreService.calculateMode(scores));
    }

    @Test
    void testCalculateMode_UniqueScores() {
        Map<String, Integer> scores = Map.of("Maths", 70, "English", 80, "Biology", 90);
        Integer mode = scoreService.calculateMode(scores);
        assertTrue(List.of(70, 80, 90).contains(mode));
    }

    @Test
    void testCalculateMode_WithClearMode() {
        Map<String, Integer> scores = Map.of("Maths", 90, "English", 80, "Physics", 90);
        assertEquals(90, scoreService.calculateMode(scores));
    }

    @Test
    void testCalculateMode_WithMultipleModes() {
        Map<String, Integer> scores = Map.of("Maths", 80, "English", 80, "Yoruba", 90, "Fine Arts", 90);
        Integer mode = scoreService.calculateMode(scores);
        assertTrue(List.of(80, 90).contains(mode));
    }


    @Test
    void testLoadStudentScores_WithValidPageable_ReturnsPage() {

        Pageable pageable = PageRequest.of(0, 2);
        List<StudentScore> students = List.of(
            new StudentScore(1L, UUID.randomUUID(), "Femi", Map.of("Maths", 90)),
            new StudentScore(2L, UUID.randomUUID(), "Ada", Map.of("English", 85))
        );
        Page<StudentScore> page = new PageImpl<>(students, pageable, students.size());

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<StudentScore> result = scoreService.loadStudentScores(filterDto, pageable);

        assertEquals(2, result.getContent().size());
        assertEquals("Femi", result.getContent().get(0).getStudentName());

        verify(repository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testLoadStudentScores_EmptyResult() {

        FilterDto filterDto = new FilterDto();

        Pageable pageable = PageRequest.of(0, 2);
        Page<StudentScore> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        Specification<StudentScore> spec = StudentScoreSpecification.build(filterDto);
        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(emptyPage);

        Page<StudentScore> result = scoreService.loadStudentScores(filterDto, pageable);

        assertTrue(result.getContent().isEmpty());
        verify(repository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testGetStudentScoresReport_ReturnsDtoPage() {

        FilterDto filterDto = new FilterDto();
        Pageable pageable = PageRequest.of(0, 2);

        Map<String, Integer> scores = Map.of("Math", 80, "Eng", 90, "Sci", 100);
        StudentScore student = new StudentScore(1L, UUID.randomUUID(), "John Doe", scores);

        Page<StudentScore> mockPage = new PageImpl<>(List.of(student));

        filterDto.setStudentName("John");
        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(mockPage);
        Page<StudentScoreReportDto> result = scoreService.getStudentScoresReport(filterDto, pageable);

        assertEquals(1, result.getContent().size());
        StudentScoreReportDto dto = result.getContent().get(0);
        assertEquals("John Doe", dto.getStudentName());
        assertEquals(scores, dto.getSubjectScores());
        assertEquals(90.0, dto.getMeanScore());
        assertEquals(90.0, dto.getMedianScore());
        assertEquals(80, dto.getModeScore());

        verify(repository, times(1)).findAll(any(Specification.class), eq(pageable));
    }


    @Test
    void testAddScores_WithValidList_ReturnsUUIDs() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        StudentScore s1 = new StudentScore(1L, uuid1, "John", Map.of("Math", 90));
        StudentScore s2 = new StudentScore(2L, uuid2, "Jane", Map.of("Eng", 85));

        List<StudentScore> input = List.of(s1, s2);
        when(repository.saveAll(input)).thenReturn(input);
        List<UUID> result = scoreService.addScores(input);

        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(uuid1, uuid2)));
        verify(repository, times(1)).saveAll(input);
    }

    @Test
    void testAddScores_WithEmptyList_ReturnsEmptyUUIDList() {
        List<StudentScore> input = List.of();

        when(repository.saveAll(input)).thenReturn(List.of());

        List<UUID> result = scoreService.addScores(input);

        assertTrue(result.isEmpty());
        verify(repository).saveAll(input);
    }
}