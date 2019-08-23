package com.theundertaker11.demonicintervention.infusions;

import java.util.Random;

import com.theundertaker11.demonicintervention.api.KarmaUtils;
import com.theundertaker11.demonicintervention.api.infusion.Infusion;
import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.capability.vampiredata.IVampireData;
import com.theundertaker11.demonicintervention.init.DIConfig;
import com.theundertaker11.demonicintervention.init.ItemRegistry;
import com.theundertaker11.demonicintervention.proxy.packets.DIPacketHandler;
import com.theundertaker11.demonicintervention.proxy.packets.servertoclient.SyncIsDaytime;
import com.theundertaker11.demonicintervention.util.DelayObj;
import com.theundertaker11.demonicintervention.util.ModUtils;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber
public class InfusionVampirism extends Infusion {

	public InfusionVampirism(String nameForShow) {
		super(nameForShow, true);
	}

	/**
	 * Makes vampires drink blood when shift+right clicking drinkable entities
	 * (Humans and villagers)
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public static void onRightClick(EntityInteract event) {
		if (event.getSide() == Side.SERVER && event.getEntityPlayer() != null && event.getEntityPlayer().isSneaking()
					&& event.getEntityPlayer().getHeldItemMainhand().isEmpty()
					&& InfusionUtils.hasInfusion(Infusions.vampirism, event.getEntityPlayer())
					&& event.getHand() == EnumHand.MAIN_HAND) {
			
			if (event.getTarget() instanceof EntityLivingBase) {
				EntityLivingBase target = (EntityLivingBase) event.getTarget();
				if (ModUtils.isDrinkable(target)) {
					IVampireData vampData = InfusionUtils.getVampireData(event.getEntityPlayer());
					//Gets a percent then cast the amount of karma to an int, so drinking 1 blood doesn't get you 5 karma. Basically math.floor
					double percentDrank = (double)vampData.drinkBloodFromTarget(DIConfig.bloodBottleAmount, event.getEntityPlayer(), target, false) / (double)DIConfig.bloodBottleAmount;
					KarmaUtils.addKarma(event.getEntityPlayer(), (int)((double)DIConfig.vampireDrinkingKarma * percentDrank));
					event.getWorld().playSound(null, event.getEntityPlayer().getPosition(),
							SoundEvent.REGISTRY.getObjectById(229), SoundCategory.PLAYERS, 0.4F, 1.1F); // 229 generic drink sound

					// Invisible Alphas won't be noticed while drinking
					boolean isInvisibleAlpha = false;
					if (vampData.getIsAlphaVampire() && !ModUtils.hasAnyArmorOrBaubles(event.getEntityPlayer())
							&& event.getEntityPlayer().isPotionActive(Potion.getPotionById(ModUtils.invisibility)))
						isInvisibleAlpha = true;
					// Percent chances to hurt the target entity aka alert them
					if (!isInvisibleAlpha) {
						Random rand = new Random();
						if (ModUtils.lightLevel(event.getEntityPlayer().getPosition(), event.getWorld()) <= 5) {
							if ((rand.nextInt(100) + 1 <= 5)) {
								target.attackEntityFrom(DamageSource.causePlayerDamage(event.getEntityPlayer()), 1);
							}
						} else {
							if ((rand.nextInt(100) + 1 <= 70)) {
								target.attackEntityFrom(DamageSource.causePlayerDamage(event.getEntityPlayer()), 1);
							}
						}
					}
				}
			} else return;
		}
	}

	/**
	 * Tries to kill player if in the sun
	 * <p>
	 * Also removes any potion effects vampires should not have, and gives night
	 * vision.
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		IVampireData extraData = InfusionUtils.getVampireData(event.player);
		if (event.side == Side.SERVER && InfusionUtils.hasInfusion(Infusions.vampirism, event.player)
				&& extraData != null) {
			World world = event.player.getEntityWorld();
			boolean isAlpha = InfusionUtils.isAlphaVampire(event.player);
			boolean isCreative = event.player.isCreative();

			// Kills player in sun
			if (!isSunSafe(event.player, isAlpha) && !isCreative) {
				sunKillVampire(event.player);
			}

			// Hurts player and takes away blood if they have garlic
			// Hurts even more if they have the Garlic Charm
			if (!isAlpha && !isCreative) {
				handleVampireHoldingGarlic(event, extraData);
			}

			boolean hasBlood = (extraData.getBloodLevel() > 0);

			handleBloodBuffsAndDebuffs(event, extraData, hasBlood, isCreative, isAlpha);
			
			// Removes any fire resistance.
			if (event.player.isPotionActive(Potion.getPotionById(ModUtils.fireResistance))) {
				event.player.removePotionEffect(Potion.getPotionById(ModUtils.fireResistance));
			}

			// Makes Alphas invisible without armor or baubles.
			if (hasBlood && isAlpha) {
				if (!ModUtils.hasAnyArmorOrBaubles(event.player)) {
					event.player.addPotionEffect(
							new PotionEffect(Potion.getPotionById(ModUtils.invisibility), 10, 0, false, false));
				}
			}

		}
	}
	
	/**
	 * Handles benefits of having blood and downsides of not having any
	 * @param event
	 * @param extraData
	 * @param hasBlood
	 * @param isCreative
	 * @param isAlpha
	 */
	public static void handleBloodBuffsAndDebuffs(PlayerTickEvent event, IVampireData extraData, boolean hasBlood, boolean isCreative, boolean isAlpha) {

		if (hasBlood) {
			event.player.addPotionEffect(new PotionEffect(Potion.getPotionById(ModUtils.nightVision), 410, 0, false, false));
			event.player.getFoodStats().setFoodLevel(20);
			event.player.getFoodStats().setFoodSaturationLevel(1F);
			if (event.player.isPotionActive(Potion.getPotionById(ModUtils.poison))) {
				event.player.removePotionEffect(Potion.getPotionById(ModUtils.poison));
			}
			if (event.player.isPotionActive(Potion.getPotionById(ModUtils.wither))) {
				event.player.removePotionEffect(Potion.getPotionById(ModUtils.wither));
			}
		} else if (event.player.isPotionActive(Potion.getPotionById(ModUtils.nightVision)))
			event.player.removePotionEffect(Potion.getPotionById(ModUtils.nightVision));
		// Removes certain effects. Poison and wither only if they have blood
		if (event.player.isPotionActive(Potion.getPotionById(ModUtils.regeneration))) {
			event.player.removePotionEffect(Potion.getPotionById(ModUtils.regeneration));
		}
		// Adds debuffs if no blood
		if (!hasBlood && !isCreative) {
			event.player
					.addPotionEffect(new PotionEffect(Potion.getPotionById(ModUtils.moveSlowness), 45, 1, false, true));
			event.player.addPotionEffect(
					new PotionEffect(Potion.getPotionById(ModUtils.miningSlowDown), 45, 0, false, true));
			if (isAlpha) {
				event.player.getFoodStats().setFoodLevel(0);
				event.player.getFoodStats().setFoodSaturationLevel(0F);
			}
		}
	}

