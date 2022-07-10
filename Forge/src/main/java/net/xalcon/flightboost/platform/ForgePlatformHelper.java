package net.xalcon.flightboost.platform;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.xalcon.flightboost.FlightBoostConfigForge;
import net.xalcon.flightboost.IFlightBoostConfig;
import net.xalcon.flightboost.items.BoostCharmItem;
import net.xalcon.flightboost.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper
{

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    private EnchantmentCategory boostCharm = EnchantmentCategory.create("boost_charm", item -> item instanceof BoostCharmItem);

    @Override
    public EnchantmentCategory getBoostCharmEnchantmentCategory()
    {
        return boostCharm;
    }

    @Override
    public IFlightBoostConfig getConfig()
    {
        return FlightBoostConfigForge.GENERAL;
    }
}
