import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;

/**
 *  This class is the main class for "Jewel Hunt".
 *  
 *  This game is built using the Zuul-better framework for "World of Zuul" - 
 *  a very simple, text based adventure game.  
 *  The Zuul framework helps structure the game setup, room navigation, and commands. 
 *   
 *  The game's concept was inspired by the 1995 movie 'Jumanji' and the 
 *  mobile adventure game 'Diamond Rush'.
 *  
 *  The player must collect all the jewels in different environments and return them 
 *  to the center of the map to win the game. 
 *  
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes 
 * @author  Jawhara Jannah
 * @version 2.0
 */

public class Game 
{
    private Parser parser;                                  // To parse commands from the player                                                          
    private Room currentRoom;                               // Current room the player is in
    private Room previousRoom;                              //  The previous room
    private Stack<Room> roomHistory;                        //  Stack to keep track of room history (for the 'back' command)
    private List<Room>allRooms = new ArrayList<>();         //  List of all rooms in the game
    private Player player;                                  //  The player 
    private Character stranger;                             //  A charcter who wanders the game
    private Character templeGuardian;                       //  The guardian of the temple character                             
    private boolean gameOver;                               //  Flag to track whether the game is over
    private Set<String> jewelsInTemple = new HashSet<>();   //  Track the jewels in the Temple
    
