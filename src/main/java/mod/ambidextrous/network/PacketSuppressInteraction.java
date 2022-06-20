package mod.ambidextrous.network;

import mod.ambidextrous.core.EventPlayerInteract;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSuppressInteraction
{
	private InteractionHand hand = InteractionHand.MAIN_HAND;
	public boolean newState = false;

	public PacketSuppressInteraction(InteractionHand newHand, boolean newState) {
		this.hand = newHand;
		this.newState = newState;
	}

	public static void toBytes(PacketSuppressInteraction msg, FriendlyByteBuf packetBuffer) {
		packetBuffer.writeEnum(msg.hand);
		packetBuffer.writeBoolean(msg.newState);
	}

	public static PacketSuppressInteraction fromBytes(FriendlyByteBuf packetBuffer) {
		return new PacketSuppressInteraction(packetBuffer.readEnum(InteractionHand.class), packetBuffer.readBoolean());
	}

	public static void handle(PacketSuppressInteraction msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer player = ctx.get().getSender();
			EventPlayerInteract.setPlayerSuppressionState(player, msg.hand, msg.newState, false);
		});
		ctx.get().setPacketHandled(true);
	}

}
