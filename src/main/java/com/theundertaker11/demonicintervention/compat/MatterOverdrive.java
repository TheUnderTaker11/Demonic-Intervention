package com.theundertaker11.demonicintervention.compat;

import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.capability.infusions.IInfusions;
import com.theundertaker11.demonicintervention.infusions.InfusionVampirism;
import com.theundertaker11.demonicintervention.infusions.Infusions;

import matteroverdrive.api.android.IAndroid;
import matteroverdrive.entity.player.MOPlayerCapabilityProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@EventBusSubscriber
public class MatterOverdrive {

	/**
	 * Removes vampirism (And other things in the future) from the player if they are an android.<p>
	 * If you ain't got mostly biological parts then a lot of infusions/curses would not make sense.
	 * @param event
	 */
	@SubscribeEvent
	public static void checkAndroidCompat(PlayerTickEvent event)
	{
		try {
			if(isPlayerMOAndriod(event.player)) {
				IInfusions infusions = InfusionUtils.getIInfusions(event.player);
				if(infusions.hasInfusion(Infusions.vampirism)) {
					infusions.removeInfusion(Infusions.vampirism);
					event.player.sendMessage(new TextComponentString(I18n.format("entitymessage.androidconflict.name")));
				}
				if(infusions.hasInfusion(Infusions.vampireHunter)) {
					infusions.removeInfusion(Infusions.vampireHunter);
					event.player.sendMessage(new TextComponentString(I18n.format("entitymessage.androidconflict.name")));
				}
			}
		}catch(NoSuchMethodError e) {}
	}
	
	@Optional.Method(modid = "matteroverdrive")
	public static boolean isPlayerMOAndriod(EntityPlayer player) {
		IAndroid android = MOPlayerCapabilityProvider.GetAndroidCapability(player);
		if(android != null) {
			return android.isAndroid();
		}
		return false;
	}
}
