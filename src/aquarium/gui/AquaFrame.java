package aquarium.gui;
import aquarium.memento.CareTaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represent the main frame of the program.
 * @version 3.3 2021
 * @author Yosi kariv 205507460
 *         Amit Cohen 316432137
 */

public class AquaFrame extends JFrame implements ActionListener {

    private AquaPanel panel; //AquaPanel objext.
    private JMenuBar menuBar; //the bar
    private JMenu File, background, Help; // the buttons on the bar
    private JMenu Memento;//for hw3
    private JMenuItem exit, Image , Blue, none, help; // the sub-buttons
    private JMenuItem save, restore;//for hw3


    /**
     *This constructor is default constructor that set the frame.
     * @return not return anything.
     */
    public AquaFrame()
    {
//-------------------------------------------------------set the frame------------------------------
        this.setTitle("My Aquarium");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
//-------------------------------------------------add the panel to the frame-----------------------
        panel = new AquaPanel();
        this.setContentPane(panel);
//-------------------------------------------------------menu bar------------------------------------
        menuBar  = new JMenuBar();

        //File JMenu
        File = new JMenu("File");
        exit = new JMenuItem("Exit");
        exit.addActionListener(this);
        File.add(exit);
        menuBar.add(File);

        //Background JMenu
        background = new JMenu("Background");
        Image = new JMenuItem("Image");
        Blue = new JMenuItem("Blue");
        none = new JMenuItem("None");

        Image.addActionListener(this);
        Blue.addActionListener(this);
        none.addActionListener(this);

        background.add(Image);
        background.add(Blue);
        background.add(none);
        menuBar.add(background);

        //Memento JMenu
        Memento = new JMenu("Memento");
        save = new JMenuItem("Save Object State");
        save.addActionListener(this);
        restore = new JMenuItem("Restore Object State");
        restore.addActionListener(this);
        Memento.add(save);
        Memento.add(restore);
        menuBar.add(Memento);

        //Help JMenu
        Help = new JMenu("Help");
        help = new JMenuItem("Help");
        help.addActionListener(this);
        Help.add(help);
        menuBar.add(Help);




        this.setJMenuBar(menuBar);
        this.setSize(1200, 650);
        this.setVisible(true);
    }

    /**
     * actionPerformed method that make the functionaility to the menuItems.
     * @param e
     * @return not return anything.
     */

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exit)
        {
            System.exit(0);
        }

        if (e.getSource() == Image)
        {
            panel.setFlagImage(true);
            repaint();
        }
        if (e.getSource() == Blue)
        {
            panel.setFlagImage(false);
            panel.setBackground(Color.CYAN);
        }

        if (e.getSource() == none)
        {
            panel.setFlagImage(false);
            panel.setBackground(Color.LIGHT_GRAY);
        }

        if (e.getSource() == help)
        {
            JOptionPane.showMessageDialog(this, "Home Work 3 \n GUI@ Threads");
        }

        if(e.getSource() == save)
        {
            new SaveObjDialog(new CareTaker());
        }

        if (e.getSource() == restore)
        {
            new RestoreObjState();
        }

    }

    /**
     * main function to run this program.
     * @param args
     * @return not return anything.
     */
    public static void main(String[] args)
    {
        new AquaFrame();
    }



}


