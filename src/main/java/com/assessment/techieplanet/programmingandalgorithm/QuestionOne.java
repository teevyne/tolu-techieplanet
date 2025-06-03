package com.assessment.techieplanet.programmingandalgorithm;

import java.util.Scanner;

public class QuestionOne {

    private static final String PAST_HOUR = " past ";
    private static final String BEFORE_HOUR = " to ";
    private static final String HOUR_MARK = " o’clock";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int enteredHour = Integer.parseInt(scanner.nextLine());
        int enteredMinute = Integer.parseInt(scanner.nextLine());
        System.out.printf("Entered time => %s:%s%n", enteredHour, enteredMinute);
        System.out.println(convertTimeToWords(enteredHour, enteredMinute));
    }

    private static final String[] NUMBERS_IN_WORDS = {
            "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
            "eleven", "twelve", "thirteen", "fourteen", "quarter", "sixteen", "seventeen",
            "eighteen", "nineteen", "twenty", "twenty one", "twenty two", "twenty three",
            "twenty four", "twenty five", "twenty six", "twenty seven", "twenty eight",
            "twenty nine", "half"
    };

    private static String convertTimeToWords(int hour, int minute) {
        try {
            if (hour < 1 || hour > 12 || minute < 0 || minute >= 60) {
                return "Invalid input";
            }

            if (minute == 0) {
                return NUMBERS_IN_WORDS[hour] + HOUR_MARK;
            } else if (minute <= 30) {
                if (minute == 15 || minute == 30) {
                    return String.format("%s %s %s", NUMBERS_IN_WORDS[minute], PAST_HOUR, NUMBERS_IN_WORDS[hour]);
                }
                String minuteInText = minute == 1 ? " minute" : " minutes";
                return String.format("%s %s %s %s", NUMBERS_IN_WORDS[minute], minuteInText,  PAST_HOUR, NUMBERS_IN_WORDS[hour]);
            } else {
                int remainingMinutes = 60 - minute;
                int nextHour = hour == 12 ? 1 : hour + 1;
                if (remainingMinutes == 15) {
                    return String.format("%s %s %s", NUMBERS_IN_WORDS[remainingMinutes], BEFORE_HOUR, NUMBERS_IN_WORDS[nextHour]);
                }
                String minuteText = remainingMinutes == 1 ? " minute" : " minutes";
                return String.format("%s %s %s %s", NUMBERS_IN_WORDS[remainingMinutes], minuteText,  BEFORE_HOUR, NUMBERS_IN_WORDS[nextHour]);
            }
        } catch (RuntimeException e) {
            return "Invalid input format";
        }
    }
}