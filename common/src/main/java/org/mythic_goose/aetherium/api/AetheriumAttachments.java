package org.mythic_goose.aetherium.api;

import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.amalgamation.api.attachment_v1.AttachmentSpec;
import org.mythic_goose.amalgamation.api.attachment_v1.CommonAttachment;
import org.mythic_goose.amalgamation.platform.Services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AetheriumAttachments {
    public static final CommonAttachment<BigInteger> AETH_BALANCE = Services.ATTACHMENTS.register(
            AttachmentSpec.<BigInteger>builder(
                    Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "aeth_balance"),
                    () -> BigInteger.ZERO
            ).copyOnDeath()
                    .persistent(Codec.STRING.xmap(BigInteger::new, BigInteger::toString))
                    .syncWith(ByteBufCodecs.STRING_UTF8.map(BigInteger::new, BigInteger::toString),
                            true // owner-only
                    )
                    .build()
    );

    public static final CommonAttachment<List<String>> AETH_KNOWN_ITEMS = Services.ATTACHMENTS.register(
            AttachmentSpec.<List<String>>builder(
                            Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "aeth_known_items"),
                            ArrayList::new
                    )
                    .copyOnDeath()
                    .persistent(Codec.STRING.listOf())
                    .syncWith(
                            ByteBufCodecs.collection(ArrayList::new, ByteBufCodecs.STRING_UTF8),
                            true // owner-only
                    )
                    .build()
    );

    public static void register() {}
}