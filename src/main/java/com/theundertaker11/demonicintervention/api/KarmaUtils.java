package com.theundertaker11.demonicintervention.api;

import com.theundertaker11.demonicintervention.capability.extradata.IExtraData;
import com.theundertaker11.demonicintervention.util.ModUtils;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class KarmaUtils {

	/**
	 * Add karma. Negative numbers = bad karma, positive = good karma.
	 * @param player
	 * @param amount
	 * @return the new total karma a player has
	 */
	public static int addKarma(EntityPlayer player, int amount) {
		IExtraData data = ModUtils.getExtraData(player);
		if(data == null) return 0;
		player.sendMessage(new TextComponentString(I18n.format("entitymessage.karmagained.name") + amount));
		return data.addKarma(amount);
	}
	public static int addKarmaNoMessage(EntityPlayer player, int amount) {
		IExtraData data = ModUtils.getExtraData(player);
		if(data == null) return 0;
		return data.addKarma(amount);
	}
	public static int getKarma(EntityPlayer player) {
		IExtraData data = ModUtils.getExtraData(player);
		if(data == null) return 0;
		return data.getKarma();
	}
	public static void setKarma(EntityPlayer player, int amount) {
		IExtraData data = ModUtils.getExtraData(player);
		if(data == null) return;
		data.setKarma(amount);
	}
}
