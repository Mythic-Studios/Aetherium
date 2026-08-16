package org.mythic_goose.aetherium.api;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class AethCommands {
    /**
     * Well this setting is only enabled during testing, not in a beta or full release.
     * Keep dreaming as I haven't made this a OP only command yet.
     * There isn't a setting to enable this separately unless you're a mod dev,
     * If so just copy this class
     */
    private static final boolean enabled = true;

    public static void register() {
        if (enabled) {
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
        else {
            CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
                dispatcher.register(Commands.literal("aeth")
                        .then(Commands.literal("set")
                                .then(Commands.argument("amount", StringArgumentType.word())
                                        .executes(ctx -> {
                                            ctx.getSource().sendSuccess(
                                                    () -> Component.literal("Well that's a shame, This feature is disabled"), false);
                                            return 1;
                                        }))));
            });
        }
    }
}