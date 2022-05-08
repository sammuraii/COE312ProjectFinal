import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Scanner;

//Controls the gameplay behavior of the class
//Runnable as this thread will control the game progression
public class GameMaster extends AbstractObserverSubject implements Runnable {
    static TCP_Client t = new TCP_Client("192.168.1.103", 2000);
    private static GameMaster instance;

    private GameMaster(String config, String log) {
        Thread th = new Thread(this);
        th.start();
    }

    public synchronized static GameMaster getInstance(String config, String log) {
        if (instance == null) {
            instance = new GameMaster(config, log);
        }
        return instance;
    }
    FileInputStream configFile;
    FileInputStream logFile;

    public String introduction = "In 1995, Chuck Noland, a systems analyst executive," +
            "travels the world resolving productivity problems at FedEx depots. " +
            "He lives with his girlfriend Kelly Frears in Memphis, Tennessee," +
            "but his workaholism interferes with their relationship. " +
            "During a family Christmas dinner," +
            "Chuck is summoned to resolve a work problem in Malaysia. " +
            "The FedEx cargo plane he is on gets caught in a storm and" +
            "crashes into the Pacific Ocean. Chuck is the only survivor and" +
            "escapes with an inflatable life raft. The next day+," +
            "he washes up on an uninhabited island.\n\nNow you, Chuck Noland, must survive.";
            
    
   
    // Creating all locations
    static IslandNorth islandNorth = new IslandNorth("Island North", "The north of the island");
    static IslandSouth islandSouth = new IslandSouth("Island South", "The south of the island");
    static IslandEast islandEast = new IslandEast("Island East", "The east of the island");
    static IslandWest islandWest = new IslandWest("Island West", "The west of the island");
    
    static Location[] locationList = { islandNorth,islandSouth,islandEast,islandWest };

    //Mandatory Instances
    Character[] characterList;
    Objectives objectives = new Objectives();
    static Clock clock = Clock.getInstance();
    Map map = new Map();
    
    //Variables that store gamedata.
    ArrayList<Boolean> VisitedLocations = new ArrayList<Boolean>();
   
    //Auxilary Variables
    static Subject[] stdSubjects = { clock }; // standard subjects that all objects and characters observe
    static Player player = Player.getInstance(stdSubjects,"Chuck Noland",islandNorth,100,3);
    
    //making Vikram Kumar and his items
    Undead FedExPilot = new Undead("Vikram Kumar",islandNorth,0,4,"The corpse of the pilot flying the plane.");
    Objects idCard = new Objects(stdSubjects, "IDCard",
            "A pilot license that belongs to vikram kumar");
    Objects wallet = new Objects(stdSubjects, "Wallet",
            "A wallet that belongs to vikram kumar, unfortunately the money in it is useless here.");
    Objects watch = new Objects(stdSubjects, "Watch",
            "A geo-tracking timepiece that changes time according to location. It must be shockproof, hence surviving the crash");
    Objects goldBracelet = new Objects(stdSubjects, "Bracelet",
            "An expensive gold bracelet, may or may not come in handy later");

    //Creating Tribals
    Tribals tribal1 = new Tribals("Olskfghj",islandNorth, 100, 3, "A tribal habitant of the island", "Oonga Boongas");
    Tribals tribal2 = new Tribals("Glksjdfh",islandNorth, 100, 3, "A tribal habitant of the island","Oonga Boongas");
    Tribals tribal3 = new Tribals("Pslkkjh",islandSouth, 100, 3,"A tribal habitant of the island", "Oonga Boongas");
    Tribals tribal4 = new Tribals("Qxkdjfh",islandSouth, 100, 3, "A tribal habitant of the island","Oonga Boongas");
    Tribals tribal5 = new Tribals("Csdjfh",islandEast, 100, 3, "A tribal habitant of the island","Oonga Boongas");
    Tribals tribal6 = new Tribals("Lsjdhfg",islandEast, 100, 3, "A tribal habitant of the island","Oonga Boongas");
    Tribals tribal7 = new Tribals("Pskh",islandWest, 100, 3, "A tribal habitant of the island","Oonga Boongas");
    
    

