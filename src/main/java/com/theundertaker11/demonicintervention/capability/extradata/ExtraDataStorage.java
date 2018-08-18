package com.theundertaker11.demonicintervention.capability.extradata;

import com.theundertaker11.demonicintervention.api.infusion.Infusion;
import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.capability.infusions.IInfusions;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ExtraDataStorage implements IStorage<IExtraData>{

	private static final String BLOODLEVEL_ID = "BloodLevel";
	private static final String ALPHA_ID = "isAlpha";
	@Override
 	public NBTBase writeNBT(Capability<IExtraData> capability, IExtraData instance, EnumFacing side)
 	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger(BLOODLEVEL_ID, instance.getBloodLevel());
		tag.setBoolean(ALPHA_ID, instance.getIsAlphaVampire());
		return tag;
 	}



 	@Override
 	public void readNBT(Capability<IExtraData> capability, IExtraData instance, EnumFacing side, NBTBase nbt)
 	{
 		NBTTagCompound tag = (NBTTagCompound)nbt;
 		instance.setBloodLevel(tag.getInteger(BLOODLEVEL_ID));
 		instance.setIsAlphaVampire(tag.getBoolean(ALPHA_ID));
 	}
	
	
}
