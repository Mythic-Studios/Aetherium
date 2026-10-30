package org.mythic_goose.aetherium.component;

import net.minecraft.core.component.DataComponentType;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface DataComponentRegistrar {
    <T> Supplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> op);
}