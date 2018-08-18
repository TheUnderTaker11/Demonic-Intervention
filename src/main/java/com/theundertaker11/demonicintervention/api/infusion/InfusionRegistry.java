package com.theundertaker11.demonicintervention.api.infusion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.theundertaker11.demonicintervention.infusions.InfusionNoFallDamage;
import com.theundertaker11.demonicintervention.infusions.InfusionVampirism;
import com.theundertaker11.demonicintervention.infusions.Infusions;

import net.minecraft.client.resources.I18n;
/**
 * Make sure your mod loads after mine or it could screw up a fair amount of things
 * @author TheUnderTaker11
 *
 */
public class InfusionRegistry {
	/**Quick access map of all infusion, infusion ID is the key to get the infusion object */
	public static HashMap infusionMap = new HashMap();
	
	private static int nextid = 0;
	public static void init()
	{
		registerInfusion(Infusions.vampirism);
		registerInfusion(Infusions.noFallDamage);
		registerInfusion(Infusions.vampireHunter);
	}
	/**
	 * To register an infusion, declare a static Infusion, then set that equal to this method with a new instantiation of your class
	 * as the parameter. Here is an example.
	 * <p>
	 * public static Infusion exampleInfusion;
	 * <p>
	 * exampleInfusion = registerInfusion(new ExampleInfusionClass());
	 * @param infusion
	 * @return
	 */
	public static <T extends Infusion> T registerInfusion(T infusion)
	{
		infusion.setID(nextid++);
		InfusionUtils.totalInfusionCount+=1;
		infusionMap.put(infusion.getID(), infusion);
		return infusion;
	}
}
