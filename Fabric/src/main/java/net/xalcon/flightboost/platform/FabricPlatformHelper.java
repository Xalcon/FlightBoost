package net.xalcon.flightboost.platform;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.xalcon.flightboost.FlightBoostConfigFabric;
import net.xalcon.flightboost.FlightBoostFabric;
import net.xalcon.flightboost.IFlightBoostConfig;
import net.xalcon.flightboost.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements IPlatformHelper
{

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public EnchantmentCategory getBoostCharmEnchantmentCategory()
    {
        return ClassTinkerers.getEnum(EnchantmentCategory.class, "BOOST_CHARM");
    }

    FlightBoostConfigFabric config;

    @Override
    public IFlightBoostConfig getConfig()
    {
        if(config == null)
        {
            config = new FlightBoostConfigFabric();
            config.readConfigFromFile();
            config.saveConfigToFile();
        }
        return config;
    }
}
