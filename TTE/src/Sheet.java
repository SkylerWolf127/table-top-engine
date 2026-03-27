import java.io.Serializable;

//would it be a good idea to have the setup UI function condensed inside the player object? or do it in the main class?
//Serializable interface allows for save / load. Write sheet object into a stream of bytes to local storage.
//Save / load routine should have the ability to access different parts of the File system. Not just PWD.

public class Sheet implements Serializable {
    private static final long serialVersionUID = 1L;

    //test flag
    boolean isSetup; //flag for checking if the sheet is fully setup. Potentially use for unsaved progress?
    //top of sheet
    private String characterName;
    private String characterClass;
    private String characterRace;
    private String characterBackground;
    private String characterAlignment;
    private String playerName;
    private int characterEXP;

    //ability scores
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;

    //ability score modifiers
    private int strenghMod;
    private int dexterityMod;
    private int constitutionMod;
    private int intelligenceMod;
    private int wisdomMod;
    private int charismaMod;

    //saving throws
    private int strengthSave;
    private int dexteritySave;
    private int constitutionSave;
    private int intelligenceSave;
    private int wisdomSave;
    private int charismaSave;

    //extra
    private int inspiration;
    private int profBonus;
    private int armorClass;
    private int initiative;
    private int speed;
    private int currentHP;
    private int hpMax;
    private int tempHP;
    private String hitDiceType;
    private int hitDiceTotal;
    //death saves (Use these for success / fail on death saves. death = failure, life = success)
    int deathSaves;
    int lifeSaves;

    //skills | The wall of booleans lmfao (Used for proficiency. false = not prof. true = prof.)
    boolean acrobatics;
    boolean animalHandling;
    boolean arcana;
    boolean athletics;
    boolean deception;
    boolean history;
    boolean insight;
    boolean intimidation;
    boolean investigation;
    boolean medicine;
    boolean nature;
    boolean perception; //(different from passive perception)
    boolean performance;
    boolean persuasion;
    boolean religion;
    boolean sleightOfHand;
    boolean stealth;
    boolean survival;



    //constructor
    public Sheet(){
        characterName = "";
        characterClass = "";
        characterRace = "";
        characterBackground = "";
        characterAlignment = "";
        playerName = "";
        characterEXP = 0;
        strength = 0;
        dexterity = 0;
        constitution = 0;
        intelligence = 0;
        wisdom = 0;
        charisma = 0;
        strenghMod = 0;
        dexterityMod = 0;
        constitutionMod = 0;
        intelligenceMod = 0;
        wisdomMod = 0;
        charismaMod = 0;
        isSetup = false;
        strengthSave = 0;
        dexteritySave = 0;
        constitutionSave = 0;
        intelligenceSave = 0;
        wisdomSave = 0;
        charismaSave = 0;
        inspiration = 0;
        profBonus = 0;
        //
        acrobatics = false;
        animalHandling = false;
        arcana = false;
        athletics = false;
        deception = false;
        history = false;
        insight = false;
        intimidation = false;
        investigation = false;
        medicine = false;
        nature = false;
        perception = false;
        performance = false;
        persuasion = false;
        religion = false;
        sleightOfHand = false;
        stealth = false;
        survival = false;
        //
        armorClass = 0;
        initiative = 0;
        speed = 0;
        currentHP = 0;
        hpMax = 0;
        tempHP = 0;
        hitDiceType = "";
        hitDiceTotal = 0;
        deathSaves = 0;
        lifeSaves = 0;
    }//end constructor

    // test flag
    public boolean isSetup() {
        return isSetup;
    }

    public void setSetup(boolean isSetup) {
        this.isSetup = isSetup;
    }
    //mod calculations
    private int calculateModifier(int score) {
        return (int)Math.floor((score - 10) / 2.0);
    }

    // top of sheet
    public String getCharacterName() { return characterName; }
    public void setCharacterName(String characterName) { this.characterName = characterName; }

    public String getCharacterClass() { return characterClass; }
    public void setCharacterClass(String characterClass) { this.characterClass = characterClass; }

    public String getCharacterRace() { return characterRace; }
    public void setCharacterRace(String characterRace) { this.characterRace = characterRace; }

    public String getCharacterBackground() { return characterBackground; }
    public void setCharacterBackground(String characterBackground) { this.characterBackground = characterBackground; }

    public String getCharacterAlignment() { return characterAlignment; }
    public void setCharacterAlignment(String characterAlignment) { this.characterAlignment = characterAlignment; }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public int getCharacterEXP() { return characterEXP; }
    public void setCharacterEXP(int characterEXP) { this.characterEXP = characterEXP; }

    // ability scores
    public int getStrength() { return strength; }
    public void setStrength(int strength) {
        this.strength = strength;
        this.strenghMod = calculateModifier(strength);
    }

