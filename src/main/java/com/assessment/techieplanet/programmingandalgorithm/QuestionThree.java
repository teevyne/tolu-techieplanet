package com.assessment.techieplanet.programmingandalgorithm;

public class QuestionThree {

    public static void main(String[] args) {
        String input = "1234445123444512344451234445123444512344451234445";
        int result = sumDigits(input);
        System.out.println("Sum of digits -> " + result);
        int digitalRoot = digitalRoot(input);
        System.out.println("Digital root -> " + digitalRoot);
    }

    public static int sumDigits(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        int firstDigit = s.charAt(0) - '0';
        return firstDigit + sumDigits(s.substring(1));
    }

    public static int digitalRoot(String s) {
        int sum = sumDigits(s);
        while (sum >= 10) {sum = sumDigits(String.valueOf(sum));}
        return sum;
    }
}
