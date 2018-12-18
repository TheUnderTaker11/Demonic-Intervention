package com.theundertaker11.demonicintervention.init;

import com.theundertaker11.demonicintervention.items.BaseItem;
import com.theundertaker11.demonicintervention.items.BloodBottle;
import com.theundertaker11.demonicintervention.items.BloodCollector;
import com.theundertaker11.demonicintervention.items.ItemChalice;
import com.theundertaker11.demonicintervention.items.ItemDebuggingTool;
import com.theundertaker11.demonicintervention.items.ItemEssenceCollector;
import com.theundertaker11.demonicintervention.items.ItemVampireBook;
import com.theundertaker11.demonicintervention.items.baubles.GarlicCharm;
import com.theundertaker11.demonicintervention.items.crops.ItemGarlic;
import com.theundertaker11.demonicintervention.items.tools.ItemGarlicSword;
import com.theundertaker11.demonicintervention.render.IItemModelProvider;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemRegistry {

	public static Item debuggingTool;
	public static Item essence;
	public static Item umbrella;
	public static Item garlicSword;
	public static Item garlic;
	public static Item garlicCharm;
	public static Item bloodBottle;
	public static Item bloodCollector;
	public static Item evilEssence;
	public static Item condensedEvil;
	public static Item pureEssence;
	public static Item condensedPurity;
	public static Item vampireBook;
	public static Item chalice;
	
	public static void init()
	{
		debuggingTool = register(new ItemDebuggingTool("debuggingtool"));
		essence = register(new ItemEssenceCollector("essence"));
		umbrella = register(new BaseItem("umbrella", false, I18n.format("desc.umbrella.name")));
		garlicSword = register(new ItemGarlicSword("garlicsword"));
		garlic = register(new ItemGarlic("garlic", I18n.format("desc.garlic.name")));
		garlicCharm = register(new GarlicCharm("garliccharm", I18n.format("desc.garliccharm.name")));
		bloodBottle = register(new BloodBottle("bloodbottle"));
		bloodCollector = register(new BloodCollector("bloodcollector"));
		evilEssence = register(new BaseItem("evilessence"));
		condensedEvil = register(new BaseItem("condensedevil"));
		pureEssence = register(new BaseItem("pureessence"));
		condensedPurity = register(new BaseItem("condensedpurity"));
		vampireBook = register(new ItemVampireBook("vampirebook"));
		chalice = register(new ItemChalice("chalice"));
	}
	
	private static <T extends Item> T register(T item) {
		ForgeRegistries.ITEMS.register(item);

		if (item instanceof IItemModelProvider) {
			((IItemModelProvider) item).registerItemModel(item);
		}
		return item;
	}
}
