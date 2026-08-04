package mod.ambidextrous.core;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.util.Lazy;

public class ClientSetup {
    // track the standard MC bind so it can be restored when we arn't one of the
    // two below.
    public static KeyMapping bindingOriginal = null;

    // basically the mod, in two lines.
    public static final String CATEGORY = "key.ambidextrous.category";
    public static final Lazy<KeyMapping> MAIN_HAND = Lazy.of(() -> new KeyMapping("mod.ambidextrous.mainhand", KeyConflictContext.IN_GAME, InputConstants.UNKNOWN, CATEGORY));
    public static final Lazy<KeyMapping> OFF_HAND = Lazy.of(() -> new KeyMapping("mod.ambidextrous.offhand", KeyConflictContext.IN_GAME, InputConstants.UNKNOWN, CATEGORY));

    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(MAIN_HAND.get());
        event.register(OFF_HAND.get());
    }
}
