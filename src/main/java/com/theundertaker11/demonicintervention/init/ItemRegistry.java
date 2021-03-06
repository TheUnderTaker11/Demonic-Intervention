package com.theundertaker11.demonicintervention.init;

import com.theundertaker11.demonicintervention.items.BaseItem;
import com.theundertaker11.demonicintervention.items.BloodBottle;
import com.theundertaker11.demonicintervention.items.BloodCollector;
import com.theundertaker11.demonicintervention.items.ItemChalice;
import com.theundertaker11.demonicintervention.items.ItemEssenceCollector;
import com.theundertaker11.demonicintervention.items.ItemKarmaExtractor;
import com.theundertaker11.demonicintervention.items.ItemKarmaItems;
import com.theundertaker11.demonicintervention.items.ItemVampireBook;
import com.theundertaker11.demonicintervention.items.baubles.GarlicCharm;
import com.theundertaker11.demonicintervention.items.crops.ItemGarlic;
import com.theundertaker11.demonicintervention.items.infoitems.ItemDebuggingTool;
import com.theundertaker11.demonicintervention.items.infoitems.ItemKarmaReader;
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
	public static Item karmaReader;
	public static Item karmaExtractor;
	
	public static void init()
	{
		debuggingTool = register(new ItemDebuggingTool("debuggingtool"));
		essence = register(new ItemEssenceCollector("essence"));
		umbrella = register(new BaseItem("umbrella", false, I18n.format("desc.umbrella.name"), false));
		garlicSword = register(new ItemGarlicSword("garlicsword"));
		garlic = register(new ItemGarlic("garlic", I18n.format("desc.garlic.name")));
		garlicCharm = register(new GarlicCharm("garliccharm", I18n.format("desc.garliccharm.name")));
		bloodBottle = register(new BloodBottle("bloodbottle"));
		bloodCollector = register(new BloodCollector("bloodcollector"));
		evilEssence = register(new ItemKarmaItems("evilessence"));
		condensedEvil = register(new ItemKarmaItems("condensedevil"));
		pureEssence = register(new ItemKarmaItems("pureessence"));
		condensedPurity = register(new ItemKarmaItems("condensedpurity"));
		vampireBook = register(new ItemVampireBook("vampirebook"));
		chalice = register(new ItemChalice("chalice"));
		karmaReader = register(new ItemKarmaReader("karmareader"));
		karmaExtractor = register(new ItemKarmaExtractor("karmaextractor"));
	}
	
	private static <T extends Item> T register(T item) {
		ForgeRegistries.ITEMS.register(item);

		if (item instanceof IItemModelProvider) {
			((IItemModelProvider) item).registerItemModel(item);
		}
		return item;
	}
}
