package net.xalcon.flightboost.client;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.xalcon.flightboost.common.CommonProxy;

public class ClientProxy extends CommonProxy
{
    @Override
    public boolean isOnCooldown(Item item)
    {
        return Minecraft.getMinecraft().player.getCooldownTracker().hasCooldown(item);
    }
}
