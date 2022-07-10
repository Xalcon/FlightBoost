package net.xalcon.flightboost;

import net.xalcon.flightboost.platform.Services;

public class FlightBoost
{
    // This method serves as an initialization hook for the mod. The vanilla
    // game has no mechanism to load tooltip listeners so this must be
    // invoked from a mod loader specific project like Forge or Fabric.
    public static void init() {

        Constants.LOG.info("FlightBoost initializing on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.isDevelopmentEnvironment() ? "development" : "production");
        ModRegistry.initialize();
    }
}