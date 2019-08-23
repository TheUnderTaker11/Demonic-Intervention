package com.theundertaker11.demonicintervention.items;

import com.theundertaker11.demonicintervention.api.KarmaUtils;
import com.theundertaker11.demonicintervention.init.ItemRegistry;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemKarmaExtractor extends BaseItem{

	public ItemKarmaExtractor(String name) {
		super(name);
		this.setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
		if(hand == EnumHand.MAIN_HAND && !world.isRemote && player.isSneaking()) {
			ItemStack stack = player.getHeldItemMainhand();
			if(stack.getItem() == ItemRegistry.karmaExtractor) {
				int karma = KarmaUtils.getKarma(player);
				if(karma >= 100) {
					KarmaUtils.addKarmaNoMessage(player, -100);
					if(!player.addItemStackToInventory(new ItemStack(ItemRegistry.pureEssence))) {
						EntityItem entItem = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ItemRegistry.pureEssence));
						world.spawnEntity(entItem);
					}
				}
				else if(karma <= -100) {
					KarmaUtils.addKarmaNoMessage(player, 100);
					if(!player.addItemStackToInventory(new ItemStack(ItemRegistry.evilEssence))) {
						EntityItem entItem = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ItemRegistry.pureEssence));
						world.spawnEntity(entItem);
					}
				}
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
    }
}
