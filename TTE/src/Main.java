import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Table-Top-Engine | Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLayout(new FlowLayout());

        JButton createSheetButton = new JButton("Create New Sheet");

        // Button action → opens new window
        createSheetButton.addActionListener(e -> openSheetCreator());

        frame.add(createSheetButton);
        frame.setVisible(true);
    }

    // Opens the sheet creation window
    public static void openSheetCreator() {
        JFrame sheetFrame = new JFrame("Create Character Sheet");
        sheetFrame.setSize(500, 600);
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