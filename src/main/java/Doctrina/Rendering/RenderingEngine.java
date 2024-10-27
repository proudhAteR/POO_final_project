package Doctrina.Rendering;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class RenderingEngine {

    private JFrame frame;
    private JPanel panel;
    private BufferedImage bufferedImage;
    private Graphics2D bufferEngine;

    private static RenderingEngine instance;

    private RenderingEngine() {
        initializeFrame();
        initializePanel();
    }

    public static RenderingEngine getInstance() { // singleton
        if (instance == null) {
            instance = new RenderingEngine();
        }
        return instance;
    }

    public void start() {
        frame.setVisible(true);
    }

    public void stop() {
        frame.setVisible(false);
        frame.dispose();
    }

    public Doctrina.Rendering.Canvas buildCanvas() {
        int width = 800;
        int height = 600;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferEngine = bufferedImage.createGraphics();
        bufferEngine.setRenderingHints(buildRenderingHints());
        return new Canvas(bufferEngine);
    }

    public void drawBufferOnScreen() {
        Graphics2D graphics2D = (Graphics2D) panel.getGraphics();
        graphics2D.drawImage(bufferedImage, 0, 0, panel);
        Toolkit.getDefaultToolkit().sync();
        graphics2D.dispose();
    }

    public void addKeyListener(KeyListener keyListener) {
        panel.addKeyListener(keyListener);
    }

    private void initializePanel() {
        panel = new JPanel();
        panel.setBackground(Color.BLUE);
        panel.setFocusable(true); // very important
        panel.setDoubleBuffered(true);
        frame.add(panel);
    }

    private void initializeFrame() {
        frame = new JFrame();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // the window will pop up in the middle of the screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // When the user closes the window, the program will stop
        frame.setState(JFrame.NORMAL);
        frame.setUndecorated(true);
    }

    private RenderingHints buildRenderingHints() {
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        return hints;
    }
}
