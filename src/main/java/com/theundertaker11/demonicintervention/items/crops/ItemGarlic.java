package com.theundertaker11.demonicintervention.items.crops;

import java.util.List;

import javax.annotation.Nullable;

import com.theundertaker11.demonicintervention.DemonicInterventionMain;
import com.theundertaker11.demonicintervention.init.BlockRegistry;
import com.theundertaker11.demonicintervention.init.ItemRegistry;
import com.theundertaker11.demonicintervention.render.IItemModelProvider;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGarlic extends ItemSeeds implements IItemModelProvider{

	private String desc = "";
	private String name;
	public ItemGarlic(String name, String desc) {
		super(BlockRegistry.blockGarlic, Blocks.FARMLAND);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		setCreativeTab(DemonicInterventionMain.DICreativeTab);
		this.desc = desc;
		this.name = name;
		MinecraftForge.addGrassSeed(new ItemStack(ItemRegistry.garlic), 5);
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
		if(!desc.equals("")) {
			tooltip.add(this.desc);
		}
    }
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if(hand != EnumHand.MAIN_HAND) 
			return EnumActionResult.PASS;
        ItemStack itemstack = player.getHeldItemMainhand();
        net.minecraft.block.state.IBlockState state = worldIn.getBlockState(pos);
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this) && worldIn.isAirBlock(pos.up()))
        {
            worldIn.setBlockState(pos.up(), BlockRegistry.blockGarlic.getDefaultState());
            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }
	
	@Override
	public void registerItemModel(Item item) {
		DemonicInterventionMain.proxy.registerItemRenderer(this, 0, name);
	}
	
	@Override
    public net.minecraft.block.state.IBlockState getPlant(net.minecraft.world.IBlockAccess world, BlockPos pos)
    {
        return BlockRegistry.blockGarlic.getDefaultState();
    }
}
