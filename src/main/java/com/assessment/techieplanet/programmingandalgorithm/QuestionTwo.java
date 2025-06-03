package com.assessment.techieplanet.programmingandalgorithm;

import java.util.HashSet;
import java.util.Set;

public class QuestionTwo {
    public static void main(String[] args) {
        int[][] a = {
                {1, 3, 1, 2, 3, 4, 4, 3, 5},
                {1, 1, 1, 1, 1, 1, 1},
                {2, 1, 2, 2, 3, 1, 5}
        };

        removeDuplicates(a);

        for (int[] row : a) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    public static void removeDuplicates(int[][] integerArray) {
        for (int[] row : integerArray) {
            Set<Integer> seenValue = new HashSet<>();
            for (int j = 0; j < row.length; j++) {
                if (!seenValue.add(row[j])) {
                    row[j] = 0;
                }
            }
        }
    }
}