	/**
	 * Hurts player and removes blood if they have garlic in their inventory.
	 * <p>
	 * Does even more if they have a garlic charm.
	 * 
	 * @param event
	 */
	public static void handleVampireHoldingGarlic(PlayerTickEvent event, IVampireData extraData) {
		if (!ModUtils.isOnCooldown(event.player.getPersistentID(), "vampireInventoryGarlic")
				&& ModUtils.hasGarlic(event.player)) {
			event.player.attackEntityFrom(ModUtils.VAMPIRE_UNBLOCKABLE, 1F);
			extraData.removeBlood(90, event.player);
			ModUtils.addCooldown(event.player.getPersistentID(), "vampireInventoryGarlic", 20);
		}
		if (!ModUtils.isOnCooldown(event.player.getPersistentID(), "vampireInventoryGarlicCharm")
				&& ModUtils.hasGarlicCharm(event.player, true)) {
			event.player.attackEntityFrom(ModUtils.VAMPIRE_UNBLOCKABLE, 2F);
			extraData.removeBlood(150, event.player);
			ModUtils.addCooldown(event.player.getPersistentID(), "vampireInventoryGarlicCharm", 20);
		}
	}

	/**
	 * Used to decide when vampires should take damage and to increase vampire's
	 * attack damage
	 * 
	 * @param event
	 */
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onDamageTaken(LivingHurtEvent event) {

		// What Decides how much damage is done to vampires.
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (InfusionUtils.hasInfusion(Infusions.vampirism, player)) {
				if ((event.getSource().isFireDamage() || event.getSource() == DamageSource.LAVA)
						&& !ModUtils.isOnCooldown(player.getPersistentID(), "firedamagedelay")) {
					if (InfusionUtils.isAlphaVampire(player))
						player.attackEntityFrom(ModUtils.VAMPIRE_FIRE, 8F);
					else
						player.attackEntityFrom(ModUtils.VAMPIRE_FIRE, 4F);
					ModUtils.addCooldown(player.getPersistentID(), "firedamagedelay", 3);
				}
				event.setAmount(calculateDamageToVampire(event));
			}
		}
		// Increases vampires direct damage by 50%.
		if (event.getSource().getImmediateSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getImmediateSource();
			IVampireData extraData = InfusionUtils.getVampireData(player);
			if (InfusionUtils.hasInfusion(Infusions.vampirism, player) && extraData != null) {
				// Only do 30% of orginal damage without blood.
				if (extraData.getBloodLevel() > 0) {
					if (InfusionUtils.isAlphaVampire(player))
						event.setAmount(event.getAmount() * 1.75F);
					else
						event.setAmount(event.getAmount() * 1.5F);
				} else {
					event.setAmount(event.getAmount() * 0.3F);
				}
			}
		}
	}

	/**
	 * Decides how much damage an entity should do to a vampire/alpha vamp.
	 * 
	 * @param event
	 * @return
	 */
	public static float calculateDamageToVampire(LivingHurtEvent event) {
		// Make sure my unblockable damage sources get through.
		if (event.getSource() == DamageSource.STARVE || event.getSource() == ModUtils.SUN_DEATH
				|| event.getSource() == ModUtils.VAMPIRE_UNBLOCKABLE || event.getSource() == ModUtils.VAMPIRE_FIRE) {
			// System.out.println("Amount: " + event.getAmount());
			return event.getAmount();
		}

		// Bosses do 20% damage.
		if (event.getSource().getTrueSource() instanceof EntityLiving) {
			EntityLiving ent = (EntityLiving) event.getSource().getTrueSource();
			if (!ent.isNonBoss()) {
				return event.getAmount() * 0.2F;
			}
		}

		// Damage calculation is different if the player being hit is an alpha.
		if (event.getEntityLiving() instanceof EntityPlayer) {
			if (InfusionUtils.isAlphaVampire((EntityPlayer) event.getEntityLiving()))
				return calculateDamageToAlphaVampire(event);
		}

		// Normal vampire calculations
		float addedAmount = 0;
		if (event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer attacker = (EntityPlayer) event.getSource().getTrueSource();
			if (InfusionUtils.hasInfusion(Infusions.vampirism, attacker)) {
				return event.getAmount();
			}
			if (InfusionUtils.hasInfusion(Infusions.vampireHunter, attacker)) {
				if (attacker.getHeldItemMainhand().getItem() == ItemRegistry.garlicSword) {
					addedAmount += 36.0F;
				} else {
					return event.getAmount();
				}
			} else if (attacker.getHeldItemMainhand().getItem() == ItemRegistry.garlicSword) {
				addedAmount += 6.0F;
			}
			if (ModUtils.hasGarlic(attacker)) {
				addedAmount += 1.0F;
			}
			try {
				if (ModUtils.hasBaubleGarlic(attacker))
					addedAmount += (event.getAmount() * .15F);
			} catch (NoSuchMethodError e) {
			}
		}
		return addedAmount;
	}

	/**
	 * Special calculations to alpha vampires, see what if any damage they take.
	 * 
	 * @param event
	 * @return
	 */
	public static float calculateDamageToAlphaVampire(LivingHurtEvent event) {
		if (event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer attacker = (EntityPlayer) event.getSource().getTrueSource();
			if (InfusionUtils.hasInfusion(Infusions.vampireHunter, attacker))
				return (event.getAmount() * 1.3F);
			if (InfusionUtils.isAlphaVampire(attacker)) {
				return event.getAmount();
			}
		}
		return 0F;
	}

	/**
	 * Checks all possible things to see if the player should die to the sun
	 * 
	 * @param player
	 * @return
	 */
	public static boolean isSunSafe(EntityPlayer player, boolean isAlpha) {
		syncIsDaytime(player);
		if (!isAlpha && !isSunSafeSuperBasic(player)) {
			if(player.getHeldItemMainhand().getItem() == ItemRegistry.umbrella && player.getHeldItemOffhand().isEmpty()) {
				return true;
			}
			else return false;
		}
		if (isAlpha) {
			return isSunSafeAlpha(player);
		}
		return true;
	}

	public static boolean isSunSafeAlpha(EntityPlayer player) {
		if (!isSunSafeSuperBasic(player)) {
			if (player.getHeldItemMainhand().getItem() == ItemRegistry.umbrella) {
				if (ModUtils.hasDelay(player.getPersistentID(), "alphaUmbrellaDelay")) {
					DelayObj delay = ModUtils.getDelayObj(player.getPersistentID(), "alphaUmbrellaDelay");
					delay.setShouldCountDown(true);
					if (delay.isFinished()) {
						return false;
					} else {
						return true;
					}
				} else {
					ModUtils.addDelay(player.getPersistentID(), "alphaUmbrellaDelay", 400);
					ModUtils.getDelayObj(player.getPersistentID(), "alphaUmbrellaDelay").setShouldCountDown(true);
				}
			}
			// Kills player if they are relying on the 8 seconds but have no blood
			IVampireData data = InfusionUtils.getVampireData(player);
			if (data != null) {
				if (data.getBloodLevel() <= 0)
					return false;
			}
			//
			if (ModUtils.hasDelay(player.getPersistentID(), "alphaSunDelay")) {
				DelayObj delay = ModUtils.getDelayObj(player.getPersistentID(), "alphaSunDelay");
				delay.setShouldCountDown(true);
				if (delay.isFinished()) {
					return false;
				} else {
					data.removeBlood(5, player);
					return true;
				}
			} else {
				ModUtils.addDelay(player.getPersistentID(), "alphaSunDelay", 160);
				ModUtils.getDelayObj(player.getPersistentID(), "alphaSunDelay").setShouldCountDown(true);
			}
		} else {
			DelayObj sunDelay = ModUtils.getDelayObj(player.getPersistentID(), "alphaSunDelay");
			DelayObj umbrellaDelay = ModUtils.getDelayObj(player.getPersistentID(), "alphaUmbrellaDelay");
			if (sunDelay != null) {
				sunDelay.setShouldCountDown(false);
				sunDelay.reset();
			}
			if (umbrellaDelay != null) {
				umbrellaDelay.setShouldCountDown(false);
				umbrellaDelay.reset();
			}
			return true;
		}
		return true;
	}

	/**
	 * Basically just checks if player is in direct sunlight
	 * 
	 * @param player
	 * @return
	 */
	public static boolean isSunSafeSuperBasic(EntityPlayer player) {
		if (!player.isCreative() && player.getEntityWorld().isDaytime()
				&& !ModUtils.isOnCooldown(player.getPersistentID(), "vampireDeath")
				&& player.getEntityWorld().provider.isSurfaceWorld()
				&& !player.getEntityWorld().isRainingAt(player.getPosition())
				&& player.getEntityWorld().getHeight(player.getPosition()).getY() < (player.getPosition().getY() + 1)) {
			return false;
		}
		return true;
	}

	public static void sunKillVampire(EntityPlayer player) {
		player.attackEntityFrom(ModUtils.SUN_DEATH, Float.MAX_VALUE);
		if (!ModUtils.isOnCooldown(player.getPersistentID(), "vampireDeath"))
			ModUtils.addCooldown(player.getPersistentID(), "vampireDeath", 600);
	}

	/**
	 * Because for some reason the client can't be bothered to sync the isDaytime()
	 * for itself :(
	 * 
	 * @param player
	 */
	private static void syncIsDaytime(EntityPlayer player) {
		IVampireData extraData = InfusionUtils.getVampireData(player);
		if (extraData != null) {
			if (extraData.isDaytime() != player.getEntityWorld().isDaytime()) {
				extraData.setIsDaytime(player.getEntityWorld().isDaytime());
				DIPacketHandler.INSTANCE.sendTo(new SyncIsDaytime(player), (EntityPlayerMP) player);
			}
		}
	}
}
