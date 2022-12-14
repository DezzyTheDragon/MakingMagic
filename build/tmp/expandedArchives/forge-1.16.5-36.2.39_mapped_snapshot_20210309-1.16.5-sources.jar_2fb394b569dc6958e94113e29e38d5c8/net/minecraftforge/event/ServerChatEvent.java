/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.event;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * ServerChatEvent is fired whenever a C01PacketChatMessage is processed. <br>
 * This event is fired via {@link ForgeHooks#onServerChatEvent(NetHandlerPlayServer, String, ITextComponent)},
 * which is executed by the {@link NetHandlerPlayServer#processChatMessage(CPacketChatMessage)}<br>
 * <br>
 * {@link #username} contains the username of the player sending the chat message.<br>
 * {@link #message} contains the message being sent.<br>
 * {@link #player} the instance of EntityPlayerMP for the player sending the chat message.<br>
 * {@link #component} contains the instance of ChatComponentTranslation for the sent message.<br>
 * <br>
 * This event is {@link Cancelable}. <br>
 * If this event is canceled, the chat message is never distributed to all clients.<br>
 * <br>
 * This event does not have a result. {@link HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
@Cancelable
public class ServerChatEvent extends net.minecraftforge.eventbus.api.Event
{
    private final String message, username;
    private final ServerPlayerEntity player;
    private ITextComponent component;
    public ServerChatEvent(ServerPlayerEntity player, String message, ITextComponent component)
    {
        super();
        this.message = message;
        this.player = player;
        this.username = player.getGameProfile().getName();
        this.component = component;
    }

    public void setComponent(ITextComponent e)
    {
        this.component = e;
    }

    public ITextComponent getComponent()
    {
        return this.component;
    }

    public String getMessage() { return this.message; }
    public String getUsername() { return this.username; }
    public ServerPlayerEntity getPlayer() { return this.player; }
}
