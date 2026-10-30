package org.mythic_goose.aetherium;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import org.mythic_goose.aetherium.api.*;

public class FabricAethValues {
    public static void init() {
        Aeth naturalBase = Aeth.ofDecimalString("0.001");
        AethValues.setTag(ConventionalItemTags.SEEDS, naturalBase.add(Aeth.ofDecimalString("0.008")));
        AethValues.setTag(ConventionalItemTags.GRAVELS, naturalBase.add(Aeth.ofDecimalString("0.009")).multiply(2));
        AethValues.setTag(ConventionalItemTags.FLOWERS, naturalBase.add(Aeth.ofDecimalString("0.003").multiply(2)));
        AethValues.setTag(ConventionalItemTags.DYES, naturalBase.add(Aeth.ofDecimalString("0.008").multiply(2)));
        AethValues.setTag(ConventionalItemTags.SANDSTONE_SLABS, naturalBase.add(Aeth.ofDecimalString("0.002")).multiply(4).divide(2));

        Aeth rockBase = Aeth.ofDecimalString("0.01");
        AethValues.setTag(ConventionalItemTags.STONES, rockBase.add(Aeth.ofDecimalString("0.005")));
        AethValues.setTag(ConventionalItemTags.COBBLESTONES, rockBase);
        AethValues.setTag(ConventionalItemTags.NETHERRACKS, rockBase);
    }
}
