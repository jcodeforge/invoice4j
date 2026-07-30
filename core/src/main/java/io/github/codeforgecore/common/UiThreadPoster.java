package io.github.codeforgecore.common;

import java.awt.*;

/**
 * This class is a non-static wrapper around {@link EventQueue} class
 */
public class UiThreadPoster {

    public void post(Runnable runnable) {
        EventQueue.invokeLater(runnable);
    }
}
