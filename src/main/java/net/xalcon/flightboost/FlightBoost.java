package net.xalcon.flightboost;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.xalcon.flightboost.common.CommonProxy;
import org.apache.logging.log4j.Logger;

@Mod(modid = FlightBoost.MODID, name = FlightBoost.NAME, version = FlightBoost.VERSION)
public class FlightBoost
{
    public static final String MODID = "flightboost";
    public static final String NAME = "FlightBoost";
    public static final String VERSION = "@VERSION@";

    private static Logger logger;

    @SidedProxy(clientSide = "net.xalcon." + MODID + ".client.ClientProxy", serverSide = "net.xalcon." + MODID + ".common.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }
}
