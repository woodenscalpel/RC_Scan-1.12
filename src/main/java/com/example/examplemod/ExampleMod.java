package com.example.examplemod;

import com.example.examplemod.common.modules.CapabilityProviderModuleCoord;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ExampleMod.MODID, name = ExampleMod.NAME, version = ExampleMod.VERSION,dependencies = "required-after:scannable")
public class ExampleMod
{
    public static final String MODID = "examplemod";
    public static final String NAME = "Example Mod";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

        logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(new CapabilityProviderModuleCoord.StructureLogger());

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        //MinecraftForge.EVENT_BUS.register(new rendertest());

        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
