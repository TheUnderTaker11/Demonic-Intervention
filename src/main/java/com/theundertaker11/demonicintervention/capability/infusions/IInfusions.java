package com.theundertaker11.demonicintervention.capability.infusions;

import java.util.List;

import com.theundertaker11.demonicintervention.api.infusion.Infusion;

public interface IInfusions{

	public void addInfusion(Infusion infusion);
	
	public void removeInfusion(Infusion infusion);
	
	public boolean hasInfusion(Infusion infusion);
	
	public void removeAllInfusions();
	
	public List<Infusion> getInfusionList();
	
	public void setInfusionList(List<Infusion> list);
}
