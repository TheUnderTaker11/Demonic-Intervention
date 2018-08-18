package com.theundertaker11.demonicintervention.api.infusion;

import com.theundertaker11.demonicintervention.capability.infusions.IInfusions;

import net.minecraft.client.resources.I18n;
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
	
	/**String lang key, formatted before being shown to player */
	private String nameForShow;

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
		return I18n.format(this.nameForShow);
	}
	/**
	 * Can be used to change the lang key at runtime, if ever needed.
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
}
