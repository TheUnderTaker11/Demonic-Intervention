package com.theundertaker11.demonicintervention.capability.extradata;

import com.theundertaker11.demonicintervention.capability.vampiredata.IVampireData;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ExtraDataStorage  implements IStorage<IExtraData>{

	private static final String KARMA = "KARMA";
	@Override
 	public NBTBase writeNBT(Capability<IExtraData> capability, IExtraData instance, EnumFacing side)
 	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger(KARMA, instance.getKarma());
		return tag;
 	}



 	@Override
 	public void readNBT(Capability<IExtraData> capability, IExtraData instance, EnumFacing side, NBTBase nbt)
 	{
 		NBTTagCompound tag = (NBTTagCompound)nbt;
 		instance.setKarma(tag.getInteger(KARMA));
 	}
 	
}
