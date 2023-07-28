package aquarium.gui;

import aquarium.animals.Swimmable;
import aquarium.events.Observer;
import aquarium.plants.Immobile;
import aquarium.singleton.Singleton;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.CyclicBarrier;


/**
 * This class represent the main panel of the program.
 * @version 3.3 2021
 * @author Yosi kariv 205507460
 *         Amit Cohen 316432137
 */
public class AquaPanel extends JPanel implements ActionListener, Observer {

    private JButton AddAnimal, AddPlant, DuplicateAnimal, Decorator, Sleep, WakeUp, Reset, Food, Info, Exit; //buttons for the down buttons.
    private JPanel buttonPanel, infoPanel; //new panels.
    public static final HashSet<Swimmable> animal = new HashSet<Swimmable>();
    public static final HashSet<Immobile> planet = new HashSet<Immobile>();
    private int clickCount = 0; //clickCount for the info.
    private boolean setImage = false; //flag for the image.
    private Swimmable[] fishs; //array of Swimmable objects
    private DefaultTableModel tb;
    private JTable table; //new table
    private JScrollPane sp; // new ScrollPane for the table.
    private String[] columnNames = {"Animal", "Color", "Size", "Hor. speed", "Ver. speed", "Eat counter"}; //Columns for the table
    private String[][] data; // for the data of the table.
    private CyclicBarrier barrier;
    private final String BACKGROUND_PATH = "background.jpg";
    private static Singleton worm = null;
    private BufferedImage bgImage;


