package mod.ambidextrous.network;

import mod.ambidextrous.core.EventPlayerInteract;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;
import java.util.function.Supplier;

public class PacketSuppressInteraction
{
	private Hand hand = Hand.MAIN_HAND;
	public boolean newState = false;

	public PacketSuppressInteraction(Hand newHand, boolean newState) {
		this.hand = newHand;
		this.newState = newState;
	}

	public static void toBytes(PacketSuppressInteraction msg, PacketBuffer packetBuffer) {
		packetBuffer.writeEnum(msg.hand);
		packetBuffer.writeBoolean(msg.newState);
	}

	public static PacketSuppressInteraction fromBytes(PacketBuffer packetBuffer) {
		return new PacketSuppressInteraction(packetBuffer.readEnum(Hand.class), packetBuffer.readBoolean());
	}

	public static void handle(PacketSuppressInteraction msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			EventPlayerInteract.setPlayerSuppressionState(player, msg.hand, msg.newState, false);
		});
		ctx.get().setPacketHandled(true);
	}

}
