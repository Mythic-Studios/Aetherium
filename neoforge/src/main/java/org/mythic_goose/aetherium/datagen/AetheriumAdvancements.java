package org.mythic_goose.aetherium.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.advancements.triggers.ChangeDimensionTrigger;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.advancements.triggers.PlayerTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.init.AetheriumItems;
import org.mythic_goose.aetherium.util.AetheriumStructureTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AetheriumAdvancements extends AdvancementProvider {
    public AetheriumAdvancements(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new AetheriumCoreAdvancements()));
    }

    public static class AetheriumCoreAdvancements implements AdvancementSubProvider {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> output) {
            var items = registries.lookupOrThrow(Registries.ITEM);
            var blocks = registries.lookupOrThrow(Registries.BLOCK);
            var structures = registries.lookupOrThrow(Registries.STRUCTURE);

            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(
                            AetheriumItems.AETHERIUM_DUST.get(),
                            Component.translatable("advancements.aetherium_core.root.title"),
                            Component.translatable("advancements.aetherium_core.root.description"),
                            Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID,"gui/advancements/background/aetherium_core"),
                            AdvancementType.TASK,
                            false,
                            false,
                            false
                    )
                    .addCriterion("has_dust", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.AETHERIUM_DUST.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/root");


            AdvancementHolder charged = Advancement.Builder.advancement()
                    .parent(root)
                    .display(
                            AetheriumItems.AETHERIUM_CHARGED_AMETHYST.get(),
                            Component.translatable("advancements.aetherium_core.charged.title"),
                            Component.translatable("advancements.aetherium_core.charged.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_charged_amethyst", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.AETHERIUM_CHARGED_AMETHYST.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/charged_amethyst");

            AdvancementHolder capsule = Advancement.Builder.advancement()
                    .parent(charged)
                    .display(
                            AetheriumItems.EMPTY_CAPSULE.get(),
                            Component.translatable("advancements.aetherium_core.capsule.title"),
                            Component.translatable("advancements.aetherium_core.capsule.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_capsule", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.EMPTY_CAPSULE.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/capsule");

            AdvancementHolder voidmass = Advancement.Builder.advancement()
                    .parent(capsule)
                    .display(
                            AetheriumItems.COMPRESSED_VOIDMASS.get(),
                            Component.translatable("advancements.aetherium_core.voidmass_capsule.title"),
                            Component.translatable("advancements.aetherium_core.voidmass_capsule.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_capsule", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.FULL_CAPSULE_VOIDMASS.get())))
                    .addCriterion("has_voidmass", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.COMPRESSED_VOIDMASS.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/voidmass");

            AdvancementHolder voidmass_tools = Advancement.Builder.advancement()
                    .parent(voidmass)
                    .display(
                            AetheriumItems.VOIDMASS_SWORD.get(),
                            Component.translatable("advancements.aetherium_core.voidmass_alternative_tools.title"),
                            Component.translatable("advancements.aetherium_core.voidmass_alternative_tools.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_sword", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.VOIDMASS_SWORD.get())))
                    .addCriterion("has_spear", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.VOIDMASS_SPEAR.get())))
                    .addCriterion("has_pickaxe", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.VOIDMASS_PICKAXE.get())))
                    .addCriterion("has_axe", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.VOIDMASS_AXE.get())))
                    .addCriterion("has_shovel", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.VOIDMASS_SHOVEL.get())))
                    .addCriterion("has_chisel", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.VOIDMASS_CHISEL.get())))
                    .addCriterion("has_hoe", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.VOIDMASS_HOE.get())))
                    .addCriterion("has_bow", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.VOIDMASS_BOW.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/voidmass_alternative_tools");

            AdvancementHolder voidmass_armor = Advancement.Builder.advancement()
                    .parent(voidmass)
                    .display(
                            AetheriumItems.VOIDMASS_CHESTPLATE.get(),
                            Component.translatable("advancements.aetherium_core.voidmass_armor.title"),
                            Component.translatable("advancements.aetherium_core.voidmass_armor.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.VOIDMASS_HELMET.get())))
                    .addCriterion("has_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.VOIDMASS_CHESTPLATE.get())))
                    .addCriterion("has_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.VOIDMASS_LEGGINGS.get())))
                    .addCriterion("has_boots", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.VOIDMASS_BOOTS.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/voidmass_armor");

            AdvancementHolder voidmass_chisel = Advancement.Builder.advancement()
                    .parent(voidmass)
                    .display(
                            AetheriumItems.VOIDMASS_CHISEL.get(),
                            Component.translatable("advancements.aetherium_core.voidmass_chisel.title"),
                            Component.translatable("advancements.aetherium_core.voidmass_chisel.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_chisel", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.VOIDMASS_CHISEL.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/voidmass_chisel");

            AdvancementHolder astral = Advancement.Builder.advancement()
                    .parent(voidmass_chisel)
                    .display(
                            AetheriumItems.ASTRAL_SHARD.get(),
                            Component.translatable("advancements.aetherium_core.astral.title"),
                            Component.translatable("advancements.aetherium_core.astral.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_capsule", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.FULL_CAPSULE_ASTRAL.get())))
                    .addCriterion("has_astral", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ASTRAL_SHARD.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/astral");

            AdvancementHolder the_caverns = Advancement.Builder.advancement()
                    .parent(astral)
                    .display(
                            AetheriumItems.ENERGY_TRANSFORMER.get(),
                            Component.translatable("advancements.aetherium_core.the_caverns.title"),
                            Component.translatable("advancements.aetherium_core.the_caverns.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("in_caverns", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(
                            ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "the_caverns"))))
                    .save(output, AetheriumConstants.MOD_ID + ":core/the_caverns");

            AdvancementHolder shine_bright = Advancement.Builder.advancement()
                    .parent(voidmass_chisel)
                    .display(
                            AetheriumItems.ASTRAL_STAR.get(),
                            Component.translatable("advancements.aetherium_core.shine_bright.title"),
                            Component.translatable("advancements.aetherium_core.shine_bright.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_star", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ASTRAL_STAR.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/shine_bright");

            AdvancementHolder summoning_gem = Advancement.Builder.advancement()
                    .parent(shine_bright)
                    .display(
                            AetheriumItems.SUMMONING_GEM.get(),
                            Component.translatable("advancements.aetherium_core.summoning_gem.title"),
                            Component.translatable("advancements.aetherium_core.summoning_gem.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_gem", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.SUMMONING_GEM.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/summoning_gem");

            AdvancementHolder astral_dungeon = Advancement.Builder.advancement()
                    .parent(the_caverns)
                    .display(
                            AetheriumItems.ASTRAL_BRICKS.get(),
                            Component.translatable("advancements.aetherium_core.astral_dungeon.title"),
                            Component.translatable("advancements.aetherium_core.astral_dungeon.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("in_astral_dungeon", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location()
                                    .setStructures(registries.lookupOrThrow(Registries.STRUCTURE)
                                            .getOrThrow(AetheriumStructureTags.NEEDED_FOR_DUNGEON_ADVANCEMENT))))
                    .save(output, AetheriumConstants.MOD_ID + ":core/astral_dungeon");

            AdvancementHolder lock_key = Advancement.Builder.advancement()
                    .parent(astral_dungeon)
                    .display(
                            AetheriumItems.ASTRAL_KEY.get(),
                            Component.translatable("advancements.aetherium_core.lock_key.title"),
                            Component.translatable("advancements.aetherium_core.lock_key.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_lock_key", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ASTRAL_KEY.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/lock_key");

            AdvancementHolder astral_template = Advancement.Builder.advancement()
                    .parent(lock_key)
                    .display(
                            AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get(),
                            Component.translatable("advancements.aetherium_core.astral_template.title"),
                            Component.translatable("advancements.aetherium_core.astral_template.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_astral_template", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ASTRAL_UPGRADE_TEMPLATE.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/astral_template");

            AdvancementHolder tabled = Advancement.Builder.advancement()
                    .parent(charged)
                    .display(
                            AetheriumItems.ARCANE_STATION.get(),
                            Component.translatable("advancements.aetherium_core.physical_table.title"),
                            Component.translatable("advancements.aetherium_core.physical_table.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_arcane_station", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ARCANE_STATION.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/arcane_station");

            AdvancementHolder astral_tools = Advancement.Builder.advancement()
                    .parent(astral_template)
                    .display(
                            AetheriumItems.ASTRAL_SWORD.get(),
                            Component.translatable("advancements.aetherium_core.astral_tools.title"),
                            Component.translatable("advancements.aetherium_core.astral_tools.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_sword", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ASTRAL_SWORD.get())))
                    .addCriterion("has_spear", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ASTRAL_SPEAR.get())))
                    .addCriterion("has_pickaxe", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ASTRAL_PICKAXE.get())))
                    .addCriterion("has_axe", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ASTRAL_AXE.get())))
                    .addCriterion("has_shovel", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ASTRAL_SHOVEL.get())))
                    .addCriterion("has_hoe", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ASTRAL_HOE.get())))
                    .addCriterion("has_bow", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ASTRAL_BOW.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/astral_tools");

            AdvancementHolder astral_armor = Advancement.Builder.advancement()
                    .parent(astral_template)
                    .display(
                            AetheriumItems.ASTRAL_CHESTPLATE.get(),
                            Component.translatable("advancements.aetherium_core.astral_armor.title"),
                            Component.translatable("advancements.aetherium_core.astral_armor.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ASTRAL_HELMET.get())))
                    .addCriterion("has_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ASTRAL_CHESTPLATE.get())))
                    .addCriterion("has_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ASTRAL_LEGGINGS.get())))
                    .addCriterion("has_boots", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ASTRAL_BOOTS.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/astral_armor");

            AdvancementHolder fertile = Advancement.Builder.advancement()
                    .parent(the_caverns)
                    .display(
                            AetheriumItems.FERTILIZER.get(),
                            Component.translatable("advancements.aetherium_core.fertile.title"),
                            Component.translatable("advancements.aetherium_core.fertile.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_arcane_station", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.FERTILIZER.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/fertilizer");

            AdvancementHolder aetherium_seed = Advancement.Builder.advancement()
                    .parent(fertile)
                    .display(
                            AetheriumItems.AETHERIUM_SEEDS.get(),
                            Component.translatable("advancements.aetherium_core.aetherium_seed.title"),
                            Component.translatable("advancements.aetherium_core.aetherium_seed.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_arcane_station", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.AETHERIUM_SEEDS.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/aetherium_seed");

            AdvancementHolder energy_orb = Advancement.Builder.advancement()
                    .parent(the_caverns)
                    .display(
                            AetheriumItems.ENERGY_ORB.get(),
                            Component.translatable("advancements.aetherium_core.energy_orb.title"),
                            Component.translatable("advancements.aetherium_core.energy_orb.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_arcane_station", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ENERGY_ORB.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/energy_orb");

            AdvancementHolder energy_seed = Advancement.Builder.advancement()
                    .parent(energy_orb)
                    .display(
                            AetheriumItems.ENERGY_SEEDS.get(),
                            Component.translatable("advancements.aetherium_core.energy_seed.title"),
                            Component.translatable("advancements.aetherium_core.energy_seed.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_arcane_station", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.ENERGY_SEEDS.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/energy_seed");

            // Money Teirs
            AdvancementHolder crystal = Advancement.Builder.advancement()
                    .parent(root)
                    .display(
                            AetheriumItems.AETHERIUM_CRYSTAL.get(),
                            Component.translatable("advancements.aetherium_core.crystal_aetherium.title"),
                            Component.translatable("advancements.aetherium_core.crystal_aetherium.description"),
                            null,
                            AdvancementType.GOAL,
                            true,
                            false,
                            false
                    )
                    .addCriterion("has_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.AETHERIUM_CRYSTAL.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/crystal_teir");

            AdvancementHolder ingot = Advancement.Builder.advancement()
                    .parent(crystal)
                    .display(
                            AetheriumItems.AETHERIUM_INGOT.get(),
                            Component.translatable("advancements.aetherium_core.aetherium_ingot.title"),
                            Component.translatable("advancements.aetherium_core.aetherium_ingot.description"),
                            null,
                            AdvancementType.GOAL,
                            true,
                            false,
                            false
                    )
                    .addCriterion("has_aetherium", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.AETHERIUM_INGOT.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/ingot_teir");

            AdvancementHolder block = Advancement.Builder.advancement()
                    .parent(ingot)
                    .display(
                            AetheriumItems.AETHERIUM_BLOCK.get(),
                            Component.translatable("advancements.aetherium_core.aetherium_block.title"),
                            Component.translatable("advancements.aetherium_core.aetherium_block.description"),
                            null,
                            AdvancementType.GOAL,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_aetherium", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.AETHERIUM_BLOCK.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/block_teir");

            AdvancementHolder compress_block = Advancement.Builder.advancement()
                    .parent(block)
                    .display(
                            AetheriumItems.COMPRESS_AETHERIUM_BLOCK.get(),
                            Component.translatable("advancements.aetherium_core.compressed_aetherium_block.title"),
                            Component.translatable("advancements.aetherium_core.compressed_aetherium_block.description"),
                            null,
                            AdvancementType.GOAL,
                            true,
                            true,
                            false
                    )
                    .addCriterion("has_aetherium", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetheriumItems.COMPRESS_AETHERIUM_BLOCK.get())))
                    .save(output, AetheriumConstants.MOD_ID + ":core/compressed_aetherium_block");

        }
    }
}
