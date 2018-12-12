package com.theundertaker11.demonicintervention.blocks.generalblocks;

import java.util.Random;

import com.theundertaker11.demonicintervention.DemonicInterventionMain;
import com.theundertaker11.demonicintervention.init.ItemRegistry;
import com.theundertaker11.demonicintervention.render.IItemModelProvider;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGarlic extends BlockCrops implements IItemModelProvider{

	public static final PropertyInteger CROP_AGE = PropertyInteger.create("age", 0, 2);
	private static final AxisAlignedBB[] CROP_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.35D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D)};
	private String name;
	
	public BlockGarlic(String name) {
		super();
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.name = name;

	}
	@Override
	public void registerItemModel(Item itemBlock) {
		DemonicInterventionMain.proxy.registerItemRenderer(itemBlock, 0, name);
	}
	@Override
	protected PropertyInteger getAgeProperty()
	{
		return CROP_AGE;
	}
	@Override
	public int getMaxAge()
	{
		return 2;
	}
	@Override
	protected int getBonemealAgeIncrease(World worldIn)
	{
		return 1;
	}
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {CROP_AGE});
	}
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return CROP_AABB[((Integer)state.getValue(this.getAgeProperty())).intValue()];
	}
	@Override
	protected Item getSeed()
    {
        return ItemRegistry.garlic;
    }
	@Override
    protected Item getCrop()
    {
        return ItemRegistry.garlic;
    }
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ItemRegistry.garlic;
    }
}
