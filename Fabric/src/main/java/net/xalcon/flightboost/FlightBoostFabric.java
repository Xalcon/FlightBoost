package net.xalcon.flightboost;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class FlightBoostFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        EnchantmentCategory target = ClassTinkerers.getEnum(EnchantmentCategory.class, "BOOST_CHARM");


        FlightBoost.init();
    }
}
