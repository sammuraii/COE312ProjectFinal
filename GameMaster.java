import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Scanner;

//Controls the gameplay behavior of the class
//Runnable as this thread will control the game progression
public class GameMaster extends AbstractObserverSubject implements Runnable {
    TCP_Client t = new TCP_Client("192.168.0.195", 52855, 0);
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
    Clock clock = Clock.getInstance();
    Map map = new Map();
    
    //Variables that store gamedata.
    ArrayList<Boolean> VisitedLocations = new ArrayList<Boolean>();
   
    //Auxilary Variables
    Subject[] stdSubjects = { clock }; // standard subjects that all objects and characters observe
    Player player = Player.getInstance(stdSubjects,"Chuck Noland",islandNorth,100,3);
    
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


    Consumables Tree = new Consumables("Tree", "A beautiful palm tree. Can be cut to gather resources.", islandNorth, false);
    Objects Wood = new Objects("Wood", "A piece of palm wood");
    Objects Leaves = new Objects("Leaves", "Leaves of palm tree");

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
        Tree.dropObjects.add(Wood);
        Tree.dropObjects.add(Leaves);
        islandNorth.currentlyPlacedObjects.add(Tree);

        FedExPilot.inventory.add(idCard);
        FedExPilot.inventory.add(wallet);
        FedExPilot.inventory.add(watch);
        FedExPilot.inventory.add(goldBracelet);
        FedExPilot.nextState();
        

        
        L1();
    }
    

    public void L1() {
        // DONE - Notification(from location): New Location Unlocked

        // Time: Day night cycle based on time - SAAD
        // Will send messages -> player ------ done
        // Time takes it's observers as characterlist -> In other works, all characters
        // observe time. ---- done
        // Day 1 longer - Day counter, when daycount == 0 sleep(longertime) --- done

        // COME BACK TO THIS // Disable functions at night unless there is some object
        // SAMARTH

        // Undead has method called activate -> dead -> undead state
        // Discover and search Vikram corpse //OBJECTIVE 1 -> Triggered by search()
        // function for the corpse.
        // He will find
        // Pilot License
        // Wallet
        // Watch -> used to track time
        // VALUABLES
        // Gold bracelet

        // SAAD
        // Viewtime -> Morning, Afternoon, Evening, Night
        // Viewtime -> Exact time

        // Message format for time, updates every minute
        // (origin,"time","5:00")
        // Before finding watch (Strategy)

        // SAMARTH

        // Can watch out for tribals that attack at 0:00, if Objective(buildhouse -
        // completed)

        // Create the instances for boxes
        // Array to hold objects
        // Wilson
        // LV Bag -> increase inventory space from 2 objects to 30 (Send message to
        // gamemaster -> sends message to player to increase inventory)
        //
        //

        // Find axe while looking around ->objective

        // ROSHNI

        // If time is past 5:00 and player has an axe, create a wolf at islandEast.
        
        

        // Two classes attack types extend AttackType-> instances in animal-> DONE
        // scratch attack & pounce attack -> DONE
        // Implement attacks - Strategy to implement attacks (Sends a message to what is ->DONE
        // being attacked -> to reduce health) ->DONE

        //SAMARTH

        // Every living thing must have a state -> when killed state changes to dead
        // When state changes -> actions that can be performed change
        // Player thinks to himself, looks like this makes a good blanket, I can use
        // this to spend the night
    }

    public void L2() {
        // ROSHNI

        // Cut wood.
        // Use axe to cut wood (5x wood)
        // collect leaves(3x leaves)
        // get this from one tree

        // Create Tree

        // --Not Assigned--//
        // Crafting anything -> makes player tired, reduces health
        // COME BACK TO - Reduce health when crafting objects
        // Two types of objects -> Large & small, deteriorate health differently

        // if Objective(buildhouse - completed), tribals appear everyday at 00:00

        // COME BACK TO -- Fighting logic for tribals -
        // Obj: Collect
        // Eats egg from hen everyday
        // L2();
        // Build a House
        // Fight the corpse of Vikram Kumar
        // Befriend a Tribal
        // Optional(Befriend William)

        // L2();
    }

    public void L3() {
        // Portable toilet washes up on shore
        // Create raft using craftable class
        // rescued by cargo ship
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
                case "acquire":
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