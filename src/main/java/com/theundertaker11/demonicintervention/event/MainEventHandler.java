package com.theundertaker11.demonicintervention.event;

import com.theundertaker11.demonicintervention.util.DelayObj;
import com.theundertaker11.demonicintervention.util.ModUtils;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber
public class MainEventHandler {

	
	private static boolean canRun = true;
	//24000 is 20 minutes
	@SubscribeEvent
	public static void GameTick(TickEvent.ServerTickEvent event) {
		if (canRun) {
			if(!ModUtils.cooldowns.isEmpty()) {
				for(int i=0; i < ModUtils.cooldowns.size(); i++) {
					ModUtils.cooldowns.get(i).removeTick();
					if(ModUtils.cooldowns.get(i).isFinished())
						ModUtils.cooldowns.remove(i);
				}
			}
			if(!ModUtils.delayList.isEmpty()) {
				for(int i=0; i < ModUtils.delayList.size(); i++) {
					DelayObj obj = ModUtils.delayList.get(i);
					if(obj.shouldCountDown()) {
						obj.removeTick();
					}
				}
			}
		}
		canRun = (!canRun);
	}
}
