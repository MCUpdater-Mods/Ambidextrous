package mod.ambidextrous.core;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    // track the standard MC bind so it can be restored when we arn't one of the
    // two below.
    public static KeyBinding bindingOriginal = null;

    // basically the mod, in two lines.
    public static KeyBinding bindingMainHand;
    public static KeyBinding bindingOffHand;

    public static void init(final FMLClientSetupEvent event) {
        final String category = "key.categories.gameplay";

        ClientRegistry.registerKeyBinding( bindingOffHand = new KeyBinding( "mod.ambidextrous.offhand", KeyConflictContext.IN_GAME, InputMappings.UNKNOWN, category ) );
        ClientRegistry.registerKeyBinding( bindingMainHand = new KeyBinding( "mod.ambidextrous.mainhand", KeyConflictContext.IN_GAME, InputMappings.UNKNOWN, category ) );

    }
}
