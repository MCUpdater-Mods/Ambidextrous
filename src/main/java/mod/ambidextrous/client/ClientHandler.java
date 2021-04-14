package mod.ambidextrous.client;

import mod.ambidextrous.core.ClientSetup;
import mod.ambidextrous.core.EventPlayerInteract;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientHandler
{
	// track when a button was pressed to allow swapping to the previous button
	// seamlessly. 0 = not pressed.
	static long msecondsForMainHand = 0;
	static long msecondsForOffHand = 0;

	// check key-binds.
	@SubscribeEvent
	public static void tick(final TickEvent.ClientTickEvent e )
	{
		final Minecraft mc = Minecraft.getInstance();

		if ( ClientSetup.bindingOriginal == null )
		{
			// find keybind for use item, we need this...
			ClientSetup.bindingOriginal = mc.options.keyUse;
		}

		// when mousing up switch to the other button if its down.
		if ( msecondsForMainHand != 0 && msecondsForMainHand < msecondsForOffHand && ClientSetup.bindingMainHand.isDown() && !ClientSetup.bindingOffHand.isDown() )
		{
			EventPlayerInteract.setPlayerSuppressionState( mc.player, Hand.OFF_HAND, true, true );
			mc.options.keyUse = ClientSetup.bindingMainHand;
			msecondsForOffHand = 0;
		}

		if ( msecondsForOffHand != 0 && msecondsForOffHand < msecondsForMainHand && ClientSetup.bindingOffHand.isDown() && !ClientSetup.bindingMainHand.isDown() )
		{
			EventPlayerInteract.setPlayerSuppressionState( mc.player, Hand.MAIN_HAND, true, true );
			mc.options.keyUse = ClientSetup.bindingOffHand;
			msecondsForMainHand = 0;
		}

		// handle switch binds to new active key.
		if ( mc.options.keyUse != ClientSetup.bindingMainHand && ClientSetup.bindingMainHand.consumeClick() )
		{
			ClientSetup.bindingMainHand.clickCount++;
			EventPlayerInteract.setPlayerSuppressionState( mc.player, Hand.OFF_HAND, true, true );
			mc.options.keyUse = ClientSetup.bindingMainHand;
			msecondsForMainHand = System.currentTimeMillis();
		}

		if ( mc.options.keyUse != ClientSetup.bindingOffHand && ClientSetup.bindingOffHand.consumeClick() )
		{
			ClientSetup.bindingOffHand.clickCount++;
			EventPlayerInteract.setPlayerSuppressionState( mc.player, Hand.MAIN_HAND, true, true );
			mc.options.keyUse = ClientSetup.bindingOffHand;
			msecondsForOffHand = System.currentTimeMillis();
		}

		// stop using one of the two key binds.
		if ( !ClientSetup.bindingMainHand.isDown() && !ClientSetup.bindingOffHand.isDown() && mc.options.keyUse != ClientSetup.bindingOriginal )
		{
			EventPlayerInteract.setPlayerSuppressionState( mc.player, Hand.OFF_HAND, false, true );
			mc.options.keyUse = ClientSetup.bindingOriginal;
			msecondsForMainHand = 0;
			msecondsForOffHand = 0;
		}
	}

}
