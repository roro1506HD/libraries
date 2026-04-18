package ovh.roro.libraries.packetlistener;

import net.minecraft.network.protocol.Packet;
import org.bukkit.craftbukkit.entity.CraftPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("rawtypes")
public class PacketEvent<T extends Packet> {

    private final T packet;
    private final CraftPlayer player;
    private final List<Packet<?>> additionalPackets;

    private Packet<?> packetToProcess;
    private boolean cancelled;

    PacketEvent(T packet, CraftPlayer player) {
        this.packet = packet;
        this.player = player;
        this.additionalPackets = new ArrayList<>();

        this.packetToProcess = packet;
    }

    public boolean cancelled() {
        return this.cancelled;
    }

    public void cancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public T packet() {
        return this.packet;
    }

    public void packet(Packet<?> packet) {
        if (packet.type().flow() != this.packet.type().flow()) {
            throw new IllegalArgumentException("Cannot set packet: provided packet is not on the same flow and the same state");
        }

        this.packetToProcess = Objects.requireNonNull(packet);
    }

    public void addPacket(Packet<?> packet) {
        this.additionalPackets.add(Objects.requireNonNull(packet));
    }

    public CraftPlayer player() {
        return this.player;
    }

    Packet<?> packetToProcess() {
        return this.packetToProcess;
    }

    List<Packet<?>> additionalPackets() {
        return this.additionalPackets;
    }
}
