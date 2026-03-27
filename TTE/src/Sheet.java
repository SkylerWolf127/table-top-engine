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

    @Override
    public String toString() {

        return this.characterName + " " + this.characterClass + " " + this.characterRace + " " + this.characterBackground;
    }
}
