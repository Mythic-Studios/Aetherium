package org.mythic_goose.aetherium.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.amalgamation.api.menu_v1.ExtendedMenuType;
import org.mythic_goose.amalgamation.api.menu_v1.MenuRegistrar;

import java.util.function.Supplier;

public class NeoForgeMenuRegistrar implements MenuRegistrar {
    private final DeferredRegister<MenuType<?>> menus =
            DeferredRegister.create(Registries.MENU, AetheriumConstants.MOD_ID);

    public void registerToBus(IEventBus bus) {
        menus.register(bus);
    }

    @Override
    public <T extends ExtendedMenuType<?, ?>> Supplier<T> register(String name, T type) {
        menus.register(name, () -> type);
        return () -> type;
    }
}