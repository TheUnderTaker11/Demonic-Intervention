package com.theundertaker11.demonicintervention.proxy.packets;

import com.theundertaker11.demonicintervention.Reference;
import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.proxy.packets.servertoclient.SyncVampireDataPacket;
import com.theundertaker11.demonicintervention.proxy.packets.servertoclient.SyncInfusionsPacket;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/**
 * Places to add packets
 * -Here
 * -Create it's own class
 * -PacketRegistry in init package
 * @author TheUnderTaker11
 *
 */
@EventBusSubscriber
public class DIPacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
	
	@SubscribeEvent
	public static void playerLogin(PlayerLoggedInEvent event) {
		if(!event.player.world.isRemote && InfusionUtils.getIInfusions(event.player) != null) {
			INSTANCE.sendTo(new SyncInfusionsPacket(event.player), (EntityPlayerMP) event.player);
			INSTANCE.sendTo(new SyncVampireDataPacket(event.player), (EntityPlayerMP) event.player);
		}
	}
	
	@SubscribeEvent
	public static void playerChangeDim(PlayerChangedDimensionEvent event) {
		if(!event.player.world.isRemote && InfusionUtils.getIInfusions(event.player) != null) {
			INSTANCE.sendTo(new SyncInfusionsPacket(event.player), (EntityPlayerMP) event.player);
			INSTANCE.sendTo(new SyncVampireDataPacket(event.player), (EntityPlayerMP) event.player);
		}
	}
	
	@SubscribeEvent
	public static void playerRespawn(PlayerRespawnEvent event) {
		if(!event.player.world.isRemote && InfusionUtils.getIInfusions(event.player) != null) {
			INSTANCE.sendTo(new SyncInfusionsPacket(event.player), (EntityPlayerMP) event.player);
			INSTANCE.sendTo(new SyncVampireDataPacket(event.player), (EntityPlayerMP) event.player);
		}
	}
	
}
