package pid.logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import scala.reflect.io.Directory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Mod(modid = "pid-logger", name = "PID-Logger", version = "1.1",
    clientSideOnly = true, canBeDeactivated = false)
public class PIDLogger {

    @Instance
    public static PIDLogger instance;

    static org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger("PID-Logger");

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        String processFullname = ManagementFactory.getRuntimeMXBean().getName();

        // PROCESS FORMAT: [PROCESS ID]@[PC]
        // IDs: [0]@[1]

        String PID = processFullname.split("@")[0];
        String DESKTOP = processFullname.split("@")[1];

        LOGGER.info("Minecraft PID: " + PID);
        LOGGER.info("Desktop (Session): " + DESKTOP);

        long memorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean()).getTotalPhysicalMemorySize();

        // RAM SIZE FOR JVM (MINECRAFT SETTINGS)

        LOGGER.info("RAM-JVM (bits): " + memorySize);
        LOGGER.info("RAM-JVM (megabytes): " + (memorySize/8/1024/1024));
        LOGGER.info("RAM-JVM (gigabytes): " + (memorySize/8/1024/1024/1024));

        // CURRENT SESSION UUID FROM PID (ID).

        byte[] bitArray = PID.getBytes(StandardCharsets.UTF_8);

        UUID currentUUID = UUID.nameUUIDFromBytes(bitArray);

        LOGGER.info("UUID (from PID): " + currentUUID.toString());

        File DIR$ = new File("./session-logs/");

        if(!DIR$.exists()) {
            DIR$.mkdirs();
        }

        File FILE_LOG = new File("./session-logs/session" + formatDate(LocalDateTime.now()) + ".dat");

        String LOCALES = "Minecraft PID: " + PID + "\n"
                        + "Desktop (Session): " + DESKTOP + "\n"
                        + "RAM-JVM (bits): " + memorySize + "\n"
                        + "RAM-JVM (megabytes): " + (memorySize/8/1024/1024) + "\n"
                        + "RAM-JVM (gigabytes): " + (memorySize/8/1024/1024/1024) + "\n"
                        + "UUID (from PID): " + currentUUID;

        try {
            FILE_LOG.createNewFile();

            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_LOG));
            writer.write(LOCALES);

            writer.close();
        }
        catch (IOException ioException) {
            LOGGER.info("CRITICAL IOEXCEPTION: CREATING LOG FILE (" + ioException + ")");
        }
    }

    private static String formatDate(LocalDateTime time) {
        int yearValue = time.getYear();
        int monthValue = time.getMonthValue();
        int dayValue = time.getDayOfMonth();
        int hourValue = time.getHour();
        int minuteValue = time.getMinute();
        int secValue = time.getSecond();

        // FORMAT:
        // YEARS::MONTHS:DAYS::HOURS::MINUTES::SECONDS

        String LOCAL_TIME = "-" + yearValue + "-" + monthValue + "-" + dayValue + "-" + hourValue + "-" + minuteValue + "-" + secValue;

        return LOCAL_TIME;
    }
}
