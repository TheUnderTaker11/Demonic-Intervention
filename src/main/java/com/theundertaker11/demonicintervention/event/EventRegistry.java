package com.theundertaker11.demonicintervention.event;

import com.theundertaker11.demonicintervention.capability.CapabilityHandler;
import com.theundertaker11.demonicintervention.infusions.InfusionNoFallDamage;

import net.minecraftforge.common.MinecraftForge;

public class EventRegistry {

	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
	}
}
