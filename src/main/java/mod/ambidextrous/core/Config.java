package mod.ambidextrous.core;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {

    public static ForgeConfigSpec CLIENT_CONFIG;
    public static ForgeConfigSpec.BooleanValue reverseScroll;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        CLIENT_CONFIG = configBuilder.build();
    }

    private static void setupConfig(ForgeConfigSpec.Builder builder) {
        builder.push("General");
        reverseScroll = builder.comment("Reverse scrollwheel direction for hotbar").define("reverseHotbar", false);
        builder.pop();
    }
}
