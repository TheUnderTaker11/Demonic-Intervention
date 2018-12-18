package com.theundertaker11.demonicintervention.items;

import java.util.List;

import javax.annotation.Nullable;

import com.theundertaker11.demonicintervention.DemonicInterventionMain;
import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.infusions.Infusions;
import com.theundertaker11.demonicintervention.init.ItemRegistry;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemChalice extends BaseItem {

	public static final String ENTITY_NAME = "DIChaliceEntName";
	public static final String ENT_UUID = "DIChaliceUUID";

	public ItemChalice(String name) {
		super(name);
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		switch (stack.getItemDamage()) {
		case 0:
			tooltip.add("An empty chalice");
			break;
		case 1:
			tooltip.add("Chalice filled with blood.");
			break;
		case 2:
			tooltip.add("Chalice full of bubbling blood. Drink this to become a vampire.");
			break;
		}
		if (stack.hasTagCompound()) {
			NBTTagCompound tag = stack.getTagCompound();
			tooltip.add("Blood of " + tag.getString(this.ENTITY_NAME));
		}
	}

	@Override
	public void registerItemModel(Item item) {
		DemonicInterventionMain.proxy.registerItemRenderer(this, 0, name);
		DemonicInterventionMain.proxy.registerItemRenderer(this, 1, "chalice1");
		DemonicInterventionMain.proxy.registerItemRenderer(this, 2, "chalice2");
	}

	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		EntityPlayer player = entityLiving instanceof EntityPlayer ? (EntityPlayer) entityLiving : null;
		if (player == null)
			return stack;

		if (stack.getItemDamage() == 2 && !InfusionUtils.hasInfusion(Infusions.vampirism, player)
				&& InfusionUtils.getVampireProgressionLevel(player) == 1) {
			InfusionUtils.addInfusion(player, Infusions.vampirism);
			player.sendMessage(new TextComponentString("You feel powerful. Your days as a mortal man are finished."));
		}else {
			return stack;
		}

		return new ItemStack(ItemRegistry.chalice);
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.setActiveHand(handIn);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab == DemonicInterventionMain.DICreativeTab) {
			items.add(new ItemStack(this, 1, 0));
			items.add(new ItemStack(this, 1, 1));
			items.add(new ItemStack(this, 1, 2));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return stack.getItemDamage() == 2;
	}
}
