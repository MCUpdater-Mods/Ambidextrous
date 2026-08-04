package mod.ambidextrous.client;

import mod.ambidextrous.core.ClientSetup;
import mod.ambidextrous.core.Config;
import mod.ambidextrous.core.EventPlayerInteract;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(Dist.CLIENT)
public class ClientHandler
{
	// track when a button was pressed to allow swapping to the previous button
	// seamlessly. 0 = not pressed.
	static long msecondsForMainHand = 0;
	static long msecondsForOffHand = 0;

	// check key-binds.
	@SubscribeEvent
	public static void tick(final ClientTickEvent.Post e)
	{
		final Minecraft mc = Minecraft.getInstance();

		if ( ClientSetup.bindingOriginal == null )
		{
			// find keybind for use item, we need this...
			ClientSetup.bindingOriginal = mc.options.keyUse;
		}

		// when mousing up switch to the other button if its down.
		if ( msecondsForMainHand != 0 && msecondsForMainHand < msecondsForOffHand && ClientSetup.MAIN_HAND.get().isDown() && !ClientSetup.OFF_HAND.get().isDown() )
		{
			EventPlayerInteract.setPlayerSuppressionState( mc.player, InteractionHand.OFF_HAND, true, true );
			mc.options.keyUse = ClientSetup.MAIN_HAND.get();
			msecondsForOffHand = 0;
		}

		if ( msecondsForOffHand != 0 && msecondsForOffHand < msecondsForMainHand && ClientSetup.OFF_HAND.get().isDown() && !ClientSetup.MAIN_HAND.get().isDown() )
		{
			EventPlayerInteract.setPlayerSuppressionState( mc.player, InteractionHand.MAIN_HAND, true, true );
			mc.options.keyUse = ClientSetup.OFF_HAND.get();
			msecondsForMainHand = 0;
		}

		// handle switch binds to new active key.
		if ( mc.options.keyUse != ClientSetup.MAIN_HAND.get() && ClientSetup.MAIN_HAND.get().consumeClick() )
		{
			ClientSetup.MAIN_HAND.get().clickCount++;
			EventPlayerInteract.setPlayerSuppressionState( mc.player, InteractionHand.OFF_HAND, true, true );
			mc.options.keyUse = ClientSetup.MAIN_HAND.get();
			msecondsForMainHand = System.currentTimeMillis();
		}

		if ( mc.options.keyUse != ClientSetup.OFF_HAND.get() && ClientSetup.OFF_HAND.get().consumeClick() )
		{
			ClientSetup.OFF_HAND.get().clickCount++;
			EventPlayerInteract.setPlayerSuppressionState( mc.player, InteractionHand.MAIN_HAND, true, true );
			mc.options.keyUse = ClientSetup.OFF_HAND.get();
			msecondsForOffHand = System.currentTimeMillis();
		}

		// stop using one of the two key binds.
		if ( !ClientSetup.MAIN_HAND.get().isDown() && !ClientSetup.OFF_HAND.get().isDown() && mc.options.keyUse != ClientSetup.bindingOriginal )
		{
			EventPlayerInteract.setPlayerSuppressionState( mc.player, InteractionHand.OFF_HAND, false, true );
			mc.options.keyUse = ClientSetup.bindingOriginal;
			msecondsForMainHand = 0;
			msecondsForOffHand = 0;
		}
	}

	@SubscribeEvent
	public static void onScroll(InputEvent.MouseScrollingEvent event) {
		final Minecraft mc = Minecraft.getInstance();
		if (!mc.player.isSpectator()) {
			if (Config.reverseScroll.get()) {
				mc.player.getInventory().swapPaint(event.getScrollDeltaY() * -1.0f);
				event.setCanceled(true);
			}
		}
	}
}
