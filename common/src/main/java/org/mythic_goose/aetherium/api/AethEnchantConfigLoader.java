package org.mythic_goose.aetherium.api;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import org.mythic_goose.amalgamation.api.registry_v1.RegistryConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Optional;

public class AethEnchantConfigLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger("aetherium");

    // this entry sets the fallback for enchantments with no explicit value.
    private static final Identifier DEFAULT_ID = Identifier.fromNamespaceAndPath("aetherium", "default");

    private static final RegistryConfigLoader<ResourceKey<Enchantment>, BigDecimal> LOADER =
            RegistryConfigLoader.<ResourceKey<Enchantment>, BigDecimal>builder()
                    .configDir(Path.of("Aetherium"))
                    .fileName("aetherium_enchantment_values.json")
                    .logger(LOGGER)
                    .resolver(id -> Optional.of(ResourceKey.create(Registries.ENCHANTMENT, id)))
                    .valueParser(com.google.gson.JsonElement::getAsBigDecimal)
                    .setter((key, amount) -> {
                        Aeth value = Aeth.ofDecimalString(amount.toPlainString());
                        if (key.identifier().equals(DEFAULT_ID)) {
                            AethValues.setDefaultEnchantment(value);
                        } else {
                            AethValues.setEnchantment(key, value);
                        }
                    })
                    .defaultJson(() -> {
                        JsonObject example = new JsonObject();
                        example.addProperty("_comment", "enchantment id, value equals the Aeth cost of that enchantment at level I. Each higher level doubles it (Sharpness V = 16x). A book costs its base value plus the sum of its enchantments. 'aetherium:default' is the fallback for any enchantment not listed.");
                        example.addProperty("aetherium:default", 40);
                        example.addProperty("minecraft:mending", 400);
                        example.addProperty("minecraft:unbreaking", 30);
                        example.addProperty("minecraft:sharpness", 30);
                        return example;
                    })
                    .build();

    public static void load() {
        LOADER.load();
    }
}