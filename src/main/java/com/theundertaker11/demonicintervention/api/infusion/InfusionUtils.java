package com.theundertaker11.demonicintervention.api.infusion;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.theundertaker11.demonicintervention.capability.infusions.IInfusions;
import com.theundertaker11.demonicintervention.capability.infusions.InfusionsCapabilityProvider;

import net.minecraft.entity.player.EntityPlayer;

public class InfusionUtils {
	
	public static int totalInfusionCount = 0;
	
	/**
	 * Get's the IInfusions capability off of the player
	 * @param player
	 * @return IInfusions capability, can be null
	 */
	@Nullable
	public static IInfusions getIInfusions(EntityPlayer player)
	{
		return player.getCapability(InfusionsCapabilityProvider.INFUSIONS_CAPABILITY, null);
	}
	/**
	 * Checks if the player has the given infusion, make sure to call this server-side only.
	 * @param infusion
	 * @param player
	 * @return
	 */
	public static boolean hasInfusion(@Nonnull Infusion infusion, EntityPlayer player)
	{
		IInfusions infusions = getIInfusions(player);
		if(infusions!=null)
		{
			return infusions.hasInfusion(infusion);
		}
		else return false;
	}
	
	@Nullable
	public static Infusion getInfusionFromID(int id)
	{
		if(InfusionRegistry.infusionMap.get(id) instanceof Infusion)
			return (Infusion)InfusionRegistry.infusionMap.get(id);
		else return null;
	}
}
