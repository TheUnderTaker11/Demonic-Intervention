package com.theundertaker11.demonicintervention.items;

import com.theundertaker11.demonicintervention.DemonicInterventionMain;
import com.theundertaker11.demonicintervention.render.IItemModelProvider;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseItem extends Item implements IItemModelProvider{

	protected String name;
	boolean hasEffct;
	
	public BaseItem(String name, boolean hasEnchantedEffect){
		super();
		setRegistryName(name);
		this.name= name;
		this.hasEffct=hasEnchantedEffect;
		setUnlocalizedName(name);
		setCreativeTab(DemonicInterventionMain.DICreativeTab);
	}
	public BaseItem(String name){
		this(name, false);
	}
	@Override
	public void registerItemModel(Item item) {
		DemonicInterventionMain.proxy.registerItemRenderer(this, 0, name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return this.hasEffct;
	}
}
