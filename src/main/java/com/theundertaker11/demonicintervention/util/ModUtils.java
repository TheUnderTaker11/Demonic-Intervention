package com.theundertaker11.demonicintervention.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.capability.maxhealth.IMaxHealth;
import com.theundertaker11.demonicintervention.capability.maxhealth.MaxHealthCapabilityProvider;
import com.theundertaker11.demonicintervention.init.ItemRegistry;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

public class ModUtils{
	
	/** The countdown for this is automatically handled in MainEventHandler.class */
	public static ArrayList<CooldownObj> cooldowns = new ArrayList<>();
	
	/** Must use {@link DelayObj#setShouldCountDown(boolean)} and {@link DelayObj#reset()} in the place you use this list. <p>
	 * When shouldCountDown=true, it automatically counts down to 0, but stays on the list so you can reset it for the next time. <p>
	 * I use this list to count the time an Alpha Vampire is in the sun before death, or when he is holding an umbrella.*/
	public static ArrayList<DelayObj> delayList = new ArrayList<>();
	
	public static final DamageSource SUN_DEATH = new ModUtils.UnblockableDamageSource("sun_death", "death.sun.name").setFireDamage();
	public static final DamageSource VAMPIRE_UNBLOCKABLE = new ModUtils.UnblockableDamageSource("vampire_death", "death.vampire.name");
	public static final DamageSource VAMPIRE_FIRE = new ModUtils.UnblockableDamageSource("vampire_fire_death", "death.vampirefire.name");
	
	//Potion effect ID's for easy use.
	public static final int moveSpeed = 1;
	public static final int moveSlowness = 2;
	public static final int digSpeed = 3;
	public static final int miningSlowDown = 4;
	public static final int strength = 5;
	public static final int jumpBoost = 8;
	public static final int nausea = 9;
	public static final int regeneration = 10;
	public static final int resistance = 11;
	public static final int fireResistance = 12;
	public static final int waterBreathing = 13;
	public static final int invisibility = 14;
	public static final int blindness = 15;
	public static final int nightVision = 16;
	public static final int hunger = 17;
	public static final int weakness = 18;
	public static final int poison = 19;
	public static final int wither = 20;
	
	public static final int ATTRIBUTE_MODIFIER_OPERATION_ADD = 0;
	
	public static void addCooldown(UUID id, String cooldownName, int ticks) {
		cooldowns.add(new CooldownObj(id, cooldownName, ticks));
	}
	
	/**
	 * There will be no two delays on this list with same UUID and name.
	 * @param id
	 * @param name
	 * @param ticks
	 */
	public static void addDelay(UUID id, String name, int ticks) {
		if(!hasDelay(id, name)){
			delayList.add(new DelayObj(id, name, ticks));
		}
	}
	
