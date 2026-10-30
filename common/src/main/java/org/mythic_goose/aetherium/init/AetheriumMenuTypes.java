package org.mythic_goose.aetherium.init;

import net.minecraft.core.BlockPos;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.menu.ArcaneStationMenu;
import org.mythic_goose.aetherium.menu.CurrencyExchangeMenu;
import org.mythic_goose.amalgamation.api.menu_v1.ExtendedMenuType;
import org.mythic_goose.amalgamation.api.menu_v1.MenuRegistrar;

import java.util.function.Supplier;

public class AetheriumMenuTypes {

    public static Supplier<ExtendedMenuType<ArcaneStationMenu, BlockPos>> ARCANE_STATION_MENU_TYPE;
    public static Supplier<ExtendedMenuType<CurrencyExchangeMenu, BlockPos>> CURRENCY_EXCHANGE_MENU_TYPE;

    public static void init(MenuRegistrar registrar) {
        ARCANE_STATION_MENU_TYPE = registrar.register("arcane_station",
                new ExtendedMenuType<>(ArcaneStationMenu::new, BlockPos.STREAM_CODEC));

        CURRENCY_EXCHANGE_MENU_TYPE = registrar.register("currency_exchange",
                new ExtendedMenuType<>(CurrencyExchangeMenu::new, BlockPos.STREAM_CODEC));

        AetheriumConstants.LOG.info("Registering Mod Menu Types for " + AetheriumConstants.MOD_ID);
    }
}