import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RGBPanel extends JPanel implements KeyListener {
    private int mColorIndex = 0;
    private Color[] mColors = {Color.RED, Color.BLUE, Color.GREEN};
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        System.out.println("KEY PRESSED");
        if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT){
            mColorIndex = (mColorIndex + 1) % 3;
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT){
            mColorIndex = (mColorIndex + 2) % 3;
        }
        setBackground(mColors[mColorIndex]);

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
    public static void main(String[] args){
        JFrame frame = new JFrame();
        RGBPanel panel = new RGBPanel();
        System.out.println("VISIBLE");
        panel.addKeyListener(panel);
        panel.setBackground(panel.mColors[0]);
        frame.addKeyListener(panel);
        frame.add(panel);
        frame.setSize(600,400);
        frame.setVisible(true);
    }
}
