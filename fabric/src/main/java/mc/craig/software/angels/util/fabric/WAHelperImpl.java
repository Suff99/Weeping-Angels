package mc.craig.software.angels.util.fabric;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class WAHelperImpl {

    public static Packet<?> spawnPacket(Entity livingEntity) {
        return new ClientboundAddEntityPacket(livingEntity);
    }
}
