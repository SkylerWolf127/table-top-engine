
//would it be a good idea to have the setup UI function condensed inside the player object? or do it in the main class?
public class Sheet {

    //test flag
    boolean isSetup;
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
    int deathSaves;
    int lifeSaves;

    //skills
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
        return super.toString();
    }
}
