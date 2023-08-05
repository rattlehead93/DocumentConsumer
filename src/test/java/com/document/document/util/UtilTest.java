package com.document.document.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UtilTest {
    @Test
    public void returnsCorrectNumberOfUniqueStrings() {
        assertThat(CalculationUtil.getNumberOfUniqueSubstrings("abc")).isEqualTo(6);
    }

    @Test
    public void returnsZeroWhenContentEmptyOrNull() {
        assertThat(CalculationUtil.getNumberOfUniqueSubstrings("")).isEqualTo(0);
        assertThat(CalculationUtil.getNumberOfUniqueSubstrings(null)).isEqualTo(0);
    }

    @Test
    public void returnsZeroWhenStringContainsOnlyWhiteSpace() {
        assertThat(CalculationUtil.getNumberOfUniqueSubstrings("       ")).isEqualTo(0);
    }
}
