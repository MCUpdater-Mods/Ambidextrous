package mod.ambidextrous.network;

import mod.ambidextrous.core.EventPlayerInteract;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerHandler {
	public static void handleSuppress(SuppressInteraction suppressInteraction, IPayloadContext context) {
		context.enqueueWork(() -> {
			ServerPlayer player = (ServerPlayer) context.player();
			EventPlayerInteract.setPlayerSuppressionState(player, suppressInteraction.hand(), suppressInteraction.state(), false);
		});
	}
}
