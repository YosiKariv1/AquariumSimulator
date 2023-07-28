package aquarium.gui;

import aquarium.animals.Fish;
import aquarium.animals.Jellyfish;
import aquarium.animals.Swimmable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Integer.parseInt;
import static aquarium.gui.AquaPanel.animal;



/**
 * This class represent the dialog opened after clicking Add Animal button
 * @version 3.3 2021
 * @author Yosi kariv 205507460
 *         Amit Cohen 316432137
 */
public class AddAnimalDialog extends JDialog implements ActionListener {

    private JPanel panel;//new panel.
    private AquaPanel p; //AquaPanel object.
    private JLabel title, option1, option2, option3, option4, option5, option6; //labels for the add animal window.
    private JRadioButton Fish, JellyFish;//radioButtons for choosing the animal.
    private ButtonGroup group; //ButtonGroup for the radioButtons.
    private Insets insets; //Insests object for chanfing the location on the panel.
    private Dimension size; //Simension object to determine the size.
    private JTextField textField, textField1; //textField for the size entering.
    private JSlider slider1, slider2; //sliders for horSpeed and verSpeed.
    private JComboBox colorList; //ComboBox for the color list.
    private String[] colors = {"Black", "Red", "Blue", "Green", "Cyan", "Orange", "Yellow", "Magenta", "Pink"}; //list of Colors.
    private JButton addButton;//



    /**
     *This constructor is setting up the dialog for the Add animal oprion.
     * @return not return anything.
     */
    public AddAnimalDialog(AquaPanel p)
    {
        this.p = p;
//----------------------------------------setting up the dialog--------------------------------------------------
        this.setSize(480, 550);
        this.setResizable(false);
        this.setVisible(true);
        this.getContentPane().setLayout(null);
        this.setTitle("Add Animal");

//----------------------------------------setting up the panel---------------------------------------------------
        panel = new JPanel(null);
        panel.setBounds(0, 0, 480, 550);
        this.getContentPane().add(panel, BorderLayout.CENTER);

//--------------------------------1.Radio Button for the first option--------------------------------------------
        //create label and RadioButtons for the first option.
        option1 = new JLabel("1. Choose Animal: ");
        Fish = new JRadioButton("Fish");
        JellyFish = new JRadioButton("JellyFish");
        Fish.setSelected(true);

        //adding Fish and JellyFish to the ActionListener
        Fish.addActionListener(this);
        JellyFish.addActionListener(this);

        //new ButtonGroup for the RadioButtons.
        group = new ButtonGroup();


        //setting up the location on the screen for the label and the radio button
        insets = panel.getInsets();
        size = option1.getPreferredSize();
        option1.setBounds(25 + insets.left, 130 + insets.top, size.width, size.height);

        //determine the location of the Fish RadioButton.
        size = Fish.getPreferredSize();
        Fish.setBounds(175 + insets.left, 130 + insets.top, size.width, size.height);

        //determine the location for JellyFish RadioButton.
        size = JellyFish.getPreferredSize();
        JellyFish.setBounds(250 + insets.left, 130 + insets.top, size.width, size.height);

        //adding the RadioButton to the groupButton and the label and RadioButton to the panel.
        group.add(Fish);
        group.add(JellyFish);
        panel.add(option1);
        panel.add(Fish);
        panel.add(JellyFish);

//--------------------------------2.Text Field for the second option--------------------------------------------
        //create label and TextField for the second option.
        option2 = new JLabel("2. Choose The Size (Only Between 20 - 320): ");
        textField = new JTextField(10);
        textField.addActionListener(this);

        //determine the location for the option2 label.
        size = option2.getPreferredSize();
        option2.setBounds(25 + insets.left, 180 + insets.top, size.width, size.height);

        //determine the location for the textField.
        size = textField.getPreferredSize();
        textField.setBounds(285 + insets.left, 180 + insets.top, size.width, size.height);

        //adding the label and the textField to the panel.
        panel.add(option2);
        panel.add(textField);

//------------------------------------3.Slider for the third option--------------------------------------------
        //create label and slider for the third option.
        option3 = new JLabel("3. Choose Horizontal Speed:");
        slider1 = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);

        //slider configuration
        slider1.setMajorTickSpacing(1);
        slider1.setMinorTickSpacing(1);
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);

        //determine the location for the option3 label.
        size = option3.getPreferredSize();
        option3.setBounds(25 + insets.left, 220 + insets.top, size.width, size.height);

        //determine the location for the option3 slider.
        size = slider1.getPreferredSize();
        slider1.setBounds(200 + insets.left, 220 + insets.top, size.width, size.height);

        //adding the label and the slider to the panel.
        panel.add(option3);
        panel.add(slider1);

