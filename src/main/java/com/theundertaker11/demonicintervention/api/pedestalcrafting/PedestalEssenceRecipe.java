package com.theundertaker11.demonicintervention.api.pedestalcrafting;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class PedestalEssenceRecipe extends PedestalRecipe{
	/**If the ritual can only be done with a player as the target and not just any EntityLiving */
	private boolean onlyPlayers;
	
	public PedestalEssenceRecipe(String name, int tier, int cost, int time, short karmaType, boolean onlyPlayers) {
		super(name, tier, cost, time, karmaType);
		this.onlyPlayers = onlyPlayers;
	}
	
	public boolean isOnlyPlayers() {
		return this.onlyPlayers;
	}
}