    public int getDexterity() { return dexterity; }
    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
        this.dexterityMod = calculateModifier(dexterity);
    }

    public int getConstitution() { return constitution; }
    public void setConstitution(int constitution) {
        this.constitution = constitution;
        this.constitutionMod = calculateModifier(constitution);
    }

    public int getIntelligence() { return intelligence; }
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
        this.intelligenceMod = calculateModifier(intelligence);
    }

    public int getWisdom() { return wisdom; }
    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
        this.wisdomMod = calculateModifier(wisdom);
    }


    public int getCharisma() { return charisma; }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
        this.charismaMod = calculateModifier(charisma);
    }

    // modifiers
    public int getStrenghMod() { return strenghMod; }
    public void setStrenghMod(int strenghMod) { this.strenghMod = strenghMod; }

    public int getDexterityMod() { return dexterityMod; }
    public void setDexterityMod(int dexterityMod) { this.dexterityMod = dexterityMod; }

    public int getConstitutionMod() { return constitutionMod; }
    public void setConstitutionMod(int constitutionMod) { this.constitutionMod = constitutionMod; }

    public int getIntelligenceMod() { return intelligenceMod; }
    public void setIntelligenceMod(int intelligenceMod) { this.intelligenceMod = intelligenceMod; }

    public int getWisdomMod() { return wisdomMod; }
    public void setWisdomMod(int wisdomMod) { this.wisdomMod = wisdomMod; }

    public int getCharismaMod() { return charismaMod; }
    public void setCharismaMod(int charismaMod) { this.charismaMod = charismaMod; }

    // saving throws
    public int getStrengthSave() { return strengthSave; }
    public void setStrengthSave(int strengthSave) { this.strengthSave = strengthSave; }

    public int getDexteritySave() { return dexteritySave; }
    public void setDexteritySave(int dexteritySave) { this.dexteritySave = dexteritySave; }

    public int getConstitutionSave() { return constitutionSave; }
    public void setConstitutionSave(int constitutionSave) { this.constitutionSave = constitutionSave; }

    public int getIntelligenceSave() { return intelligenceSave; }
    public void setIntelligenceSave(int intelligenceSave) { this.intelligenceSave = intelligenceSave; }

    public int getWisdomSave() { return wisdomSave; }
    public void setWisdomSave(int wisdomSave) { this.wisdomSave = wisdomSave; }

    public int getCharismaSave() { return charismaSave; }
    public void setCharismaSave(int charismaSave) { this.charismaSave = charismaSave; }

    // extras
    public int getInspiration() { return inspiration; }
    public void setInspiration(int inspiration) { this.inspiration = inspiration; }

    public int getProfBonus() { return profBonus; }
    public void setProfBonus(int profBonus) { this.profBonus = profBonus; }

    public int getArmorClass() { return armorClass; }
    public void setArmorClass(int armorClass) { this.armorClass = armorClass; }

    public int getInitiative() { return initiative; }
    public void setInitiative(int initiative) { this.initiative = initiative; }

    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }

    public int getCurrentHP() { return currentHP; }
    public void setCurrentHP(int currentHP) { this.currentHP = currentHP; }

    public int getHpMax() { return hpMax; }
    public void setHpMax(int hpMax) { this.hpMax = hpMax; }

    public int getTempHP() { return tempHP; }
    public void setTempHP(int tempHP) { this.tempHP = tempHP; }

    public String getHitDiceType() { return hitDiceType; }
    public void setHitDiceType(String hitDiceType) { this.hitDiceType = hitDiceType; }

    public int getHitDiceTotal() { return hitDiceTotal; }
    public void setHitDiceTotal(int hitDiceTotal) { this.hitDiceTotal = hitDiceTotal; }

    public int getDeathSaves() { return deathSaves; }
    public void setDeathSaves(int deathSaves) { this.deathSaves = deathSaves; }

    public int getLifeSaves() { return lifeSaves; }
    public void setLifeSaves(int lifeSaves) { this.lifeSaves = lifeSaves; }

    // skills (booleans)
    public boolean isAcrobatics() { return acrobatics; }
    public void setAcrobatics(boolean acrobatics) { this.acrobatics = acrobatics; }

    public boolean isAnimalHandling() { return animalHandling; }
    public void setAnimalHandling(boolean animalHandling) { this.animalHandling = animalHandling; }

    public boolean isArcana() { return arcana; }
    public void setArcana(boolean arcana) { this.arcana = arcana; }

    public boolean isAthletics() { return athletics; }
    public void setAthletics(boolean athletics) { this.athletics = athletics; }

    public boolean isDeception() { return deception; }
    public void setDeception(boolean deception) { this.deception = deception; }

    public boolean isHistory() { return history; }
    public void setHistory(boolean history) { this.history = history; }

    public boolean isInsight() { return insight; }
    public void setInsight(boolean insight) { this.insight = insight; }

    public boolean isIntimidation() { return intimidation; }
    public void setIntimidation(boolean intimidation) { this.intimidation = intimidation; }

    public boolean isInvestigation() { return investigation; }
    public void setInvestigation(boolean investigation) { this.investigation = investigation; }

    public boolean isMedicine() { return medicine; }
    public void setMedicine(boolean medicine) { this.medicine = medicine; }

    public boolean isNature() { return nature; }
    public void setNature(boolean nature) { this.nature = nature; }

    public boolean isPerception() { return perception; }
    public void setPerception(boolean perception) { this.perception = perception; }

    public boolean isPerformance() { return performance; }
    public void setPerformance(boolean performance) { this.performance = performance; }

    public boolean isPersuasion() { return persuasion; }
    public void setPersuasion(boolean persuasion) { this.persuasion = persuasion; }

    public boolean isReligion() { return religion; }
    public void setReligion(boolean religion) { this.religion = religion; }

    public boolean isSleightOfHand() { return sleightOfHand; }
    public void setSleightOfHand(boolean sleightOfHand) { this.sleightOfHand = sleightOfHand; }

    public boolean isStealth() { return stealth; }
    public void setStealth(boolean stealth) { this.stealth = stealth; }

    public boolean isSurvival() { return survival; }
    public void setSurvival(boolean survival) { this.survival = survival; }

    @Override
    public String toString() {

        return this.characterName + " " + this.characterClass + " " + this.characterRace + " " + this.characterBackground;
    }
}
