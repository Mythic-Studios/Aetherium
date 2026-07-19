package org.mythic_goose.aetherium.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jspecify.annotations.NonNull;
import org.mythic_goose.aetherium.init.ModItems;
import org.mythic_goose.aetherium.util.AetheriumItemTags;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.data.recipes.RecipeProvider.getItemName;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @NonNull
    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registries, @NonNull RecipeOutput output) {
        return new AetheriumRecipeProvider(registries, output);
    }

    @Override
    public @NonNull String getName() {
        return "Aetherium Recipes";
    }

    // Named (non-anonymous) subclass so we can add helper methods like templateSmithing
    private static class AetheriumRecipeProvider extends RecipeProvider {
        public AetheriumRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            super(registries, output);
        }

        @Override
        public void buildRecipes() {
            nineBlockStorageRecipes(RecipeCategory.MISC, ModItems.COMPRESSED_VOIDMASS, RecipeCategory.MISC, ModItems.VOIDMASS_BLOCK);

            shaped(RecipeCategory.MISC, ModItems.EMPTY_CAPSULE)
                    .pattern("ngn")
                    .pattern("gag")
                    .pattern("ngn")
                    .define('n', Items.NETHERITE_INGOT)
                    .define('g', Items.GLASS)
                    .define('a', ModItems.AETHERIUM_CHARGED_AMETHYST)
                    .unlockedBy(getHasName(ModItems.AETHERIUM_CHARGED_AMETHYST), has(ModItems.AETHERIUM_CHARGED_AMETHYST))
                    .group("capsule")
                    .save(output, "build_capsule");

            shaped(RecipeCategory.MISC, ModItems.EMPTY_CAPSULE)
                    .pattern("na")
                    .define('n', Items.NETHERITE_INGOT)
                    .define('a', ModItems.CAPSULE_FRAGMENT)
                    .unlockedBy(getHasName(ModItems.CAPSULE_FRAGMENT), has(ModItems.CAPSULE_FRAGMENT))
                    .group("capsule")
                    .save(output, "repair_capsule");

            shaped(RecipeCategory.MISC, ModItems.AETHERIUM_CHARGED_AMETHYST)
                    .pattern("dad")
                    .pattern("aea")
                    .pattern("dad")
                    .define('a', Items.AMETHYST_SHARD)
                    .define('e', Items.EMERALD)
                    .define('d', ModItems.AETHERIUM_DUST)
                    .unlockedBy(getHasName(ModItems.AETHERIUM_DUST), has(ModItems.AETHERIUM_DUST))
                    .group("aetherium")
                    .save(output);

            shaped(RecipeCategory.MISC, ModItems.ARCANE_STATION)
                    .pattern(" a ")
                    .pattern("eoe")
                    .pattern("ooo")
                    .define('a', ModItems.AETHERIUM_CHARGED_AMETHYST)
                    .define('e', Items.EMERALD)
                    .define('o', Items.OBSIDIAN)
                    .unlockedBy(getHasName(ModItems.AETHERIUM_CHARGED_AMETHYST), has(ModItems.AETHERIUM_CHARGED_AMETHYST))
                    .group("aetherium")
                    .save(output);

            shaped(RecipeCategory.MISC, ModItems.VOIDMASS_UPGRADE_TEMPLATE, 2)
                    .pattern("vtv")
                    .pattern("vev")
                    .pattern("vvv")
                    .define('t', ModItems.VOIDMASS_UPGRADE_TEMPLATE)
                    .define('e', Items.EMERALD)
                    .define('v', ModItems.COMPRESSED_VOIDMASS)
                    .unlockedBy(getHasName(ModItems.VOIDMASS_UPGRADE_TEMPLATE), has(ModItems.VOIDMASS_UPGRADE_TEMPLATE))
                    .group("aetherium")
                    .save(output);

            templateSmithing(ModItems.VOIDMASS_UPGRADE_TEMPLATE, Items.NETHERITE_SWORD,
                    AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, ModItems.VOIDMASS_SWORD);

            templateSmithing(ModItems.VOIDMASS_UPGRADE_TEMPLATE, Items.NETHERITE_AXE,
                    AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, ModItems.VOIDMASS_AXE);

            templateSmithing(ModItems.VOIDMASS_UPGRADE_TEMPLATE, Items.NETHERITE_PICKAXE,
                    AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, ModItems.VOIDMASS_PICKAXE);

            templateSmithing(ModItems.VOIDMASS_UPGRADE_TEMPLATE, Items.NETHERITE_SHOVEL,
                    AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, ModItems.VOIDMASS_SHOVEL);

            templateSmithing(ModItems.VOIDMASS_UPGRADE_TEMPLATE, Items.NETHERITE_HOE,
                    AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, ModItems.VOIDMASS_HOE);

            templateSmithing(ModItems.VOIDMASS_UPGRADE_TEMPLATE, Items.NETHERITE_SPEAR,
                    AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, ModItems.VOIDMASS_SPEAR);
        }

        public void templateSmithing(final Item template, final Item base, TagKey<Item> material, final RecipeCategory category, final Item result) {
            SmithingTransformRecipeBuilder.smithing(
                            Ingredient.of(template),
                            Ingredient.of(base),
                            this.tag(material),
                            category,
                            result
                    ).unlocks("has_correct_material", this.has(material))
                    .save(this.output, getItemName(result) + "_smithing");
        }
    }
}