    //Objects to be placed in locations
     Objects axe = new Objects("Axe", "An emergency glass breaking axe from the plane's debris. A good weapon to have."); 
     Objects stones = new Objects("Stones", "A Flint Stone. Looks like it can be used to start a fire...");
     Food apple1 = new Food(player, "Apple", "An apple. Gives 10 HP upon eating.", 10, islandNorth);
     Food apple2 = new Food(player, "Apple", "An apple. Gives 10 HP upon eating.", 10, islandNorth);
     Food apple3 = new Food(player, "Apple", "An apple. Gives 10 HP upon eating.", 10, islandSouth);
     Food apple4 = new Food(player, "Apple", "An apple. Gives 10 HP upon eating.", 10, islandSouth);
     Food apple5 = new Food(player, "Apple", "An apple. Gives 10 HP upon eating.", 10, islandEast);
     Food apple6 = new Food(player, "Apple", "An apple. Gives 10 HP upon eating.", 10, islandEast);
     Food apple7 = new Food(player, "Apple", "An apple. Gives 10 HP upon eating.", 10, islandWest);
     Food apple8 = new Food(player, "Apple", "An apple. Gives 10 HP upon eating.", 10, islandWest);
     Food apple9 = new Food(player, "Apple", "An apple. Gives 10 HP upon eating.", 10, islandWest);

    //making FedEx Packages

    Consumables box1 = new Consumables("Box","An unopened FedEx package, try to open to see what is inside", islandSouth, false);
    Consumables box2 = new Consumables("Box","An unopened FedEx package, try to open to see what is inside", islandWest, false);
    Consumables box3 = new Consumables("Box","An unopened FedEx package, try to open to see what is inside", islandWest, false);
    Objects wilson = new Objects("Wilson", "A tennis ball made by Wilson Sporting Goods Company. A washed up FedEx Package found on the island.");
    Objects lvbag = new Objects("Bag", "A Louis Vitton Bag with space to hold many items. A washed up FedEx Package found on the island.");
    Objects SwissKnife = new Objects("Knife", "A Swiss Army Knife, can be used as a weapon. A washed up FedEx Package found on the island.");
    Consumables Tree1 = new Consumables("Tree", "A beautiful palm tree. Can be cut to gather resources.", islandNorth, false);
    Objects Wood1 = new Objects("Wood", "A piece of palm wood");
    Objects Leaves1 = new Objects("Leaves", "Leaves of palm tree");
    Consumables Tree2 = new Consumables("Tree", "A beautiful palm tree. Can be cut to gather resources.", islandNorth, false);
    Objects Wood2 = new Objects("Wood", "A piece of palm wood");
    Objects Leaves2 = new Objects("Leaves", "Leaves of palm tree");

    Consumables Tree3 = new Consumables("Tree", "A beautiful oak tree. Can be cut to gather resources.", islandSouth, false);
    Objects Wood3 = new Objects("Wood", "A piece of oak wood");
    Objects Leaves3 = new Objects("Leaves", "Leaves of oak tree");
    Consumables Tree4 = new Consumables("Tree", "A beautiful oak tree. Can be cut to gather resources.", islandSouth, false);
    Objects Wood4 = new Objects("Wood", "A piece of oak wood");
    Objects Leaves4 = new Objects("Leaves", "Leaves of oak tree");

    Consumables Tree5 = new Consumables("Tree", "A beautiful birch tree. Can be cut to gather resources.", islandWest, false);
    Objects Wood5 = new Objects("Wood", "A piece of birch wood");
    Objects Leaves5 = new Objects("Leaves", "Leaves of birch tree");
    Consumables Tree6 = new Consumables("Tree", "A beautiful birch tree. Can be cut to gather resources.", islandWest, false);
    Objects Wood6 = new Objects("Wood", "A piece of birch wood");
    Objects Leaves6 = new Objects("Leaves", "Leaves of birch tree");

    Consumables Tree7 = new Consumables("Tree", "A beautiful acacia tree. Can be cut to gather resources.", islandEast, false);
    Objects Wood7 = new Objects("Wood", "A piece of acacia wood");
    Objects Leaves7 = new Objects("Leaves", "Leaves of acacia tree");
    Consumables Tree8 = new Consumables("Tree", "A beautiful acacia tree. Can be cut to gather resources.", islandEast, false);
    Objects Wood8 = new Objects("Wood", "A piece of acacia wood");
    Objects Leaves8 = new Objects("Leaves", "Leaves of acacia tree");


