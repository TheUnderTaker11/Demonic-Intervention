package com.theundertaker11.demonicintervention.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.config.Configuration;

public class DIConfig {

	public static boolean enableVampirism;
	public static int bloodBottleAmount;
	private static String[] drinkableEntities;
	public static int tamingKarma;
	public static int killingVillagerKarma;
	public static int vampireDrinkingKarma;
	public static int killingZombieKarma;
	public static int villageKarmaRange;
	public static int karmaForVampireHunter;
	public static int karmaForVampire;
	public static int curingZombieKarma;
	public static int killingWitherKarma;
	public static int creatingIronGolemKarma;
	public static int killingAnimalKarma;
	public static int feedingAnimalKarma;
	
	public static void init(Configuration config)
	{
		final String infu = "Infusions";
		final String vamp = "Vampire Config";
		final String karma = "Karma Gain Tweaking";
		final String ritualKarma = "Karma for Rituals";
		
		//Vampire Config
		enableVampirism = config.getBoolean("Enable Vampirism", vamp, true, "Set to false to make it so players can't be vampires. Will also disable vampire hunters.");
		bloodBottleAmount = config.getInt("Blood bottle amount", vamp, 180, 10, 1000, "How much blood will each blood bottle hold. Also dictates how much a vampire drinks with each right click.");
		drinkableEntities = new String[2];
		drinkableEntities[0] = "entityplayer";
		drinkableEntities[1] = "entityvillager";
		drinkableEntities = config.getStringList("Vampire Drinkable Entities List", vamp, drinkableEntities, "All LOWERCASE only, put in the format of the 2 examples here.");
		
		//Getting Karma config
		tamingKarma = config.getInt("Karma gained from taming animals", karma, 200, 0, 10000000, "");
		killingVillagerKarma = config.getInt("Karma gained from killing villagers", karma, -30, -10000000, 0, "");
		vampireDrinkingKarma = config.getInt("Karma gained from drinking blood", karma, -5, -10000000, 0, "");
		killingZombieKarma = config.getInt("Karma gained from kill zombies in a village", karma, 5, 0, 10000000, "");
		villageKarmaRange = config.getInt("Distance past village radius", karma, 10, 0, 10000000, "The distance past normal village radius to be considered when doing Karma things");
		curingZombieKarma = config.getInt("Karma gained from curing a zombie villager", karma, 75, 0, 10000000, "");
		killingWitherKarma = config.getInt("Karma gained from killing/spawning wither", karma, 5, 0, 10000000, "Will be postive for killing, negative for spawning.");
		creatingIronGolemKarma = config.getInt("Karma gained from curing a zombie villager", karma, 15, 0, 10000000, "");
		killingAnimalKarma = config.getInt("Karma gained from killing animals", karma, -1, -10000000, 0, "");
		feedingAnimalKarma = config.getInt("Karma gained from feeding animals", karma, 1, 0, 10000000, "");
		
		//Required Karma for Rituals config
		karmaForVampireHunter = config.getInt("Minimum Karma to become Vampire Hunter", karma, 300, 0, 10000000, "");
		karmaForVampire = config.getInt("Max Karma to become Vampire", karma, -300, -10000000, 0, "");
	}
	
	public static boolean isDrinkableEntity(Entity entity) {
		if(entity instanceof EntityVillager)
			return true;
		for(String entry : drinkableEntities) {
			if(entry.equals(entity.getClass().getSimpleName().toLowerCase()))
				return true;
		}
		return false;
	}
}
