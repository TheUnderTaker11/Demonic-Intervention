package com.theundertaker11.demonicintervention.capability;

import com.theundertaker11.demonicintervention.Reference;
import com.theundertaker11.demonicintervention.capability.extradata.ExtraData;
import com.theundertaker11.demonicintervention.capability.extradata.ExtraDataCapabilityProvider;
import com.theundertaker11.demonicintervention.capability.extradata.ExtraDataStorage;
import com.theundertaker11.demonicintervention.capability.extradata.IExtraData;
import com.theundertaker11.demonicintervention.capability.infusions.IInfusions;
import com.theundertaker11.demonicintervention.capability.infusions.Infusions;
import com.theundertaker11.demonicintervention.capability.infusions.InfusionsCapabilityProvider;
import com.theundertaker11.demonicintervention.capability.infusions.InfusionsStorage;
import com.theundertaker11.demonicintervention.capability.maxhealth.IMaxHealth;
import com.theundertaker11.demonicintervention.capability.maxhealth.MaxHealth;
import com.theundertaker11.demonicintervention.capability.maxhealth.MaxHealthCapabilityProvider;
import com.theundertaker11.demonicintervention.capability.maxhealth.MaxHealthStorage;
import com.theundertaker11.demonicintervention.util.ModUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;

public class CapabilityHandler {
	 
	public static final ResourceLocation INFUSIONS_CAPABILITY = new ResourceLocation(Reference.MODID, "infusions");
	public static final ResourceLocation MAXHEALTH_CAPABILITY = new ResourceLocation(Reference.MODID, "maxhealth");
	public static final ResourceLocation EXTRADATA_CAPABILITY = new ResourceLocation(Reference.MODID, "extradata");

	public static void init()
	{
		CapabilityManager.INSTANCE.register(IMaxHealth.class, new MaxHealthStorage(), MaxHealth::new);
		CapabilityManager.INSTANCE.register(IInfusions.class, new InfusionsStorage(), Infusions::new);
		CapabilityManager.INSTANCE.register(IExtraData.class, new ExtraDataStorage(), ExtraData::new);
	}
	
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event)
	{
		 if(event.getObject()!=null&&event.getObject() instanceof EntityPlayer)
		 {
			 event.addCapability(INFUSIONS_CAPABILITY, new InfusionsCapabilityProvider());
			 
			 event.addCapability(EXTRADATA_CAPABILITY, new ExtraDataCapabilityProvider());
			 
			 final MaxHealth maxHealth = new MaxHealth((EntityLivingBase) event.getObject());
			 event.addCapability(MAXHEALTH_CAPABILITY, new MaxHealthCapabilityProvider(maxHealth));
		 }
		 if(event.getObject()!=null&&event.getObject() instanceof EntityVillager)
		 {
			 event.addCapability(EXTRADATA_CAPABILITY, new ExtraDataCapabilityProvider());
		 }
	}
	 
	@SubscribeEvent
	public void onPlayerChangeDim(PlayerChangedDimensionEvent event)
	{
		 final IMaxHealth maxHealth = ModUtils.getIMaxHealth(event.player);
		 if (maxHealth != null)
		 {
			 maxHealth.synchronise();
		 }
	}
}
