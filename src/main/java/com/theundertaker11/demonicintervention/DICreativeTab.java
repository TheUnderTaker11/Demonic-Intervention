package com.theundertaker11.demonicintervention;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DICreativeTab extends CreativeTabs
{
    public DICreativeTab(int index, String name)
    {
        super(index, name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem()
    {
        return new ItemStack(Items.ENDER_EYE);
    }
}
