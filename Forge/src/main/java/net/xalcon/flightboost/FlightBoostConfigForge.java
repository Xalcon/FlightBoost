package net.xalcon.flightboost;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class FlightBoostConfigForge
{
    public static final ForgeConfigSpec spec;

    public static final Config GENERAL;

    static
    {
        final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        spec = specPair.getRight();
        GENERAL = specPair.getLeft();
    }

    public static class Config implements IFlightBoostConfig
    {

        private final ForgeConfigSpec.ConfigValue<Integer> boostCharmBaseCooldown;
        private final ForgeConfigSpec.ConfigValue<Integer> cooldownReductionPerLevel;
        private final ForgeConfigSpec.ConfigValue<Integer> chargedBoostCharmMaxCharge;
        private final ForgeConfigSpec.ConfigValue<Integer> chargedBoostCharmChargePerUse;
        private final ForgeConfigSpec.ConfigValue<Integer> chargedBoostCharmExplosionChargeMultiplier;

        private Config(ForgeConfigSpec.Builder builder)
        {
            builder.push("General");
            boostCharmBaseCooldown = builder
                    .comment("The base cooldown in ticks. 1 second = 20 ticks")
                    .translation("config.flightboost.boostCharmBaseCooldown.description")
                    .defineInRange("baseCooldown", 20 + 5 * 15, 0, Short.MAX_VALUE);
            cooldownReductionPerLevel = builder
                    .comment("The amount of cooldown reduction per level in ticks. 1 second = 20 ticks. The enchant has a total of 5 levels")
                    .translation("config.flightboost.cooldownReductionPerLevel.description")
                    .defineInRange("cooldownReductionPerEnchantLevel", 15, 0, Short.MAX_VALUE);
            builder.pop();

            builder.push("ChargedBoostCharm");
            chargedBoostCharmMaxCharge = builder
                    .comment("The maximum amount of charge a charged boost charm can hold")
                    .translation("config.flightboost.chargedBoostCharmMaxCharge.description")
                    .defineInRange("maxCharge", 4 * 64 * 4, 0, Short.MAX_VALUE);
            chargedBoostCharmChargePerUse = builder
                    .comment("Amount of charge used per boost")
                    .translation("config.flightboost.chargedBoostCharmChargePerUse.description")
                    .define("chargePerUse", 4);
            chargedBoostCharmExplosionChargeMultiplier = builder
                    .comment("""
                            Multiplier to how much charge the charged boost charm gains per explosion.\s
                            The Multiplier is multiplied with the explosion strength, the result is then added as charge to the boost charm.\s
                            CHARGE = MULTIPLIER * (EXPLOSION_RADIUS - DISTANCE_TO_EXPLOSION)\s
                            Radius of common explosives: TNT = 4, Creeper = 3, End Crystal = 6
                            """)
                    .translation("config.flightboost.chargedBoostCharmExplosionChargeModifier.description")
                    .defineInRange("explosionChargingMultiplier", 50, 0, Short.MAX_VALUE);
            builder.pop();
        }

        @Override
        public int getBoostCharmBaseCooldown()
        {
            return boostCharmBaseCooldown.get();
        }

        @Override
        public int getBoostCharmCdrPerLevel()
        {
            return cooldownReductionPerLevel.get();
        }

        @Override
        public int getChargedBoostCharmMaxCharge()
        {
            return chargedBoostCharmMaxCharge.get();
        }

        @Override
        public int getChargedBoostCharmChargePerUse()
        {
            return chargedBoostCharmChargePerUse.get();
        }

        @Override
        public int getChargedBoostCharmExplosionChargeMultiplier()
        {
            return chargedBoostCharmExplosionChargeMultiplier.get();
        }
    }
}
