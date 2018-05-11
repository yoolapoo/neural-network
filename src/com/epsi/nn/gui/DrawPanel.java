package com.epsi.nn.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

public class DrawPanel extends JPanel {

    private BufferedImage image;
    private Graphics2D g2;
    private int currentX, currentY, oldX, oldY;

    public DrawPanel() {
        setOpaque(true);
        setDoubleBuffered(false);
        setBackground(Color.black);
        createListener();
    }

    private void createListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // save coord x,y when mouse is pressed
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // coord x,y when drag mouse
                currentX = e.getX();
                currentY = e.getY();

                if (g2 != null) {
                    // draw line if g2 context not null
                    g2.setStroke(new BasicStroke(10));
                    g2.drawLine(oldX, oldY, currentX, currentY);
                    // refresh draw area to repaint
                    repaint();
                    // store current coords x,y as olds x,y
                    oldX = currentX;
                    oldY = currentY;
                }
            }
        });
    }

    private void createImage() {
        image = new BufferedImage(getSize().width, getSize().height, 1);
        g2 = (Graphics2D) image.getGraphics();
        g2.setBackground(Color.black);
        g2.setPaint(new Color(0, 0, 0));
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setStroke(new BasicStroke(18));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(Color.white);
    }

    protected void paintComponent(Graphics g) {
        if (image == null) {
            createImage();
        }

        g.drawImage(image, 0, 0, null);
    }

    protected BufferedImage getNetworkImage() {
        return DrawPanel.resize(image, 28, 28);
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
