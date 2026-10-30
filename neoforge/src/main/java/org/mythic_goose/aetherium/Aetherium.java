package org.mythic_goose.aetherium;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.mythic_goose.aetherium.api.energy_system.armor.ChargedArmorItem;
import org.mythic_goose.aetherium.component.ModDataComponents;
import org.mythic_goose.aetherium.component.NeoForgeDataComponentRegistrar;
import org.mythic_goose.aetherium.entity.astralex.AstralexBoss;
import org.mythic_goose.aetherium.entity.cavern_cow.CavernCowAnimal;
import org.mythic_goose.aetherium.init.*;
import org.mythic_goose.amalgamation.api.registry_v1.NeoForgeCreativeTabRegistrar;
import org.mythic_goose.amalgamation.api.registry_v1.NeoForgeRegistrationBackend;
import org.mythic_goose.amalgamation.platform.NeoForgeAttachmentHelper;

@Mod(AetheriumConstants.MOD_ID)
public class Aetherium {

    public Aetherium(IEventBus eventBus) {

        NeoForgeRegistrationBackend.init(eventBus);
        NeoForgeAttachmentHelper.init(AetheriumConstants.MOD_ID, eventBus);

        AetheriumCommon.init();

        var menuRegistrar = new NeoForgeMenuRegistrar();
        AetheriumMenuTypes.init(menuRegistrar);
        menuRegistrar.registerToBus(eventBus);

        var componentRegistrar = new NeoForgeDataComponentRegistrar(AetheriumConstants.MOD_ID);
        ModDataComponents.init(componentRegistrar);
        componentRegistrar.registerToBus(eventBus);

        var tabRegistrar = new NeoForgeCreativeTabRegistrar(AetheriumConstants.MOD_ID);
        AetheriumCreativeTab.init(tabRegistrar);
        tabRegistrar.registerToBus(eventBus);

        eventBus.addListener(this::commonSetup);
        eventBus.addListener(Aetherium::onEntityAttributeCreation);
        eventBus.addListener(this::addCreative);

        NeoForgeAethValues.init();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        AetheriumCommon.commonSetup();
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(AetheriumEntities.CAVERN_COW.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                CavernCowAnimal::checkMobSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.OR);
    }

    @SubscribeEvent
    public void onLivingDamagePost(LivingDamageEvent.Post event) {
        LivingEntity entity = event.getEntity();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) continue;
            ItemStack piece = entity.getItemBySlot(slot);
            if (piece.getItem() instanceof ChargedArmorItem armor) {
                armor.drain(piece, armor.chargePerHit());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            AstralexBoss.removePlayerFromAll(player);
        }
    }

    private static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(AetheriumEntities.ASTRALEX_BOSS.get(), AstralexBoss.createAttributes().build());
        event.put(AetheriumEntities.CAVERN_COW.get(), CavernCowAnimal.createAttributes().build());
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(AetheriumItems.CAVERN_COW_SPAWN_EGG.get());
        }
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(AetheriumItems.MEDICINE_TECHNOLOGY_DISC.get());
        }
    }
}