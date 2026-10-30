package org.mythic_goose.aetherium.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import org.mythic_goose.aetherium.item.clock_data.ClockData;

import java.util.function.Supplier;

public class ModDataComponents {

    public static Supplier<DataComponentType<Integer>> CHARGE;
    public static Supplier<DataComponentType<Integer>> FERTILIZER_CHARGE;
    public static Supplier<DataComponentType<ClockData>> CLOCK_DATA;
    public static Supplier<DataComponentType<Integer>> TOOL_CHARGE;
    public static Supplier<DataComponentType<Integer>> ARMOR_CHARGE;

    public static void init(DataComponentRegistrar registrar) {
        CHARGE = registrar.register("charge", builder -> builder
                .persistent(Codec.INT)
                .networkSynchronized(ByteBufCodecs.VAR_INT));

        FERTILIZER_CHARGE = registrar.register("fertilizer_charge", builder -> builder
                .persistent(Codec.INT)
                .networkSynchronized(ByteBufCodecs.VAR_INT));

        CLOCK_DATA = registrar.register("clock_data", builder -> builder
                .persistent(ClockData.CODEC)
                .networkSynchronized(ClockData.STREAM_CODEC));

        TOOL_CHARGE = registrar.register("tool_charge", builder -> builder
                .persistent(Codec.intRange(0, Integer.MAX_VALUE))
                .networkSynchronized(ByteBufCodecs.VAR_INT));

        ARMOR_CHARGE = registrar.register("armor_charge", builder -> builder
                .persistent(Codec.intRange(0, Integer.MAX_VALUE))
                .networkSynchronized(ByteBufCodecs.VAR_INT));
    }
}