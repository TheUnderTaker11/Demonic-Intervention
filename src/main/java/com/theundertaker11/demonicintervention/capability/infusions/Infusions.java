package com.theundertaker11.demonicintervention.capability.infusions;

import java.util.ArrayList;
import java.util.List;

import com.theundertaker11.demonicintervention.api.infusion.Infusion;
import com.theundertaker11.demonicintervention.proxy.packets.DIPacketHandler;
import com.theundertaker11.demonicintervention.proxy.packets.servertoclient.SyncInfusionsPacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class Infusions implements IInfusions{

	private List<Infusion> InfusionList = new ArrayList<Infusion>();
	
	@Override
	public void addInfusion(Infusion infusion, EntityPlayer player)
	{
		if(!InfusionList.contains(infusion))
		{
			InfusionList.add(infusion);
			if(player!=null)
				DIPacketHandler.INSTANCE.sendTo(new SyncInfusionsPacket(player), (EntityPlayerMP) player);
		}
	}

	@Override
	public void removeInfusion(Infusion infusion, EntityPlayer player)
	{
		InfusionList.remove(infusion);
		if(player!=null)
			DIPacketHandler.INSTANCE.sendTo(new SyncInfusionsPacket(player), (EntityPlayerMP) player);
	}

	@Override
	public void addInfusion(Infusion infusion)
	{
		InfusionList.add(infusion);	
	}

	@Override
	public void removeInfusion(Infusion infusion)
	{
		InfusionList.remove(infusion);
	}
	
	@Override
	public boolean hasInfusion(Infusion infusion)
	{
		return InfusionList.contains(infusion);
	}

	@Override
	public void removeAllInfusions()
	{
		InfusionList = new ArrayList<Infusion>();
	}

	@Override
	public List<Infusion> getInfusionList()
	{
		return InfusionList;
	}

	@Override
	public void setInfusionList(List<Infusion> list)
	{
		InfusionList = list;
	}

	@Override
	public void removeAllInfusions(EntityPlayer player) {
		removeAllInfusions();
		if(player!=null)
			DIPacketHandler.INSTANCE.sendTo(new SyncInfusionsPacket(player), (EntityPlayerMP) player);
	}
}
