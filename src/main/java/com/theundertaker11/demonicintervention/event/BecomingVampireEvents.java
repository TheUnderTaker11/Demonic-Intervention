package com.theundertaker11.demonicintervention.event;

import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.capability.infusions.IInfusions;
import com.theundertaker11.demonicintervention.infusions.Infusions;
import com.theundertaker11.demonicintervention.init.BlockRegistry;
import com.theundertaker11.demonicintervention.init.ItemRegistry;
import com.theundertaker11.demonicintervention.items.ItemChalice;
import com.theundertaker11.demonicintervention.util.ModUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class BecomingVampireEvents {

	/**
	 * Saves player from a fall, then tells them in chat to take the life of another.
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public static void onFall(LivingFallEvent event) {
		EntityLivingBase ent = event.getEntityLiving();
		if(ent instanceof EntityPlayer && (event.getDistance()-3) > ent.getHealth())
		{
			EntityPlayer player = (EntityPlayer)ent;
			IInfusions infusions = InfusionUtils.getIInfusions(player);
			if(!InfusionUtils.hasInfusion(Infusions.vampirism, player) && InfusionUtils.getVampireProgressionLevel(player) == 0) {
				ItemStack flower = getFlower(player);
				if(!flower.isEmpty()) {
					InfusionUtils.getVampireData(player).addVampProgressionLevel(player);
					event.setDamageMultiplier(0);
					player.sendMessage(new TextComponentString("The Flower Speaks to You: Good, you have taken the leap. Now use the shining heart of a beast so foul to kill another of your kind, then drink of its blood from a golden chalice to attain the true power you seek."));
				}
			}
		}
	}
	/**
	 * Checks for player killing villager/other player with nether star in hand, then fills a golden chalice with the blood they need to 
	 * drink to become vampires
	 * @param event
	 */
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if(event.getSource().getTrueSource() instanceof EntityPlayer 
				&& (event.getEntityLiving() instanceof EntityPlayer || event.getEntityLiving() instanceof EntityVillager)) {
			EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
			System.out.println("Progression level is " + InfusionUtils.getVampireProgressionLevel(player));
			if(player.getHeldItemMainhand().getItem() == Items.NETHER_STAR && InfusionUtils.getVampireProgressionLevel(player) == 1) {
				ItemStack chalice = getChalice(player);
				if(chalice.getItemDamage() == 0) {
					NBTTagCompound tag = ModUtils.getTagCompound(chalice);
					chalice.setItemDamage(2);
					tag.setString(ItemChalice.ENTITY_NAME, event.getEntityLiving().getName());
					tag.setUniqueId(ItemChalice.ENT_UUID, event.getEntityLiving().getUniqueID());
					player.sendMessage(new TextComponentString("The Chalice in your inventory caught some of the blood as you slayed them. It now bubbles softly."));
				}
			}
		}
	}
	private static ItemStack getFlower(EntityPlayer player) {
		for(int i=0; i< player.inventory.getSizeInventory(); i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			if(stack.getItem() == Item.getItemFromBlock(BlockRegistry.netherFlower)) {
				return stack;
			}
		}
		return ItemStack.EMPTY;
	}
	
	private static ItemStack getChalice(EntityPlayer player) {
		for(int i=0; i< player.inventory.getSizeInventory(); i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			if(stack.getItem() == ItemRegistry.chalice) {
				return stack;
			}
		}
		return ItemStack.EMPTY;
	}
}
