package com.theundertaker11.demonicintervention.items.infoitems;

import com.theundertaker11.demonicintervention.api.KarmaUtils;
import com.theundertaker11.demonicintervention.items.BaseItem;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemKarmaReader extends BaseItem{

	public ItemKarmaReader(String name)
	{
		super(name);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if(!worldIn.isRemote&&hand==EnumHand.MAIN_HAND)
        {
        	if(playerIn.isSneaking() && playerIn.isCreative())
        	{
        		KarmaUtils.setKarma(playerIn, 0);
        		playerIn.sendMessage(new TextComponentString(I18n.format("entitymessage.karmalevel.name") + KarmaUtils.getKarma(playerIn)));
        	}
        	else{
        		playerIn.sendMessage(new TextComponentString(I18n.format("entitymessage.karmalevel.name") + KarmaUtils.getKarma(playerIn)));
        	}
        }
		
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(hand));
    }
}
