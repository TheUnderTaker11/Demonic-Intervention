package com.theundertaker11.demonicintervention.items;

import java.util.List;

import javax.annotation.Nullable;

import com.theundertaker11.demonicintervention.api.KarmaUtils;
import com.theundertaker11.demonicintervention.init.ItemRegistry;
import com.theundertaker11.demonicintervention.util.NBTKeys;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemKarmaItems extends BaseItem {

	private static int karmaBaseAmount = 100;
	
	public ItemKarmaItems(String name) {
		super(name);
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
		tooltip.add("Right click to turn into karma.");
		tooltip.add("Shift right click to use whole stack.");
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (!worldIn.isRemote && hand == EnumHand.MAIN_HAND) {
			ItemStack stack = playerIn.getHeldItemMainhand();
			if (stack.getItem() == ItemRegistry.evilEssence) {
				if(playerIn.isSneaking()) {
					KarmaUtils.addKarma(playerIn, stack.getCount() * -karmaBaseAmount);
					stack.shrink(stack.getCount());
				}else {
					KarmaUtils.addKarma(playerIn, -karmaBaseAmount);
					stack.shrink(1);
				}
			}
			if (stack.getItem() == ItemRegistry.condensedEvil) {
				if(playerIn.isSneaking()) {
					KarmaUtils.addKarma(playerIn, stack.getCount() * -karmaBaseAmount * 9);
					stack.shrink(stack.getCount());
				}else {
					KarmaUtils.addKarma(playerIn, -karmaBaseAmount * 9);
					stack.shrink(1);
				}
			}
			if (stack.getItem() == ItemRegistry.pureEssence) {
				if(playerIn.isSneaking()) {
					KarmaUtils.addKarma(playerIn, stack.getCount() * karmaBaseAmount);
					stack.shrink(stack.getCount());
				}else {
					KarmaUtils.addKarma(playerIn, karmaBaseAmount);
					stack.shrink(1);
				}
			}
			if (stack.getItem() == ItemRegistry.condensedPurity) {
				if(playerIn.isSneaking()) {
					KarmaUtils.addKarma(playerIn, stack.getCount() * karmaBaseAmount * 9);
					stack.shrink(stack.getCount());
				}else {
					KarmaUtils.addKarma(playerIn, karmaBaseAmount * 9);
					stack.shrink(1);
				}
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(hand));
	}
}
