import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("RTS Skeleton");
        GamePanel panel = new GamePanel();
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        panel.start();
    }
}
