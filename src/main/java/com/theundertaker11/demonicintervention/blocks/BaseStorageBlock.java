package com.theundertaker11.demonicintervention.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BaseStorageBlock extends BaseBlock
{
    public BaseStorageBlock(String name, Material mat, float hardness, float resistance)
	{
		super(name, mat, hardness, resistance);
		isBlockContainer = true;
	}

    public BaseStorageBlock(String name)
    {
        super(name);
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
    @Override
    public abstract TileEntity createTileEntity(World world, IBlockState state);
}
