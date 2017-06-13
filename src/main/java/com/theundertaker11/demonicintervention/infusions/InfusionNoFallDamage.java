package com.theundertaker11.demonicintervention.infusions;

import com.theundertaker11.demonicintervention.api.infusion.Infusion;
import com.theundertaker11.demonicintervention.api.infusion.InfusionRegistry;
import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.capability.infusions.IInfusions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class InfusionNoFallDamage extends Infusion{

	public InfusionNoFallDamage(String nameForShow, boolean isCurse)
	{
		super(nameForShow, isCurse);
	}

	@SubscribeEvent
	public static void onFall(LivingFallEvent event)
	{
		if(event.getEntityLiving().getEntityWorld().isRemote) return;
		
		EntityLivingBase ent = event.getEntityLiving();
		if(ent instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)ent;
			IInfusions iInfusions = InfusionUtils.getIInfusions(player);
			if(iInfusions!=null&&iInfusions.hasInfusion(InfusionRegistry.noFallDamage))
			{
				event.setDistance(0);
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event)
	{
		Entity sourceEntity = event.getSource().getEntity();
		EntityLivingBase hurtEntity = event.getEntityLiving();
		if(hurtEntity==null||sourceEntity==null) return;
		
		if(hurtEntity instanceof EntityPlayer&&sourceEntity instanceof EntityLivingBase)
		{
			EntityPlayer player = (EntityPlayer)hurtEntity;
			EntityLivingBase ent = (EntityLivingBase)sourceEntity;
			IInfusions iInfusions = InfusionUtils.getIInfusions(player);
			if(iInfusions!=null&&iInfusions.hasInfusion(InfusionRegistry.noFallDamage))
			{
				player.knockBack(ent, 4.0F, ent.posX-player.posX, ent.posZ-player.posZ);
			}
		}
	}
}
