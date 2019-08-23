package com.theundertaker11.demonicintervention.crafting.pedestal.essence;

import com.theundertaker11.demonicintervention.api.KarmaUtils;
import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.api.pedestalcrafting.PedestalEssenceRecipe;
import com.theundertaker11.demonicintervention.infusions.Infusions;
import com.theundertaker11.demonicintervention.init.DIConfig;
import com.theundertaker11.demonicintervention.init.ItemRegistry;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class RitualVampireHunter extends PedestalEssenceRecipe{

	public RitualVampireHunter(String name, int tier, int cost, int time) {
		super(name, tier, cost, time, (short)1, true);
		this.setResult(new ItemStack(ItemRegistry.essence, 1, 0));
		this.setMiddleItem(new ItemStack(ItemRegistry.essence, 1, 1));
		this.addItem(new ItemStack(ItemRegistry.garlicSword));
		this.addItem(new ItemStack(ItemRegistry.garlicCharm));
		this.addItem(new ItemStack(ItemRegistry.pureEssence));
		this.addItem(new ItemStack(ItemRegistry.pureEssence));
	}

	@Override
	public void onFinish(BlockPos pos, EntityPlayer caster, EntityLivingBase target) {
		System.out.println("Please god");
		if(target instanceof EntityPlayer) {
			System.out.println("Please god2");
			EntityPlayer targetPlayer = (EntityPlayer) target;
			if(KarmaUtils.getKarma(targetPlayer) >= DIConfig.karmaForVampireHunter) {
				if(!InfusionUtils.hasConflictingInfusions(targetPlayer, Infusions.vampireHunter)) {
					InfusionUtils.addInfusion(targetPlayer, Infusions.vampireHunter);
					targetPlayer.sendMessage(new TextComponentString(I18n.format("entitymessage.becomevampirehunter.name")));
				}
				else {
					targetPlayer.sendMessage(new TextComponentString(I18n.format("entitymessage.conflictinginfusion.name")));
				}
			}else {
				targetPlayer.sendMessage(new TextComponentString(I18n.format("entitymessage.notenoughkarma.name")));
				targetPlayer.sendMessage(new TextComponentString("Current: " + KarmaUtils.getKarma(targetPlayer) + ", Karma needed: " + DIConfig.karmaForVampireHunter));
				return;
			}
		}
	}
}
