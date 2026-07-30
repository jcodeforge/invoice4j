package io.github.codeforgecore.screens.dialogs;

import io.github.codeforgecore.Constants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Use this modal dialog to let the user choose one string from a list.
 */
public class ListDialog extends JDialog implements ActionListener {

    public interface ResultListener {
        void onValueSelected(String value);
    }

    private final JList<String> mList;
    private ResultListener mListener;

    public ListDialog(Frame frame, String labelText, String title, String[] data,
                      ResultListener listener) {
        super(frame, title, true);
        mListener = listener;
        mList = new JList<>(data) {
            @Override
            public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation,
                                                  int direction) {
                int row;
                if (orientation == SwingConstants.VERTICAL &&
                        direction < 0 && (row = getFirstVisibleIndex()) != -1) {
                    Rectangle bounds = getCellBounds(row, row);
                    if ((bounds.y == visibleRect.y) && (row != 0)) {
                        Point loc = bounds.getLocation();
                        loc.y--;
                        int prevIndex = locationToIndex(loc);
                        Rectangle prevR = getCellBounds(prevIndex, prevIndex);

                        if (prevR == null || prevR.y >= bounds.y) {
                            return 0;
                        }
                        return prevR.height;
                    }
                }

                return super.getScrollableUnitIncrement(visibleRect, orientation, direction);
            }
        };

        JButton cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(this);

        JButton setButton = new JButton("Auswählen");
        setButton.setActionCommand("Set");
        setButton.addActionListener(this);
        getRootPane().setDefaultButton(setButton);

        mList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        mList.setLayoutOrientation(JList.VERTICAL);
        mList.setVisibleRowCount(-1);
        mList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    setButton.doClick();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(mList);
        scrollPane.setPreferredSize(new Dimension(Constants.DEFAULT_FRAME_WIDTH,
                Constants.DEFAULT_FRAME_HEIGHT));
        scrollPane.setAlignmentX(LEFT_ALIGNMENT);

        //Create a container so that we can add a title around
        //the scroll pane.  Can't add a title directly to the
        //scroll pane because its background would be white.
        //Lay out the label and scroll pane from top to bottom.
        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel(labelText);
        label.setLabelFor(mList);
        listPane.add(label);
        listPane.add(Box.createRigidArea(new Dimension(0,5)));
        listPane.add(scrollPane);
        listPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //Lay out the buttons from left to right.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(cancelButton);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(setButton);

        //Put everything together, using the content pane's BorderLayout.
        Container contentPane = getContentPane();
        contentPane.add(listPane, BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.PAGE_END);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Set".equals(e.getActionCommand())) {
            if (mListener != null) {
                mListener.onValueSelected(mList.getSelectedValue());
            }
        }

        dispose();
    }
}
