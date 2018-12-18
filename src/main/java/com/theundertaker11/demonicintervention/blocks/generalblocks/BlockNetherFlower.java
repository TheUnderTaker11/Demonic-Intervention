package com.theundertaker11.demonicintervention.blocks.generalblocks;

import com.theundertaker11.demonicintervention.DemonicInterventionMain;
import com.theundertaker11.demonicintervention.render.IItemModelProvider;

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockNetherFlower extends BlockBush implements IItemModelProvider{

	//private final static AxisAlignedBB hitbox = new AxisAlignedBB(0.25, 0.0D, 0.25D, .75D, .75D, .75D);
	private String Name;
	public BlockNetherFlower(String name) {
		super();
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(DemonicInterventionMain.DICreativeTab);
		this.Name = name;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BUSH_AABB;
	}
	@Override
	public void registerItemModel(Item itemBlock) {
		DemonicInterventionMain.proxy.registerItemRenderer(itemBlock, 0, Name);
	}
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
		if(worldIn.getBlockState(pos.down()).getBlock() == Blocks.NETHERRACK)
			return true;
		else return false;
    }
	@Override
	protected boolean canSustainBush(IBlockState state)
    {
		if(state.getBlock() == Blocks.NETHERRACK)
			return true;
		else return false;
    }
	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        if (state.getBlock() == this) //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
        {
            IBlockState below = worldIn.getBlockState(pos.down());
            return (below.getBlock() == Blocks.NETHERRACK);
        }
        return this.canSustainBush(worldIn.getBlockState(pos.down()));
    }
}