    /**
     * Constructor for creating the AquaPanel.
     *
     * @return not return anything.
     */
    public AquaPanel() {
        //setting the Button Panel
        setLayout(null);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 10, 0, 0));
        buttonPanel.setBounds(0, 565, 1200, 25);
        add(buttonPanel);

        //setting table on tablePanel
        tb = new DefaultTableModel();
        table = new JTable(tb);
        table.validate();
        sp = new JScrollPane(table);

        //setting new panel for the table.
        infoPanel = new JPanel();
        infoPanel.setBounds(0, 0, 450, 122);
        infoPanel.add(sp);
        infoPanel.setVisible(false);
        this.add(infoPanel);

        //create new buttons.
        AddAnimal = new JButton("AddAnimal");
        AddPlant = new JButton("Add Plant");
        DuplicateAnimal = new JButton("Duplicate");
        Decorator = new JButton("Decorator");
        Sleep = new JButton("Sleep");
        WakeUp = new JButton("WakeUp");
        Reset = new JButton("Reset");
        Food = new JButton("Food");
        Info = new JButton("Info");
        Exit = new JButton("Exit");

        //adding the buttons to the actionListener.
        AddAnimal.addActionListener(this);
        AddPlant.addActionListener(this);
        DuplicateAnimal.addActionListener(this);
        Decorator.addActionListener(this);
        Sleep.addActionListener(this);
        WakeUp.addActionListener(this);
        Reset.addActionListener(this);
        Food.addActionListener(this);
        Info.addActionListener(this);
        Exit.addActionListener(this);

        //adding the buttons to thr buttonPanel.
        buttonPanel.add(AddAnimal);
        buttonPanel.add(AddPlant);
        buttonPanel.add(DuplicateAnimal);
        buttonPanel.add(Decorator);
        buttonPanel.add(Sleep);
        buttonPanel.add(WakeUp);
        buttonPanel.add(Reset);
        buttonPanel.add(Food);
        buttonPanel.add(Info);
        buttonPanel.add(Exit);

        worm = Singleton.getInstance(this);

        try {
            bgImage = ImageIO.read(new File(BACKGROUND_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * actionPerformed method that make the functionaility to buttons..
     *
     * @param e
     * @return not return anything.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == AddAnimal) {
            if (animal.size() < 5)
                new AddAnimalDialog(this);
            else
                JOptionPane.showMessageDialog(this, "You Can Have Only 5 Animals");

        }

        if (e.getSource() == AddPlant) {
            if (planet.size() < 5)
                new AddPlantDialog(this);
            else
                JOptionPane.showMessageDialog(this, "You Can Have Only 5 Plants");
        }

        if (e.getSource() == DuplicateAnimal) {
            new DuplicateAnimal(this);
        }

        if(e.getSource() == Decorator)
        {
            new JPanelDecorator();
        }

        if (e.getSource() == Sleep) {
            for (Swimmable swimmable : animal)
                swimmable.setSuspend();

            System.out.println("Sleep");
        }

        if (e.getSource() == WakeUp) {
            for (Swimmable swimmable : animal)
                swimmable.setResume();

            System.out.println("WakeUp");
        }

        if (e.getSource() == Reset) {
            for (Swimmable swimmable : animal)
                swimmable.interrupt();

            animal.clear();
            planet.clear();
            System.out.println("Reset");
        }

        if (e.getSource() == Food) {
            if (animal.size() > 0) {
                barrier = new CyclicBarrier(animal.size());
                worm.setAlive(true);
                for (Swimmable s : animal)
                    s.setBarrier(barrier);

                Swimmable.setCallBack(this);
                Swimmable.setFood(true);
            }
            System.out.println("Food");
        }

        if (e.getSource() == Info) {
            clickCount++;
            fillTable();

            if (clickCount % 2 == 0 && clickCount > 0) {
                infoPanel.setVisible(true);
                clickCount = 0;
            }

            if (clickCount % 2 != 0) {
                infoPanel.setVisible(false);
            }
        }

        if (e.getSource().equals(Exit)) {
            System.exit(0);
        }
    }

    /**
     * paintComponenet method to set up the aquarium image and the food.
     *
     * @param
     * @return not return anything.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (setImage && bgImage != null) {
            Dimension d = this.getSize();
            g.drawImage(bgImage, 0, 0, d.width, d.height, this);
        }

        // Draw the food if it is exists.
        if(worm.getAlive())
            worm.draw(g);

        if (animal.size() > 0) {
            for (Swimmable Swimmable : animal)
                Swimmable.drawCreature(g);
        }

        if (planet.size() > 0) {
            for (Immobile Immobile : planet)
                Immobile.drawCreature(g);
        }
        repaint();
    }

    /**
     * setFlag method to set up the flg to be the received flag.
     *
     * @param f
     * @return not return anything.
     */
    public boolean setFlagImage(boolean f) {
        return setImage = f;
    }

    /**
     * fillTable method to create the info table.
     *
     * @return not return anything.
     */
    public void fillTable() {
        data = new String[6][6];
        fishs = animal.toArray(new Swimmable[animal.size()]);
        int totalEat = 0;
        int k = 0;

        for (int i = 0; i < fishs.length; i++) {
            data[i][0] = fishs[i].getAnimalName();
            data[i][1] = fishs[i].getColorName();
            data[i][2] = Integer.toString(fishs[i].getSize());
            data[i][3] = Integer.toString(fishs[i].getHorSpeed());
            data[i][4] = Integer.toString(fishs[i].getVerSpeed());
            data[i][5] = Integer.toString(fishs[i].getEatCount());
        }
        for (Swimmable swimmable : animal) {
            totalEat += swimmable.getEatCount();
            k++;
        }
        data[5][0] = "Total";
        data[5][columnNames.length - 1] = String.valueOf(totalEat);
        tb.setDataVector(data, columnNames);
    }

    /**
     * Draw the food.
     *
     * @param g Graphic object.
     */
    private void drawFood(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.orange);
        g2.drawArc(this.getWidth() / 2, this.getHeight() / 2 - 5, 10, 10, 30, 210);
        g2.drawArc(this.getWidth() / 2, this.getHeight() / 2 + 5, 10, 10, 180, 270);
    }

    /**
     * Callback function. This function will be invoked when a fish catches the food.
     * The function will increase the size of the fish using eatInc() method, and reset all of the fishes to their normal state.
     *
     * @param fishes The animal that cought the food.
     */
    public void callbackFunction(Swimmable fishes) {
        Swimmable.setFood(false);
        worm.setAlive(false);

        // Call eatInc() method for the animal that caught the food.
        fishes.eatInc();

        // Rotate the direction of all of the animals, to make them swim in the opposite direction. (They lost the "race" for the food)
        for (Swimmable s : animal)
            s.rotateDirection();
    }

    @Override
    public void update(String msg){
        JOptionPane.showMessageDialog(this,msg);
    }

    public static void setWormInstance()
    {
        Singleton.set();
        worm = null;
    }

    public Singleton getWormInstance(){return worm;}
}
