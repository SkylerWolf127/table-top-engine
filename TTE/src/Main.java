//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/*
I pulled save / load (PlayerSheetIO) class from a previous project.
Works pretty well, will tune if it becomes problematic
-Skyler
 */
public class Main{
    public static void main(String[] args) throws IOException
    {

        //Main Menu Window
        JFrame frame = new JFrame("Table-Top-Engine | Alpha 0 | Main Menu");
        JButton helloButton = new JButton("Hello");
        helloButton.setSize(50, 50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().add(helloButton, BorderLayout.CENTER);
        //RENDER FRAME
        frame.setPreferredSize(new Dimension(640,480));
        frame.pack();
        frame.setBackground(Color.PINK);
        frame.setVisible(true);


        //Settings Window
        JFrame settingsFrame = new JFrame("Table-Top-Engine | Alpha 0 | Settings");
        frame.setSize(400, 300);
        frame.pack();
        frame.setBackground(Color.PINK);
        frame.setVisible(true);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create sheet window
    }
}