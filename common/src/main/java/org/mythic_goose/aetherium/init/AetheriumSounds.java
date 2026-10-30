package org.mythic_goose.aetherium.init;

import net.minecraft.sounds.SoundEvent;
import org.mythic_goose.amalgamation.api.registry_v1.RegistryEntry;
import org.mythic_goose.amalgamation.api.registry_v1.SoundRegistry;

public class AetheriumSounds extends SoundRegistry {

    public static RegistryEntry<SoundEvent> MEDICINE_TECHNOLOGY_KULAKOVKA;

    public static void init() {
        MEDICINE_TECHNOLOGY_KULAKOVKA = registerJukeboxSong("medicine_technology_kulakovka");
    }
}