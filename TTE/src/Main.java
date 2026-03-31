import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Table-Top-Engine | Main Menu | Alpha 0.1 | GUI Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLayout(new FlowLayout());

        JButton createSheetButton = new JButton("Create New Sheet"); //button works
        JButton loadSheetButton = new JButton("Load Sheet"); //button does not work
        JButton showSheetButton = new JButton("Show Sheet"); //button works
        JButton wumboButton = new JButton("Fun!"); //button does not work
        JButton exitButton = new JButton("Exit"); //button does not work
        JButton creditsButton = new JButton("Credits");

        //listeners
        createSheetButton.addActionListener(e -> openSheetCreator());
        wumboButton.addActionListener(e -> {JOptionPane.showMessageDialog(frame, "You've been surprised by the Fun! dialog box!!!");});
        exitButton.addActionListener(e -> {System.exit(0);});
        creditsButton.addActionListener(e -> {openCreditWindow();});

        //add buttons
        frame.add(createSheetButton);
        frame.add(loadSheetButton);
        frame.add(showSheetButton);
        frame.add(wumboButton);
        frame.add(exitButton);
        frame.add(creditsButton);
        //items added below this line will not be rendered.
        frame.setVisible(true);

        //create empty sheet object
        Sheet loadSheet = new Sheet();

        //show sheet
        showSheetButton.addActionListener(e -> { //determine if character sheet hasn't been setup. Checks if the name variable is empty.
           if(loadSheet.getCharacterName() == ""){
               JOptionPane.showMessageDialog(frame, "No valid sheet has been loaded. \n" +
                       "Please load a sheet and try again.");
           }
           else{

           }

        });

        //TODO: Implement this.
        //load character
        loadSheetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hello there!");
            }
        });
    }


    // generic error window  #### DEPRECATED CAUSE I'M A MORON. use JOptionPane.showMessageDialog(parentFrame, "Error Text");
    /*
    public static void openErrorWindow(String errorText){
        JFrame errorFrame = new JFrame("Error | "+ errorText);
        errorFrame.setSize(640, 480);
        errorFrame.setLayout(new FlowLayout());
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new GridLayout(1, 0));
        JLabel errorLabel = new JLabel(errorText);
        JButton acknowledgeButton = new JButton("Acknowledged");
        errorPanel.add(acknowledgeButton);
        errorFrame.add(errorLabel);
        errorFrame.add(acknowledgeButton);
        errorFrame.setVisible(true);

        acknowledgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorFrame.dispose();
            }
        });

    }

     */
    public static void openCreditWindow(){ //TODO: Fix the formatting on this
        JFrame creditsWindow = new JFrame("Credits");
        creditsWindow.setSize(640,480);
        creditsWindow.setLayout(new FlowLayout());
        JTextArea creditsLabel = new JTextArea("Table Top Engine \n" +
                "Brought to you by: The C-TEAM \n" +
                "Program manager - Maeve \n" +
                "Lead architect - Skyler \n" +
                "Documentation and provider of cat pictures - Tara \n\n" +
                "Built on top of Java using OpenJDK and IntelliJ Community Edition. Code available under GPL V2.0 license \n" +
                "Copyright 2026 The C-TEAM \n\n\n\n Thank you for using our program!");
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creditsWindow.dispose();
            }});
        creditsWindow.add(creditsLabel);
        creditsWindow.add(okButton);
        creditsWindow.setVisible(true);
    }

    // Opens the sheet creation window
    public static void openSheetCreator() { //TODO: Implement checks to make sure all data fields are filled out before saving.
        JFrame sheetFrame = new JFrame("Create Character Sheet");
        sheetFrame.setSize(640, 1000);
        sheetFrame.setLayout(new GridLayout(0, 2));

        Sheet sheet = new Sheet();

        //preamble
        JTextField nameField = new JTextField();
        JTextField classField = new JTextField();
        JTextField raceField = new JTextField();
        JTextField playerField = new JTextField();
        JTextField expField = new JTextField();

        sheetFrame.add(new JLabel("Character Name:"));
        sheetFrame.add(nameField);

        sheetFrame.add(new JLabel("Class:"));
        sheetFrame.add(classField);

        sheetFrame.add(new JLabel("Race:"));
        sheetFrame.add(raceField);

        sheetFrame.add(new JLabel("Player Name:"));
        sheetFrame.add(playerField);

        sheetFrame.add(new JLabel("EXP:"));
        sheetFrame.add(expField);

        //core scores
        JTextField strField = new JTextField();
        JLabel strMod = new JLabel("Mod: 0");

        JTextField dexField = new JTextField();
        JLabel dexMod = new JLabel("Mod: 0");

        JTextField conField = new JTextField();
        JLabel conMod = new JLabel("Mod: 0");

        JTextField intField = new JTextField();
        JLabel intMod = new JLabel("Mod: 0");

        JTextField wisField = new JTextField();
        JLabel wisMod = new JLabel("Mod: 0");

        JTextField chaField = new JTextField();
        JLabel chaMod = new JLabel("Mod: 0");

        // Helper to enforce integer-only input + update modifier
        java.util.function.BiConsumer<JTextField, Runnable> attachListener = (field, updateAction) -> {
            field.addActionListener(e -> {
                try {
                    Integer.parseInt(field.getText()); // validate
                    updateAction.run();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(sheetFrame, "Please enter a valid integer!");
                    field.setText("");
                }
            });
        };

        //gen fields onto the UI
        sheetFrame.add(new JLabel("Strength:"));
        sheetFrame.add(strField);
        sheetFrame.add(strMod);

        sheetFrame.add(new JLabel("Dexterity:"));
        sheetFrame.add(dexField);
        sheetFrame.add(dexMod);

        sheetFrame.add(new JLabel("Constitution:"));
        sheetFrame.add(conField);
        sheetFrame.add(conMod);

        sheetFrame.add(new JLabel("Intelligence:"));
        sheetFrame.add(intField);
        sheetFrame.add(intMod);

        sheetFrame.add(new JLabel("Wisdom:"));
        sheetFrame.add(wisField);
        sheetFrame.add(wisMod);

        sheetFrame.add(new JLabel("Charisma:"));
        sheetFrame.add(chaField);
        sheetFrame.add(chaMod);

        attachListener.accept(strField, () -> {
            sheet.setStrength(Integer.parseInt(strField.getText()));
            strMod.setText("Mod: " + sheet.getStrenghMod());
        });

        attachListener.accept(dexField, () -> {
            sheet.setDexterity(Integer.parseInt(dexField.getText()));
            dexMod.setText("Mod: " + sheet.getDexterityMod());
        });

        attachListener.accept(conField, () -> {
            sheet.setConstitution(Integer.parseInt(conField.getText()));
            conMod.setText("Mod: " + sheet.getConstitutionMod());
        });

        attachListener.accept(intField, () -> {
            sheet.setIntelligence(Integer.parseInt(intField.getText()));
            intMod.setText("Mod: " + sheet.getIntelligenceMod());
        });

        attachListener.accept(wisField, () -> {
            sheet.setWisdom(Integer.parseInt(wisField.getText()));
            wisMod.setText("Mod: " + sheet.getWisdomMod());
        });

        attachListener.accept(chaField, () -> {
            sheet.setCharisma(Integer.parseInt(chaField.getText()));
            chaMod.setText("Mod: " + sheet.getCharismaMod());
        });

        //save dat shit
        JButton saveButton = new JButton("Save Sheet");

        saveButton.addActionListener(e -> {
            try {
                sheet.setCharacterName(nameField.getText());
                sheet.setCharacterClass(classField.getText());
                sheet.setCharacterRace(raceField.getText());
                sheet.setPlayerName(playerField.getText());
                sheet.setCharacterEXP(Integer.parseInt(expField.getText()));

                PlayerSheetIO.savePlayerSheetToFile(sheet, "playerSheet.dat");

                JOptionPane.showMessageDialog(sheetFrame, "Sheet Saved!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(sheetFrame, "Error: " + ex.getMessage());
            }
        });

        sheetFrame.add(saveButton);

        sheetFrame.setVisible(true);
    }
}