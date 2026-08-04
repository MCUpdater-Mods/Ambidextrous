package mod.ambidextrous.network;

import io.netty.buffer.ByteBuf;
import mod.ambidextrous.Ambidextrous;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.InteractionHand;

import java.util.function.IntFunction;

public record SuppressInteraction(InteractionHand hand, Boolean state) implements CustomPacketPayload {
	public static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(Ambidextrous.MODID, "suppress_interaction");
	public static final CustomPacketPayload.Type<SuppressInteraction> TYPE = new CustomPacketPayload.Type<>(LOCATION);

	public static final IntFunction<InteractionHand> BY_ID = ByIdMap.continuous(
			InteractionHand::ordinal,
			InteractionHand.values(),
			ByIdMap.OutOfBoundsStrategy.ZERO
	);

	public static final StreamCodec<ByteBuf, SuppressInteraction> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.idMapper(BY_ID, InteractionHand::ordinal),
			SuppressInteraction::hand,
			ByteBufCodecs.BOOL,
			SuppressInteraction::state,
			SuppressInteraction::new
	);

	@Override
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
