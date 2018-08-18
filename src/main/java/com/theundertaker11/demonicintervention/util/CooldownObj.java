package com.theundertaker11.demonicintervention.util;

import java.util.UUID;
/**
 * This class is used to add cooldowns to entities. It doesn't handle any of it,
 * just is an object that can be used. <p>
 * 
 * The main event handler is what counts down each of these objects put in the list in ModUtils class
 * @author TheUnderTaker11
 *
 */
public class CooldownObj {
	
	private UUID ID;
	private int ticksLeft;
	private String CooldownName;
	
	public CooldownObj(UUID id, String cooldownName, int ticks)
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
	public UUID getID()
	{
		return this.ID;
	}
	public String getName() {
		return this.CooldownName;
	}
}
