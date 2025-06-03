package com.assessment.techieplanet.programmingandalgorithm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuestionOneTest {

    @Test
    void testExactHour() {
        assertEquals("five o’clock", (QuestionOne.convertTimeToWords(5, 0)));
    }

    @Test
    void testOneMinutePast() {
        assertEquals("one minute past five", QuestionOne.convertTimeToWords(5, 1));
    }

    @Test
    void testQuarterPast() {
        assertEquals("quarter past five", QuestionOne.convertTimeToWords(5, 15));
    }

    @Test
    void testHalfPast() {
        assertEquals("half past five", QuestionOne.convertTimeToWords(5, 30));
    }

    @Test
    void testQuarterTo() {
        assertEquals("quarter to six", QuestionOne.convertTimeToWords(5, 45));
    }

    @Test
    void testOneMinuteTo() {
        assertEquals("one minute to six", QuestionOne.convertTimeToWords(5, 59));
    }

    @Test
    void testInvalidHour() {
        assertEquals("Invalid input", QuestionOne.convertTimeToWords(13, 15));
    }

    @Test
    void testInvalidMinute() {
        assertEquals("Invalid input", QuestionOne.convertTimeToWords(5, 60));
    }

}