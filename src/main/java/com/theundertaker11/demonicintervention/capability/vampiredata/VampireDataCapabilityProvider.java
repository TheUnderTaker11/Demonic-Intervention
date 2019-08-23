package com.theundertaker11.demonicintervention.capability.vampiredata;

import com.theundertaker11.demonicintervention.capability.vampiredata.IVampireData;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class VampireDataCapabilityProvider implements ICapabilitySerializable<NBTBase>{

	@CapabilityInject(IVampireData.class)
	public static final Capability<IVampireData> VampireDATA_CAPABILITY = null;

	private IVampireData instance = VampireDATA_CAPABILITY.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == VampireDATA_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == VampireDATA_CAPABILITY ? VampireDATA_CAPABILITY.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return VampireDATA_CAPABILITY.getStorage().writeNBT(VampireDATA_CAPABILITY, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		VampireDATA_CAPABILITY.getStorage().readNBT(VampireDATA_CAPABILITY, this.instance, null, nbt);
	}
}
