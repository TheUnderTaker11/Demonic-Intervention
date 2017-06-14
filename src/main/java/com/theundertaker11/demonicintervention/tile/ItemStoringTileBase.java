package com.theundertaker11.demonicintervention.tile;

import com.theundertaker11.demonicintervention.blocks.BaseStorageBlock;
import com.theundertaker11.demonicintervention.blocks.ModelBlockBase;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStoringTileBase extends TileEntity{
	
	private int slotNumber = 1;
	
	public ItemStoringTileBase(){super();}
	
	public void setSlotNumber(int i)
	{
		this.slotNumber = i;
	}
	
	public int getNumberOfSlots()
	{
		return this.slotNumber;
	}
	
	//If you overwrite this make sure to call the super
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
        compound.setTag("inputitem", itemStackHandler.serializeNBT());
        return super.writeToNBT(compound);
	}
	
	//If you overwrite this make sure to call the super
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if (compound.hasKey("inputitem"))
		{
			itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("inputitem"));
		}
	}
	
	protected ItemStackHandler itemStackHandler = new ItemStackHandler(this.slotNumber){
        @Override
        protected void onContentsChanged(int slot){
            markDirty();
        }
    };
    
	@Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return (T) itemStackHandler;
        }
        return super.getCapability(capability, facing);
    }

    @Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
	    return (oldState.getBlock() != newState.getBlock());
	}
}
