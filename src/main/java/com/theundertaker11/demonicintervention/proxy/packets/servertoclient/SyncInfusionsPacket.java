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

public class SyncInfusionsPacket implements IMessage {

	EntityPlayer player;
	private boolean isVampire = false;
	
	public SyncInfusionsPacket() {}
	
	public SyncInfusionsPacket(EntityPlayer Player) {
		player = Player;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		isVampire = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(InfusionUtils.hasInfusion(Infusions.vampirism, player));
	}

	public static class Handler implements IMessageHandler<SyncInfusionsPacket, IMessage> {

		@Override
		public IMessage onMessage(final SyncInfusionsPacket message, final MessageContext ctx) {
			
			EntityPlayerSP clientPlayer = Minecraft.getMinecraft().player;
			if(message.isVampire)
				InfusionUtils.addInfusion(clientPlayer, Infusions.vampirism);
			else
				InfusionUtils.removeInfusion(clientPlayer, Infusions.vampirism);
			/*
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.getEntityWorld();
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					EntityPlayerSP clientPlayer = Minecraft.getMinecraft().player;
					InfusionUtils.getIInfusions(clientPlayer).setInfusionList(InfusionUtils.getIInfusions(ctx.getServerHandler().player).getInfusionList());
				}
			});
			*/
			return null;
		}
	}
}