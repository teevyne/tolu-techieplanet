package com.assessment.techieplanet;

import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.request.StudentScoreRequest;

import com.assessment.techieplanet.applicationdevelopment.scoreservice.entity.StudentScore;
import com.assessment.techieplanet.applicationdevelopment.scoreservice.repository.StudentScoreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("integration-test")
@Transactional
public class StudentScoreIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentScoreRepository repository;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("studentdb")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void shouldCreateStudentScores_WhenValidDataProvided() throws Exception {

        List<StudentScoreRequest> requests = List.of(
                new StudentScoreRequest("John Doe", Map.of(
                        "Mathematics", 85,
                        "English", 90,
                        "Science", 88,
                        "History", 92,
                        "Art", 87
                )),
                new StudentScoreRequest("Jane Smith", Map.of(
                        "Mathematics", 95,
                        "English", 89,
                        "Science", 94,
                        "History", 91,
                        "Art", 93
                ))
        );

        mockMvc.perform(post("/api/v1/scores/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requests)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.code").value(201))
            .andExpect(jsonPath("$.message").value("Added scores to the database"))
            .andExpect(jsonPath("$.status").value(true))
            .andExpect(jsonPath("$.data").isArray());

        List<StudentScore> savedScores = repository.findAll();
        assertEquals(2, savedScores.size());
    }

    @Test
    void shouldReturnValidationError_WhenInvalidScoresProvided() throws Exception {

        List<StudentScoreRequest> invalidRequests = List.of(
            new StudentScoreRequest("John Doe", Map.of(
                    "Mathematics", 150,
                    "English", -10,
                    "Science", 88,
                    "History", 92,
                    "Art", 87
            ))
        );

//        mockMvc.perform(post("/api/v1/scores/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(invalidRequests)))
//            .andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/v1/scores/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequests)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("Invalid score")));

    }

    @Test
    void shouldGetStudentScoresReport_WithPaginationAndFiltering() throws Exception {

        repository.saveAll(List.of(
            new StudentScore(1L, UUID.randomUUID(), "Alice Johnson", Map.of(
                "Mathematics", 85, "English", 90, "Science", 88, "History", 92, "Art", 87)),
            new StudentScore(2L, UUID.randomUUID(), "Bob Wilson", Map.of(
                "Mathematics", 78, "English", 82, "Science", 85, "History", 80, "Art", 83)),
            new StudentScore(3L, UUID.randomUUID(), "Charlie Brown", Map.of(
                "Mathematics", 95, "English", 89, "Science", 94, "History", 91, "Art", 93))
        ));

        mockMvc.perform(get("/api/v1/scores/report")
                    .param("page", "0")
                    .param("size", "2")
                    .param("sort", "studentName,asc"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.content", hasSize(3)))
            .andExpect(jsonPath("$.data.totalElements").value(3))
            .andExpect(jsonPath("$.data.totalPages").value(1))
            .andExpect(jsonPath("$.data.content[0].studentName").value("Alice Johnson"))
            .andExpect(jsonPath("$.data.content[0].meanScore").value(88.4))
            .andExpect(jsonPath("$.data.content[0].medianScore").value(88.0))
            .andExpect(jsonPath("$.data.content[0].subjectScores.Mathematics").value(85));

    }
}