      public static Location findLocation(String l) throws NoSuchObjectException {
        for (Location loc : locationList) {
            if (loc.name.equalsIgnoreCase(l))
                return loc;
        }
        throw new NoSuchObjectException(l);
        }

        

     //Objective Mission Functions

    public void launchWolf()
    {
        if (Objectives.fightWolf==false && Objectives.foundAxe == true && clock.inGameHours >= 17) {
            Message m = new Message(this, "objective", "fightWolf");
            publishMessage(m);
            Animal wolf1 = new Animal("Wolf", "white", "fur", "wild");
            
            islandEast.currentlyPlacedAnimals.add(wolf1);
           
            UI.printNormal("\n"
            + "                             .d$$b\n"
            + "                           .' TO$;\\\n"
            + "                          /  : TP._;\n"
            + "                         / _.;  :Tb|\n"
            + "                        /   /   ;j$j\n"
            + "                    _.-\"       d$$$$\n"
            + "                  .' ..       d$$$$;\n"
            + "                 /  /P'      d$$$$P. |\\\n"
            + "                /   \"      .d$$$P' |\\^\"l\n"
            + "              .'           `T$P^\"\"\"\"\"  :\n"
            + "          ._.'      _.'                ;\n"
            + "       `-.-\".-'-' ._.       _.-\"    .-\"\n"
            + "     `.-\" _____  ._              .-\"\n"
            + "    -(.g$$$$$$$b.              .'\n"
            + "      \"\"^^T$$$P^)            .(:\n"
            + "        _/  -\"  /.'         /:/;\n"
            + "     ._.'-'`-'  \")/         /;/;\n"
            + "  `-.-\"..--\"\"   \" /         /  ;\n"
            + " .-\" ..--\"\"        -'          :\n"
            + " ..--\"\"--.-\"         (\\      .-(\\\n"
            + "   ..--\"\"              `-\\(\\/;`\n"
            + "     _.                      :\n"
            + "                             ;`-\n"
            + "                            :\\\n"
            + "                            ;  ");
    
            UI.print("A wolf has appeared and it seems to be approaching you to attack!");
            wolf1.registerObserver(player);
        }
    }
    
    public void intro() {

        clock.registerObserver(player);
        player.registerObserver(clock);

        //register all observers that couldn't be done before due to circular dependence.
        player.registerObserver(islandEast);
        player.registerObserver(islandNorth);
        player.registerObserver(islandWest);
        player.registerObserver(islandSouth);
        this.registerObserver(objectives);

       //UI.print(introduction);
        islandEast.currentlyPlacedObjects.add(axe);
        islandNorth.currentlyPlacedObjects.add(stones);
        
        islandSouth.currentlyPlacedObjects.add(box1);
        islandWest.currentlyPlacedObjects.add(box2);
        islandWest.currentlyPlacedObjects.add(box3);
        box1.dropObjects.add(SwissKnife);
        box2.dropObjects.add(wilson);
        box3.dropObjects.add(lvbag);

        Tree1.dropObjects.add(Wood1);
        Tree1.dropObjects.add(Leaves1);
        islandNorth.currentlyPlacedObjects.add(Tree1);
        Tree2.dropObjects.add(Wood2);
        Tree2.dropObjects.add(Leaves2);
        islandNorth.currentlyPlacedObjects.add(Tree2);

        Tree3.dropObjects.add(Wood3);
        Tree3.dropObjects.add(Leaves3);
        islandSouth.currentlyPlacedObjects.add(Tree3);
        Tree4.dropObjects.add(Wood4);
        Tree4.dropObjects.add(Leaves4);
        islandSouth.currentlyPlacedObjects.add(Tree4);

        Tree5.dropObjects.add(Wood5);
        Tree5.dropObjects.add(Leaves5);
        islandWest.currentlyPlacedObjects.add(Tree5);
        Tree6.dropObjects.add(Wood6);
        Tree6.dropObjects.add(Leaves6);
        islandWest.currentlyPlacedObjects.add(Tree6);

        Tree7.dropObjects.add(Wood7);
        Tree7.dropObjects.add(Leaves7);
        islandEast.currentlyPlacedObjects.add(Tree7);
        Tree8.dropObjects.add(Wood8);
        Tree8.dropObjects.add(Leaves8);
        islandEast.currentlyPlacedObjects.add(Tree8);

        FedExPilot.inventory.add(idCard);
        FedExPilot.inventory.add(wallet);
        FedExPilot.inventory.add(watch);
        FedExPilot.inventory.add(goldBracelet);
        FedExPilot.nextState();

        L1();
    }
    

