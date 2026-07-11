package org.mythic_goose.aetherium;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import org.mythic_goose.aetherium.api.AethTooltips;
import org.mythic_goose.aetherium.client.AethHud;
import org.mythic_goose.aetherium.init.ModMenuTypes;
import org.mythic_goose.aetherium.menu.ArcaneStationScreen;
import org.mythic_goose.aetherium.menu.CurrencyExchangeScreen;

public class AetheriumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AethHud.register();
        AethTooltips.register();

        MenuScreens.register(ModMenuTypes.ARCANE_STATION_MENU_TYPE, ArcaneStationScreen::new);
        MenuScreens.register(ModMenuTypes.CURRENCY_EXCHANGE_MENU_TYPE, CurrencyExchangeScreen::new);
    }
}
