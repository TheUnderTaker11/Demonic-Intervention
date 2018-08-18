package com.theundertaker11.demonicintervention.util;

import java.util.UUID;

public class DelayObj {
	private UUID ID;
	private final int tickMax;
	private int ticks;
	private String delayName;
	private boolean shouldCountDown = false;
	
	public DelayObj(UUID id, String cooldownName, int tickMax)
	{
		this.ID=id;
		this.tickMax=tickMax;
		this.ticks = tickMax;
		this.delayName=cooldownName;
	}
	
	public void removeTick()
	{
		this.ticks--;
		if(ticks<0)
			ticks=0;
	}
	public boolean shouldCountDown() {
		return this.shouldCountDown;
	}
	public void setShouldCountDown(boolean shouldCountDown) {
		this.shouldCountDown = shouldCountDown;
	}
	public void removeTick(int amount)
	{
		this.ticks-=amount;
		if(ticks<0)
			ticks=0;
	}
	public boolean isFinished()
	{
		return (this.ticks<=0);
	}
	public void reset() {
		this.ticks = this.tickMax;
	}
	public UUID getID()
	{
		return this.ID;
	}
	public String getName() {
		return this.delayName;
	}
}
