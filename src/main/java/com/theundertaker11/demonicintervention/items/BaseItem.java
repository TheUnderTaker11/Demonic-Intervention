package com.theundertaker11.demonicintervention.items;

import java.util.List;

import javax.annotation.Nullable;

import com.theundertaker11.demonicintervention.DemonicInterventionMain;
import com.theundertaker11.demonicintervention.render.IItemModelProvider;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseItem extends Item implements IItemModelProvider{

	protected String name;
	boolean hasEffct;
	protected String description;
	
	public BaseItem(String name, boolean hasEnchantedEffect, String description){
		super();
		setRegistryName(name);
		this.name= name;
		this.hasEffct=hasEnchantedEffect;
		this.description = description;
		setUnlocalizedName(name);
		setCreativeTab(DemonicInterventionMain.DICreativeTab);
	}
	public BaseItem(String name, String desc) {
		this(name, false, desc);
	}
	public BaseItem(String name, boolean hasEnchantedEffect) {
		this(name, hasEnchantedEffect, "");
	}
	public BaseItem(String name){
		this(name, false);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
		if(!description.equals("")) {
			tooltip.add(this.description);
		}
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
