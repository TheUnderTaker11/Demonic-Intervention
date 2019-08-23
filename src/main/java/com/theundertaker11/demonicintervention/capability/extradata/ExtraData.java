package com.theundertaker11.demonicintervention.capability.extradata;

public class ExtraData  implements IExtraData{

	private int karma;
	
	@Override
	public void transferAllInfo(IExtraData data) {
		this.karma = data.getKarma();
	}
	
	@Override
	public int getKarma() {
		return karma;
	}

	@Override
	public int addKarma(int karma) {
		this.karma += karma;
		return this.karma;
	}

	@Override
	public void setKarma(int karma) {
		this.karma = karma;
	}
	

}
