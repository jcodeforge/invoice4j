package io.github.jcodeforge.core.screens.common.renderer;

import io.github.jcodeforge.core.Constants;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TableCellTextAreaRenderer extends JTextArea implements TableCellRenderer {

    private static final int MAX_ROW_HEIGHT = 250;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        setLineWrap(true);
        setWrapStyleWord(true);
        setText((String) value);
        setFont(Constants.SMALL_FONT);
        setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);

        if (table.getRowHeight(row) < getPreferredSize().height &&
                getPreferredSize().height < MAX_ROW_HEIGHT) {
            table.setRowHeight(row, getPreferredSize().height);
        }

        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }

        return this;
    }
}
