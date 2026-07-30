package io.github.codeforgecore.utils;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

/**
 * This class is a non-static wrapper around static logger class
 */
public class Logger {

    private final static java.util.logging.Logger LOG =
            java.util.logging.Logger.getLogger(Logger.class.getSimpleName());

    private static final LocalXMLFormatter sFormatter = new LocalXMLFormatter();

    public Logger(String filePath) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        try {
            LOG.setLevel(Level.ALL);
            LOG.setUseParentHandlers(false);

            Handler systemOut = new ConsoleHandler();
            systemOut.setLevel(Level.ALL);
            systemOut.setFormatter(sFormatter);

            Handler fileOut = new FileHandler(file.getAbsolutePath(), 5 * 1024 * 1024, 5, true);
            fileOut.setLevel(Level.ALL);
            fileOut.setFormatter(sFormatter);

            LOG.addHandler(systemOut);
            LOG.addHandler(fileOut);

        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public void e(String tag, String message) {
        LOG.severe(tag + ": "+ message);
    }

    public void i(String tag, String message) {
        LOG.info(tag + ": "+ message);
    }

    public void v(String tag, String message) {
        if (BuildConfig.DEBUG) {
            LOG.finer(tag + ": "+ message);
        }
    }

    public void d(String tag, String message) {
        if (BuildConfig.DEBUG) {
            LOG.fine(tag + ": "+ message);
        }
    }

    private static class LocalXMLFormatter extends Formatter {

        private static final ZoneId sZoneId = ZoneId.of("Europe/Berlin");

        private static final DateTimeFormatter sFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
                        .withZone(sZoneId);

        @Override
        public String format(LogRecord record) {
            String timestamp = sFormatter.format(Instant.ofEpochMilli(record.getMillis()));

            return String.format(
                    """
                    <record>
                      <date>%s</date>
                      <level>%s</level>
                      <thread>%d</thread>
                      <class>%s</class>
                      <message>%s</message>
                    </record>
                    """,
                    timestamp,
                    record.getLevel().getName(),
                    record.getLongThreadID(),
                    record.getSourceClassName(),
                    formatMessage(record)
            );
        }
    }
}
