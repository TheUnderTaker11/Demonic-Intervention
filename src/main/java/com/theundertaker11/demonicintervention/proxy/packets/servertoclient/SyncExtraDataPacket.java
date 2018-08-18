package com.theundertaker11.demonicintervention.proxy.packets.servertoclient;

import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.infusions.Infusions;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncExtraDataPacket implements IMessage {

	EntityPlayer player;
	private boolean isAlpha;
	private int bloodLevel;
	
	public SyncExtraDataPacket() {}
	
	public SyncExtraDataPacket(EntityPlayer Player) {
		player = Player;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		isAlpha = buf.readBoolean();
		bloodLevel = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(InfusionUtils.isAlphaVampire(player));
		buf.writeInt(InfusionUtils.getExtraData(player).getBloodLevel());
	}

	public static class Handler implements IMessageHandler<SyncExtraDataPacket, IMessage> {

		@Override
		public IMessage onMessage(final SyncExtraDataPacket message, final MessageContext ctx) {
			
			EntityPlayerSP clientPlayer = Minecraft.getMinecraft().player;
			if(InfusionUtils.getExtraData(clientPlayer) != null)
			{
				InfusionUtils.getExtraData(clientPlayer).setIsAlphaVampire(message.isAlpha);
				InfusionUtils.getExtraData(clientPlayer).setBloodLevel(message.bloodLevel);
			}
			return null;
		}
	}
}