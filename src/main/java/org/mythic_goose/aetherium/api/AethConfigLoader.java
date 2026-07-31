package org.mythic_goose.aetherium.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

public class AethConfigLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger("aetherium");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void load() {
        Path configDir = FabricLoader.getInstance().getConfigDir().resolve("Aetherium");
        Path file = configDir.resolve("aetherium_values.json");

        try {
            Files.createDirectories(configDir);

            if (!Files.exists(file)) {
                writeDefault(file);
                return; // nothing to override on a fresh install
            }

            int loaded = 0;
            try (Reader reader = Files.newBufferedReader(file)) {
                JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

                for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
                    String key = entry.getKey();
                    if (key.startsWith("_")) continue;

                    Identifier id = Identifier.tryParse(key);
                    if (id == null) {
                        LOGGER.warn("Aetherium config: invalid item id '{}', skipping", key);
                        continue;
                    }

                    Optional<Holder.Reference<Item>> holder = BuiltInRegistries.ITEM.get(id);
                    if (holder.isEmpty()) {
                        LOGGER.warn("Aetherium config: unknown item '{}', skipping", key);
                        continue;
                    }

                    Item item = holder.get().value();
                    BigDecimal amount = entry.getValue().getAsBigDecimal();
                    AethValues.set(item, Aeth.ofDecimalString(amount.toPlainString()));
                    loaded++;
                }
            }
            LOGGER.info("Aetherium: loaded {} value override(s) from values.json", loaded);
        } catch (IOException | RuntimeException e) {
            LOGGER.error("Aetherium: failed to load values.json config", e);
        }
    }

    private static void writeDefault(Path file) throws IOException {
        JsonObject example = new JsonObject();
        example.addProperty("_comment", "item id, value equals Aeth amount (Can be in decimals). This overrides built-in values or adds new items entirely (for mods). Diamond is set to its default value");
        example.addProperty("minecraft:diamond", 30);
        example.addProperty("modid:custom_item", 42.26);

        Files.writeString(file, GSON.toJson(example));
        LOGGER.info("Aetherium: wrote default values.json at {}", file);
    }
}
