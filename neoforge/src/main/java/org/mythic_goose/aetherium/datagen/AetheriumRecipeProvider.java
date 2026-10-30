package org.mythic_goose.aetherium.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
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

public class AetheriumRecipeProvider extends RecipeProvider {
    public AetheriumRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new AetheriumRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "Aetherium Recipes";
        }
    }

    @Override
    public void buildRecipes() {
        nineBlockStorageRecipes(RecipeCategory.MISC, AetheriumItems.COMPRESSED_VOIDMASS.get(), RecipeCategory.MISC, AetheriumItems.VOIDMASS_BLOCK.get());

        shaped(RecipeCategory.MISC, AetheriumItems.EMPTY_CAPSULE.get())
                .pattern("ngn")
                .pattern("sas")
                .pattern("ngn")
                .define('n', Items.IRON_INGOT)
                .define('g', Items.GLASS)
                .define('s', Items.NETHERITE_SCRAP)
                .define('a', AetheriumItems.AETHERIUM_CHARGED_AMETHYST.get())
                .unlockedBy(getHasName(AetheriumItems.AETHERIUM_CHARGED_AMETHYST.get()), has(AetheriumItems.AETHERIUM_CHARGED_AMETHYST.get()))
                .group("capsule")
                .save(output, "build_capsule");

        shaped(RecipeCategory.MISC, AetheriumItems.EMPTY_CAPSULE.get())
                .pattern("na")
                .define('n', Items.NETHERITE_SCRAP)
                .define('a', AetheriumItems.CAPSULE_FRAGMENT.get())
                .unlockedBy(getHasName(AetheriumItems.CAPSULE_FRAGMENT.get()), has(AetheriumItems.CAPSULE_FRAGMENT.get()))
                .group("capsule")
                .save(output, "repair_capsule");

        shaped(RecipeCategory.MISC, AetheriumItems.AETHERIUM_CHARGED_AMETHYST.get())
                .pattern("dad")
                .pattern("aea")
                .pattern("dad")
                .define('a', Items.AMETHYST_SHARD)
                .define('e', Items.EMERALD)
                .define('d', AetheriumItems.AETHERIUM_DUST.get())
                .unlockedBy(getHasName(AetheriumItems.AETHERIUM_DUST.get()), has(AetheriumItems.AETHERIUM_DUST.get()))
                .group("aetherium")
                .save(output);

        shaped(RecipeCategory.MISC, AetheriumItems.ARCANE_STATION.get())
                .pattern(" a ")
                .pattern("eoe")
                .pattern("ooo")
                .define('a', AetheriumItems.AETHERIUM_CHARGED_AMETHYST.get())
                .define('e', Items.EMERALD)
                .define('o', Items.OBSIDIAN)
                .unlockedBy(getHasName(AetheriumItems.AETHERIUM_CHARGED_AMETHYST.get()), has(AetheriumItems.AETHERIUM_CHARGED_AMETHYST.get()))
                .group("aetherium")
                .save(output);

        shaped(RecipeCategory.MISC, AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), 2)
                .pattern("vtv")
                .pattern("vev")
                .pattern("vvv")
                .define('t', AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get())
                .define('e', Items.EMERALD)
                .define('v', AetheriumItems.COMPRESSED_VOIDMASS.get())
                .unlockedBy(getHasName(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get()), has(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get()))
                .group("aetherium")
                .save(output);

        shaped(RecipeCategory.MISC, AetheriumItems.CLOCK_OF_MATTER.get())
                .pattern("bvm")
                .pattern("vev")
                .pattern("svr")
                .define('b', Items.COOKED_BEEF)
                .define('m', Items.BONE_MEAL)
                .define('s', Items.RABBIT_FOOT)
                .define('r', Items.GLISTERING_MELON_SLICE)
                .define('e', Items.CLOCK)
                .define('v', AetheriumItems.COMPRESSED_VOIDMASS.get())
                .unlockedBy(getHasName(AetheriumItems.COMPRESSED_VOIDMASS.get()), has(AetheriumItems.COMPRESSED_VOIDMASS.get()))
                .group("aetherium")
                .save(output);

        shaped(RecipeCategory.MISC, AetheriumItems.ASTRAL_SHARD.get(), 1)
                .pattern("ff")
                .pattern("ff")
                .define('f', AetheriumItems.ASTRAL_FRAGMENTS.get())
                .unlockedBy(getHasName(AetheriumItems.ASTRAL_FRAGMENTS.get()), has(AetheriumItems.ASTRAL_FRAGMENTS.get()))
                .group("aetherium")
                .save(output);

        shaped(RecipeCategory.MISC, AetheriumItems.ANTIMATTER_DISC.get())
                .pattern("va")
                .pattern("av")
                .define('v', AetheriumItems.VOIDMASS_BLOCK.get())
                .define('a', AetheriumItems.ASTRAL_SHARD.get())
                .unlockedBy(getHasName(AetheriumItems.ASTRAL_SHARD.get()), has(AetheriumItems.ASTRAL_SHARD.get()))
                .group("aetherium")
                .save(output)
        ;

        shaped(RecipeCategory.MISC, AetheriumItems.ENERGY_TRANSFORMER.get())
                .pattern("bgb")
                .pattern("gag")
                .pattern("bgb")
                .define('b', Items.IRON_BLOCK)
                .define('g', Items.GOLD_BLOCK)
                .define('a', AetheriumItems.AETHERIUM_CHARGED_AMETHYST.get())
                .unlockedBy(getHasName(AetheriumItems.AETHERIUM_CHARGED_AMETHYST.get()), has(AetheriumItems.AETHERIUM_CHARGED_AMETHYST.get()))
                .save(output)
        ;

        shaped(RecipeCategory.MISC, AetheriumItems.SUMMONING_GEM.get())
                .pattern("sss")
                .pattern("sas")
                .pattern("sss")
                .define('s', AetheriumItems.ASTRAL_SHARD.get())
                .define('a', AetheriumItems.ASTRAL_STAR.get())
                .unlockedBy(getHasName(AetheriumItems.ASTRAL_STAR.get()), has(AetheriumItems.ASTRAL_STAR.get()))
                .unlockedBy(getHasName(AetheriumItems.ASTRAL_SHARD.get()), has(AetheriumItems.ASTRAL_SHARD.get()))
                .save(output)
        ;

        shaped(RecipeCategory.MISC, AetheriumItems.ENERGY_SEEDS.get(), 4)
                .pattern(" s ")
                .pattern("sos")
                .pattern(" s ")
                .define('s', AetheriumItems.AETHERIUM_SEEDS.get())
                .define('o', AetheriumItems.ENERGY_ORB.get())
                .unlockedBy(getHasName(AetheriumItems.AETHERIUM_SEEDS.get()), has(AetheriumItems.AETHERIUM_SEEDS.get()))
                .unlockedBy(getHasName(AetheriumItems.ENERGY_ORB.get()), has(AetheriumItems.ENERGY_ORB.get()))
                .save(output);
        ;


        // Voidmass Stuff
        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.DIAMOND_SWORD,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_SWORD.get());
        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.DIAMOND_AXE,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_AXE.get());
        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.DIAMOND_PICKAXE,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_PICKAXE.get());
        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.DIAMOND_SHOVEL,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_SHOVEL.get());
        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.DIAMOND_HOE,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_HOE.get());
        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.DIAMOND_SPEAR,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_SPEAR.get());

        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.DIAMOND,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_CHISEL.get());

        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.BOW,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_BOW.get());

        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.DIAMOND_HELMET,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_HELMET.get());
        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.DIAMOND_CHESTPLATE,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_CHESTPLATE.get());
        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.DIAMOND_LEGGINGS,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_LEGGINGS.get());
        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.DIAMOND_BOOTS,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_BOOTS.get());

        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.NETHERITE_HELMET,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_HELMET.get(), "netherite");
        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.NETHERITE_CHESTPLATE,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_CHESTPLATE.get(), "netherite");
        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.NETHERITE_LEGGINGS,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_LEGGINGS.get(), "netherite");
        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.NETHERITE_BOOTS,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_BOOTS.get(), "netherite");

        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.NETHERITE_SWORD,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_SWORD.get(), "netherite");
        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.NETHERITE_AXE,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_AXE.get(), "netherite");
        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.NETHERITE_PICKAXE,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_PICKAXE.get(), "netherite");
        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.NETHERITE_SHOVEL,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_SHOVEL.get(), "netherite");
        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.NETHERITE_HOE,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_HOE.get(), "netherite");
        templateSmithing(AetheriumItems.VOIDMASS_UPGRADE_TEMPLATE.get(), Items.NETHERITE_SPEAR,
                AetheriumItemTags.REPAIRS_VOIDMASS, RecipeCategory.COMBAT, AetheriumItems.VOIDMASS_SPEAR.get(), "netherite");

        // Astral
        templateSmithing(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get(), AetheriumItems.VOIDMASS_SPEAR.get(),
                AetheriumItemTags.REPAIRS_ASTRAL, RecipeCategory.COMBAT, AetheriumItems.ASTRAL_SPEAR.get());

        templateSmithing(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get(), AetheriumItems.VOIDMASS_AXE.get(),
                AetheriumItemTags.REPAIRS_ASTRAL, RecipeCategory.COMBAT, AetheriumItems.ASTRAL_AXE.get());

        templateSmithing(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get(), AetheriumItems.VOIDMASS_PICKAXE.get(),
                AetheriumItemTags.REPAIRS_ASTRAL, RecipeCategory.COMBAT, AetheriumItems.ASTRAL_PICKAXE.get());

        templateSmithing(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get(), AetheriumItems.VOIDMASS_SHOVEL.get(),
                AetheriumItemTags.REPAIRS_ASTRAL, RecipeCategory.COMBAT, AetheriumItems.ASTRAL_SHOVEL.get());

        templateSmithing(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get(), AetheriumItems.VOIDMASS_SWORD.get(),
                AetheriumItemTags.REPAIRS_ASTRAL, RecipeCategory.COMBAT, AetheriumItems.ASTRAL_SWORD.get());

        templateSmithing(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get(), AetheriumItems.VOIDMASS_HOE.get(),
                AetheriumItemTags.REPAIRS_ASTRAL, RecipeCategory.COMBAT, AetheriumItems.ASTRAL_HOE.get());

        templateSmithing(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get(), AetheriumItems.VOIDMASS_HELMET.get(),
                AetheriumItemTags.REPAIRS_ASTRAL, RecipeCategory.COMBAT, AetheriumItems.ASTRAL_HELMET.get());
        templateSmithing(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get(), AetheriumItems.VOIDMASS_CHESTPLATE.get(),
                AetheriumItemTags.REPAIRS_ASTRAL, RecipeCategory.COMBAT, AetheriumItems.ASTRAL_CHESTPLATE.get());
        templateSmithing(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get(), AetheriumItems.VOIDMASS_LEGGINGS.get(),
                AetheriumItemTags.REPAIRS_ASTRAL, RecipeCategory.COMBAT, AetheriumItems.ASTRAL_LEGGINGS.get());
        templateSmithing(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get(), AetheriumItems.VOIDMASS_BOOTS.get(),
                AetheriumItemTags.REPAIRS_ASTRAL, RecipeCategory.COMBAT, AetheriumItems.ASTRAL_BOOTS.get());

        templateSmithing(AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get(), AetheriumItems.VOIDMASS_BOW.get(),
                AetheriumItemTags.REPAIRS_ASTRAL, RecipeCategory.COMBAT, AetheriumItems.ASTRAL_BOW.get());

        shaped(RecipeCategory.MISC, AetheriumItems.FERTILIZER.get())
                .pattern("sas")
                .pattern("sss")
                .define('s', AetheriumItems.CAVERN_HIDE.get())
                .define('a', Items.BONE_MEAL)
                .unlockedBy(getHasName(AetheriumItems.CAVERN_HIDE.get()), has(AetheriumItems.CAVERN_HIDE.get()))
                .save(output)
        ;
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

    public void templateSmithing(final Item template, final Item base, TagKey<Item> material, final RecipeCategory category, final Item result, String alternative) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(template),
                        Ingredient.of(base),
                        this.tag(material),
                        category,
                        result
                ).unlocks("has_correct_material", this.has(material))
                .save(this.output, getItemName(result) + "_smithing_" + alternative);
    }
}