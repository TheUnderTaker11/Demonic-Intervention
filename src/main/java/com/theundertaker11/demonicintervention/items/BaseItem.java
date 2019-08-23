package com.theundertaker11.demonicintervention.items;

import java.util.List;

import javax.annotation.Nullable;

import com.theundertaker11.demonicintervention.DemonicInterventionMain;
import com.theundertaker11.demonicintervention.render.IItemModelProvider;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseItem extends Item implements IItemModelProvider{

	protected String name;
	protected boolean hasEffct;
	protected String description;
	protected boolean canBreak;
	
	public BaseItem(String name, boolean hasEnchantedEffect, String description, boolean CanBreak){
		super();
		setRegistryName(name);
		this.name= name;
		this.hasEffct=hasEnchantedEffect;
		this.description = description;
		this.canBreak = !CanBreak;
		setUnlocalizedName(name);
		setCreativeTab(DemonicInterventionMain.DICreativeTab);
	}
	public BaseItem(String name, String desc) {
		this(name, false, desc, true);
	}
	public BaseItem(String name, boolean hasEnchantedEffect) {
		this(name, hasEnchantedEffect, "", true);
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
	
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player)
    {
        return this.canBreak;
    }
}
