package io.github.jcodeforge.core.screens.dialogs;

import javax.swing.*;
import java.awt.*;

public class DialogsManager {

    public static int CONFIRM_DIALOG_YES_OPTION = JOptionPane.YES_OPTION;
    public static int CONFIRM_DIALOG_NO_OPTION = JOptionPane.NO_OPTION;

    public void showMessageDialog(Component parent, String message, String title) {
        // Todo Set additional options
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public Object showInputDialog(Component parent, String message, String title,
                                  Object initialSelectionValue) {

        return JOptionPane.showInputDialog(parent, message, title, JOptionPane.QUESTION_MESSAGE,
                null, null, initialSelectionValue);
    }

    public int showConfirmDialog(Component parent, String message, String title) {
        return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }

    public void showListDialog(Component parent, String labelText, String title,
                                 String[] values, ListDialog.ResultListener listener) {
        Frame frame = JOptionPane.getFrameForComponent(parent);
        ListDialog dialog = new ListDialog(frame, labelText, title, values, listener);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
}
