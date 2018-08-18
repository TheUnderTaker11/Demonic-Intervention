package com.theundertaker11.demonicintervention.blocks.generalblocks;

import javax.annotation.Nullable;

import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.blocks.BaseBlock;
import com.theundertaker11.demonicintervention.capability.infusions.IInfusions;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockSoulCleaner extends BaseBlock {

	public BlockSoulCleaner(String name)
	{
		super(name);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(worldIn.isRemote) return true;
		
		clearPlayerInfusions(playerIn);
		return true;
	}
	
	public static void clearPlayerInfusions(EntityPlayer player)
	{
		IInfusions infusions = InfusionUtils.getIInfusions(player);
		if(infusions!=null)
		{
			infusions.removeAllInfusions();
			player.sendMessage(new TextComponentString("Removed all infusions and curses."));
		}
		else {
			player.sendMessage(new TextComponentString("There was no existing infusions or curses."));
		}
		
	}
}
