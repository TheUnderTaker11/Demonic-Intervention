package com.theundertaker11.demonicintervention.items;

import java.util.List;

import com.theundertaker11.demonicintervention.api.infusion.Infusion;
import com.theundertaker11.demonicintervention.api.infusion.InfusionRegistry;
import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.capability.infusions.IInfusions;
import com.theundertaker11.demonicintervention.util.ModUtils;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDebuggingTool extends BaseItem {

	public static final String InfusionNBTID = "InfusionNBT";
	
	public ItemDebuggingTool(String name)
	{
		super(name);
		this.setMaxStackSize(1);
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
		NBTTagCompound tag = ModUtils.getTagCompound(stack);
		Infusion selectedInfusion = InfusionUtils.getInfusionFromID(tag.getInteger(InfusionNBTID));
		
		tooltip.add(selectedInfusion.getNameForShow());
		
    }
	
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if(!worldIn.isRemote&&hand==EnumHand.MAIN_HAND)
        {
        	if(playerIn.isSneaking())
        	{
        		cycle(itemStackIn, playerIn);
        	}
        	else{
        		toggleSelectedInfusion(itemStackIn, playerIn);
        	}
        }
		
		return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }
	
	public static void cycle(ItemStack stack, EntityPlayer player)
	{
		if(stack.getTagCompound()==null)
		{
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger(InfusionNBTID, 0);
		}
		NBTTagCompound tag = ModUtils.getTagCompound(stack);
		int id = nextInfusionIDForDebugging(tag.getInteger(InfusionNBTID));
		
		tag.setInteger(InfusionNBTID, id);
		player.addChatMessage(new TextComponentString(I18n.format("misc.infusionset.name")+": "+InfusionUtils.getInfusionFromID(id).getNameForShow()));
	}
	
	public static void toggleSelectedInfusion(ItemStack stack, EntityPlayer player)
	{
		NBTTagCompound tag = ModUtils.getTagCompound(stack);
		
		IInfusions infusions = InfusionUtils.getIInfusions(player);
		if(infusions!=null)
		{
			Infusion itemsInfusion = InfusionUtils.getInfusionFromID(tag.getInteger(InfusionNBTID));
			if(itemsInfusion!=null)
			{
				if(infusions.hasInfusion(itemsInfusion))
				{
					infusions.removeInfusion(itemsInfusion);
					player.addChatMessage(new TextComponentString(I18n.format("misc.removed.name")+": "+itemsInfusion.getNameForShow()));
				}
				else
				{
					infusions.addInfusion(itemsInfusion);
					player.addChatMessage(new TextComponentString(I18n.format("misc.added.name")+": "+itemsInfusion.getNameForShow()));
				}
			}
		}
		else{
			player.addChatMessage(new TextComponentString("Something went wrong!"));
		}
	}
	
	public static int nextInfusionIDForDebugging(int currentID)
	{
		int newID = currentID+1;
		if(newID>=InfusionUtils.totalInfusionCount)
		{
			newID = 0;
		}
		if(InfusionUtils.getInfusionFromID(newID)==null)
		{
			newID = 0;
		}
		return newID;
	}
}