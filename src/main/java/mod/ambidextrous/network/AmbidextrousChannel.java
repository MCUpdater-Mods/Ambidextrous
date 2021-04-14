package mod.ambidextrous.network;

import mod.ambidextrous.core.Ambidextrous;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class AmbidextrousChannel {
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE;

    public static void init() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Ambidextrous.MODID,"suppress"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

        INSTANCE.registerMessage(0,
                PacketSuppressInteraction.class,
                PacketSuppressInteraction::toBytes,
                PacketSuppressInteraction::fromBytes,
                PacketSuppressInteraction::handle
        );
    }
}
