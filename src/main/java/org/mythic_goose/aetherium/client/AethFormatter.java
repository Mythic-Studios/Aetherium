package org.mythic_goose.aetherium.client;

import org.mythic_goose.aetherium.api.Aeth;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AethFormatter {
    private static final String[] SUFFIXES = {
            "", "k", "m", "b", "t", "qd", "qt", "sx", "sp", "oc", "no", "dc"
    };
    private static final BigDecimal THOUSAND = BigDecimal.valueOf(1000);

    public static String format(Aeth aeth) {
        BigDecimal value = aeth.toBigDecimal();
        int tier = 0;

        while (value.compareTo(THOUSAND) >= 0 && tier < SUFFIXES.length - 1) {
            value = value.divide(THOUSAND, 10, RoundingMode.HALF_DOWN);
            tier++;
        }

        if (tier == 0) {
            return value.setScale(3, RoundingMode.HALF_DOWN)
                    .stripTrailingZeros().toPlainString();
        }

        BigDecimal rounded = value.setScale(2, RoundingMode.HALF_DOWN);

        // Rounding to 2 decimals can carry a value like 999.999 up to 1000.00 for this
        // tier — without this, that would print as "1000.00k" instead of "1.00m".
        while (rounded.compareTo(THOUSAND) >= 0 && tier < SUFFIXES.length - 1) {
            rounded = rounded.divide(THOUSAND, 2, RoundingMode.HALF_DOWN);
            tier++;
        }

        return rounded.toPlainString() + SUFFIXES[tier];
    }
}