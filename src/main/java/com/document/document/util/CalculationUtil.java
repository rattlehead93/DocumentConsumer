package com.document.document.util;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class CalculationUtil {
    //Vanilla implementation
    public static int getNumberOfUniqueSubstrings(final String s) {
        if(s == null) return 0;
        if(s.isBlank()) return 0;

        int n = s.length();

        if(n == 0) return 0;

        Set<String> set = new HashSet<>();
        IntStream.range(0, n)
                .forEach(i -> IntStream.range(i, n)
                        .forEach( j -> set.add(s.substring(i, j + 1))));
        return set.size();
    }
}
