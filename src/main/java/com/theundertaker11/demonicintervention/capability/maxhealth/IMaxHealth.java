package com.theundertaker11.demonicintervention.capability.maxhealth;

/**
 * A capability to provide a max health bonus to an entity.
 *
 * @author Choonster
 */
public interface IMaxHealth {
	/**
	 * Get the bonus max health.
	 *
	 * @return The bonus max health
	 */
	float getBonusMaxHealth();

	/**
	 * Set the bonus max health.
	 *
	 * @param bonusMaxHealth The bonus max health
	 */
	void setBonusMaxHealth(float bonusMaxHealth);

	/**
	 * Add an amount to the current bonus max health.
	 *
	 * @param healthToAdd The amount of health to add
	 */
	void addBonusMaxHealth(float healthToAdd);
	/**
	 * Synchronise the entity's max health to watching clients.
	 */
	 void synchronise();
}
