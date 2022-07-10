package net.xalcon.flightboost.mixin;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.xalcon.flightboost.items.ChargedBoostCharmItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Explosion.class)
public class ExplosionMixin
{

    @Inject(at = @At("HEAD"), method = "explode()V", cancellable = true)
    private void onExplode(CallbackInfo info)
    {
        var self = ((ExplosionAccessor) this);

        var r = self.getRadius();
        var d = r * 2;
        var x = self.getX();
        var y = self.getY();
        var z = self.getZ();

        var level = self.getLevel();
        var entities = level.getEntities(null, AABB.ofSize(new Vec3(x, y, z), d, d, d));
        var items = entities.stream().filter(e -> e instanceof ItemEntity item && item.getItem().getItem() instanceof ChargedBoostCharmItem).map(e -> (ItemEntity)e).toList();
        if(items.size() > 0)
        {
            info.cancel();
            var explosionLocation = new Vec3(x, y, z);
            items.forEach(entity -> {
                var itemStack = entity.getItem();
                var item = (ChargedBoostCharmItem)itemStack.getItem();
                item.onNearbyExplosion(itemStack, entity.position(), explosionLocation, r);
            });
        }
    }
}


