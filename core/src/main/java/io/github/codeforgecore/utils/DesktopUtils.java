package io.github.codeforgecore.utils;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.nio.channels.FileLock;

public abstract class DesktopUtils {

    private static final String WINDOWS_STARTUP_DIR = System.getenv("APPDATA") +
            "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";

    public static boolean open(String path) {
        try {
            Desktop.getDesktop().open(new File(path));
            return true;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void browse(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));

        } catch (Exception ignored) {
            // Todo do anything here ?
        }
    }

    /**
     * The following method is the simplest and most robust method to provide a
     * single instance based on a file lock and shut down hook.
     */
    public static boolean isInstanceRunning(String instanceId) {
        String path = System.getProperty("user.home") + File.separator +
                ".fpsoft" + File.separator + instanceId + ".lock";

        try {
            final File file = new File(path);
            file.getParentFile().mkdirs();

            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            final FileLock fileLock = randomAccessFile.getChannel().tryLock();
            if (fileLock != null) {
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        try {
                            fileLock.release();
                            randomAccessFile.close();
                            file.delete();
                        } catch (Exception _) {
                        }
                    }
                });

                return false;
            }
        } catch (Exception _) {}

        return true;
    }

    public static void registerAutostart(String targetPath, String name) {
        File batFile = new File(WINDOWS_STARTUP_DIR, name + "-run.bat");

        try (FileWriter writer = new FileWriter(batFile)) {
            writer.write("@echo off\n");
            writer.write("cmd /c start javaw -jar \"" + targetPath + "\"\n");
            writer.write("exit");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void unregisterAutostart(String name) {
        File batFile = new File(WINDOWS_STARTUP_DIR, name + "-run.bat");
        if (batFile.exists()) {
            batFile.delete();
        }
    }
}
