package io.github.jcodeforge.core.screens.common;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class MouseHoverListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        onHover();
    }

    public abstract void onHover();
}