//------------------------------------4.Slider for the fourth option--------------------------------------------
        //create label and slider for the fourth option.
        option4 = new JLabel("4. Choose Vertical Speed:");
        slider2 = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);

        //slider configuration
        slider2.setMajorTickSpacing(1);
        slider2.setMinorTickSpacing(1);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);

        //determine the location for the option4 label.
        size = option4.getPreferredSize();
        option4.setBounds(25 + insets.left, 290 + insets.top, size.width, size.height);

        //determine the location for the slider2.
        size = slider2.getPreferredSize();
        slider2.setBounds(200 + insets.left, 290 + insets.top, size.width, size.height);

        //adding the label and the slider to the panel.
        panel.add(option4);
        panel.add(slider2);

//------------------------------------5.Combo Box for the fifth option--------------------------------------------
        //create label and ComboBox for the fifth option.
        option5 = new JLabel("5. Choose Color:");
        colorList = new JComboBox(colors);
        colorList.setSelectedIndex(4);
        colorList.addActionListener(this);

        //determine the location for the option5 label.
        size = option5.getPreferredSize();
        option5.setBounds(25 + insets.left, 360 + insets.top, size.width, size.height);

        //determine the location for the ComboBox.
        size = colorList.getPreferredSize();
        colorList.setBounds(200 + insets.left, 360 + insets.top, size.width, size.height);

        //adding the label and the ComboBox to the panel
        panel.add(option5);
        panel.add(colorList);

//------------------------------------6.Text Box for the Sixth option--------------------------------------------
        //create label and TextField for the second option.
        option6 = new JLabel("6. Choose The Food Frequency(Only Between 1 - 100): ");
        textField1 = new JTextField(7);
        textField1.addActionListener(this);

        //determine the location for the option2 label.
        size = option6.getPreferredSize();
        option6.setBounds(25 + insets.left, 410 + insets.top, size.width, size.height);

        //determine the location for the textField.
        size = textField1.getPreferredSize();
        textField1.setBounds(340 + insets.left, 410 + insets.top, size.width, size.height);

        //adding the label and the textField to the panel.
        panel.add(option6);
        panel.add(textField1);

//---------------------------------------------setting the title--------------------------------------------------
        //create label for the AddAnimal button.
        title = new JLabel("Add Animal");
        title.setFont(new Font(title.getName(), Font.BOLD, 30));

        //determine the location for the label.
        size = title.getPreferredSize();
        title.setBounds(150 + insets.left, 50 + insets.top, size.width, size.height);

        //adding the label to the panel.
        panel.add(title);

//-----------------------------------------------Create Button----------------------------------------
        //create new button for the crete button
        addButton = new JButton("Create");
        addButton.addActionListener(this);

        //determine the location for the addButton.
        size = addButton.getPreferredSize();
        addButton.setBounds(180 + insets.left, 460 + insets.top, size.width, size.height);

        //adding the addButton to the panel.
        panel.add(addButton);

    }

    /**
     * actionPerformed method that make the functionality for the AddAnimal dialog.
     * @param e
     * @return not return anything.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == addButton)
            AddAnimal();
    }

    public void AddAnimal()
    {
        int horSpeed = slider1.getValue();
        int size = parseInt(textField.getText());
        int verSpeed = slider2.getValue();
        int index = colorList.getSelectedIndex();
        int foodFreq = parseInt(textField1.getText());
        String colorName = (String) colorList.getItemAt(index);
        Color color = StringToColor(colorName);

        if (size < 20 || size > 320) {
            JOptionPane.showMessageDialog(this, "Please Choose Size Between 20 to 320");
            textField.setText("");
            return;
        }
        if (foodFreq < 1 || foodFreq > 100) {
            JOptionPane.showMessageDialog(this, "Please Choose Food Frequency Between 1 to 100");
            textField1.setText("");
            return;
        }
        else
        {

            if (Fish.isSelected()) {
                Swimmable fish = new Fish(p, size , horSpeed, verSpeed, color, colorName, foodFreq);
                fish.start();
                animal.add(fish);
            }

            if (JellyFish.isSelected()) {
                Swimmable jellyFish = new Jellyfish(p, size, horSpeed, verSpeed, color, colorName, foodFreq);
                jellyFish.start();
                animal.add(jellyFish);
            }
            this.dispose();
        }
    }


    /**
     * StringToColor function that convert a String received to a Color object.
     * @param col
     * @return color Object
     */
    public Color StringToColor(String col)
    {
        Color color;
        switch (col) {
            case "Red":
                color = new Color(255, 0, 0);
                break;
            case "Blue":
                color = new Color(0, 0, 255);
                break;
            case "Green":
                color = new Color(0, 255, 0);
                break;
            case "Orange":
                color = new Color(255, 165, 0);
                break;
            case "Cyan":
                color = new Color(0, 255, 255);
                break;
            case "Pink":
                color = new Color(255, 105, 180);
                break;
            case "Black":
                color = new Color(0, 0, 0);
                break;
            case "Magenta":
                color = new Color(255, 0, 255);
                break;
            case "Yellow":
                color = new Color(255, 255, 0);
                break;
            default:
                color = new Color(255, 240, 240);
                break;
        }
        return color;
    }

}