    public void L1() {

        //house built
        //fire made

        while(Objectives.BuiltHouse==false){

        }
        L2();
    }

    public void L2() {
       //add objectives while(){ }

        
        L3();
       
    }

    public void L3() {
       //rescue ship comes
       //chuck waves
       //and he is saved :))))
    }

    //Commands for control panel
    CommandLook cLook = new CommandLook(player); //0
    CommandInspect cInspect = new CommandInspect(player); //1
    CommandAcquire cAcquire = new CommandAcquire(player);//2
    CommandTakeItem cTakeItem = new CommandTakeItem(player);//3
    CommandBattle cBattle = new CommandBattle(player);//4
    CommandInteract cInteract = new CommandInteract(player);//5
    CommandEat cEat = new CommandEat(player);//6
    CommandTravel cTravel = new CommandTravel(player);//7
    CommandInventory cInventory = new CommandInventory(player);//8
    CommandHealth cHealth = new CommandHealth(player);//9
    CommandMap cMap = new CommandMap(player);//10
    CommandSleep cSleep = new CommandSleep(player);//11
    CommandTime cTime = new CommandTime(player); //12
    CommandMake cMake = new CommandMake(player);//13
    CommandHelp cHelp = new CommandHelp(player);//14

    //Control panel and command array
    Command [] cmds = {cLook, cInspect, cAcquire, cTakeItem, cBattle, cInteract, cEat,cTravel, cInventory,cHealth, cMap, cSleep, cTime, cMake, cHelp}; // add more commands as needed
    ControlPanel cp = new ControlPanel(cmds);
    @Override
    public void run() {
        intro();
        String input;
        String[] commands;
        while (true) {
            if(player.inventory.contains(watch) && Objectives.foundWatch==false){
                publishMessage(new Message(this, "Objective", "foundWatch")); //message intended for Objectives class
            }
            if(player.inventory.contains(lvbag) && Objectives.foundLVBag==false){
                publishMessage(new Message(this, "Objective", "foundLVBag")); //message intended for Objectives class
            }
            launchWolf(); //Second objective to clear in the game.
            UI.printnln("command > ");
            input = UI.read();
            commands = input.split(" ");
            switch(commands[0].toLowerCase()){
                case "look":
                    cp.buttonWasPressed(0,input);
                    break;
                case "inspect":
                    cp.buttonWasPressed(1, input);
                    break;
                case "collect":
                    cp.buttonWasPressed(2, input);
                    break;
                case "take":
                    cp.buttonWasPressed(3, input);
                    break;
                case "battle":
                    cp.buttonWasPressed(4, input);
                    break;
                case "chop":                    
                    cp.buttonWasPressed(5, input);
                    break;
                case "open":                    
                    cp.buttonWasPressed(5, input);
                    break;
                case "eat":
                    cp.buttonWasPressed(6, input);
                    break;
                case "travel":
                    cp.buttonWasPressed(7, input);
                    break;
                case "inventory":
                    cp.buttonWasPressed(8, input);
                    break;
                case "health":
                    cp.buttonWasPressed(9, input);
                    break;
                case "map":
                    cp.buttonWasPressed(10, input);
                    break;
                case "sleep":
                    cp.buttonWasPressed(11, input);
                    break;
                case "time":
                    cp.buttonWasPressed(12, input);
                    break;
                case "make":
                    cp.buttonWasPressed(13, input);
                    break;
                case "?": //case "help":
                    cp.buttonWasPressed(14,input);
                    break;
                default:
                UI.print("Invalid command!");
            }
        }

    }

    @Override
    public void update(Message m) {
        
        
    }
}