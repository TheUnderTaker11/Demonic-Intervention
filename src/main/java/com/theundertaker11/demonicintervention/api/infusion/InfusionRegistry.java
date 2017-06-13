package com.theundertaker11.demonicintervention.api.infusion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.theundertaker11.demonicintervention.infusions.InfusionNoFallDamage;
import com.theundertaker11.demonicintervention.infusions.InfusionVampirism;

public class InfusionRegistry {
	/**Quick access map of all infusion, infusion ID is the key to get the infusion object */
	public static HashMap infusionMap = new HashMap();
	
	private static int nextid = -1;
	
	public static Infusion vampirism;
	public static Infusion noFallDamage;
	
	public static void init()
	{
		vampirism = registerInfusion(new InfusionVampirism("Curse of Vampirism"));
		noFallDamage = registerInfusion(new  InfusionNoFallDamage("Infusion of the Feather", false));
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
		infusion.setID(getNextInfusionID());
		infusionMap.put(infusion.getID(), infusion);
		return infusion;
	}
	
	/**
	 * Called to set ID's to all infusions.
	 * @return
	 */
	private static int getNextInfusionID()
	{
		nextid+=1;
		InfusionUtils.totalInfusionCount+=1;
		return nextid;
	}
}
