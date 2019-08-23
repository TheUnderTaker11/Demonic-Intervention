package com.theundertaker11.demonicintervention.api.infusion;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.theundertaker11.demonicintervention.capability.infusions.IInfusions;
import com.theundertaker11.demonicintervention.capability.infusions.InfusionsCapabilityProvider;
import com.theundertaker11.demonicintervention.capability.vampiredata.IVampireData;
import com.theundertaker11.demonicintervention.capability.vampiredata.VampireDataCapabilityProvider;
import com.theundertaker11.demonicintervention.infusions.Infusions;

import net.minecraft.client.entity.EntityPlayerSP;
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
	/**
	 * Add an infusion. Ideally this will also handle all special cases, such as vampires needing progression and blood level set.
	 * @param player
	 * @param infusion
	 */
	public static void addInfusion(EntityPlayer player, Infusion infusion)
	{
		addingHandleSpecialCases(player, infusion);
		if(player instanceof EntityPlayerSP) {
			getIInfusions(player).addInfusion(infusion);
			return;
		}
		
		getIInfusions(player).addInfusion(infusion, player);
	}
	/**
	 * Ideally will handle all special cases, such as removing hearts when it was an alpha vampire etc.
	 * @param player
	 * @param infusion
	 */
	public static void removeInfusion(EntityPlayer player, Infusion infusion)
	{
		removalHandleSpecialCases(player, infusion);
		if(player instanceof EntityPlayerSP) {
			getIInfusions(player).removeInfusion(infusion);
			return;
		}
		
		getIInfusions(player).removeInfusion(infusion, player);
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
		return (Infusion) InfusionRegistry.infusionMap.get(id);
	}
	
	private static void removalHandleSpecialCases(EntityPlayer player, Infusion infusion) {
		if(infusion == Infusions.vampirism) {
			IVampireData data = getVampireData(player);
			if(data != null) {
				data.setIsAlphaVampire(false, player);
				data.setBloodLevel(0, player);
				data.setIsDaytime(false);
				data.setVampProgressionLevel(0, player);
			}
		}
	}
	
	private static void addingHandleSpecialCases(EntityPlayer player, Infusion infusion) {
		IVampireData data = getVampireData(player);
		if(data != null) {
			if(infusion == Infusions.vampirism) {
				data.setVampProgressionLevel(2, player);
				data.setBloodLevel(1000, player);
			}
		}
	}
	
	public static boolean hasConflictingInfusions(EntityPlayer player, Infusion infusion) {
		IInfusions infusions = getIInfusions(player);
		if(infusions.hasInfusion(infusion))
			return true;
		//For Vampire Hunter
		if(infusion == Infusions.vampireHunter) {
			if(infusions.hasInfusion(Infusions.vampirism))
				return true;
		}
		//For Vampires
		if(infusion == Infusions.vampirism) {
			if(infusions.hasInfusion(Infusions.vampireHunter))
				return true;
		}
		return false;
	}
/* ------------------------------------------------	End Infusion things ------------------------------------------------*/
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
/* ------------------------------------------------Vampire Data things start------------------------------------------------*/
	/**
	 * Gets IVampireData capability of the player
	 * @param player
	 * @return IVampireData capability, could be null
	 */
	@Nullable
	public static IVampireData getVampireData(EntityLivingBase entity) {
		return entity.getCapability(VampireDataCapabilityProvider.VampireDATA_CAPABILITY, null);
	}
	/**
	 * Adds blood the the player, only if they are a vampire of course.
	 * @param player
	 * @return The amount of blood that could not be added.
	 */
	public static int addBlood(EntityPlayer player, int amount) {
		if(hasInfusion(Infusions.vampirism, player) && getVampireData(player) != null) {
			return getVampireData(player).addBlood(amount);
		}
		return amount;
	}
	
	public static boolean isAlphaVampire(EntityPlayer player) {
		if(hasInfusion(Infusions.vampirism, player) && getVampireData(player) != null && getVampireData(player).getIsAlphaVampire()) {
			return true;
		}
		return false;
	}
	
	public static int getVampireProgressionLevel(EntityPlayer player) {
		if(getVampireData(player) != null)
			return getVampireData(player).getVampProgression();
		else return 0;
	}
/* ------------------------------------------------Vampire Data things end------------------------------------------------*/
}
