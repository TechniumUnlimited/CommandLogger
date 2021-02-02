package technium.commandlogger;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mod.EventBusSubscriber()
public class CommandEventHandler {

    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy-HH:mm:ss");
    public static Boolean logOps = Config.logOps.get();
    public static Boolean logPlayers = Config.logUsers.get();

    //Gets the command, user who ran command, and position of the user
    @SubscribeEvent
    public static void onCommand(CommandEvent event) {
        Entity commandUser = event.getParseResults().getContext().getSource().getEntity();
        if (commandUser instanceof PlayerEntity) {
            if (logPlayers || logOps) {
                String commandString = event.getParseResults().getReader().getString();             // /time set day
                String userString = event.getParseResults().getContext().getSource().getName();     // Dev
                String userPosSTring = event.getParseResults().getContext().getSource().getPos().toString();   // (-132.07325670774682, 64.0, 24.49485178546798)

                if (commandUser.hasPermissionLevel(4) && logOps) {
                    try {
                        writeCommandLog("[" + dtf.format(LocalDateTime.now()) + "]: Command Issued by Op: " + commandString + " - User: " + userString + " - User Position: " + userPosSTring);
                    } catch (IOException e) {
                        CommandLogger.LOGGER.info("New Command Logged but Failed To Be Saved:");
                        CommandLogger.LOGGER.info("[CommandLogger]:[" + dtf.format(LocalDateTime.now()) + "]: Command Issued by Op: " + commandString + " - User: " + userString + " - User Position: " + userPosSTring);
                    }
                }
                if (!commandUser.hasPermissionLevel(4) && logPlayers) {
                    try {
                        writeCommandLog("[" + dtf.format(LocalDateTime.now()) + "]: Command Issued: " + commandString + " - User: " + userString + " - User Position: " + userPosSTring);
                    } catch (IOException e) {
                        CommandLogger.LOGGER.info("New Command Logged but Failed To Be Saved:");
                        CommandLogger.LOGGER.info("[CommandLogger]:[" + dtf.format(LocalDateTime.now()) + "]: Command Issued: " + commandString + " - User: " + userString + " - User Position: " + userPosSTring);
                    }
                }
            }
        }
    }

    public static void writeCommandLog(final String s) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(CommandLogger.logsPath.resolve("commandsauditlog.log").toString(), true)));
        out.println(s);
        out.close();
    }
}
