package com.wfphantom.unpluggedsable.mixin;

import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommonPacketListenerImpl.class)
public abstract class ServerCommonPacketListenerImplMixin {
    @Unique
    private static final String UNPLUGGED_CONNECTION = "com.sakuraryoko.unplugged_afk.impl.player.unplugged.UnpluggedConnection";

    @Shadow @Final protected Connection connection;

    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;)V", at = @At("HEAD"), cancellable = true)
    private void unpluggedsable$skipFakeConnection(Packet<?> packet, @Nullable PacketSendListener listener, CallbackInfo ci) {
        if (this.connection.getClass().getName().equals(UNPLUGGED_CONNECTION)) ci.cancel();
    }
}