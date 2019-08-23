package com.theundertaker11.demonicintervention.capability.vampiredata;

import com.theundertaker11.demonicintervention.api.infusion.Infusion;
import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.capability.infusions.IInfusions;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class VampireDataStorage implements IStorage<IVampireData>{

	private static final String BLOODLEVEL_ID = "BloodLevel";
	private static final String VAMP_PROGRESSION = "vampProgression";
	@Override
 	public NBTBase writeNBT(Capability<IVampireData> capability, IVampireData instance, EnumFacing side)
 	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger(BLOODLEVEL_ID, instance.getBloodLevel());
		tag.setInteger(VAMP_PROGRESSION, instance.getVampProgression());
		return tag;
 	}



 	@Override
 	public void readNBT(Capability<IVampireData> capability, IVampireData instance, EnumFacing side, NBTBase nbt)
 	{
 		NBTTagCompound tag = (NBTTagCompound)nbt;
 		instance.setBloodLevel(tag.getInteger(BLOODLEVEL_ID));
 		instance.setVampProgressionLevel(tag.getInteger(VAMP_PROGRESSION));
 	}
	
	
}
