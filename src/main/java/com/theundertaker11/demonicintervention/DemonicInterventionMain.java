package com.theundertaker11.demonicintervention;

import com.theundertaker11.demonicintervention.api.infusion.InfusionRegistry;
import com.theundertaker11.demonicintervention.capability.CapabilityHandler;
import com.theundertaker11.demonicintervention.crafting.RecipeRegistry;
import com.theundertaker11.demonicintervention.event.EventRegistry;
import com.theundertaker11.demonicintervention.init.BlockRegistry;
import com.theundertaker11.demonicintervention.init.DIConfig;
import com.theundertaker11.demonicintervention.init.ItemRegistry;
import com.theundertaker11.demonicintervention.init.PacketRegistry;
import com.theundertaker11.demonicintervention.init.TileEntityRegistry;
import com.theundertaker11.demonicintervention.proxy.CommonProxy;
import com.theundertaker11.demonicintervention.worldgen.FlowerGen;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Reference.MODID, version = Reference.VERSION, dependencies = "after:matteroverdrive")
public class DemonicInterventionMain
{
	public static final CreativeTabs DICreativeTab = new DICreativeTab(CreativeTabs.getNextID(), "DICreativeTab");
	
	@SidedProxy(clientSide = Reference.CLIENTPROXY, serverSide = Reference.SERVERPROXY)
	public static CommonProxy proxy;
	
	@Mod.Instance
    public static DemonicInterventionMain instance;
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		DIConfig.init(config);
		InfusionRegistry.init();
		ItemRegistry.init();
		BlockRegistry.init();
		TileEntityRegistry.init();
		PacketRegistry.init();
    }
	
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	CapabilityHandler.init();
    	EventRegistry.init();
    	RecipeRegistry.init();
    	
    	GameRegistry.registerWorldGenerator(new FlowerGen(), 50000);
    }
}
