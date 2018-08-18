package com.theundertaker11.demonicintervention.blocks.ritualmainblock;

import com.jcraft.jorbis.Block;
import com.theundertaker11.demonicintervention.init.BlockRegistry;
import com.theundertaker11.demonicintervention.tile.ItemStoringTileBase;

import net.minecraft.block.BlockQuartz;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileRitualMain extends ItemStoringTileBase{

	private int tier;
	public TileRitualMain() {super();}
	
	public ItemStack getStoredStack()
	{
		return itemStackHandler.getStackInSlot(0);
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
        compound.setInteger("tier", this.tier);
        return super.writeToNBT(compound);
	}
	
	//If you overwrite this make sure to call the super
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if (compound.hasKey("tier"))
		{
			this.tier = compound.getInteger("tier");
		}
	}
	/**
	 * Set this to a variable, cause it does the whole multiblock check each time this is called.
	 * 
	 * @return Negative if evil, positive if holy
	 */
	public int getTier() {
		this.updateTier();
		return this.tier;
	}
	/**
	 * Just gets what tier was last time the multiblock was updated.
	 * @return
	 */
	public int getTierNoUpdate() {
		return this.tier;
	}
	private void updateTier() {
		tier = 0;
		if(this.isTier1(true)) tier = -1;
		if(this.isTier1(false)) tier = 1;
		if(this.isTier2(true)) tier = -2;
		if(this.isTier2(false)) tier = 2;
		
	}
	public boolean isTier1(boolean isEvil) {
		if(!isEvil && checkTier1Good())
				return true;
		if(isEvil && checkTier1Evil())
				return true;

		return false;
	}
	public boolean isTier2(boolean isEvil) {
		
		return false;
	}
	
	private boolean checkTier1Good() {
		if(checkLava() && checkRingTier1Good() && checkPedestalsT1()) {
			return true;
		}
		return false;
	}
	private boolean checkTier1Evil() {
		if(checkLava() && checkRingTier1Evil() && checkPedestalsT1()) {
			return true;
		}
		return false;
	}
	private boolean checkLava() {
		if(world.getBlockState(pos.up()).getBlock() == Blocks.LAVA
				&& world.getBlockState(pos.up().north()).getBlock() == Blocks.LAVA
				&& world.getBlockState(pos.up().south()).getBlock() == Blocks.LAVA
				&& world.getBlockState(pos.up().east()).getBlock() == Blocks.LAVA
				&& world.getBlockState(pos.up().west()).getBlock() == Blocks.LAVA
				&& world.getBlockState(pos.up().north().east()).getBlock() == Blocks.LAVA
				&& world.getBlockState(pos.up().north().west()).getBlock() == Blocks.LAVA
				&& world.getBlockState(pos.up().south().east()).getBlock() == Blocks.LAVA
				&& world.getBlockState(pos.up().south().west()).getBlock() == Blocks.LAVA
				) {
			return true;
		}
		return false;
	}
	//How to find quartz pillar- world.getBlockState(pos.up().north(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().north(2)).getValue(BlockQuartz.VARIANT).getName().equals("lines_y")
	
	/**
	 * By ring I mean ring around the blocks of lava
	 * @return
	 */
	private boolean checkRingTier1Good() {
		if(world.getBlockState(pos.up().north(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().north(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().north(3)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().north(3)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().south(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().south(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().south(3)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().south(3)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().east(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().east(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().east(3)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().east(3)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().west(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().west(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().west(3)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().west(3)).getValue(BlockQuartz.VARIANT).getName().equals("default")

				&& world.getBlockState(pos.up().north(2).east()).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().north(3)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().south(2).east()).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().south(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().north(2).east(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().north(3)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().south(2).east(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().south(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				
				&& world.getBlockState(pos.up().north(2).west()).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().north(3)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().south(2).west()).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().south(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().north(2).west(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().north(3)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().south(2).west(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().south(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
					
				&& world.getBlockState(pos.up().east(2).south()).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().east(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().west(2).north()).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().west(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().east(2).north()).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().east(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().west(2).south()).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().west(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")

				){
			return true;
		}
		return false;
	}
	/**
	 * By ring I mean ring around the blocks of lava
	 * @return
	 */
	private boolean checkRingTier1Evil() {
		if(world.getBlockState(pos.up().north(2)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().north(3)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().south(2)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().south(3)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().west(2)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().west(3)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().east(2)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().east(3)).getBlock() == Blocks.RED_NETHER_BRICK
				
				&& world.getBlockState(pos.up().north(2).east()).getBlock() == Blocks.RED_NETHER_BRICK 
				&& world.getBlockState(pos.up().south(2).east()).getBlock() == Blocks.RED_NETHER_BRICK 
				&& world.getBlockState(pos.up().north(2).east(2)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().south(2).east(2)).getBlock() == Blocks.RED_NETHER_BRICK
				
				&& world.getBlockState(pos.up().north(2).west()).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().south(2).west()).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().north(2).west(2)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().south(2).west(2)).getBlock() == Blocks.RED_NETHER_BRICK
					
				&& world.getBlockState(pos.up().east(2).south()).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().west(2).north()).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().east(2).north()).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().west(2).south()).getBlock() == Blocks.RED_NETHER_BRICK

				) {
			return true;
		}
		return false;
	}
	
	private boolean checkPedestalsT1() {
		if(world.getBlockState(pos.up().north(3).up()).getBlock() == BlockRegistry.pedestal
			&& world.getBlockState(pos.up().east(3).up()).getBlock() == BlockRegistry.pedestal	
			&& world.getBlockState(pos.up().south(3).up()).getBlock() == BlockRegistry.pedestal
			&& world.getBlockState(pos.up().west(3).up()).getBlock() == BlockRegistry.pedestal
				) {
			return true;
		}
		return false;
	}
}
