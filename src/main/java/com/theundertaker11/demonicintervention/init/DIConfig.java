package com.theundertaker11.demonicintervention.init;

import net.minecraftforge.common.config.Configuration;

public class DIConfig {

	public static boolean enableVampirism;
	
	
	public static void init(Configuration config)
	{
		enableVampirism = config.getBoolean("Enable Vampirism", "General", true, "Set to false to make it so players can't be vampires. Will also disable vampire hunters.");
		
	}
}
