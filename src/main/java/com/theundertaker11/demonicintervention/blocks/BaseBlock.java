package com.theundertaker11.demonicintervention.blocks;

import com.theundertaker11.demonicintervention.DemonicInterventionMain;
import com.theundertaker11.demonicintervention.render.IItemModelProvider;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BaseBlock extends Block implements IItemModelProvider{
	protected String Name;
	
	public BaseBlock(String name, Material material, float hardness, float resistance) {
        super(material);
        setRegistryName(name);
        this.Name=name;
        setUnlocalizedName(name);
        setCreativeTab(DemonicInterventionMain.DICreativeTab);
        setHardness(hardness);
        setResistance(resistance);
        setHarvestLevel("pickaxe", 0);
    }

    public BaseBlock(String name) {
        this(name, Material.IRON, 0.5f, 0.5f);
    }

	@Override
	public void registerItemModel(Item itemBlock) {
		DemonicInterventionMain.proxy.registerItemRenderer(itemBlock, 0, Name);
	}
}
