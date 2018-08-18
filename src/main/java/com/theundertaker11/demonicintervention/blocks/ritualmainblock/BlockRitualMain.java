package com.theundertaker11.demonicintervention.blocks.ritualmainblock;

import com.theundertaker11.demonicintervention.blocks.ModelBlockBase;

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

public class BlockRitualMain extends ModelBlockBase{

	private final static AxisAlignedBB hitbox = new AxisAlignedBB(0.0, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);
	
	public BlockRitualMain(String name)
	{
		super(name);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return hitbox;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
    {
		return new TileRitualMain();
    }
	
	@Override
	public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		//if(worldIn.isRemote) return true;
		TileEntity tEntity = worldIn.getTileEntity(pos);
		if(tEntity!=null&&tEntity instanceof TileRitualMain&&hand==EnumHand.MAIN_HAND)
		{
			TileRitualMain tile = (TileRitualMain)tEntity;
			IItemHandler inv = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			System.out.println("Tier: " + tile.getTier());
			if(!playerIn.getHeldItemMainhand().isEmpty())
			{
				playerIn.setHeldItem(EnumHand.MAIN_HAND, inv.insertItem(0, playerIn.getHeldItemMainhand(), false));
			}
			else
			{
				if(!inv.getStackInSlot(0).isEmpty())
				{
					playerIn.setHeldItem(EnumHand.MAIN_HAND, inv.extractItem(0, inv.getStackInSlot(0).getCount(), false));
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
        
        if (tile!=null&&tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)!=null&&tile instanceof TileRitualMain)
        {
        	TileRitualMain tileentity = (TileRitualMain)tile;
        	IItemHandler input = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        	if(input.getStackInSlot(0)!=null)
        	{
        		ItemStack inputstack = input.getStackInSlot(0);
        		EntityItem entityinput = new EntityItem(tileentity.getWorld(), tileentity.getPos().getX(), tileentity.getPos().getY(), tileentity.getPos().getZ(), inputstack);
        		tileentity.getWorld().spawnEntity(entityinput);
        	}
        }
        super.breakBlock(worldIn, pos, state);
    }
}
