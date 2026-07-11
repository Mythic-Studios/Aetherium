package org.mythic_goose.aetherium.init;

import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.MenuType;
import org.mythic_goose.aetherium.Aetherium;
import org.mythic_goose.aetherium.menu.ArcaneStationMenu;
import org.mythic_goose.aetherium.menu.CurrencyExchangeMenu;

public class ModMenuTypes {
    public static final MenuType<ArcaneStationMenu> ARCANE_STATION_MENU_TYPE =
            Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "arcane_station_menu"),
                    new ExtendedMenuType<>(ArcaneStationMenu::new, BlockPos.STREAM_CODEC));

    public static final MenuType<CurrencyExchangeMenu> CURRENCY_EXCHANGE_MENU_TYPE =
            Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "currency_exchange_menu"),
                    new ExtendedMenuType<>(CurrencyExchangeMenu::new, BlockPos.STREAM_CODEC));


    public static void registerModMenuTypes() {
        Aetherium.LOGGER.info("Registering Mod Menu Types for " + Aetherium.MOD_ID);
    }
}