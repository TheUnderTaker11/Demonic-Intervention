package com.theundertaker11.demonicintervention.proxy.packets.servertoclient;

import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncIsDaytime implements IMessage {

	EntityPlayer player;
	private boolean isDaytime;
	
	public SyncIsDaytime() {}
	
	public SyncIsDaytime(EntityPlayer Player) {
		player = Player;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		isDaytime = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(InfusionUtils.getExtraData(player).isDaytime());
	}

	public static class Handler implements IMessageHandler<SyncIsDaytime, IMessage> {

		@Override
		public IMessage onMessage(final SyncIsDaytime message, final MessageContext ctx) {
			
			EntityPlayerSP clientPlayer = Minecraft.getMinecraft().player;
			if(InfusionUtils.getExtraData(clientPlayer) != null)
			{
				InfusionUtils.getExtraData(clientPlayer).setIsDaytime(message.isDaytime);
			}
			return null;
		}
	}
}