	public static boolean hasDelay(UUID id, String name) {
		for(DelayObj obj : delayList) {
			if(obj.getName().equals(name) && obj.getID().equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	@Nullable
	public static DelayObj getDelayObj(UUID id, String name) {
		for(DelayObj obj : delayList) {
			if(obj.getName().equals(name) && obj.getID().equals(id)) {
				return obj;
			}
		}
		return null;
	}
	/**
	 * Checks if the UUID is in the cooldown list still and has the same cooldown name
	 * @param uuid
	 * @return
	 */
	public static boolean isOnCooldown(UUID uuid, String cooldownName) {
		for(CooldownObj obj : cooldowns) {
			if(obj.getID().equals(uuid) && obj.getName().equals(cooldownName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Honestly took way to long to find out how to do this shit.
	 * @param pos
	 * @param world
	 * @return
	 */
	public static int lightLevel(BlockPos pos, World world)
    {
		int skyLightSub = world.calculateSkylightSubtracted(1.0f);
		int blockLight = world.getLightFor(EnumSkyBlock.BLOCK, pos);
		int skyLight = world.getLightFor(EnumSkyBlock.SKY, pos) - skyLightSub;
		return skyLight + blockLight;
    }
	/**
	 * This makes it so I don't have to check for null on every tag compound.
	 * 
	 * @param stack
	 * @return
	 */
	public static NBTTagCompound getTagCompound(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null){
            tag = new NBTTagCompound();
            stack.setTagCompound(tag);
        }
        return tag;
    }

	/**
	 * Teleports a player, given the x, y, z, and dimension ID.
	 * Works cross-dimensionally(Hence needing dimension ID)
	 * @param player
	 * @param x
	 * @param y
	 * @param z
	 * @param dimension
	 */
	public static void TeleportPlayer(EntityPlayer player, double x, double y, double z, int dimension)
	{
		if(player.dimension==dimension)
		{
			player.setPositionAndUpdate(x, y, z);
		}
		else
		{
			player.changeDimension(dimension);
			player.setPositionAndUpdate(x, y, z);	
		}
	}
	
	public static boolean hasAnyArmorOrBaubles(EntityPlayer player) {
		for(ItemStack stack : player.inventory.armorInventory) {
			if(!stack.isEmpty())
				return true;
		}
		if(!player.getHeldItemMainhand().isEmpty() || !player.getHeldItemOffhand().isEmpty())
			return true;
		try{
			if(hasAnyBaubles(player))
				return true;
		}catch(NoSuchMethodError e) {}
		
		return false;
	}
	
	@Optional.Method(modid = "baubles")
	public static boolean hasAnyBaubles(EntityPlayer player) {
		IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
		for(int i=0;i<baubles.getSlots();i++) {
			if(!baubles.getStackInSlot(i).isEmpty())
				return true;
		}
		return false;
	}
	/**
	 * Checks if there is normal garlic in the player's inventory
	 * @param player
	 * @return
	 */
	public static boolean hasGarlic(EntityPlayer player) {
		for(int i=0;i<player.inventory.getSizeInventory();i++) {
			if(player.inventory.getStackInSlot(i).getItem()==ItemRegistry.garlic) {
				return true;
			}
		}	
		return false;
	}
	/**
	 * Checks if the player has the garlic charm
	 * @param player
	 * @param includeInventory True if you want to test if it's in the players normal inventory and not just baubles.
	 * @return
	 */
	public static boolean hasGarlicCharm(EntityPlayer player, boolean includeInventory) {
		boolean hasCharm = false;
		try {
			hasCharm = hasBaubleGarlic(player);
		}catch(NoSuchMethodError e) {}
		if(includeInventory) {
			for(int i=0;i<player.inventory.getSizeInventory();i++) {
				if(player.inventory.getStackInSlot(i).getItem()==ItemRegistry.garlicCharm) {
					return true;
				}
			}
		}
		return hasCharm;
	}
	@Optional.Method(modid = "baubles")
	public static boolean hasBaubleGarlic(EntityPlayer player) {
		if(BaublesApi.isBaubleEquipped(player, ItemRegistry.garlicCharm)!=-1) {
			return true;
		}
		return false;
	}
	
	public static boolean isDrinkable(EntityLivingBase ent) {
		if(ent instanceof EntityVillager || ent instanceof EntityPlayer)
		{
			if(InfusionUtils.getExtraData(ent) != null) {
				if(InfusionUtils.getExtraData(ent).getBloodLevel()>0) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Gets the IMaxHealth of the given entity
	 */
	@Nullable
	public static IMaxHealth getIMaxHealth(EntityLivingBase entity)
	{
		return entity.getCapability(MaxHealthCapabilityProvider.MAX_HEALTH_CAPABILITY, null);
	}
	
	private static class UnblockableDamageSource extends DamageSource{

		private String deathMessageLangKey;
		public UnblockableDamageSource(String damageTypeIn, String deathMessageLangKey) {
			super(damageTypeIn);
			this.setDamageAllowedInCreativeMode();
			this.setDamageBypassesArmor();
			//this.setFireDamage();
			this.deathMessageLangKey = deathMessageLangKey;
		}
		@Override
		public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
	    {
			return new TextComponentString(entityLivingBaseIn.getName() + " " + I18n.format(deathMessageLangKey));
	    }
	}
	
	public static List<Entity> getEntitiesInRange(Class<? extends Entity> entityType, World world, double x, double y, double z, double radius) {
		return getEntitesInTange(entityType, world, x - radius, y - radius, z - radius, x + radius, y + radius,
				z + radius);
	}

	public static List<Entity> getEntitesInTange(Class<? extends Entity> entityType, World world, double x, double y, double z, double x2,
			double y2, double z2) {
		return world.getEntitiesWithinAABB(entityType, new AxisAlignedBB(x, y, z, x2, y2, z2));
	}
}
