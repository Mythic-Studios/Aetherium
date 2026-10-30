package org.mythic_goose.aetherium.api;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public final class Aeth implements Comparable<Aeth> {
    // 3 decimal places of precision (0.001 granularity). Bump this if you
    // ever need finer-than-milli amounts.
    public static final int SCALE = 3;
    private static final BigInteger SCALE_FACTOR = BigInteger.TEN.pow(SCALE);

    // 1 decillion cap, expressed in raw scaled units
    public static final BigInteger MAX_RAW =
            new BigInteger("1000000000000000000000000000000000").multiply(SCALE_FACTOR);

    private final BigInteger raw; // actual value * 1000

    private Aeth(BigInteger raw) {
        this.raw = raw.max(BigInteger.ZERO).min(MAX_RAW);
    }

    public static Aeth ofRaw(BigInteger raw) { return new Aeth(raw); }
    public static Aeth ofDecimalString(String s) {
        BigDecimal bd = new BigDecimal(s).setScale(SCALE, RoundingMode.HALF_UP);
        return new Aeth(bd.unscaledValue());
    }
    public static Aeth ofUnits(long wholeAeth) {
        return new Aeth(BigInteger.valueOf(wholeAeth).multiply(SCALE_FACTOR));
    }

    public Aeth add(Aeth other)      { return new Aeth(raw.add(other.raw)); }
    public Aeth subtract(Aeth other) { return new Aeth(raw.subtract(other.raw)); }
    public boolean canAfford(Aeth cost) { return raw.compareTo(cost.raw) >= 0; }

    public BigInteger rawValue() { return raw; }

    public BigDecimal toBigDecimal() {
        return new BigDecimal(raw, SCALE);
    }

    @Override public int compareTo(Aeth o) { return raw.compareTo(o.raw); }

    public Aeth divide(long divisor) {
        return new Aeth(raw.divide(BigInteger.valueOf(divisor)));
    }
    public Aeth multiply(long multiply) {
        return new Aeth(raw.multiply(BigInteger.valueOf(multiply)));
    }

    public BigInteger wholeUnitsAffordable(Aeth unitPrice) {
        if (unitPrice.raw.signum() <= 0) return BigInteger.ZERO;
        return this.raw.divide(unitPrice.raw);
    }

    public Aeth createHelmet() {
        return new Aeth(raw.multiply(BigInteger.valueOf(5)));
    }
    public Aeth createChestplate() {
        return new Aeth(raw.multiply(BigInteger.valueOf(8)));
    }
    public Aeth createLeggings() {
        return new Aeth(raw.multiply(BigInteger.valueOf(7)));
    }
    public Aeth createBoots() {
        return new Aeth(raw.multiply(BigInteger.valueOf(4)));
    }
    public Aeth createOreBlocks() {
        return new Aeth(raw.multiply(BigInteger.valueOf(9)));
    }
}