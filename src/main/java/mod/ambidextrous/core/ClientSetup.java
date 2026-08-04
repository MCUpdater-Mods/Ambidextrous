package mod.ambidextrous.core;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    // track the standard MC bind so it can be restored when we arn't one of the
    // two below.
    public static KeyMapping bindingOriginal = null;

    // basically the mod, in two lines.
    public static KeyMapping bindingMainHand;
    public static KeyMapping bindingOffHand;

    public static void registerKeys(RegisterKeyMappingsEvent event) {
        final String category = "key.categories.gameplay";

        event.register( bindingOffHand = new KeyMapping( "mod.ambidextrous.offhand", KeyConflictContext.IN_GAME, InputConstants.UNKNOWN, category ) );
        event.register( bindingMainHand = new KeyMapping( "mod.ambidextrous.mainhand", KeyConflictContext.IN_GAME, InputConstants.UNKNOWN, category ) );
    }
}
