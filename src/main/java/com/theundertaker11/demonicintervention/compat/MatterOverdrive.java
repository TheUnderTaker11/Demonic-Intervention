package com.theundertaker11.demonicintervention.compat;

import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.infusions.Infusions;

import matteroverdrive.api.android.IAndroid;
import matteroverdrive.entity.player.MOPlayerCapabilityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
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
	public static void onPlayerTick(PlayerTickEvent event)
	{
		try {
			if(isPlayerMOAndriod(event.player) && InfusionUtils.hasInfusion(Infusions.vampirism, event.player)) {
				InfusionUtils.removeInfusion(event.player, Infusions.vampirism);
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
