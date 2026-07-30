package io.github.codeforgecore.screens.common.frames;

import javax.swing.*;
import java.awt.*;

public class FrameHelper {

    private final JDesktopPane mRootPane;

    public FrameHelper(Container rootPane) {
        mRootPane = (JDesktopPane) rootPane;
    }

    private boolean isFrameShown(JInternalFrame frame) {
        return frame.getClass().isInstance(mRootPane.getSelectedFrame());
    }

    private void clearHistory() {
        for (JInternalFrame frame : mRootPane.getAllFrames()) {
            try {
                frame.setSelected(false);
            } catch (java.beans.PropertyVetoException _) {}
        }

        mRootPane.removeAll();
    }

    public void replaceFrame(JInternalFrame frame, boolean clearHistory) {
        if (!isFrameShown(frame)) {
            if (clearHistory) {
                clearHistory();
            }

            mRootPane.add(frame);
            frame.setVisible(true);
            frame.setSize(mRootPane.getSize());
            mRootPane.setSelectedFrame(frame);

            try {
                frame.setSelected(true);
            } catch (java.beans.PropertyVetoException _) {}
        }
    }

    private void goBackInFrameHistory() {
        JInternalFrame selectedFrame = mRootPane.getSelectedFrame();
        selectedFrame.setVisible(false);
        mRootPane.remove(selectedFrame);

        JInternalFrame currFame = mRootPane.getAllFrames()[0];
        mRootPane.setSelectedFrame(currFame);

        try {
            selectedFrame.setSelected(false);
            currFame.setSelected(true);
        } catch (java.beans.PropertyVetoException _) {}
    }

    public void navigateBack() {
        if (mRootPane.getAllFrames().length > 1) {
            goBackInFrameHistory();
        }
    }
}
