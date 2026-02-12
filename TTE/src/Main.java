//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {

        //Main Menu Window
        JFrame frame = new JFrame("Table-Top-Engine | Alpha 0 | Main Menu");
        JButton helloButton = new JButton("Hello");
        helloButton.setSize(50, 50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().add(button, BorderLayout.CENTER);
        //RENDER FRAME
        frame.pack();
        frame.setSize(800, 600);
        frame.setBackground(Color.PINK);
        frame.setVisible(true);


        //Settings Window
        JFrame settingsFrame = new JFrame("Table-Top-Engine | Alpha 0 | Settings");
        frame.setSize(800, 600);
        frame.pack();
        frame.setBackground(Color.PINK);
        frame.setVisible(true);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create sheet window



    }
}