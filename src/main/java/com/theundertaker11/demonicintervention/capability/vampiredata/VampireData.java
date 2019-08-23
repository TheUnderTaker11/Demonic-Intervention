package com.theundertaker11.demonicintervention.capability.vampiredata;

import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.capability.maxhealth.IMaxHealth;
import com.theundertaker11.demonicintervention.infusions.Infusions;
import com.theundertaker11.demonicintervention.init.DIConfig;
import com.theundertaker11.demonicintervention.proxy.packets.DIPacketHandler;
import com.theundertaker11.demonicintervention.proxy.packets.servertoclient.SyncVampireDataPacket;
import com.theundertaker11.demonicintervention.util.ModUtils;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class VampireData implements IVampireData{

	private int bloodLevel;
	private static final int maxBloodLevel = 10000;
	private boolean isDaytime;
	/** 0=Nothing, 1=Has Leaped, 2=Is Vampire, 3=Is Alpha */
	private int vampProgression;
	private static final int MAX_VAMP_PROGRESSION = 3;
	
	@Override
	public int getBloodLevel() {
		return this.bloodLevel;
	}

	@Override
	public void setBloodLevel(int amount) {
		this.bloodLevel = amount;
		if(bloodLevel>getMaxBloodLevel()) {
			this.bloodLevel = getMaxBloodLevel();
		}
	}

	/**
	 * Returns amount of blood that could not be added.
	 */
	@Override
	public int addBlood(int amount) {
		int cantAdd = 0;
		this.bloodLevel+=amount;
		if(bloodLevel>getMaxBloodLevel()) {
			cantAdd = bloodLevel - getMaxBloodLevel();
			this.bloodLevel = getMaxBloodLevel();
		}
		return cantAdd;
	}

	@Override
	public int removeBlood(int amount) {
		this.bloodLevel-=amount;
		if(bloodLevel<0) {
			//Will be negative so just adding them together works
			int amountRemoved = amount + this.bloodLevel;
			this.bloodLevel = 0;
			return amountRemoved;
		}
		return amount;
	}

	@Override
	public boolean getIsAlphaVampire() {
		return (this.vampProgression == 3);
	}

	@Override
	public void setIsAlphaVampire(boolean isAlpha) {
			if(isAlpha)
				this.vampProgression = 3;
			else
				this.vampProgression = 2;
	}

	@Override
	public int getMaxBloodLevel() {
		if(this.getIsAlphaVampire())
			return this.maxBloodLevel*2;
		else 
			return this.maxBloodLevel;
	}

	@Override
	public int getMaxBloodLevel(EntityLivingBase entity) {
		if(entity instanceof EntityPlayer) {
			if(!InfusionUtils.hasInfusion(Infusions.vampirism, (EntityPlayer)entity))
				return maxBloodLevel/8;//1250
		}
		if(entity instanceof EntityVillager) {
			return maxBloodLevel/10;
		}
		if(entity == null) {
			return maxBloodLevel/10;
		}
		return 0;
	}
	
	@Override
	public void setBloodLevel(int amount, EntityPlayer player) {
		setBloodLevel(amount);
		sendSyncPacket(player);
	}

	@Override
	public int addBlood(int amount, EntityPlayer player) {
		addBlood(amount);
		sendSyncPacket(player);
		return 0;
	}

	@Override
	public int removeBlood(int amount, EntityPlayer player) {
		int amountRemoved = removeBlood(amount);
		sendSyncPacket(player);
		return amountRemoved;
	}

	@Override
	public void setIsAlphaVampire(boolean isAlpha, EntityPlayer player) {
		IMaxHealth hearts = ModUtils.getIMaxHealth((EntityLivingBase) player);
		if(hearts != null && this.getIsAlphaVampire() != isAlpha) {
			if(isAlpha)
				hearts.addBonusMaxHealth(30);
			else
				hearts.addBonusMaxHealth(-30);
			//hearts.setBonusMaxHealth(0);
		}
		setIsAlphaVampire(isAlpha);
		sendSyncPacket(player);
	}

	@Override
	public int drinkBloodFromTarget(int amount, EntityPlayer player, EntityLivingBase target, boolean simulate) {
		if(InfusionUtils.getVampireData(target) != null) {
			IVampireData targetData = InfusionUtils.getVampireData(target);
			if(target instanceof EntityPlayer) {
				if(InfusionUtils.hasInfusion(Infusions.vampirism, (EntityPlayer)target)) {
					return 0;
				}
				else{
					 if(!simulate) 
						 return (amount - addBlood(targetData.removeBlood(amount, (EntityPlayer)target), player));
					 else 
						 return 1;
				}
			}
			if(DIConfig.isDrinkableEntity(target)) {
				return (amount - addBlood(targetData.removeBlood(amount), player));
			}
				
		}
		
		return 0;
	}

	@Override
	public int addBloodNormalEntity(int amount) {
		int cantAdd = 0;
		this.bloodLevel+=amount;
		if(bloodLevel>getMaxBloodLevel(null)) {
			cantAdd = bloodLevel - getMaxBloodLevel(null);
			this.bloodLevel = getMaxBloodLevel(null);
		}
		return cantAdd;
	}

	@Override
	public boolean isDaytime() {
		return this.isDaytime;
	}
	
	@Override
	public void setIsDaytime(boolean isDaytime) {
		this.isDaytime = isDaytime;
	}
	
	private void sendSyncPacket(EntityPlayer player) {
		if(!(player instanceof EntityPlayerSP))
			DIPacketHandler.INSTANCE.sendTo(new SyncVampireDataPacket(player), (EntityPlayerMP) player);
	}

	@Override
	public int getVampProgression() {
		if(this.getIsAlphaVampire()) {
			this.vampProgression = 3;
		}
		return this.vampProgression;
	}

	@Override
	public void addVampProgressionLevel(EntityPlayer player) {
		this.addVampProgressionLevel();
		if(this.vampProgression == 3) {
			this.setIsAlphaVampire(true, player);
		}
		sendSyncPacket(player);
	}
	/**
	 * Make sure this is changed if different vampire levels are added past 0-3. 
	 * <p>
	 * 0=Nothing, 1=Has Leaped, 2=Is Vampire, 3=Is Alpha
	 * <p>
	 * Handles the fact that 3 makes them alpha aka adds/removes hearts accordingly.
	 */
	@Override
	public void setVampProgressionLevel(int level, EntityPlayer player) {
		if(this.vampProgression == level)
			return;
		
		int oldLevel = this.vampProgression;
		this.setVampProgressionLevel(level);
		if(oldLevel == 3 && level < 3) {
			this.setIsAlphaVampire(false, player);
		}
		else if(oldLevel < 3 && level ==3) {
			this.setIsAlphaVampire(true, player);
		}
		sendSyncPacket(player);
	}

	@Override
	public void addVampProgressionLevel() {
		if(this.vampProgression <= this.MAX_VAMP_PROGRESSION)
			this.vampProgression += 1;
	}

	@Override
	public void setVampProgressionLevel(int level) {
		this.vampProgression = level;
		if(this.vampProgression > this.MAX_VAMP_PROGRESSION)
			this.vampProgression = 3;
		if(this.vampProgression < 0)
			this.vampProgression = 0;
	}
	
	
}