    /**
     *  Main method to start the game and move the stranger
     */
    public static void main (String[] args)
    {
        Game game = new Game();     // Create a new game instance
        game.play();                // Start the game
        game.moveStranger();        // Move the stranger to a random room
    }
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();                  // Create rooms in the game
        parser = new Parser();          // Initialize the command parser 
        previousRoom = null;            // No previous room at the start of the game
        gameOver = false;               // The game is initially not over
        roomHistory = new Stack<>();    // Initialize the room history               
        initializeCharacters();         // Initialize charcters (stranger and temple guardian)                              
    }

    /**
     * Helper method to create rooms with a name and description
     * 
     * @param name the name of the room
     * @param description a description of the room
     * @return a new Room object with the name and description
     */
    private Room createRoom(String name, String description) 
    {
        return new Room(name, description);  // Calls the Room constructor
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // Initialize room
        Room entrance, jungle, topOfTheMountain, bottomOfTheMountain, river, cave, island, garden, 
        topOfTheCastle, bottomOfTheCastle,topOfTheTower, bottomOfTheTower, temple;
        
        // Create the rooms
        entrance = createRoom("Entrance of the Enchanted Jungle" ,"at the entrance to the enchanted jungle");
        jungle = createRoom("Jungle", "in a dense, winding jungle filled with towering trees");
        bottomOfTheMountain = createRoom("Bottom of the Mountain", "at the bottom of a snowy mountain");
        topOfTheMountain = createRoom("Top of the Mountain", "on top of the snowy mountain");
        river = createRoom("River", "by a fast-flowing river");
        cave = createRoom("Cave", "in a deep, dark, and damp cave");
        island = createRoom("Island", "on a vast, deserted island");
        garden = createRoom("Garden", "in a floral garden filled with blooming tulips and berry bushes");
        bottomOfTheCastle = createRoom("Bottom of the Castle", "in a grand, ancient, and abandoned castle ");
        topOfTheCastle = createRoom("Top of the Castle", "at the top floor of the castle");
        bottomOfTheTower = createRoom("Bottom of the Tower", "in a mysterious tower");
        topOfTheTower = createRoom("Top of the Tower", "at the top of the tower");
        temple = createRoom("Temple of Jewels", "in the temple of jewels");
        
        // Add all rooms to the list
        allRooms.add(entrance);
        allRooms.add(jungle);
        allRooms.add(bottomOfTheMountain);
        allRooms.add(topOfTheMountain);
        allRooms.add(river);
        allRooms.add(cave);
        allRooms.add(island);
        allRooms.add(garden);
        allRooms.add(bottomOfTheCastle);
        allRooms.add(topOfTheCastle);
        allRooms.add(bottomOfTheTower);
        allRooms.add(topOfTheTower);
        allRooms.add(temple);
        
        // Set the strating room and player
        currentRoom = entrance;  
        player = new Player("Player", 1000);    
        
        // Initialize items in rooms
        initializeItemsInRooms(jungle, topOfTheMountain, river, cave, island, garden, topOfTheCastle, topOfTheTower);
        
        // Set up exits between rooms
        setExitsBetweenRooms(entrance, jungle, topOfTheMountain, bottomOfTheMountain, river, cave, island, garden, bottomOfTheCastle, topOfTheCastle, temple, 
        bottomOfTheTower, topOfTheTower);
    }

    /**
     * Initialize items in each room
     * 
     * @param jungle Room where jewels and fruits are placed  
     * @param topOfTheMountain Room where jewels and fruits are placed 
     * @param river Room where jewels and fruits are placed
     * @param cave Room where jewels and fruits are placed
     * @param island Room where jewels and fruits are placed
     * @param garden Room where jewels and fruits are placed
     * @param topOfTheCastle Room where jewels and fruits are placed
     * @param topOfTheTower Room where jewels and fruits are placed
     */
    private void initializeItemsInRooms(Room jungle, Room topOfTheMountain, Room river, Room cave, Room island, Room garden, 
    Room topOfTheCastle, Room topOfTheTower)
    {
        // Initialize jewels and fruits in various rooms
        
        Item jungleJewel = new Item("Emerald"," A glowing green gemstone", 100, true);
        Item jungleFruit = new Item("Mango"," A mango ", 100, true);
        jungle.addItem(jungleJewel);
        jungle.addItem(jungleFruit);
         
        Item mountainJewel = new Item("Ruby"," A sparkling red jewel", 100, true);
        Item mountainFruit = new Item("Apple"," An apple  ", 100, false);
        topOfTheMountain.addItem(mountainJewel);
        topOfTheMountain.addItem(mountainFruit);
        
        Item riverJewel = new Item("Diamond"," A pure white stone", 100, true);
        Item riverFruit = new Item("Water"," A canteen of water  ", 100, false);
        river.addItem(riverJewel);
        river.addItem(riverFruit);
        
        Item caveJewel = new Item("Sapphire"," A shimmering blue gem", 100, true);
        Item caveFruit = new Item("Fig"," A fig  ", 100, false);
        cave.addItem(caveJewel);
        cave.addItem(caveFruit);
         
        Item islandJewel = new Item("Pearl"," A polished shiny pearl", 100, true);
        Item islandFruit = new Item("Dates"," A handful of dates ", 100, true);
        island.addItem(islandJewel);
        island.addItem(islandFruit);
        
        Item castleJewel = new Item("Topaz","A magnificent orange jewel", 100, true);
        Item castleFruit = new Item("Orange"," An orange  ", 100, true);
        topOfTheCastle.addItem(castleJewel);
        topOfTheCastle.addItem(castleFruit);
        
        Item gardenJewel = new Item("Amethyst"," A vibrant purple jewel", 100, true);
        Item gardenFruit = new Item("Strawberry"," A handful of strawberries  ", 100, false);
        garden.addItem(gardenJewel);
        garden.addItem(gardenFruit);
        
        Item towerJewel = new Item("Moonstone"," A soft white stone", 100, true);
        Item towerFruit = new Item("Pomegranate"," A pomegranate  ", 100, true);
        topOfTheTower.addItem(towerJewel);
        topOfTheTower.addItem(towerFruit);
    }
    
    /**
     * Initializes the game characters
     */
    private void initializeCharacters()
    {
        // Initializes characters name, description and dialogue
        
        templeGuardian = new Character
        ("Guardian of Jewels", 
        "A wise and ancient being who watches over the temple.", 
        "The Guardian says: 'Give me my jewels if you want to escape.'");

        stranger = new Character 
        ("Stranger",  
        "A mysterious figure who wanders the jungle." ,
        "The Stranger says: 'Do you have any fruit for me? I'm feeling hungry.'\n");
    }
    
    /** 
     * Initializes exits between rooms.
     * 
     * @param entrance The room at the entrance of the jungle.
     * @param jungle The room in the jungle.
     * @param topOfTheMountain The room at the top of the mountain.
     * @param bottomOfTheMountain The room at the bottom of the mountain.
     * @param river The room by the river.
     * @param cave The cave room.
     * @param island The island room.
     * @param garden The garden room.
     * @param bottomOfTheCastle The room at the bottom of the castle.
     * @param topOfTheCastle The room at the top of the castle.
     * @param temple The temple room.
     * @param bottomOfTheTower The room at the bottom of the tower.
     * @param topOfTheTower The room at the top of the tower.
     * 
     */
    private void setExitsBetweenRooms(Room entrance, Room jungle, Room topOfTheMountain, Room bottomOfTheMountain, Room river, 
    Room cave, Room island, Room garden, Room bottomOfTheCastle, Room topOfTheCastle, Room temple, Room bottomOfTheTower, Room topOfTheTower)
    {
        // Define exits between rooms
        
        entrance.setExit("north", jungle);
        
        jungle.setExit("north", temple);
        jungle.setExit("east", bottomOfTheTower);
        jungle.setExit("south", entrance);
        jungle.setExit("west", bottomOfTheMountain);
        
        bottomOfTheMountain.setExit("up", topOfTheMountain);
        bottomOfTheMountain.setExit("north", river);
        bottomOfTheMountain.setExit("east", jungle);
        
        topOfTheMountain.setExit("down", bottomOfTheMountain);
        
        river.setExit("north", cave);
        river.setExit("east", temple);
        river.setExit("south", bottomOfTheMountain);
        
        cave.setExit("east", island);
        cave.setExit("south", river);
        
        island.setExit("east", garden);
        island.setExit("south", temple);
        island.setExit("west", cave);
        
        garden.setExit("south", bottomOfTheCastle);
        garden.setExit("west", island);
        
        bottomOfTheCastle.setExit("up", topOfTheCastle);
        bottomOfTheCastle.setExit("north", garden);
        bottomOfTheCastle.setExit("south", bottomOfTheTower);
        bottomOfTheCastle.setExit("west", temple);
        
        topOfTheCastle.setExit("down", bottomOfTheCastle);
        
        temple.setExit("north", island);
        temple.setExit("east", bottomOfTheCastle);
        temple.setExit("south", jungle);
        temple.setExit("west", river);
        
        bottomOfTheTower.setExit("up", topOfTheTower);
        bottomOfTheTower.setExit("north", bottomOfTheCastle);
        bottomOfTheTower.setExit("west", jungle);
        
        topOfTheTower.setExit("down", bottomOfTheTower);
    }
    
    /**
     *  Main play routine. Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();
        
        // Enter the main command loop.  Here we repeatedly read commands and execute them until the game is over.
        
        while (!gameOver) {
            Command command = parser.getCommand();
            processCommand(command);
            moveStranger();
        }
        
        // Game over message
        System.out.println("\nThank you for playing!\n");
        System.out.println("\nGood bye!\n");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() 
    {
        // Print game introduction and command
        
        System.out.println("");
        System.out.println("Welcome to Jewel Hunt!");
        System.out.println("=========================================================================");
        System.out.println("Each enchanted location has a unique jewel.");
        System.out.println("=========================================================================");
        System.out.println("Collect all eight jewels and return them to the Temple of Jewels to win");
        System.out.println("=========================================================================");
        System.out.println("Move with 'go' [direction]");
        System.out.println("Collect items with 'take' [item]");
        System.out.println("Drop items with 'drop' [item]");
        System.out.println("Use command word 'give' if you want to give any character an item");
        System.out.println("Check your items with 'inventory'. ");
        System.out.println("Go back to the previous room using 'back'.");
        System.out.println("Type 'help' if you need help.");
        System.out.println("=========================================================================");
        System.out.println("Good luck!");
        System.out.println("=========================================================================");
        printLocationInfo();
        
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("\nI don't know what that means\n");
            return false;
        }

        String commandWord = command.getCommandWord().toLowerCase();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } 
        else if (commandWord.equals("take")) {
            takeItem(command);
        }
        else if (commandWord.equals("drop")) {
            dropItem(command);
        }   
        else if (commandWord.equals("inventory")) {
            showInventory();
        }
        else if (commandWord.equals("back")) {
            back();
        }
        else if (commandWord.equals("give")) {
            giveItemToStranger(command);  // Handle the 'give' command
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:
    
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("\nYou are trapped here unless you find all the jewels");
        System.out.println("Collect them and bring them to the Temple of Jewels.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
        printLocationInfo();
    }

    /**
     * Prints out the long description of the current room and exits
     */
    private void printLocationInfo() {
        System.out.println("\nYou are " + currentRoom.getLongDescription());
        
        System.out.print("Exits: ");
        if (currentRoom.getExit("north") != null) {
            System.out.print("north ");
        }
        if (currentRoom.getExit("east") != null) {
            System.out.print("east ");
        }
        if (currentRoom.getExit("south") != null) {
            System.out.print("south ");
        }
        if (currentRoom.getExit("west") != null) {
            System.out.print("west ");
        }
        if (currentRoom.getExit("up") != null) {
            System.out.print("up ");
        }
        if (currentRoom.getExit("down") != null) {
            System.out.print("down ");
        }
    }
    
    /** 
     * Moves the player in a specified direction.
     * If there is not an exit, an error message is shown
     * 
     * @param command The command containing the direction to move to 
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("\nGo where?\n");
            return;
        }
    
        String direction = command.getSecondWord().toLowerCase();
        Room nextRoom = currentRoom.getExit(direction);
    
        if (nextRoom == null) {
            System.out.println("\nGo where?\n");
        } else {
            roomHistory.push(currentRoom);
            currentRoom = nextRoom;
            previousRoom = currentRoom; // Keep track of the previous room for 'back' command
            
            
            printLocationInfo(); // Show the player's current location
            System.out.println("");
            
            // Check for win condition 
            checkForWin(currentRoom);

            if (gameOver) 
                return;

            // Check if a character is in the room
            if (currentRoom.getCharacter() != null) {
                Character character = currentRoom.getCharacter();
                System.out.println("\nThere's someone here: " + character.getDescription());
                System.out.println(character.getDialogue());
            }

        }
    }

    /** 
     * Process the 'quit' command to end the game.
     * 
     * @param command The 'quit' command 
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("\nQuit what?\n");
            return false;
        }
        else {
            System.out.println("\nYou have quit Jewel Hunt.");
            System.out.println("\nThank you for playing!\n");
            System.out.println("Good bye!\n");
            return true;  
        }
    }
    
    /**
     * Processes the 'take' command to pick up an item from the current room and add it to the inventory
     * 
     * @param command The 'take' command
     */
    private void takeItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("\nTake what?\n");
            return;
        }
    
        String itemName = command.getSecondWord().toLowerCase();
        Item item = currentRoom.getItem(itemName);
        
        // Check if item exists in the room
        if (item == null) {
            System.out.println("\nThat item is not in this room.\n");
            return;
        }
    
        // Check if item is pickable
        if (!item.isPickable()) {
            System.out.println("\nYou cannot pick up " + item.getName() + ".\n");
            return;
        }
    
        // Add the item to the player's inventory
        if (player.addItem(item)) {
            System.out.println("\nYou picked up: " + item.getName() + ".\n");
            currentRoom.removeItem(itemName); // Remove the item from the room if added successfully
        } else {
            System.out.println(""); 
        }
    }

    /**
     * Processes the 'drop' command to drop an item from the player's inventory 
     * 
     * @param comman The 'drop' command
     */
    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("\nDrop what?\n");
            return;
        }

        String itemName = command.getSecondWord();
        Item item = player.getItem(itemName);

        if (item == null) {
            System.out.println("\nYou don't have that item.\n");
            return;
        }

        player.removeItem(itemName);     
        currentRoom.addItem(item);         
        System.out.println("\nYou dropped: " + item.getName()+ ".\n");
    }
    
    /**
     * Displays the player's inventory
     */
    private void showInventory() {
        player.printInventory(); 
    }

    /**
     * Checks if the player has won the game if all jewels are brought to the temple of Jewels
     * The game ends if all jewels are collected
     * 
     * @param currentRoom The room the player is currently in
     */
    private void checkForWin(Room currentRoom) 
    {
        // Define the required jewels
        List<String> requiredJewels = Arrays.asList(
            "Ruby", "Pearl", "Emerald", "Diamond", "Sapphire", "Topaz", "Amethyst", "Moonstone"
        );
    
        // Check if the player is in the "Temple of Jewels"
        if (currentRoom.getName().equals("Temple of Jewels")) {
    
            System.out.println(("\nThere's someone here: A wise and ancient being who watches over the temple."));
            System.out.println("The Guardian says: 'Give me my jewels if you want to escape.'");
            
            Set<String> collectedJewels = player.getInventory().keySet();
            
            List<String> missingJewels = new ArrayList<>(requiredJewels);
            missingJewels.removeAll(collectedJewels);
            
            // Check if the player has all the required jewels
            if (collectedJewels.containsAll(requiredJewels)) {
                System.out.println(); 
                System.out.println("Congratulations! You have collected all the jewels and brought them to the Temple. You have won the game!\n");
                gameOver = true; 
            } else {
                System.out.println("The Guardian says: 'You do not have all the jewels yet. Keep looking!'");
                System.out.println("\nMissing jewels: " + missingJewels);
            }
        }
    }
    
    /**
     * Processes the 'back' command to allow the player to go to the previous room
     */
    private void back()
    {
        if (!roomHistory.isEmpty()){
            currentRoom = roomHistory.pop();
            System.out.println("\nYou have returned to: " + currentRoom.getName());
            printLocationInfo();
        } else {
            System.out.println("\nYou are back at the starting location.\n");
        }
    }
    
    /**
     * Moves the Stranger character to random rooms 
     */
    private void moveStranger() 
    {
        // get a random room from the list
        Random random = new Random();
        Room randomRoom = allRooms.get(random.nextInt(allRooms.size())); 
        
        // Set Stranger's new random room
        stranger.setCurrentRoom(randomRoom);
        
        // Add the Stranger to the new room
        randomRoom.setCharacter(stranger);
    }

    /**
     * Processes the 'give' command which allows the player to give an item to the Stranger
     * The item is removed form the player's inventory
     * 
     * @param command The 'give' command
     */
    private void giveItemToStranger(Command command) 
    {
        // Ensures there are both a second and third word 
        if (!command.hasSecondWord() || !command.hasThirdWord()) {
            System.out.println("\nGive what to whom?\n");
            return;
        }
    
        String itemName = command.getSecondWord();
        String characterName = command.getThirdWord();
    
        
        Item item = player.getItem(itemName);
        // Check if the item exists in the player's inventory
        if (item == null) {
            System.out.println("\nYou don't have that item to give.\n");
            return;
        }
    
        // Ensure the character the item is given to is "Stranger"
        if (!characterName.equalsIgnoreCase("Stranger")) {  
            System.out.println("\nYou can only give items to the Stranger.\n");
            return;
        }
    
        
        player.removeItem(itemName);  // Remove the item from the player's inventory
    
        System.out.println("\nYou gave your " + itemName + " to the Stranger.");
        System.out.println("The Stranger says: 'Thank you for the " + itemName + "!'\n");
    
        currentRoom.removeItem(itemName);  // Remove the item from the room if needed
        
        // Update the Strangers dialogue and description
        stranger.updateDialogueAndDescription(
        "", 
        "The Stranger is content and happily eating the fruit you gave them."  
        );
        moveStranger();
        System.out.println("The Stranger is eating gratefully and will not bother you again.");        
    }
}
