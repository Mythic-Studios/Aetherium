package org.mythic_goose.aetherium.api;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import org.mythic_goose.amalgamation.api.registry_v1.RegistryConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.nio.file.Path;

public class AethConfigLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger("aetherium");

    private static final RegistryConfigLoader<Item, BigDecimal> LOADER =
            RegistryConfigLoader.<net.minecraft.world.item.Item, java.math.BigDecimal>builder()
                    .configDir(Path.of("Aetherium"))
                    .fileName("aetherium_values.json")
                    .logger(LOGGER)
                    .resolver(id -> BuiltInRegistries.ITEM.get(id).map(net.minecraft.core.Holder.Reference::value))
                    .valueParser(com.google.gson.JsonElement::getAsBigDecimal)
                    .setter((item, amount) -> AethValues.set(item, Aeth.ofDecimalString(amount.toPlainString())))
                    .defaultJson(() -> {
                        JsonObject example = new JsonObject();
                        example.addProperty("_comment", "item id, value equals Aeth amount (Can be in decimals). This overrides built-in values or adds new items entirely (for mods). Diamond is set to its default value");
                        example.addProperty("minecraft:diamond", 30);
                        example.addProperty("modid:custom_item", 42.26);
                        return example;
                    })
                    .build();

    public static void load() {
        LOADER.load();
    }
}