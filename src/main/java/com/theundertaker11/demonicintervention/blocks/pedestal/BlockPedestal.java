package com.theundertaker11.demonicintervention.blocks.pedestal;

import javax.annotation.Nullable;

import com.theundertaker11.demonicintervention.blocks.ModelBlockBase;
import com.theundertaker11.demonicintervention.util.ModUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockPedestal extends ModelBlockBase{

	private final static AxisAlignedBB hitbox = new AxisAlignedBB(0.0, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);
	
	public BlockPedestal(String name)
	{
		super(name);
		this.isBlockContainer = true;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return hitbox;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
    {
		return new TilePedestal();
    }
	
	@Override
	public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		//if(worldIn.isRemote) return true;
		TileEntity tEntity = worldIn.getTileEntity(pos);
		
		if(tEntity!=null&&tEntity instanceof TilePedestal&&hand==EnumHand.MAIN_HAND)
		{
			TilePedestal tile = (TilePedestal)tEntity;
			IItemHandler inv = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			if(!ModUtils.isStackEmpty(heldItem))
			{
				if(ModUtils.isStackEmpty(inv.getStackInSlot(0)))
				{
					inv.insertItem(0, heldItem, false);
					playerIn.setHeldItem(EnumHand.MAIN_HAND, ModUtils.setStackEmpty(heldItem));
					tile.markDirty();
				}
			}
			else
			{
				if(!ModUtils.isStackEmpty(inv.getStackInSlot(0)))
				{
					playerIn.setHeldItem(EnumHand.MAIN_HAND, inv.extractItem(0, inv.getStackInSlot(0).stackSize, false));
					tile.markDirty();
				}
			}
		}
		return true;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tile = worldIn.getTileEntity(pos);
        
        if (tile!=null&&tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)!=null&&tile instanceof TilePedestal)
        {
        	TilePedestal tileentity = (TilePedestal)tile;
        	IItemHandler input = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        	if(input.getStackInSlot(0)!=null)
        	{
        		ItemStack inputstack = input.getStackInSlot(0);
        		EntityItem entityinput = new EntityItem(tileentity.getWorld(), tileentity.getPos().getX(), tileentity.getPos().getY(), tileentity.getPos().getZ(), inputstack);
        		tileentity.getWorld().spawnEntityInWorld(entityinput);
        	}
        }
        super.breakBlock(worldIn, pos, state);
    }

}
