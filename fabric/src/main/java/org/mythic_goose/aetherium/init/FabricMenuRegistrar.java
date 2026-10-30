package org.mythic_goose.aetherium.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.mythic_goose.aetherium.AetheriumCommon;
import org.mythic_goose.amalgamation.api.menu_v1.ExtendedMenuType;
import org.mythic_goose.amalgamation.api.menu_v1.MenuRegistrar;

import java.util.function.Supplier;

public class FabricMenuRegistrar implements MenuRegistrar {
    @Override
    public <T extends ExtendedMenuType<?, ?>> Supplier<T> register(String name, T type) {
        Registry.register(BuiltInRegistries.MENU, AetheriumCommon.id(name), type);
        return () -> type;
    }
}