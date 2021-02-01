package technium.commandlogger;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CONFIG;
    public static ForgeConfigSpec.BooleanValue logUsers;
    public static ForgeConfigSpec.BooleanValue logOps;

    static {
        BUILDER.push("Vanilla Seed Options");
        loggingOptions(BUILDER);
        BUILDER.pop();
        CONFIG = BUILDER.build();
    }

    public static void loggingOptions(ForgeConfigSpec.Builder config) {
        logUsers = config.comment("Log Op's Commands?").define("True/False", true);
        logOps = config.comment("Log Regular Player's Commands?").define("True/False", true);
    }

}
