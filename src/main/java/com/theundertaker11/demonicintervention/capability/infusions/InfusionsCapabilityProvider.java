package com.theundertaker11.demonicintervention.capability.infusions;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class InfusionsCapabilityProvider implements ICapabilitySerializable<NBTBase>{

	@CapabilityInject(IInfusions.class)
	public static final Capability<IInfusions> INFUSIONS_CAPABILITY = null;

	private IInfusions instance = INFUSIONS_CAPABILITY.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == INFUSIONS_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == INFUSIONS_CAPABILITY ? INFUSIONS_CAPABILITY.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return INFUSIONS_CAPABILITY.getStorage().writeNBT(INFUSIONS_CAPABILITY, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		INFUSIONS_CAPABILITY.getStorage().readNBT(INFUSIONS_CAPABILITY, this.instance, null, nbt);
	}
}
