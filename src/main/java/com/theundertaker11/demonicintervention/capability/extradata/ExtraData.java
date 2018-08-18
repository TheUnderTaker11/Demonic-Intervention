package com.theundertaker11.demonicintervention.capability.extradata;

import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.capability.maxhealth.IMaxHealth;
import com.theundertaker11.demonicintervention.infusions.Infusions;
import com.theundertaker11.demonicintervention.proxy.packets.DIPacketHandler;
import com.theundertaker11.demonicintervention.proxy.packets.servertoclient.SyncExtraDataPacket;
import com.theundertaker11.demonicintervention.util.ModUtils;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class ExtraData implements IExtraData{

	private int bloodLevel;
	private static final int maxBloodLevel = 10000;
	private boolean isAlphaVampire;
	private boolean isDaytime;
	
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
		return this.isAlphaVampire;
	}

	@Override
	public void setIsAlphaVampire(boolean isAlpha) {
		this.isAlphaVampire = isAlpha;
	}

	@Override
	public int getMaxBloodLevel() {
		if(this.isAlphaVampire)
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
		if(entity instanceof EntityVillager || entity == null)
			return maxBloodLevel/8;//1250
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
		if(hearts != null && this.isAlphaVampire != isAlpha) {
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
		if(InfusionUtils.getExtraData(target) != null) {
			IExtraData targetData = InfusionUtils.getExtraData(target);
			
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
			
			if(target instanceof EntityVillager) {
				if(!simulate) 
					 return (amount - addBlood(targetData.removeBlood(amount), player));
				 else 
					 return 1;
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
			DIPacketHandler.INSTANCE.sendTo(new SyncExtraDataPacket(player), (EntityPlayerMP) player);
	}
}
