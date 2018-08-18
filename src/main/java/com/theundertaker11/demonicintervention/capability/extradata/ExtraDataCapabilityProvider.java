package com.theundertaker11.demonicintervention.capability.extradata;

import com.theundertaker11.demonicintervention.capability.extradata.IExtraData;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ExtraDataCapabilityProvider implements ICapabilitySerializable<NBTBase>{

	@CapabilityInject(IExtraData.class)
	public static final Capability<IExtraData> EXTRADATA_CAPABILITY = null;

	private IExtraData instance = EXTRADATA_CAPABILITY.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == EXTRADATA_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == EXTRADATA_CAPABILITY ? EXTRADATA_CAPABILITY.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return EXTRADATA_CAPABILITY.getStorage().writeNBT(EXTRADATA_CAPABILITY, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		EXTRADATA_CAPABILITY.getStorage().readNBT(EXTRADATA_CAPABILITY, this.instance, null, nbt);
	}
}
