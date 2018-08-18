package com.theundertaker11.demonicintervention.items.baubles;

import com.theundertaker11.demonicintervention.items.BaseItem;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles")
public class GarlicCharm extends BaseItem implements IBauble{

	public GarlicCharm(String name, String desc) {
		super(name, desc);
	}

	@Optional.Method(modid = "baubles")
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.CHARM;
	}

}
