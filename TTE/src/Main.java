import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main {
    //color pallete
    private static final Color BG          = new Color(0x1A1A2E);
    private static final Color PANEL_BG    = new Color(0x16213E);
    private static final Color SECTION_BG  = new Color(0x0F3460);
    private static final Color ACCENT      = new Color(0xE94560);
    private static final Color TEXT        = new Color(0xEAEAEA);
    private static final Color TEXT_DIM    = new Color(0x9A9AB0);
    private static final Color FIELD_BG    = new Color(0x0A0A1A);
    private static final Color BORDER_COL  = new Color(0x2A2A4A);

    // Keeps track of how many tabs have been created
    private static int tabNumber = 0;

    //main entry point to the program
    public static void main(String[] args) {
        // Tabbed UI rework
        // Initialize JFrame
        JFrame newFrame = new JFrame("Table Top Engine | Sheet Builder");
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(800,1000);

        // Initialize TabbedPane
        JTabbedPane tabPane = new JTabbedPane();
        tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        // Initialize MenuBar
        JMenuBar menuBar = new JMenuBar();
        JMenuItem newSheet = new JMenuItem("New Sheet");
        JMenuItem loadSheet = new JMenuItem("Load Sheet");
        //JMenuItem saveSheet = new JMenuItem("Save Sheet"); ### OLD SAVE BUTTON ###
        JMenuItem credits = new JMenuItem("Credits");

        // Add a new sheet to the tabs
        newSheet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creates a new tab in tabPane
                tabPane.addTab("New Sheet #" + (++tabNumber), new JLabel("Content of New Sheet #" + (tabNumber)));
                tabPane.setSelectedIndex(tabPane.getTabCount() - 1);

                // fun()
                openSheetCreator(tabPane.getSelectedIndex(), tabPane, newFrame);

                // Adds a close button to tab(s)
                tabPane.setTabComponentAt(0, new CloseButton(tabPane, 0)); // Needed for adding to first tab
                tabPane.addChangeListener(e1 -> {
                    for(int i = 0; i < tabPane.getTabCount(); i++) {
                        tabPane.setTabComponentAt(i, new CloseButton(tabPane, i));
                    }
                }); // Adds to all tabs
            }
        });

        // Loads a sheet from file and displays it in a new tab
        loadSheet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the file browser and load the sheet
                Sheet loaded = openLoadWindow(newFrame);

                // Only open a tab if the load actually succeeded
                if (loaded == null || loaded.getCharacterName() == null || loaded.getCharacterName().isEmpty()) {
                    return;
                }

                String tabTitle = "Loaded: " + loaded.getCharacterName() + " #" + (++tabNumber);
                tabPane.addTab(tabTitle, new JLabel("Loading..."));
                tabPane.setSelectedIndex(tabPane.getTabCount() - 1);

                // Populate the tab with the read-only sheet viewer
                openLoadedSheetViewer(tabPane.getSelectedIndex(), tabPane, loaded);

                // Adds a close button to tab(s)
                tabPane.setTabComponentAt(tabPane.getTabCount() - 1, new CloseButton(tabPane, tabPane.getTabCount() - 1));
                tabPane.addChangeListener(e1 -> {
                    for(int i = 0; i < tabPane.getTabCount(); i++) {
                        tabPane.setTabComponentAt(i, new CloseButton(tabPane, i));
                    }
                });
            }
        });
        // Add items to menu
        menuBar.add(newSheet);
        menuBar.add(loadSheet);

        // Opens the credits window
        credits.addActionListener(e -> openCreditWindow());
        menuBar.add(credits);

        // Scroll hopefully...
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        newFrame.add(scroll);

        // Add to frame
        newFrame.setJMenuBar(menuBar);
        newFrame.add(tabPane);
        newFrame.setVisible(true);
    }
    // Close button class for tabs
    static class CloseButton extends JPanel {
        public CloseButton(final JTabbedPane tabPane, int index) {
            // Initialize JLabel
            setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
            setOpaque(false);
            JLabel label = new JLabel(tabPane.getTitleAt(index));
            add(label);

            // Add the close button to the tab
            JButton button = new JButton("X");
            button.setPreferredSize(new Dimension(16, 16));
            button.addActionListener(e -> {
                tabPane.removeTabAt(index);
            });
            add(button);
        }
    }

    //load window — opens file browser, loads sheet, shows result dialog
    public static Sheet openLoadWindow(JFrame parent) {
        Sheet returnSheet = PlayerSheetIO.loadSheetFromDirectory();

        if (returnSheet != null && returnSheet.getCharacterName() != null && !returnSheet.getCharacterName().isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Loaded sheet: " + returnSheet.getCharacterName());
        } else {
            JOptionPane.showMessageDialog(parent, "Failed to load sheet.");
        }
        return returnSheet;
    }

    //this doesn't open a window anymore, but instead it fires off the data into a .txt file.
    //Accessible from the loadedSheet UI tab.
    public static void openShowSheetWindow(Sheet sheet) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== CHARACTER INFO ===\n");
        sb.append(String.format("%-20s %s%n", "Name:", sheet.getCharacterName()));
        sb.append(String.format("%-20s %s%n", "Class:", sheet.getCharacterClass()));
        sb.append(String.format("%-20s %s%n", "Race:", sheet.getCharacterRace()));
        sb.append(String.format("%-20s %s%n", "Background:", sheet.getCharacterBackground()));
        sb.append(String.format("%-20s %s%n", "Alignment:", sheet.getCharacterAlignment()));
        sb.append(String.format("%-20s %s%n", "Player:", sheet.getPlayerName()));
        sb.append(String.format("%-20s %d%n", "EXP:", sheet.getCharacterEXP()));
        sb.append(String.format("%-20s %d%n", "Level:", sheet.getCharacterLevel()));

        sb.append("\n=== ABILITY SCORES ===\n");
        sb.append(String.format("%-20s %2d  (Mod: %+d)%n", "Strength:", sheet.getStrength(), sheet.getStrenghMod()));
        sb.append(String.format("%-20s %2d  (Mod: %+d)%n", "Dexterity:", sheet.getDexterity(), sheet.getDexterityMod()));
        sb.append(String.format("%-20s %2d  (Mod: %+d)%n", "Constitution:", sheet.getConstitution(), sheet.getConstitutionMod()));
        sb.append(String.format("%-20s %2d  (Mod: %+d)%n", "Intelligence:", sheet.getIntelligence(), sheet.getIntelligenceMod()));
        sb.append(String.format("%-20s %2d  (Mod: %+d)%n", "Wisdom:", sheet.getWisdom(), sheet.getWisdomMod()));
        sb.append(String.format("%-20s %2d  (Mod: %+d)%n", "Charisma:", sheet.getCharisma(), sheet.getCharismaMod()));

        sb.append("\n=== SAVING THROWS ===\n");
        sb.append(String.format("%-20s %+d%n", "Strength:", sheet.getStrengthSave()));
        sb.append(String.format("%-20s %+d%n", "Dexterity:", sheet.getDexteritySave()));
        sb.append(String.format("%-20s %+d%n", "Constitution:", sheet.getConstitutionSave()));
        sb.append(String.format("%-20s %+d%n", "Intelligence:", sheet.getIntelligenceSave()));
        sb.append(String.format("%-20s %+d%n", "Wisdom:", sheet.getWisdomSave()));
        sb.append(String.format("%-20s %+d%n", "Charisma:", sheet.getCharismaSave()));

        sb.append("\n=== COMBAT ===\n");
        sb.append(String.format("%-20s %d%n", "Armor Class:", sheet.getArmorClass()));
        sb.append(String.format("%-20s %+d%n", "Initiative:", sheet.getInitiative()));
        sb.append(String.format("%-20s %d%n", "Speed:", sheet.getSpeed()));
        sb.append(String.format("%-20s %d / %d%n",  "HP (Cur/Max):", sheet.getCurrentHP(), sheet.getHpMax()));
        sb.append(String.format("%-20s %d%n", "Temp HP:", sheet.getTempHP()));
        sb.append(String.format("%-20s %s (%d)%n",  "Hit Dice:", sheet.getHitDiceType(), sheet.getHitDiceTotal()));
        sb.append(String.format("%-20s %d success / %d fail%n", "Death Saves:", sheet.getLifeSaves(), sheet.getDeathSaves()));

        sb.append("\n=== MISC ===\n");
        sb.append(String.format("%-20s %d%n", "Inspiration:", sheet.getInspiration()));
        sb.append(String.format("%-20s %d%n", "Prof. Bonus:",  sheet.getProfBonus()));

        sb.append("\n=== SKILL PROFICIENCIES ===\n");
        String[] skillNames = {
                "Acrobatics","Animal Handling","Arcana","Athletics","Deception",
                "History","Insight","Intimidation","Investigation","Medicine",
                "Nature","Perception","Performance","Persuasion","Religion",
                "Sleight of Hand","Stealth","Survival"
        };
        boolean[] profs = {
                sheet.isAcrobatics(),sheet.isAnimalHandling(),sheet.isArcana(),
                sheet.isAthletics(),sheet.isDeception(),sheet.isHistory(),
                sheet.isInsight(),sheet.isIntimidation(),sheet.isInvestigation(),
                sheet.isMedicine(),sheet.isNature(),sheet.isPerception(),
                sheet.isPerformance(),sheet.isPersuasion(),sheet.isReligion(),
                sheet.isSleightOfHand(),sheet.isStealth(),sheet.isSurvival()
        };
        boolean any = false;
        for (int i = 0; i < skillNames.length; i++)
            if (profs[i]) { sb.append("  [X] ").append(skillNames[i]).append("\n"); any = true; }
        if (!any) sb.append("  None\n");
        sb.append("<!>END OF CHARACTER AUDIT NO FURTHER INFORMATION OR SECRETS WERE FOUND<!>");

        // Write the report to a text file
        String safeName = sheet.getCharacterName().replaceAll("[^a-zA-Z0-9_\\-]", "_"); //replace weird spaces with underscore cause filesystem BS. (EXT4 causing problems???)
        String filename = safeName + "_sheet.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.print(sb.toString());
            JOptionPane.showMessageDialog(null,
                    "Sheet report saved to: " + new File(filename).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error writing sheet report: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Failed to save sheet report: " + e.getMessage());
        }
    }

    //Credits
    public static void openCreditWindow() {
        JFrame w = new JFrame("Credits");
        w.setSize(700, 480);
        w.setLayout(new FlowLayout());
        JTextArea t = new JTextArea(
                "Table Top Engine\nBrought to you by: The C-TEAM\n" +
                        "Program manager - Maeve\nLead architect - Skyler\n" +
                        "Documentation and provider of cat pictures - Tara\n\n" +
                        "Built on OpenJDK 21 & IntelliJ Community Edition. GPL V2.0\n" +
                        "Copyright 2026 The C-TEAM\n\nThank you for using our program!");
        JButton ok = new JButton("OK");
        ok.addActionListener(e -> w.dispose());
        w.add(t); w.add(ok);
        w.setVisible(true);
    }

    /*
    hooks PlayerSheetIO load routine to load data into a viewable sheet that's not a really fancy long string.
     */
    public static void openLoadedSheetViewer(int index, JTabbedPane pane, Sheet sheet) {
        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(BG);
        root.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        //Tab title banner
        JPanel titleBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        titleBar.setBackground(BG);
        titleBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        JLabel titleLabel = new JLabel(sheet.getCharacterName());
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 22));
        titleLabel.setForeground(ACCENT);
        JLabel subtitleLabel = new JLabel("  ·  Loaded Character Sheet  ·  Read Only");
        subtitleLabel.setFont(new Font("Georgia", Font.ITALIC, 14));
        subtitleLabel.setForeground(TEXT_DIM);

        // Edit Sheet toggle button
        JButton editBtn = new JButton("Edit Sheet");
        editBtn.setFont(new Font("Georgia", Font.BOLD, 12));
        editBtn.setBackground(SECTION_BG);
        editBtn.setForeground(TEXT);
        editBtn.setFocusPainted(false);
        editBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //New save button. Different location compared to sheet creator. Overwrites the original .dat file.
        JButton saveEditBtn = new JButton("Save Sheet");
        saveEditBtn.setFont(new Font("Georgia", Font.BOLD, 12));
        saveEditBtn.setBackground(ACCENT);
        saveEditBtn.setForeground(Color.WHITE);
        saveEditBtn.setFocusPainted(false);
        saveEditBtn.setBorderPainted(false);
        saveEditBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveEditBtn.setVisible(false); //Save button won't become visible until edit mode is triggered.

        titleBar.add(titleLabel);
        titleBar.add(subtitleLabel);
        titleBar.add(editBtn);
        titleBar.add(saveEditBtn);
        root.add(titleBar);
        root.add(vgap(14));

        //Info
        JTextField nameField = readOnlyField(sheet.getCharacterName());
        JTextField classField = readOnlyField(sheet.getCharacterClass());
        JTextField raceField = readOnlyField(sheet.getCharacterRace());
        JTextField bgField = readOnlyField(sheet.getCharacterBackground());
        JTextField alignmentField = readOnlyField(sheet.getCharacterAlignment());
        JTextField playerField = readOnlyField(sheet.getPlayerName());
        JTextField expField = readOnlyField(String.valueOf(sheet.getCharacterEXP()));
        JTextField levelField = readOnlyField(String.valueOf(sheet.getCharacterLevel()));

        JPanel infoSection = makeSection("CHARACTER INFO");
        infoSection.add(twoColRow("Character Name", nameField, "Class", classField));
        infoSection.add(vgap(8));
        infoSection.add(twoColRow("Race", raceField, "Background", bgField));
        infoSection.add(vgap(8));
        infoSection.add(twoColRow("Alignment", alignmentField, "Player Name", playerField));
        infoSection.add(vgap(8));
        infoSection.add(twoColRow("Experience Points", expField, "Character Level", levelField));
        root.add(infoSection);
        root.add(vgap(14));

        // Ability Scores
        JTextField strField = readOnlyField(String.valueOf(sheet.getStrength()));
        JTextField dexField = readOnlyField(String.valueOf(sheet.getDexterity()));
        JTextField conField = readOnlyField(String.valueOf(sheet.getConstitution()));
        JTextField intField = readOnlyField(String.valueOf(sheet.getIntelligence()));
        JTextField wisField = readOnlyField(String.valueOf(sheet.getWisdom()));
        JTextField chaField = readOnlyField(String.valueOf(sheet.getCharisma()));

        JLabel strMod = modLabel(sheet.getStrenghMod());
        JLabel dexMod = modLabel(sheet.getDexterityMod());
        JLabel conMod = modLabel(sheet.getConstitutionMod());
        JLabel intMod = modLabel(sheet.getIntelligenceMod());
        JLabel wisMod = modLabel(sheet.getWisdomMod());
        JLabel chaMod = modLabel(sheet.getCharismaMod());

        JPanel abilitySection = makeSection("ABILITY SCORES");
        abilitySection.add(abilityRow(
                new String[]{"STR","DEX","CON","INT","WIS","CHA"},
                new JTextField[]{strField,dexField,conField,intField,wisField,chaField},
                new JLabel[]{strMod,dexMod,conMod,intMod,wisMod,chaMod}
        ));
        root.add(abilitySection);
        root.add(vgap(14));

        //Combat & Misc
        JTextField acField = readOnlyField(String.valueOf(sheet.getArmorClass()));
        JTextField initField = readOnlyField(String.valueOf(sheet.getInitiative()));
        JTextField speedField = readOnlyField(String.valueOf(sheet.getSpeed()));
        JTextField hpMaxField = readOnlyField(String.valueOf(sheet.getHpMax()));
        JTextField curHpField = readOnlyField(String.valueOf(sheet.getCurrentHP()));
        JTextField tempHpField = readOnlyField(String.valueOf(sheet.getTempHP()));
        JTextField hdTypeField = readOnlyField(sheet.getHitDiceType());
        JTextField hdTotalField = readOnlyField(String.valueOf(sheet.getHitDiceTotal()));
        JTextField profBonField = readOnlyField(String.valueOf(sheet.getProfBonus()));
        JTextField inspirField = readOnlyField(String.valueOf(sheet.getInspiration()));

        JPanel combatSection = makeSection("COMBAT & MISC");
        combatSection.add(twoColRow("Armor Class", acField, "Initiative", initField));
        combatSection.add(vgap(8));
        combatSection.add(twoColRow("Speed", speedField, "Proficiency Bonus", profBonField));
        combatSection.add(vgap(8));
        combatSection.add(twoColRow("HP Max", hpMaxField, "Current HP", curHpField));
        combatSection.add(vgap(8));
        combatSection.add(twoColRow("Temp HP", tempHpField, "Inspiration", inspirField));
        combatSection.add(vgap(8));
        combatSection.add(twoColRow("Hit Dice Type", hdTypeField, "Hit Dice Total", hdTotalField));
        root.add(combatSection);
        root.add(vgap(14));

        //Saving Throws
        JTextField strSave = readOnlyField(String.valueOf(sheet.getStrengthSave()));
        JTextField dexSave = readOnlyField(String.valueOf(sheet.getDexteritySave()));
        JTextField conSave = readOnlyField(String.valueOf(sheet.getConstitutionSave()));
        JTextField intSave = readOnlyField(String.valueOf(sheet.getIntelligenceSave()));
        JTextField wisSave = readOnlyField(String.valueOf(sheet.getWisdomSave()));
        JTextField chaSave = readOnlyField(String.valueOf(sheet.getCharismaSave()));

        JPanel saveSection = makeSection("SAVING THROWS");
        saveSection.add(twoColRow("Strength", strSave, "Dexterity", dexSave));
        saveSection.add(vgap(8));
        saveSection.add(twoColRow("Constitution", conSave, "Intelligence", intSave));
        saveSection.add(vgap(8));
        saveSection.add(twoColRow("Wisdom", wisSave, "Charisma", chaSave));
        root.add(saveSection);
        root.add(vgap(14));

        //Skill Proficiencies | I hate check boxes, but I think these aren't mutable
        String[] skillNames = {
                "Acrobatics","Animal Handling","Arcana","Athletics","Deception",
                "History","Insight","Intimidation","Investigation","Medicine",
                "Nature","Perception","Performance","Persuasion","Religion",
                "Sleight of Hand","Stealth","Survival"
        };
        boolean[] profs = {
                sheet.isAcrobatics(),sheet.isAnimalHandling(),sheet.isArcana(),
                sheet.isAthletics(),sheet.isDeception(),sheet.isHistory(),
                sheet.isInsight(),sheet.isIntimidation(),sheet.isInvestigation(),
                sheet.isMedicine(),sheet.isNature(),sheet.isPerception(),
                sheet.isPerformance(),sheet.isPersuasion(),sheet.isReligion(),
                sheet.isSleightOfHand(),sheet.isStealth(),sheet.isSurvival()
        };
        JCheckBox[] skillBoxes = new JCheckBox[skillNames.length];
        for (int i = 0; i < skillNames.length; i++) {
            skillBoxes[i] = styledCheckBox(skillNames[i]);
            skillBoxes[i].setSelected(profs[i]);
            skillBoxes[i].setEnabled(false); // read-only
        }

        JPanel skillSection = makeSection("SKILL PROFICIENCIES");
        skillSection.add(skillGrid(skillBoxes));
        root.add(skillSection);
        root.add(vgap(20));

        //### EDITING ROUTINE STARTS HERE ###
        JTextField[] allFields = {
                nameField, classField, raceField, bgField, alignmentField, playerField,
                expField, levelField,
                strField, dexField, conField, intField, wisField, chaField,
                acField, initField, speedField, hpMaxField, curHpField, tempHpField,
                hdTypeField, hdTotalField, profBonField, inspirField,
                strSave, dexSave, conSave, intSave, wisSave, chaSave
        };

        //Edit Sheet Set all data fields from read only to editable
        editBtn.addActionListener(e -> {
            boolean[] editing = {editBtn.getText().equals("Edit Sheet")};
            if (editing[0]) {
                //Enter edit mode
                for (JTextField f : allFields) {
                    f.setEditable(true);
                    f.setForeground(TEXT);
                }
                for (JCheckBox cb : skillBoxes) cb.setEnabled(true);

                //reset listeners to modifier fields
                attachScoreListener(strField, strMod, v -> { sheet.setStrength(v); return sheet.getStrenghMod(); });
                attachScoreListener(dexField, dexMod, v -> { sheet.setDexterity(v); return sheet.getDexterityMod(); });
                attachScoreListener(conField, conMod, v -> { sheet.setConstitution(v); return sheet.getConstitutionMod(); });
                attachScoreListener(intField, intMod, v -> { sheet.setIntelligence(v); return sheet.getIntelligenceMod(); });
                attachScoreListener(wisField, wisMod, v -> { sheet.setWisdom(v); return sheet.getWisdomMod(); });
                attachScoreListener(chaField, chaMod, v -> { sheet.setCharisma(v); return sheet.getCharismaMod(); });

                subtitleLabel.setText("  ·  Loaded Character Sheet  ·  Editing");
                subtitleLabel.setForeground(ACCENT);
                editBtn.setText("Discard Changes");
                saveEditBtn.setVisible(true);
            } else {
                //Discard editing changes. Reference original sheet object and re-populate data fields
                nameField.setText(sheet.getCharacterName());
                classField.setText(sheet.getCharacterClass());
                raceField.setText(sheet.getCharacterRace());
                bgField.setText(sheet.getCharacterBackground());
                alignmentField.setText(sheet.getCharacterAlignment());
                playerField.setText(sheet.getPlayerName());
                expField.setText(String.valueOf(sheet.getCharacterEXP()));
                levelField.setText(String.valueOf(sheet.getCharacterLevel()));
                strField.setText(String.valueOf(sheet.getStrength()));
                dexField.setText(String.valueOf(sheet.getDexterity()));
                conField.setText(String.valueOf(sheet.getConstitution()));
                intField.setText(String.valueOf(sheet.getIntelligence()));
                wisField.setText(String.valueOf(sheet.getWisdom()));
                chaField.setText(String.valueOf(sheet.getCharisma()));
                acField.setText(String.valueOf(sheet.getArmorClass()));
                initField.setText(String.valueOf(sheet.getInitiative()));
                speedField.setText(String.valueOf(sheet.getSpeed()));
                hpMaxField.setText(String.valueOf(sheet.getHpMax()));
                curHpField.setText(String.valueOf(sheet.getCurrentHP()));
                tempHpField.setText(String.valueOf(sheet.getTempHP()));
                hdTypeField.setText(sheet.getHitDiceType());
                hdTotalField.setText(String.valueOf(sheet.getHitDiceTotal()));
                profBonField.setText(String.valueOf(sheet.getProfBonus()));
                inspirField.setText(String.valueOf(sheet.getInspiration()));
                strSave.setText(String.valueOf(sheet.getStrengthSave()));
                dexSave.setText(String.valueOf(sheet.getDexteritySave()));
                conSave.setText(String.valueOf(sheet.getConstitutionSave()));
                intSave.setText(String.valueOf(sheet.getIntelligenceSave()));
                wisSave.setText(String.valueOf(sheet.getWisdomSave()));
                chaSave.setText(String.valueOf(sheet.getCharismaSave()));
                boolean[] savedProfs = {
                        sheet.isAcrobatics(), sheet.isAnimalHandling(), sheet.isArcana(),
                        sheet.isAthletics(), sheet.isDeception(), sheet.isHistory(),
                        sheet.isInsight(), sheet.isIntimidation(), sheet.isInvestigation(),
                        sheet.isMedicine(), sheet.isNature(), sheet.isPerception(),
                        sheet.isPerformance(), sheet.isPersuasion(), sheet.isReligion(),
                        sheet.isSleightOfHand(), sheet.isStealth(), sheet.isSurvival()
                };
                for (int i = 0; i < skillBoxes.length; i++) {
                    skillBoxes[i].setSelected(savedProfs[i]);
                    skillBoxes[i].setEnabled(false);
                }
                for (JTextField f : allFields) {
                    f.setEditable(false);
                    f.setForeground(TEXT_DIM);
                }
                subtitleLabel.setText("  ·  Loaded Character Sheet  ·  Read Only");
                subtitleLabel.setForeground(TEXT_DIM);
                editBtn.setText("Edit Sheet");
                saveEditBtn.setVisible(false);
            }
        });

        // Save Sheet — writes all edited field values back to the sheet object and overwrites the .dat file
        saveEditBtn.addActionListener(e -> {
            try {
                sheet.setCharacterName(nameField.getText().trim());
                sheet.setCharacterClass(classField.getText().trim());
                sheet.setCharacterRace(raceField.getText().trim());
                sheet.setCharacterBackground(bgField.getText().trim());
                sheet.setCharacterAlignment(alignmentField.getText().trim());
                sheet.setPlayerName(playerField.getText().trim());
                sheet.setCharacterEXP(parseIntOrZero(expField.getText()));
                sheet.setCharacterLevel(parseIntOrZero(levelField.getText()));
                sheet.setStrength(parseIntOrZero(strField.getText()));
                sheet.setDexterity(parseIntOrZero(dexField.getText()));
                sheet.setConstitution(parseIntOrZero(conField.getText()));
                sheet.setIntelligence(parseIntOrZero(intField.getText()));
                sheet.setWisdom(parseIntOrZero(wisField.getText()));
                sheet.setCharisma(parseIntOrZero(chaField.getText()));
                sheet.setArmorClass(parseIntOrZero(acField.getText()));
                sheet.setInitiative(parseIntOrZero(initField.getText()));
                sheet.setSpeed(parseIntOrZero(speedField.getText()));
                sheet.setHpMax(parseIntOrZero(hpMaxField.getText()));
                sheet.setCurrentHP(parseIntOrZero(curHpField.getText()));
                sheet.setTempHP(parseIntOrZero(tempHpField.getText()));
                sheet.setHitDiceType(hdTypeField.getText().trim());
                sheet.setHitDiceTotal(parseIntOrZero(hdTotalField.getText()));
                sheet.setProfBonus(parseIntOrZero(profBonField.getText()));
                sheet.setInspiration(parseIntOrZero(inspirField.getText()));
                sheet.setStrengthSave(parseIntOrZero(strSave.getText()));
                sheet.setDexteritySave(parseIntOrZero(dexSave.getText()));
                sheet.setConstitutionSave(parseIntOrZero(conSave.getText()));
                sheet.setIntelligenceSave(parseIntOrZero(intSave.getText()));
                sheet.setWisdomSave(parseIntOrZero(wisSave.getText()));
                sheet.setCharismaSave(parseIntOrZero(chaSave.getText()));
                sheet.setAcrobatics(skillBoxes[0].isSelected());
                sheet.setAnimalHandling(skillBoxes[1].isSelected());
                sheet.setArcana(skillBoxes[2].isSelected());
                sheet.setAthletics(skillBoxes[3].isSelected());
                sheet.setDeception(skillBoxes[4].isSelected());
                sheet.setHistory(skillBoxes[5].isSelected());
                sheet.setInsight(skillBoxes[6].isSelected());
                sheet.setIntimidation(skillBoxes[7].isSelected());
                sheet.setInvestigation(skillBoxes[8].isSelected());
                sheet.setMedicine(skillBoxes[9].isSelected());
                sheet.setNature(skillBoxes[10].isSelected());
                sheet.setPerception(skillBoxes[11].isSelected());
                sheet.setPerformance(skillBoxes[12].isSelected());
                sheet.setPersuasion(skillBoxes[13].isSelected());
                sheet.setReligion(skillBoxes[14].isSelected());
                sheet.setSleightOfHand(skillBoxes[15].isSelected());
                sheet.setStealth(skillBoxes[16].isSelected());
                sheet.setSurvival(skillBoxes[17].isSelected());

                if (sheet.getCharacterName().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Character name cannot be empty.");
                    return;
                }

                // Overwrite the original file
                String saveName = sheet.getCharacterName() + ".dat";
                PlayerSheetIO.savePlayerSheetToFile(sheet, saveName);

                // Lock everything back to read-only after a successful save
                for (JTextField f : allFields) {
                    f.setEditable(false);
                    f.setForeground(TEXT_DIM);
                }
                for (JCheckBox cb : skillBoxes) cb.setEnabled(false);
                titleLabel.setText(sheet.getCharacterName());
                subtitleLabel.setText("  ·  Loaded Character Sheet  ·  Read Only");
                subtitleLabel.setForeground(TEXT_DIM);
                editBtn.setText("Edit Sheet");
                saveEditBtn.setVisible(false);

                JOptionPane.showMessageDialog(null, "Sheet saved as \"" + saveName + "\"");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error saving sheet: " + ex.getMessage());
            }
        });
