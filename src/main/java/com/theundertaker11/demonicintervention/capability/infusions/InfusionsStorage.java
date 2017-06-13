package com.theundertaker11.demonicintervention.capability.infusions;

import java.util.ArrayList;
import java.util.List;

import com.theundertaker11.demonicintervention.api.infusion.Infusion;
import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

/**
 * This class is responsible for saving and reading data from or to server
 */
public class InfusionsStorage implements IStorage<IInfusions>
{
	private static final String NBT_INFUSIONS_ARRAY_ID = "InfusionsArray";
	@Override
 	public NBTBase writeNBT(Capability<IInfusions> capability, IInfusions instance, EnumFacing side)
 	{
		NBTTagCompound tag = new NBTTagCompound();
		int[] array = new int[instance.getInfusionList().size()];
		int currentNum = 0;
		
		for(Infusion infusion : instance.getInfusionList())
		{ 
			array[currentNum] = infusion.getID();
			++currentNum;
		}
		tag.setIntArray(NBT_INFUSIONS_ARRAY_ID, array);
		return tag;
 	}



 	@Override
 	public void readNBT(Capability<IInfusions> capability, IInfusions instance, EnumFacing side, NBTBase nbt)
 	{
 		NBTTagCompound tag = (NBTTagCompound)nbt;
 		
 		for (int i = 0; i < tag.getIntArray(NBT_INFUSIONS_ARRAY_ID).length; i++)
 		{
 			Infusion infusion = InfusionUtils.getInfusionFromID(i);
 			if(infusion!=null)
 			{
 				instance.addInfusion(infusion);
 			}
 		}
 	}
}
