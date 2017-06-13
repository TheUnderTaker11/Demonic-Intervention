package com.theundertaker11.demonicintervention.capability.infusions;

import java.util.ArrayList;
import java.util.List;

import com.theundertaker11.demonicintervention.api.infusion.Infusion;

public class Infusions implements IInfusions{

	private List<Infusion> InfusionList = new ArrayList<Infusion>();
	
	@Override
	public void addInfusion(Infusion infusion)
	{
		if(!InfusionList.contains(infusion))
		{
			InfusionList.add(infusion);
		}
	}

	@Override
	public void removeInfusion(Infusion infusion)
	{
		InfusionList.remove(infusion);
	}

	@Override
	public boolean hasInfusion(Infusion infusion)
	{
		return InfusionList.contains(infusion);
	}

	@Override
	public void removeAllInfusions()
	{
		InfusionList = new ArrayList<Infusion>();
	}

	@Override
	public List<Infusion> getInfusionList()
	{
		return InfusionList;
	}

	@Override
	public void setInfusionList(List<Infusion> list)
	{
		InfusionList = list;
	}

}
