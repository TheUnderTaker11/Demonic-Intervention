package com.theundertaker11.demonicintervention.items;

import java.util.List;

import javax.annotation.Nullable;

import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.capability.extradata.IExtraData;
import com.theundertaker11.demonicintervention.infusions.Infusions;
import com.theundertaker11.demonicintervention.util.ModUtils;
import com.theundertaker11.demonicintervention.util.NBTKeys;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BloodBottle extends BaseItem{

	public BloodBottle(String name) {
		super(name);
		this.setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
		NBTTagCompound tag = ModUtils.getTagCompound(stack);
		tooltip.add("Blood of " + tag.getString(NBTKeys.NAME));
    }
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
		if(!worldIn.isRemote && entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			NBTTagCompound tag = ModUtils.getTagCompound(stack);
			if(!tag.getBoolean(NBTKeys.IS_VAMPIRE) && InfusionUtils.hasInfusion(Infusions.vampirism, player)) {
				IExtraData data = InfusionUtils.getExtraData(player);
				data.addBlood(90, player);
			}else {
				player.sendMessage(new TextComponentString(I18n.format("entitymessage.vampirecantdrink.name")));
			}
			if(tag.getBoolean(NBTKeys.IS_VAMPIRE) && !tag.getBoolean(NBTKeys.IS_ALPHA)) {
				player.sendMessage(new TextComponentString(I18n.format("entitymessage.trieddrinkingvampireblood.name")));
			}
			if(tag.getBoolean(NBTKeys.IS_ALPHA) && !InfusionUtils.hasInfusion(Infusions.vampirism, player)) {
				InfusionUtils.addInfusion(player, Infusions.vampirism);
			}
		}
		return ItemStack.EMPTY;
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 30;
    }
}
