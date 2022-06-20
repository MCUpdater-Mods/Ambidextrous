package mod.ambidextrous.core;

import mod.ambidextrous.network.AmbidextrousChannel;
import mod.ambidextrous.network.PacketSuppressInteraction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.WeakHashMap;

@Mod.EventBusSubscriber
public class EventPlayerInteract
{

	private static class SuppressionState
	{
		public SuppressionState(
				final InteractionHand hand2,
				final boolean newSetting )
		{
			hand = hand2;
			suppress = newSetting;
		}

		public InteractionHand hand = InteractionHand.MAIN_HAND;
		public boolean suppress = false;
	};

	// just for the sake of simplicity.
	private static final WeakHashMap<Player, SuppressionState> server_state = new WeakHashMap<>();
	private static final WeakHashMap<Player, SuppressionState> client_state = new WeakHashMap<>();

	// Are we on the server or the client?
	private static WeakHashMap<Player, SuppressionState> getState(
			final Player entityPlayer )
	{
		if (entityPlayer == null || entityPlayer.getCommandSenderWorld().isClientSide)
		{
			return client_state;
		}

		return server_state;
	}

	@SubscribeEvent
	public static void click(
			final PlayerInteractEvent.RightClickItem e )
	{
		handleClick( e );
	}

	@SubscribeEvent
	public static void click(
			final PlayerInteractEvent.RightClickBlock e )
	{
		handleClick( e );

		if ( !e.isCanceled() )
		{
			final ItemStack s = e.getItemStack();
			if ( s == null || s.getItem().doesSneakBypassUse( s, e.getWorld(), e.getPos(), e.getPlayer() ) )
			{
				e.setUseBlock( Event.Result.ALLOW );
			}
		}
	}

	private static void handleClick(
			final PlayerInteractEvent e )
	{
		final SuppressionState current = getState( e.getPlayer() ).get( e.getPlayer() );

		if ( current == null || !current.suppress)
		{
			// if nothing is set, or supression isn't on, then we don't care.
			return;
		}

		if ( current.hand == e.getHand() )
		{
			// if we here suppression is on, was it the correct hand?
			e.setCanceled( true );
		}
	}

	public static void setPlayerSuppressionState(
			final Player player,
			final InteractionHand hand,
			final boolean newState,
			final boolean sendToServer )
	{
		if ( sendToServer )
		{
			final PacketSuppressInteraction packetSI = new PacketSuppressInteraction(hand, newState);
			AmbidextrousChannel.INSTANCE.sendToServer( packetSI );
		}

		getState( player ).put( player, new SuppressionState( hand, newState ) );
	}

}
