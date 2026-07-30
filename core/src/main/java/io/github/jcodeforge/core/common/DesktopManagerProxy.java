package io.github.jcodeforge.core.common;

import javax.swing.*;

public class DesktopManagerProxy implements DesktopManager {

    private final DesktopManager mDelegate;

    public DesktopManagerProxy(DesktopManager delegate) {
        mDelegate = delegate;
    }

    private boolean canResizeFrame(JComponent frame) {
        return !(frame instanceof JInternalFrame);
    }

    @Override
    public void openFrame(JInternalFrame f) {
        mDelegate.openFrame(f);
    }

    @Override
    public void closeFrame(JInternalFrame f) {
        mDelegate.closeFrame(f);
    }

    @Override
    public void maximizeFrame(JInternalFrame f) {
        if (canResizeFrame(f)) {
            mDelegate.maximizeFrame(f);
        }
    }

    @Override
    public void minimizeFrame(JInternalFrame f) {
        if (canResizeFrame(f)) {
            mDelegate.minimizeFrame(f);
        }
    }

    @Override
    public void iconifyFrame(JInternalFrame f) {
        mDelegate.iconifyFrame(f);
    }

    @Override
    public void deiconifyFrame(JInternalFrame f) {
        mDelegate.deiconifyFrame(f);
    }

    @Override
    public void activateFrame(JInternalFrame f) {
        mDelegate.activateFrame(f);
    }

    @Override
    public void deactivateFrame(JInternalFrame f) {
        mDelegate.deactivateFrame(f);
    }

    @Override
    public void beginDraggingFrame(JComponent f) {
        if (canResizeFrame(f)) {
            mDelegate.beginDraggingFrame(f);
        }
    }

    @Override
    public void dragFrame(JComponent f, int newX, int newY) {
        if (canResizeFrame(f)) {
            mDelegate.dragFrame(f, newX, newY);
        }
    }

    @Override
    public void endDraggingFrame(JComponent f) {
        if (canResizeFrame(f)) {
            mDelegate.endDraggingFrame(f);
        }
    }

    @Override
    public void beginResizingFrame(JComponent f, int direction) {
        if (canResizeFrame(f)) {
            mDelegate.beginResizingFrame(f, direction);
        }
    }

    @Override
    public void resizeFrame(JComponent f, int newX, int newY, int newWidth, int newHeight) {
        if (canResizeFrame(f)) {
            mDelegate.resizeFrame(f, newX, newY, newWidth, newHeight);
        }
    }

    @Override
    public void endResizingFrame(JComponent f) {
        if (canResizeFrame(f)) {
            mDelegate.endResizingFrame(f);
        }
    }

    @Override
    public void setBoundsForFrame(JComponent f, int newX, int newY, int newWidth, int newHeight) {
        mDelegate.setBoundsForFrame(f, newX, newY, newWidth, newHeight);
    }
}
