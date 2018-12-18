package com.theundertaker11.demonicintervention.capability.extradata;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public interface IExtraData {

	public int getBloodLevel();
	
	public void setBloodLevel(int amount);
	/**
	 * Sends Sync packet to player.
	 * @param amount
	 * @param player
	 */
	public void setBloodLevel(int amount, EntityPlayer player);
	/**
	 * Returns amount of blood that could not be added.
	 */
	public int addBlood(int amount);
	/**
	 * Used when regenerating blood in the non-vampire players/villagers
	 * @param amount
	 * @return
	 */
	public int addBloodNormalEntity(int amount);
	/**
	 * Returns amount of blood that could not be added.
	 * Sends Sync packet to player.
	 */
	public int addBlood(int amount, EntityPlayer player);
	/**
	 * Returns amount of blood that was removed.
	 */
	public int removeBlood(int amount);
	/**
	 * Returns amount of blood that was removed.
	 * Sends Sync packet to player.
	 */
	public int removeBlood(int amount, EntityPlayer player);
	/**
	 * Called from the vampire who is trying to drink the blood of the target.
	 * @param amount 
	 * @param player Needed to send sync packet, player you are calling this on.
	 * @param target Entity you are attempting to drain blood from.
	 * @param simulate False if you want to actually drink blood
	 * @return Amount of blood transfered
	 */
	public int drinkBloodFromTarget(int amount, EntityPlayer player, EntityLivingBase target, boolean simulate);
	
	public boolean getIsAlphaVampire();
	
	public void setIsAlphaVampire(boolean isAlpha);
	/**
	 * Sends Sync packet to player.
	 * @param isAlpha
	 * @param player
	 */
	public void setIsAlphaVampire(boolean isAlpha, EntityPlayer player);
	
	public int getMaxBloodLevel();
	/**
	 * Used when unsure if the player is a vampire. (Aka attempting to drink blood)
	 * @param entity
	 * @return
	 */
	public int getMaxBloodLevel(EntityLivingBase entity);
	
	public boolean isDaytime();
	
	public void setIsDaytime(boolean isDaytime);
	/** 0=Nothing, 1=Has Leaped, 2=Is Vampire, 3=Is Alpha */
	public int getVampProgression();
	/** 0=Nothing, 1=Has Leaped, 2=Is Vampire, 3=Is Alpha */
	public void addVampProgressionLevel();
	/** 0=Nothing, 1=Has Leaped, 2=Is Vampire, 3=Is Alpha */
	public void setVampProgressionLevel(int level);
	
	/** 0=Nothing, 1=Has Leaped, 2=Is Vampire, 3=Is Alpha */
	public void addVampProgressionLevel(EntityPlayer player);
	/** 0=Nothing, 1=Has Leaped, 2=Is Vampire, 3=Is Alpha */
	public void setVampProgressionLevel(int level, EntityPlayer player);
}
