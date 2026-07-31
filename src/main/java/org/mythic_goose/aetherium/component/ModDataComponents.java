package org.mythic_goose.aetherium.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import org.mythic_goose.aetherium.Aetherium;
import org.mythic_goose.aetherium.item.clock_data.ClockData;

import java.util.function.UnaryOperator;

public class ModDataComponents {

    public static final DataComponentType<Integer> CHARGE =
            register("charge", builder -> builder
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.VAR_INT));

    public static final DataComponentType<ClockData> CLOCK_DATA =
            register("clock_data", builder -> builder
                    .persistent(ClockData.CODEC)
                    .networkSynchronized(ClockData.STREAM_CODEC));

    public static final DataComponentType<Integer> TOOL_CHARGE = register("tool_charge", builder -> builder
            .persistent(Codec.intRange(0, Integer.MAX_VALUE))
            .networkSynchronized(ByteBufCodecs.VAR_INT));

    public static final DataComponentType<Integer> ARMOR_CHARGE = register("armor_charge", builder -> builder
            .persistent(Codec.intRange(0, Integer.MAX_VALUE))
            .networkSynchronized(ByteBufCodecs.VAR_INT));

    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> op) {
        return Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                Aetherium.id(name),
                op.apply(DataComponentType.builder()).build());
    }

    public static void init() {
        // forces static init
    }
}