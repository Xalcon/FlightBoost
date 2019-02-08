package net.xalcon.flightboost.common.utils;

import net.minecraft.entity.Entity;
import net.minecraft.world.Explosion;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

public class ReflectionUtils
{
    private static Field explosionSizeField;
    private static Field explosionEntityField;

    public static float getExplosionSize(Explosion explosion)
    {
        if(explosionSizeField == null)
            explosionSizeField = ObfuscationReflectionHelper.findField(Explosion.class, "field_77280_f");

        try
        {
            return (float) explosionSizeField.get(explosion);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public static Entity getExplosionExploder(Explosion explosion)
    {
        if(explosionEntityField == null)
            explosionEntityField = ObfuscationReflectionHelper.findField(Explosion.class, "field_77283_e");

        try
        {
            return (Entity) explosionEntityField.get(explosion);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
