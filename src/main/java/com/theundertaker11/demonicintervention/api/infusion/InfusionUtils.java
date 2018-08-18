package com.theundertaker11.demonicintervention.api.infusion;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.theundertaker11.demonicintervention.capability.extradata.ExtraDataCapabilityProvider;
import com.theundertaker11.demonicintervention.capability.extradata.IExtraData;
import com.theundertaker11.demonicintervention.capability.infusions.IInfusions;
import com.theundertaker11.demonicintervention.capability.infusions.InfusionsCapabilityProvider;
import com.theundertaker11.demonicintervention.infusions.Infusions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
/**
 * Bunch of methods to make manipulating infusions easier. <p>
 * Also includes methods for ExtraData since most/all of that relates to different infusions
 * @author TheUnderTaker11
 */
public class InfusionUtils {

	
/*------------------------------------------------ Start infusion things------------------------------------------------*/
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
	
	public static void addInfusion(EntityPlayer player, Infusion infusion)
	{
		getIInfusions(player).addInfusion(infusion);
	}
	
	public static void removeInfusion(EntityPlayer player, Infusion infusion)
	{
		removalHandleSpecialCases(player, infusion);
		getIInfusions(player).removeInfusion(infusion);
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
	
	private static void removalHandleSpecialCases(EntityPlayer player, Infusion infusion) {
		if(infusion == Infusions.vampirism) {
			IExtraData data = getExtraData(player);
			if(data != null) {
				data.setIsAlphaVampire(false, player);
				data.setBloodLevel(0, player);
				data.setIsDaytime(false);
			}
		}
	}
/* ------------------------------------------------	End Infusion things ------------------------------------------------*/
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
/* ------------------------------------------------Extra Data things start------------------------------------------------*/
	/**
	 * Gets IExtraData capability of the player
	 * @param player
	 * @return IExtraData capability, could be null
	 */
	@Nullable
	public static IExtraData getExtraData(EntityLivingBase player) {
		return player.getCapability(ExtraDataCapabilityProvider.EXTRADATA_CAPABILITY, null);
	}
	/**
	 * Adds blood the the player, only if they are a vampire of course.
	 * @param player
	 * @return The amount of blood that could not be added.
	 */
	public static int addBlood(EntityPlayer player, int amount) {
		if(hasInfusion(Infusions.vampirism, player) && getExtraData(player) != null) {
			return getExtraData(player).addBlood(amount);
		}
		return amount;
	}
	
	public static boolean isAlphaVampire(EntityPlayer player) {
		if(hasInfusion(Infusions.vampirism, player) && getExtraData(player) != null && getExtraData(player).getIsAlphaVampire()) {
			return true;
		}
		return false;
	}
	
/* ------------------------------------------------Extra Data things end------------------------------------------------*/
}
