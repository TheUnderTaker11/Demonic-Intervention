package com.theundertaker11.demonicintervention.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
/**
 * This class is used to add cooldowns to entities. It doesn't handle any of it,
 * just is an object that can be used
 *
 * @author TheUnderTaker11
 *
 */
public class CooldownObj {
	
	private String ID;
	private int ticksLeft;
	private String CooldownName;
	
	public CooldownObj(String id, String cooldownName, int ticks)
	{
		this.ID=id;
		this.ticksLeft=ticks;
		this.CooldownName=cooldownName;
	}
	
	public void removeTick()
	{
		this.ticksLeft--;
	}
	
	public void removeTick(int amount)
	{
		this.ticksLeft-=amount;
		if(ticksLeft<0)
			ticksLeft=0;
	}
	public boolean isFinished()
	{
		return (this.ticksLeft<=0);
	}
	public String getID()
	{
		return this.ID;
	}
}
