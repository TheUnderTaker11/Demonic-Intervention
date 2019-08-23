package com.theundertaker11.demonicintervention.items;

import java.util.List;

import javax.annotation.Nullable;

import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.capability.vampiredata.IVampireData;
import com.theundertaker11.demonicintervention.infusions.Infusions;
import com.theundertaker11.demonicintervention.init.DIConfig;
import com.theundertaker11.demonicintervention.init.ItemRegistry;
import com.theundertaker11.demonicintervention.util.ModUtils;
import com.theundertaker11.demonicintervention.util.NBTKeys;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BloodCollector extends BaseItem{

	public BloodCollector(String name) {
		super(name);
		this.setMaxStackSize(1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
		tooltip.add("Needs glass bottles to put blood in");
    }

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if(!worldIn.isRemote&&hand==EnumHand.MAIN_HAND)
        {
        	ItemStack bottle = hasGlassBottle(playerIn);
        	if(!bottle.isEmpty() && InfusionUtils.getVampireData(playerIn).getBloodLevel() >= DIConfig.bloodBottleAmount) {
        		InfusionUtils.getVampireData(playerIn).removeBlood(DIConfig.bloodBottleAmount, playerIn);
        		bottle.setCount(bottle.getCount()-1);
        		if(bottle.getCount()==0)
        			bottle = ItemStack.EMPTY;
        		ItemStack newStack = new ItemStack(ItemRegistry.bloodBottle);
        		newStack.setCount(1);
    			newStack.setTagCompound(new NBTTagCompound());
    			newStack.getTagCompound().setUniqueId(NBTKeys.UUID, playerIn.getPersistentID());
    			newStack.getTagCompound().setString(NBTKeys.NAME, playerIn.getName());
    			if(InfusionUtils.hasInfusion(Infusions.vampirism, playerIn)){
    				newStack.getTagCompound().setBoolean(NBTKeys.IS_VAMPIRE, true);
    				if(InfusionUtils.isAlphaVampire(playerIn))
    					newStack.getTagCompound().setBoolean(NBTKeys.IS_ALPHA, true);
    			}
    				
    			if(!playerIn.addItemStackToInventory(newStack))
    				playerIn.getEntityWorld().spawnEntity(new EntityItem(playerIn.getEntityWorld(), playerIn.posX, playerIn.posY, playerIn.posZ, newStack));
        	}
        }
		
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(hand));
    }
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
		if(!(entity instanceof EntityLivingBase)) return false;
		EntityLivingBase ent = (EntityLivingBase) entity;
		
		ItemStack bottle = hasGlassBottle(player);
    	if(!bottle.isEmpty() && DIConfig.isDrinkableEntity(ent)) 
    	{
    		IVampireData targetData = InfusionUtils.getVampireData(ent);
    		if(targetData != null && targetData.getBloodLevel() >= DIConfig.bloodBottleAmount)
    		{
    			targetData.removeBlood(DIConfig.bloodBottleAmount);
    			ItemStack newStack = new ItemStack(ItemRegistry.bloodBottle);
    			bottle.setCount(bottle.getCount()-1);
    			if(bottle.getCount()==0)
    				bottle = ItemStack.EMPTY;
    			
    			newStack.setCount(1);
    			newStack.setTagCompound(new NBTTagCompound());
    			newStack.getTagCompound().setUniqueId(NBTKeys.UUID, ent.getPersistentID());
    			newStack.getTagCompound().setString(NBTKeys.NAME, ent.getName());
    			if(ent instanceof EntityPlayer) {
    				EntityPlayer target = (EntityPlayer) ent;
    				if(InfusionUtils.hasInfusion(Infusions.vampirism, target)){
    					newStack.getTagCompound().setBoolean(NBTKeys.IS_VAMPIRE, true);
    					if(InfusionUtils.isAlphaVampire(target))
    						newStack.getTagCompound().setBoolean(NBTKeys.IS_ALPHA, true);
    				}
    			}
    			if(!player.addItemStackToInventory(newStack))
				player.getEntityWorld().spawnEntity(new EntityItem(player.getEntityWorld(), player.posX, player.posY, player.posZ, newStack));
    		}
    	}
		return false;
    }
	
	private ItemStack hasGlassBottle(EntityPlayer player) {
		for(int i=0;i<player.inventory.getSizeInventory();i++) {
			if(player.inventory.getStackInSlot(i).getItem() == Items.GLASS_BOTTLE) {
				return player.inventory.getStackInSlot(i);
			}
		}
		return ItemStack.EMPTY;
	}
}
