package io.github.codeforgecore.utils;

import io.github.codeforgecore.Constants;
import io.github.codeforgecore.screens.common.renderer.TableCellDefaultRenderer;
import io.github.codeforgecore.screens.common.renderer.TableCellLocalDateTimeRenderer;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.time.LocalDateTime;

public abstract class FPViewUtils {

    private final static int DEFAULT_TABLE_ROW_HEIGHT = 60;
    private final static int DEFAULT_TABLE_ROW_MARGIN = 5;

    public static void adjustFrameLocation(JFrame frame, Constants.FrameLocation location) {
        // Ensure frame has a valid size
        if (frame.getWidth() == 0 || frame.getHeight() == 0) {
            frame.pack();
        }

        GraphicsConfiguration gc = frame.getGraphicsConfiguration();

        Rectangle bounds = gc.getBounds();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);

        int minX = bounds.x + insets.left;
        int minY = bounds.y + insets.top;

        int width = bounds.width - insets.left - insets.right;
        int height = bounds.height - insets.top - insets.bottom;

        int posX = 0;
        int posY = 0;
        switch (location) {
            case TOP_LEFT -> {
                posX = minX;
                posY = minY;
            }
            case TOP_RIGHT -> {
                posX = minX + width - frame.getWidth();
                posY = minY;
            }
            case BOTTOM_LEFT -> {
                posX = minX;
                posY = minY + height - frame.getHeight();
            }
            case BOTTOM_RIGHT -> {
                posX = minX + width - frame.getWidth();
                posY = minY + height - frame.getHeight();
            }
        }

        frame.setLocation(posX, posY);
    }

    public static void showProgressOverlay(JFrame parentFrame, JPanel progressOverlayView, boolean show) {
        if (progressOverlayView.getComponents().length == 0) {
            progressOverlayView.setOpaque(true);
            progressOverlayView.setBackground(new Color(0, 0, 0, 120));

            JProgressBar progressBar = new JProgressBar();
            progressBar.setIndeterminate(true);

            JLabel label = new JLabel("...");
            label.setForeground(Color.WHITE);

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

            contentPanel.add(label);
            contentPanel.add(Box.createVerticalStrut(10));
            contentPanel.add(progressBar);

            progressOverlayView.removeAll();
            progressOverlayView.add(contentPanel);

            parentFrame.setGlassPane(progressOverlayView);
        }

        showProgressOverlay(progressOverlayView, show);
    }

    private static void showProgressOverlay(JPanel progressOverlayView, boolean show) {
        SwingUtilities.invokeLater(() -> {
            progressOverlayView.setVisible(show);
            progressOverlayView.revalidate();
            progressOverlayView.repaint();
        });
    }

    public static void setTableDefaults(JTable table, int rowHeight, boolean showTableHeader,
                                        boolean focusable) {
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFocusable(focusable);
        table.setShowGrid(false);
        table.setRowHeight(rowHeight > 0 ? rowHeight : DEFAULT_TABLE_ROW_HEIGHT);
        table.setRowMargin(DEFAULT_TABLE_ROW_MARGIN);
        table.setDefaultRenderer(Object.class, new TableCellDefaultRenderer());
        table.setDefaultRenderer(LocalDateTime.class, new TableCellLocalDateTimeRenderer());
        table.setFont(Constants.SMALL_FONT);
        table.setFillsViewportHeight(true);

        if (showTableHeader) {
            JTableHeader header = table.getTableHeader();
            if (header != null) {
                header.setFont(Constants.TINY_FONT);
                header.setReorderingAllowed(false);
                header.setResizingAllowed(true);
            }
        } else {
            table.setTableHeader(null);
        }

        // Set tooltips for table cells
        addTableTooltip(table);
    }

    private static void addTableTooltip(JTable table) {
        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (row < 0 || col < 0) {
                    table.setToolTipText(null);
                    return;
                }

                Object value = table.getValueAt(row, col);
                if (value != null) {
                    String text = value.toString();

                    int preferredTextLength = 100;

                    if (!text.equals("null") && !text.isEmpty()) {
                        if (text.length() > preferredTextLength) {
                            text = text.substring(0, preferredTextLength) + "...";
                        }
                        table.setToolTipText(text);
                    } else {
                        table.setToolTipText(null);
                    }
                }
            }
        });
    }

    public static void adjustTableColumnWidth(JTable table, int width) {
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setMinWidth(width);
        }

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    public static void addHyperlink(JPanel parent, String label, MouseListener listener) {
        parent.removeAll();
        JLabel hyperlink = new JLabel(label);
        hyperlink.setForeground(Color.BLUE.darker());
        hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hyperlink.addMouseListener(listener);
        parent.add(hyperlink);
        parent.revalidate();
    }

    public static void scrollToTableTop(JTable table) {
        JViewport viewport = (JViewport) table.getParent();
        if (viewport != null) {
            JScrollPane scrollPane = (JScrollPane) viewport.getParent();
            scrollPane.getViewport().setViewPosition(new Point(0, 0));
        }
    }
}
