package mod.ambidextrous.core;


import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    public static ModConfigSpec CLIENT_CONFIG;
    public static ModConfigSpec.BooleanValue reverseScroll;

    static {
        ModConfigSpec.Builder configBuilder = new ModConfigSpec.Builder();
        setupConfig(configBuilder);
        CLIENT_CONFIG = configBuilder.build();
    }

    private static void setupConfig(ModConfigSpec.Builder builder) {
        builder.push("General");
        reverseScroll = builder.comment("Reverse scrollwheel direction for hotbar").define("reverseHotbar", false);
        builder.pop();
    }
}
