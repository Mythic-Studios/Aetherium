package org.mythic_goose.aetherium.item.clock_data;

/**
 * The four operating modes of the Clock of Matter. `modelSuffix` maps
 * directly onto the item model variant name: "clock_of_" + modelSuffix.
 */
public enum ClockMode {
    GROWTH("growth", 90),
    FROST("frost", 50),
    REGENERATION("regeneration", 75),
    DAYBREAK("daybreak", 20),
    FEEDME("feedme", 60),
    SPEEDY_WHITES("speedy_whites", 50)

    ;

    private final String modelSuffix;
    private final int drainRate;

    ClockMode(String modelSuffix, int drainRate) {
        this.modelSuffix = modelSuffix;
        this.drainRate = drainRate;
    }

    public String modelSuffix() {
        return modelSuffix;
    }

    public int drainRate() {
        return drainRate;
    }

    /** Cycles GROWTH -> FROST -> REGENERATION -> DAYBREAK -> FEEDME -> SPEEDY_WHITES -> GROWTH -> loops */
    public ClockMode next() {
        ClockMode[] values = values();
        return values[(this.ordinal() + 1) % values.length];
    }
}