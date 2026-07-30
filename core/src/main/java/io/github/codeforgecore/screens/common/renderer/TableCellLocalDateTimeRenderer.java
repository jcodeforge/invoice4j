package io.github.codeforgecore.screens.common.renderer;

import io.github.codeforgecore.utils.DateTimeUtils;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class TableCellLocalDateTimeRenderer extends TableCellDefaultRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setText(DateTimeUtils.formatDate((LocalDateTime) value));
        return this;
    }
}
