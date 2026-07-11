package org.mythic_goose.aetherium.api;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class AethCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("aeth")
                    .then(Commands.literal("set")
                            .then(Commands.argument("amount", StringArgumentType.word())
                                    .executes(ctx -> {
                                        var player = ctx.getSource().getPlayerOrException();
                                        String amt = StringArgumentType.getString(ctx, "amount");
                                        AethHelper.set(player, Aeth.ofDecimalString(amt));
                                        ctx.getSource().sendSuccess(
                                                () -> Component.literal("Set Aeth to " + amt), false);
                                        return 1;
                                    }))));
        });
    }
}