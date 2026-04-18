package ovh.roro.libraries.packetlistener;

import net.minecraft.network.protocol.Packet;

@SuppressWarnings("rawtypes")
@FunctionalInterface
public interface PacketHandler<T extends Packet> {

    void handle(PacketEvent<T> event);

}
