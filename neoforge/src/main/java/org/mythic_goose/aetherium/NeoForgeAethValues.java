package org.mythic_goose.aetherium;

import net.neoforged.neoforge.common.Tags;
import org.mythic_goose.aetherium.api.Aeth;
import org.mythic_goose.aetherium.api.AethValues;

public class NeoForgeAethValues {
    public static void init() {
        Aeth naturalBase = Aeth.ofDecimalString("0.001");
        AethValues.setTag(Tags.Items.SEEDS, naturalBase.add(Aeth.ofDecimalString("0.008")));
        AethValues.setTag(Tags.Items.GRAVELS, naturalBase.add(Aeth.ofDecimalString("0.009")).multiply(2));
        AethValues.setTag(Tags.Items.FLOWERS, naturalBase.add(Aeth.ofDecimalString("0.003").multiply(2)));
        AethValues.setTag(Tags.Items.DYES, naturalBase.add(Aeth.ofDecimalString("0.008").multiply(2)));
        AethValues.setTag(Tags.Items.SANDSTONE_SLABS, naturalBase.add(Aeth.ofDecimalString("0.002")).multiply(4).divide(2));

        Aeth rockBase = Aeth.ofDecimalString("0.01");
        AethValues.setTag(Tags.Items.STONES, rockBase.add(Aeth.ofDecimalString("0.005")));
        AethValues.setTag(Tags.Items.COBBLESTONES, rockBase);
        AethValues.setTag(Tags.Items.NETHERRACKS, rockBase);
    }
}
