package org.mythic_goose.aetherium.item.clock_data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Locale;

/**
 * Per-itemstack state for the Clock of Matter: which mode it's set to,
 * how much charge (0..MAX_CHARGE) it has left, and whether it's currently
 * toggled on. Stored as a data component so it persists through NBT saves
 * and syncs to the client automatically.
 */
public record ClockData(ClockMode mode, int charge, boolean active) {

    public static final int MAX_CHARGE = 15000;

    public static final ClockData DEFAULT = new ClockData(ClockMode.GROWTH, 0, false);

    private static final Codec<ClockMode> MODE_CODEC = Codec.STRING.xmap(
            s -> ClockMode.valueOf(s.toUpperCase(Locale.ROOT)),
            m -> m.name().toLowerCase(Locale.ROOT)
    );

    public static final Codec<ClockData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MODE_CODEC.fieldOf("mode").forGetter(ClockData::mode),
            Codec.INT.fieldOf("charge").forGetter(ClockData::charge),
            Codec.BOOL.fieldOf("active").forGetter(ClockData::active)
    ).apply(instance, ClockData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClockData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.idMapper(id -> ClockMode.values()[id], ClockMode::ordinal), ClockData::mode,
            ByteBufCodecs.VAR_INT, ClockData::charge,
            ByteBufCodecs.BOOL, ClockData::active,
            ClockData::new
    );

    public ClockData withMode(ClockMode newMode) {
        return new ClockData(newMode, charge, active);
    }

    public ClockData withCharge(int newCharge) {
        return new ClockData(mode, Math.clamp(newCharge, 0, MAX_CHARGE), active);
    }

    public ClockData withActive(boolean newActive) {
        return new ClockData(mode, charge, newActive);
    }

    public boolean isEmpty() {
        return charge <= 0;
    }
}