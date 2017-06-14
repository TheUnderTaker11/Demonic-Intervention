package com.theundertaker11.demonicintervention.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModelBlockBase extends BaseBlock {

	public ModelBlockBase(String name, Material mat, float hardness, float resistance)
	{
		super(name, mat, hardness, resistance);
	}
	public ModelBlockBase(String name)
	{
		super(name);
	}
	/**
	 * @return the AABB of the block, <b>not</b> the final AABB in the world.
	 */
	@Override
	public abstract AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos);
	
	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		return this.getBoundingBox(state, worldIn, pos).offset(pos);
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
    {
        return false;
    }

	@Override
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
}
