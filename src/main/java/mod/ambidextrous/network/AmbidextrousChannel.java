package mod.ambidextrous.network;

import mod.ambidextrous.Ambidextrous;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.NetworkRegistry;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class AmbidextrousChannel {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(
                SuppressInteraction.TYPE,
                SuppressInteraction.STREAM_CODEC,
                ServerHandler::handleSuppress
        );
    }
}