//### EDITING ROUTINE ENDS HERE ###

        //Export button | calls openShowSheetWindow to write a .txt audit file
        JButton exportBtn = new JButton("EXPORT SHEET REPORT (.txt)");
        exportBtn.setFont(new Font("Georgia", Font.BOLD, 14));
        exportBtn.setBackground(ACCENT);
        exportBtn.setForeground(Color.WHITE);
        exportBtn.setFocusPainted(false);
        exportBtn.setBorderPainted(false);
        exportBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exportBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        exportBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exportBtn.addActionListener(e -> openShowSheetWindow(sheet));

        root.add(exportBtn);
        root.add(vgap(10));

        // Wrap in scroll pane and place into the tab
        JScrollPane sheetScroll = new JScrollPane(root);
        sheetScroll.setBorder(null);
        sheetScroll.getViewport().setBackground(BG);
        sheetScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sheetScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sheetScroll.getVerticalScrollBar().setUnitIncrement(16);
        pane.setComponentAt(index, sheetScroll);
    }

    //sheet creation
    public static void openSheetCreator(int index, JTabbedPane pane, JFrame frame) {

        Sheet sheet = new Sheet();
        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(BG);
        root.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        //Title Banner
        JPanel titleBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleBar.setBackground(BG);
        titleBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        JLabel titleLabel = new JLabel("New Character Sheet"); //change font here
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        titleLabel.setForeground(ACCENT);
        JLabel subtitleLabel = new JLabel("  ·  Fill in your character details below");
        subtitleLabel.setFont(new Font("Georgia", Font.ITALIC, 14));
        subtitleLabel.setForeground(TEXT_DIM);
        titleBar.add(titleLabel);
        titleBar.add(subtitleLabel);
        root.add(titleBar);
        root.add(vgap(14));

        //Character info
        JTextField nameField = new JTextField();
        JTextField classField = new JTextField();
        JTextField raceField = new JTextField();
        JTextField bgField = new JTextField();
        JTextField alignmentField = new JTextField();
        JTextField playerField = new JTextField();
        JTextField expField = new JTextField("0");
        JTextField levelField = new JTextField("1");

        JPanel infoSection = makeSection("CHARACTER INFO");
        infoSection.add(twoColRow("Character Name", nameField, "Class", classField));
        infoSection.add(vgap(8));
        infoSection.add(twoColRow("Race", raceField, "Background", bgField));
        infoSection.add(vgap(8));
        infoSection.add(twoColRow("Alignment", alignmentField, "Player Name", playerField));
        infoSection.add(vgap(8));
        infoSection.add(twoColRow("Experience Points", expField, "Character Level", levelField));
        root.add(infoSection);
        root.add(vgap(14));

        //Ability Scores
        JTextField strField = styledField(); JLabel strMod = modLabel(sheet.getStrenghMod());
        JTextField dexField = styledField(); JLabel dexMod = modLabel(sheet.getDexterityMod());
        JTextField conField = styledField(); JLabel conMod = modLabel(sheet.getConstitutionMod());
        JTextField intField = styledField(); JLabel intMod = modLabel(sheet.getIntelligenceMod());
        JTextField wisField = styledField(); JLabel wisMod = modLabel(sheet.getWisdomMod());
        JTextField chaField = styledField(); JLabel chaMod = modLabel(sheet.getCharismaMod());

        attachScoreListener(strField, strMod, v -> { sheet.setStrength(v); return sheet.getStrenghMod(); });
        attachScoreListener(dexField, dexMod, v -> { sheet.setDexterity(v); return sheet.getDexterityMod(); });
        attachScoreListener(conField, conMod, v -> { sheet.setConstitution(v); return sheet.getConstitutionMod(); });
        attachScoreListener(intField, intMod, v -> { sheet.setIntelligence(v); return sheet.getIntelligenceMod(); });
        attachScoreListener(wisField, wisMod, v -> { sheet.setWisdom(v); return sheet.getWisdomMod(); });
        attachScoreListener(chaField, chaMod, v -> { sheet.setCharisma(v); return sheet.getCharismaMod(); });

        JPanel abilitySection = makeSection("ABILITY SCORES");
        abilitySection.add(abilityRow(
                new String[]{"STR","DEX","CON","INT","WIS","CHA"},
                new JTextField[]{strField,dexField,conField,intField,wisField,chaField},
                new JLabel[]{strMod,dexMod,conMod,intMod,wisMod,chaMod}
        ));
        root.add(abilitySection);
        root.add(vgap(14));

        //Combat statistics
        JTextField acField = styledField();
        JTextField initField = styledField();
        JTextField speedField = styledField();
        JTextField hpMaxField = styledField();
        JTextField curHpField = styledField();
        JTextField tempHpField = styledField();
        JTextField hdTypeField  = styledField();
        JTextField hdTotalField = styledField();
        JTextField profBonField = styledField();
        JTextField inspirField = styledField();

        JPanel combatSection = makeSection("COMBAT & MISC");
        combatSection.add(twoColRow("Armor Class", acField,"Initiative", initField));
        combatSection.add(vgap(8));
        combatSection.add(twoColRow("Speed",speedField,"Proficiency Bonus", profBonField));
        combatSection.add(vgap(8));
        combatSection.add(twoColRow("HP Max", hpMaxField,"Current HP", curHpField));
        combatSection.add(vgap(8));
        combatSection.add(twoColRow("Temp HP", tempHpField,"Inspiration", inspirField));
        combatSection.add(vgap(8));
        combatSection.add(twoColRow("Hit Dice Type", hdTypeField, "Hit Dice Total", hdTotalField));
        root.add(combatSection);
        root.add(vgap(14));

        //saving throws
        JTextField strSave = styledField(); JTextField dexSave = styledField();
        JTextField conSave = styledField(); JTextField intSave = styledField();
        JTextField wisSave = styledField(); JTextField chaSave = styledField();

        JPanel saveSection = makeSection("SAVING THROWS");
        saveSection.add(twoColRow("Strength", strSave, "Dexterity", dexSave));
        saveSection.add(vgap(8));
        saveSection.add(twoColRow("Constitution", conSave, "Intelligence", intSave));
        saveSection.add(vgap(8));
        saveSection.add(twoColRow("Wisdom", wisSave, "Charisma", chaSave));
        root.add(saveSection);
        root.add(vgap(14));

        //Primary skills
        String[] skillNames = {
                "Acrobatics","Animal Handling","Arcana","Athletics","Deception",
                "History","Insight","Intimidation","Investigation","Medicine",
                "Nature","Perception","Performance","Persuasion","Religion",
                "Sleight of Hand","Stealth","Survival"
        };
        JCheckBox[] skillBoxes = new JCheckBox[skillNames.length];
        for (int i = 0; i < skillNames.length; i++)
            skillBoxes[i] = styledCheckBox(skillNames[i]);

        JPanel skillSection = makeSection("SKILL PROFICIENCIES");
        skillSection.add(skillGrid(skillBoxes));
        root.add(skillSection);
        root.add(vgap(20));

        //Save dat stuff
        JButton saveBtn = new JButton("SAVE CHARACTER SHEET");
        saveBtn.setFont(new Font("Georgia", Font.BOLD, 14));
        saveBtn.setBackground(ACCENT);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setBorderPainted(false);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveBtn.addActionListener(e -> {
            try {
                sheet.setCharacterName(nameField.getText().trim());
                sheet.setCharacterClass(classField.getText().trim());
                sheet.setCharacterRace(raceField.getText().trim());
                sheet.setCharacterBackground(bgField.getText().trim());
                sheet.setCharacterAlignment(alignmentField.getText().trim());
                sheet.setPlayerName(playerField.getText().trim());
                sheet.setCharacterEXP(parseIntOrZero(expField.getText()));
                sheet.setCharacterLevel(parseIntOrZero(levelField.getText()));
                sheet.setArmorClass(parseIntOrZero(acField.getText()));
                sheet.setInitiative(parseIntOrZero(initField.getText()));
                sheet.setSpeed(parseIntOrZero(speedField.getText()));
                sheet.setHpMax(parseIntOrZero(hpMaxField.getText()));
                sheet.setCurrentHP(parseIntOrZero(curHpField.getText()));
                sheet.setTempHP(parseIntOrZero(tempHpField.getText()));
                sheet.setProfBonus(parseIntOrZero(profBonField.getText()));
                sheet.setInspiration(parseIntOrZero(inspirField.getText()));
                sheet.setHitDiceType(hdTypeField.getText().trim());
                sheet.setHitDiceTotal(parseIntOrZero(hdTotalField.getText()));
                sheet.setStrengthSave(parseIntOrZero(strSave.getText()));
                sheet.setDexteritySave(parseIntOrZero(dexSave.getText()));
                sheet.setConstitutionSave(parseIntOrZero(conSave.getText()));
                sheet.setIntelligenceSave(parseIntOrZero(intSave.getText()));
                sheet.setWisdomSave(parseIntOrZero(wisSave.getText()));
                sheet.setCharismaSave(parseIntOrZero(chaSave.getText()));
                sheet.setAcrobatics(skillBoxes[0].isSelected());
                sheet.setAnimalHandling(skillBoxes[1].isSelected());
                sheet.setArcana(skillBoxes[2].isSelected());
                sheet.setAthletics(skillBoxes[3].isSelected());
                sheet.setDeception(skillBoxes[4].isSelected());
                sheet.setHistory(skillBoxes[5].isSelected());
                sheet.setInsight(skillBoxes[6].isSelected());
                sheet.setIntimidation(skillBoxes[7].isSelected());
                sheet.setInvestigation(skillBoxes[8].isSelected());
                sheet.setMedicine(skillBoxes[9].isSelected());
                sheet.setNature(skillBoxes[10].isSelected());
                sheet.setPerception(skillBoxes[11].isSelected());
                sheet.setPerformance(skillBoxes[12].isSelected());
                sheet.setPersuasion(skillBoxes[13].isSelected());
                sheet.setReligion(skillBoxes[14].isSelected());
                sheet.setSleightOfHand(skillBoxes[15].isSelected());
                sheet.setStealth(skillBoxes[16].isSelected());
                sheet.setSurvival(skillBoxes[17].isSelected());
                sheet.setSetup(true);

                // FIX FIX FIX DO NOT LEAVE AS NULL

                // SCREAMS OF AGONY

                if (sheet.getCharacterName().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Character name cannot be empty.");
                    return;
                }
                String saveName = sheet.getCharacterName() + ".dat";
                PlayerSheetIO.savePlayerSheetToFile(sheet, saveName);
                JOptionPane.showMessageDialog(null, "Sheet saved as \"" + saveName + "\"");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error saving sheet: " + ex.getMessage());
            }
        });

        root.add(saveBtn);
        root.add(vgap(10));

        //SCROLL COMPONENTS AAAA THE VOICES THEY ARE LOUD
        JScrollPane sheetScroll = new JScrollPane(root); //wrap it in root for scrolling
        sheetScroll.setBorder(null);
        sheetScroll.getViewport().setBackground(BG);
        sheetScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sheetScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sheetScroll.getVerticalScrollBar().setUnitIncrement(16);
        pane.setComponentAt(index, sheetScroll);
    }

    //helpers for UI
    private static JPanel makeSection(String title) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(PANEL_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COL, 1),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JLabel header = new JLabel(title);
        header.setFont(new Font("Georgia", Font.BOLD, 11));
        header.setForeground(ACCENT);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        card.add(header);
        return card;
    }

    private static JPanel twoColRow(String lbl1, JTextField f1, String lbl2, JTextField f2) {
        JPanel row = new JPanel(new GridLayout(1, 2, 12, 0));
        row.setBackground(PANEL_BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        row.add(labeledField(lbl1, f1));
        row.add(labeledField(lbl2, f2));
        return row;
    }

    private static JPanel oneColRow(String lbl, JTextField field) {
        JPanel row = new JPanel(new GridLayout(1, 1));
        row.setBackground(PANEL_BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        row.add(labeledField(lbl, field));
        return row;
    }

    private static JPanel labeledField(String label, JTextField field) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(PANEL_BG);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Georgia", Font.PLAIN, 11));
        lbl.setForeground(TEXT_DIM);

        styleField(field);

        p.add(lbl);
        p.add(Box.createVerticalStrut(3));
        p.add(field);
        return p;
    }

    private static JPanel abilityRow(String[] labels, JTextField[] fields, JLabel[] mods) {
        JPanel row = new JPanel(new GridLayout(1, 6, 10, 0));
        row.setBackground(PANEL_BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        for (int i = 0; i < 6; i++) {
            JPanel cell = new JPanel();
            cell.setLayout(new BoxLayout(cell, BoxLayout.Y_AXIS));
            cell.setBackground(SECTION_BG);
            cell.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COL, 1),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));

            JLabel abbr = new JLabel(labels[i], SwingConstants.CENTER);
            abbr.setFont(new Font("Georgia", Font.BOLD, 12));
            abbr.setForeground(ACCENT);
            abbr.setAlignmentX(Component.CENTER_ALIGNMENT);

            fields[i].setHorizontalAlignment(JTextField.CENTER);
            styleField(fields[i]);

            mods[i].setAlignmentX(Component.CENTER_ALIGNMENT);

            cell.add(abbr);
            cell.add(Box.createVerticalStrut(4));
            cell.add(fields[i]);
            cell.add(Box.createVerticalStrut(4));
            cell.add(mods[i]);
            row.add(cell);
        }
        return row;
    }

    //formatter for creation
    private static JPanel skillGrid(JCheckBox[] boxes) {
        int cols = 3;
        int rows = (int) Math.ceil(boxes.length / (double) cols);
        JPanel grid = new JPanel(new GridLayout(rows, cols, 8, 4));
        grid.setBackground(PANEL_BG);
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        for (JCheckBox cb : boxes) grid.add(cb);
        return grid;
    }

    @FunctionalInterface
    interface IntToInt { int apply(int v); }

    private static void attachScoreListener(JTextField field, JLabel modLabel, IntToInt setter) {
        Runnable update = () -> {
            try {
                int mod = setter.apply(Integer.parseInt(field.getText().trim()));
                modLabel.setText(mod >= 0 ? "+" + mod : String.valueOf(mod));
                modLabel.setForeground(mod >= 0 ? new Color(0x4EC96A) : ACCENT);
            } catch (NumberFormatException ex) {
                modLabel.setText("—");
                modLabel.setForeground(TEXT_DIM);
            }
        };
        field.addActionListener(e -> update.run());
        field.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) { update.run(); }
        });
    }

    private static void styleField(JTextField f) {
        f.setBackground(FIELD_BG);
        f.setForeground(TEXT);
        f.setCaretColor(ACCENT);
        f.setFont(new Font("Georgia", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COL, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
    }

    // Creates a styled, non-editable text field for displaying loaded sheet data
    private static JTextField readOnlyField(String value) {
        JTextField f = new JTextField(value);
        f.setEditable(false);
        f.setBackground(FIELD_BG);
        f.setForeground(TEXT_DIM);   // slightly dimmed to signal read-only
        f.setCaretColor(ACCENT);
        f.setFont(new Font("Georgia", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COL, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        return f;
    }

    private static JTextField styledField() {
        JTextField f = new JTextField();
        styleField(f);
        return f;
    }

    private static JLabel modLabel(int mod) {
        JLabel l = new JLabel(mod >= 0 ? "+" + mod : String.valueOf(mod), SwingConstants.CENTER);
        l.setFont(new Font("Georgia", Font.BOLD, 12));
        l.setForeground(mod >= 0 ? new Color(0x4EC96A) : ACCENT);
        return l;
    }

    private static JCheckBox styledCheckBox(String label) {
        JCheckBox cb = new JCheckBox(label);
        cb.setBackground(PANEL_BG);
        cb.setForeground(TEXT);
        cb.setFont(new Font("Georgia", Font.PLAIN, 12));
        cb.setFocusPainted(false);
        return cb;
    }

    private static Component vgap(int h) {
        return Box.createRigidArea(new Dimension(0, h));
    }

    private static int parseIntOrZero(String s) {
        try { return Integer.parseInt(s.trim()); }
        catch (NumberFormatException e) { return 0; }
    }
}

// Maeve was here
// Ben was too
// Skyler waz here :3