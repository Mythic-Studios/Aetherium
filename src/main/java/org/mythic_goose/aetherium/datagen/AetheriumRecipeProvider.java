package org.mythic_goose.aetherium.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jspecify.annotations.NonNull;
import org.mythic_goose.aetherium.init.AetheriumItems;
import org.mythic_goose.aetherium.util.AetheriumItemTags;

import java.util.concurrent.CompletableFuture;

public class AetheriumRecipeProvider extends FabricRecipeProvider {
    public AetheriumRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @NonNull
    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registries, @NonNull RecipeOutput output) {
        return new UniqueRecipeProvider(registries, output);
    }

    @Override
    public @NonNull String getName() {
        return "Aetherium Recipes";
    }

    // Named (non-anonymous) subclass so we can add helper methods like templateSmithing
    private static class UniqueRecipeProvider extends RecipeProvider {
        public UniqueRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            super(registries, output);
        }

        @Override
        public void buildRecipes() {
            nineBlockStorageRecipes(RecipeCategory.MISC, AetheriumItems.COMPRESSED_VOIDMASS, RecipeCategory.MISC, AetheriumItems.VOIDMASS_BLOCK);

            shaped(RecipeCategory.MISC, AetheriumItems.EMPTY_CAPSULE)
                    .pattern("ngn")
                    .pattern("gag")
                    .pattern("ngn")
                    .define('n', Items.NETHERITE_INGOT)
                    .define('g', Items.GLASS)
                    .define('a', AetheriumItems.AETHERIUM_CHARGED_AMETHYST)
                    .unlockedBy(getHasName(AetheriumItems.AETHERIUM_CHARGED_AMETHYST), has(AetheriumItems.AETHERIUM_CHARGED_AMETHYST))
                    .group("capsule")
                    .save(output, "build_capsule");

            shaped(RecipeCategory.MISC, AetheriumItems.EMPTY_CAPSULE)
                    .pattern("na")
                    .define('n', Items.NETHERITE_INGOT)
                    .define('a', AetheriumItems.CAPSULE_FRAGMENT)
                    .unlockedBy(getHasName(AetheriumItems.CAPSULE_FRAGMENT), has(AetheriumItems.CAPSULE_FRAGMENT))
                    .group("capsule")
                    .save(output, "repair_capsule");

            shaped(RecipeCategory.MISC, AetheriumItems.AETHERIUM_CHARGED_AMETHYST)
                    .pattern("dad")
                    .pattern("aea")
                    .pattern("dad")
                    .define('a', Items.AMETHYST_SHARD)
                    .define('e', Items.EMERALD)
                    .define('d', AetheriumItems.AETHERIUM_DUST)
                    .unlockedBy(getHasName(AetheriumItems.AETHERIUM_DUST), has(AetheriumItems.AETHERIUM_DUST))
                    .group("aetherium")
                    .save(output);

            shaped(RecipeCategory.MISC, AetheriumItems.ARCANE_STATION)
                    .pattern(" a ")
                    .pattern("eoe")
                    .pattern("ooo")
                    .define('a', AetheriumItems.AETHERIUM_CHARGED_AMETHYST)
                    .define('e', Items.EMERALD)
                    .define('o', Items.OBSIDIAN)
                    .unlockedBy(getHasName(AetheriumItems.AETHERIUM_CHARGED_AMETHYST), has(AetheriumItems.AETHERIUM_CHARGED_AMETHYST))
                    .group("aetherium")
                    .save(output);

            shaped(RecipeCategory.MISC, AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE, 2)
                    .pattern("vtv")
                    .pattern("vev")
                    .pattern("vvv")
                    .define('t', AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE)
                    .define('e', Items.EMERALD)
                    .define('v', AetheriumItems.COMPRESSED_VOIDMASS)
                    .unlockedBy(getHasName(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE), has(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE))
                    .group("aetherium")
                    .save(output);

            shaped(RecipeCategory.MISC, AetheriumItems.CLOCK_OF_MATTER)
                    .pattern("bvb")
                    .pattern("vev")
                    .pattern("bvb")
                    .define('b', AetheriumItemTags.CLOCK_MODE_RECIPE)
                    .define('e', Items.CLOCK)
                    .define('v', AetheriumItems.COMPRESSED_VOIDMASS)
                    .unlockedBy(getHasName(AetheriumItems.COMPRESSED_VOIDMASS), has(AetheriumItems.COMPRESSED_VOIDMASS))
                    .group("aetherium")
                    .save(output);

            templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE, Items.NETHERITE_SWORD,
                    AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_SWORD);

            templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE, Items.NETHERITE_AXE,
                    AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_AXE);

            templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE, Items.NETHERITE_PICKAXE,
                    AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_PICKAXE);

            templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE, Items.NETHERITE_SHOVEL,
                    AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_SHOVEL);

            templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE, Items.NETHERITE_HOE,
                    AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_HOE);

            templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE, Items.NETHERITE_SPEAR,
                    AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_SPEAR);

            templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE, Items.NETHERITE_HELMET,
                    AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_HELMET);
            templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE, Items.NETHERITE_CHESTPLATE,
                    AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_CHESTPLATE);
            templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE, Items.NETHERITE_LEGGINGS,
                    AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_LEGGINGS);
            templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE, Items.NETHERITE_BOOTS,
                    AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_BOOTS);
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