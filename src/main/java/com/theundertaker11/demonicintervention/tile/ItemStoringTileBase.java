package com.theundertaker11.demonicintervention.tile;

import javax.annotation.Nullable;

import baubles.common.network.PacketHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
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
	
	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}
	@Override
	public NBTTagCompound getUpdateTag()
	{
		return this.writeToNBT(new NBTTagCompound());
	}
	@Override
	public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt)
    {
		
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
    @Override
    public void onLoad()
    {
    	updateEverything();
    }
    
    private void updateEverything() {
    	world.markBlockRangeForRenderUpdate(pos, pos);
    	world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
    	world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
		markDirty();
    }
    /*
    public void syncToClients() {
		if (this.world != null && !this.world.isRemote) {
			for (EntityPlayer player : this.world.playerEntities) {
				syncToClient(player);
			}
		}
	}

	public void syncToClient(EntityPlayer player) {
		NBTTagCompound syncTag = new NBTTagCompound();
		this.writeToNBT(syncTag);

		if (player instanceof EntityPlayerMP && player.getDistance(pos.getX(), pos.getY(), pos.getZ()) <= this.getMaxSyncDistanceSquared()) {
			PacketHandler.INSTANCE.sendTo(new SPacketSyncTileEntity(syncTag, this.pos), (EntityPlayerMP) player);
		}
	}
	*/
}
