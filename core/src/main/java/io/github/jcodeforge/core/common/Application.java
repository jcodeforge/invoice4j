package io.github.jcodeforge.core.common;

import javax.swing.*;
import java.io.File;
import java.net.URISyntaxException;

public class Application {

    private final JDesktopPane mDesktopPane;
    private static Application sInstance = null;

    public Application() {
        if (sInstance != null) {
            throw new IllegalStateException("Application already initialized");
        }
        sInstance = this;

        mDesktopPane = new JDesktopPane();
        mDesktopPane.setDesktopManager(new DesktopManagerProxy(mDesktopPane.getDesktopManager()));
        mDesktopPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

        try {
            String path = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI())
                    .getAbsolutePath();
            System.setProperty("jar.path", path);
        } catch (URISyntaxException _) {
            // todo anything here??
        }
    }

    public static Application getInstance() {
        return sInstance;
    }

    public JDesktopPane getDesktopPane() {
        return mDesktopPane;
    }
}
