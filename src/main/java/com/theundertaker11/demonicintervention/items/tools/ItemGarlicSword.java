package com.theundertaker11.demonicintervention.items.tools;

import com.theundertaker11.demonicintervention.DemonicInterventionMain;
import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.infusions.Infusions;
import com.theundertaker11.demonicintervention.init.ItemRegistry;
import com.theundertaker11.demonicintervention.render.IItemModelProvider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ItemGarlicSword extends ItemSword implements IItemModelProvider{

	protected String name;
	
	public ItemGarlicSword(String name) {
		super(ToolMaterial.WOOD);
		setUnlocalizedName(name);
		setCreativeTab(DemonicInterventionMain.DICreativeTab);
		setRegistryName(name);
		this.setNoRepair();
		this.name = name;
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected)
    {
		if(!entity.getEntityWorld().isRemote && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if(player.getHeldItemMainhand().getItem() == ItemRegistry.garlicSword 
					&& InfusionUtils.hasInfusion(Infusions.vampireHunter, player)) {
				stack.setItemDamage(0);
			}
		}
    }
	@Override
	public void registerItemModel(Item item) {
		DemonicInterventionMain.proxy.registerItemRenderer(this, 0, name);
	}
	
	
}
