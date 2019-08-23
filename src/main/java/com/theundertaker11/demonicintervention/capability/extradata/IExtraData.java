package com.theundertaker11.demonicintervention.capability.extradata;

public interface IExtraData {

	public void transferAllInfo(IExtraData data);
	
	public int getKarma();
	
	public int addKarma(int karma);
	
	public void setKarma(int karma);
}
