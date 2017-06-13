package com.theundertaker11.demonicintervention.api.infusion;

import com.theundertaker11.demonicintervention.capability.infusions.IInfusions;

import net.minecraft.entity.player.EntityPlayer;

/**
 * An infusion is something you put on the player that stays till it is removed, usually by some removal ritual.
 * In special cases it can be removed on death though. Curses work the same as infusions in code but are negative.
 * <p>
 * This should be extended by any class you are creating to make a new infusion. Then use InfusionRegistry to register that class
 * @author TheUnderTaker11
 *
 */
public class Infusion{
	
	/**If the infusion is mostly/fully negative. */
	public final boolean isCurse;
	
	/**String name to be shown to the player */
	private String nameForShow;
	
	/**If the infusion should be called every player tick or not */
	private boolean tickable;
	
	/**Level of the given infusion, could be good as in higher vampire levels, or bad as in a higher curse level*/
	private short level;
	
	private int ID;
	
	public Infusion(String nameForShow, boolean isCurse)
	{
		this.nameForShow = nameForShow;
		this.isCurse = isCurse;
	}
	
	/**
	 * Used to get the name that will be shown to players
	 * @return
	 */
	public String getNameForShow()
	{
		return this.nameForShow;
	}
	/**
	 * Can be used to change the name at runtime, if ever needed.
	 * @param name
	 */
	public void setNameForShow(String name)
	{
		this.nameForShow = name;
	}
	
	/**
	 * Used to get the string to be used to identify this curse in code.
	 * @return Unique String for the curse
	 */
	public int getID()
	{
		return this.ID;
	}
	
	protected void setID(int id)
	{
		this.ID = id;
	}
	
	public boolean shouldBeTicked()
	{
		return this.tickable;
	}
	/**
	 * Makes it so this infusion is called on every tick for each player that has it.
	 */
	public void setInfusionTickable()
	{
		this.tickable = true;
	}
	/**
	 * If tickable=true, then this will be called each tick for every player online (Both client and server side).
	 */
	public void tickInfusion(EntityPlayer player, IInfusions iInfusions){}
	
	/**
	 * Sets level of the infusion. Not all infusions will use different levels
	 * @param level
	 */
	public void setLevel(short level)
	{
		this.level = level;
	}
	
	/**
	 * Gets level of the infusion. Not all infusions will use different levels
	 */
	public int getLevel()
	{
		return this.level;
	}
}
