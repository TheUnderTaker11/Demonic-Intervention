package com.theundertaker11.demonicintervention.capability.infusions;

import java.util.List;

import com.theundertaker11.demonicintervention.api.infusion.Infusion;

import net.minecraft.entity.player.EntityPlayer;

public interface IInfusions{

	public void addInfusion(Infusion infusion);
	
	/** Sends Sync packet to client */
	public void addInfusion(Infusion infusion, EntityPlayer player);
	
	public void removeInfusion(Infusion infusion);
	
	/** Sends Sync packet to client */
	public void removeInfusion(Infusion infusion, EntityPlayer player);
	
	public boolean hasInfusion(Infusion infusion);
	
	public void removeAllInfusions();
	
	/** Sends Sync packet to client */
	public void removeAllInfusions(EntityPlayer player);
	
	public List<Infusion> getInfusionList();
	
	public void setInfusionList(List<Infusion> list);
}
