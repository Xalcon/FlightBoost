package net.xalcon.flightboost;

import com.oroarmor.config.Config;

import com.oroarmor.config.ConfigItem;
import com.oroarmor.config.ConfigItemGroup;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.util.List;

import static java.util.List.of;

public class FlightBoostConfigFabric extends Config implements IFlightBoostConfig
{
    public static final General GENERAL = new General();
    public static final ChargedBoostCharmConfig CHARGED_BOOST_CHARM = new ChargedBoostCharmConfig();

    public FlightBoostConfigFabric() {
        super(of(FlightBoostConfigFabric.GENERAL, FlightBoostConfigFabric.CHARGED_BOOST_CHARM), new File(FabricLoader.getInstance().getConfigDir().toFile(), "flightboost.json"), Constants.MOD_ID);
    }

    public static class General extends ConfigItemGroup {
        public static final ConfigItem<Integer> boostCharmBaseCooldown =
                new ConfigItem<>("baseCooldown", 20 + 5 * 15, "The base cooldown in ticks. 1 second = 20 ticks");

        public static final ConfigItem<Integer> cooldownReductionPerLevel =
                new ConfigItem<>("cooldownReductionPerEnchantLevel", 15, "The amount of cooldown reduction per level in ticks. 1 second = 20 ticks. The enchant has a total of 5 levels");

        public General() {
            super(of(boostCharmBaseCooldown, cooldownReductionPerLevel), "General");
        }
    }

    public static class ChargedBoostCharmConfig extends ConfigItemGroup {
        public static final ConfigItem<Integer> chargedBoostCharmMaxCharge =
                new ConfigItem<>(
                        "maxCharge",
                        4 * 64 * 4,
                        "The maximum amount of charge a charged boost charm can hold");

        public static final ConfigItem<Integer> chargedBoostCharmChargePerUse =
                new ConfigItem<>(
                        "chargePerUse",
                        4,
                        "Amount of charge used per boost");

        public static final ConfigItem<Integer> chargedBoostCharmExplosionChargeMultiplier =
                new ConfigItem<>(
                        "explosionChargingMultiplier",
                        50,
                        "Multiplier to how much charge the charged boost charm gains per explosion. The Multiplier is multiplied with the explosion strength, the result is then added as charge to the boost charm. CHARGE = MULTIPLIER * (EXPLOSION_RADIUS - DISTANCE_TO_EXPLOSION). Radius of common explosives: TNT = 4, Creeper = 3, End Crystal = 6");

        public ChargedBoostCharmConfig() {
            super(of(chargedBoostCharmMaxCharge, chargedBoostCharmChargePerUse, chargedBoostCharmExplosionChargeMultiplier), "ChargedBoostCharm");
        }
    }

    @Override
    public int getBoostCharmBaseCooldown()
    {
        return General.boostCharmBaseCooldown.getValue();
    }

    @Override
    public int getBoostCharmCdrPerLevel()
    {
        return General.cooldownReductionPerLevel.getValue();
    }

    @Override
    public int getChargedBoostCharmMaxCharge()
    {
        return ChargedBoostCharmConfig.chargedBoostCharmMaxCharge.getValue();
    }

    @Override
    public int getChargedBoostCharmChargePerUse()
    {
        return ChargedBoostCharmConfig.chargedBoostCharmChargePerUse.getValue();
    }

    @Override
    public int getChargedBoostCharmExplosionChargeMultiplier()
    {
        return ChargedBoostCharmConfig.chargedBoostCharmExplosionChargeMultiplier.getValue();
    }
}
