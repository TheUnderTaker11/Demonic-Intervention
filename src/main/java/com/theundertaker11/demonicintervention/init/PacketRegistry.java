package com.theundertaker11.demonicintervention.init;

import com.theundertaker11.demonicintervention.proxy.packets.DIPacketHandler;
import com.theundertaker11.demonicintervention.proxy.packets.servertoclient.SyncInfusionsPacket;
import com.theundertaker11.demonicintervention.proxy.packets.servertoclient.SyncIsDaytime;
import com.theundertaker11.demonicintervention.proxy.packets.servertoclient.SyncVampireDataPacket;

import net.minecraftforge.fml.relauncher.Side;

public class PacketRegistry {
	
	private static int packetID = 0;
	public static void init() {
		DIPacketHandler.INSTANCE.registerMessage(SyncInfusionsPacket.Handler.class, SyncInfusionsPacket.class, packetID++, Side.CLIENT);
		DIPacketHandler.INSTANCE.registerMessage(SyncVampireDataPacket.Handler.class, SyncVampireDataPacket.class, packetID++, Side.CLIENT);
		DIPacketHandler.INSTANCE.registerMessage(SyncIsDaytime.Handler.class, SyncIsDaytime.class, packetID++, Side.CLIENT);
	}
}
