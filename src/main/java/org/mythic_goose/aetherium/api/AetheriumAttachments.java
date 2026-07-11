package org.mythic_goose.aetherium.api;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import org.mythic_goose.aetherium.Aetherium;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AetheriumAttachments {
    public static final AttachmentType<BigInteger> AETH_BALANCE =
            AttachmentRegistry.create(
                    Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "aeth_balance"),
                    builder -> builder
                            .initializer(() -> BigInteger.ZERO)
                            .persistent(Codec.STRING.xmap(BigInteger::new, BigInteger::toString))
                            .syncWith(
                                    ByteBufCodecs.STRING_UTF8.map(BigInteger::new, BigInteger::toString),
                                    AttachmentSyncPredicate.targetOnly()
                            )
            );

    // Ordered list of item-id strings the player has "learnt" at the Arcane Station.
    // Order = learn order, which is also the order shown in the station's browse grid.
    public static final AttachmentType<List<String>> AETH_KNOWN_ITEMS =
            AttachmentRegistry.create(
                    Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "aeth_known_items"),
                    builder -> builder
                            .initializer(ArrayList::new)
                            .persistent(Codec.STRING.listOf())
                            .syncWith(
                                    ByteBufCodecs.collection(ArrayList::new, ByteBufCodecs.STRING_UTF8),
                                    AttachmentSyncPredicate.targetOnly()
                            )
            );

    public static void register() {}
}