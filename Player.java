import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents a Player.
 * The player has a name, an inventory for the items collected, a weight limit and keeps track of the 
 *
 * @author Jawhara Jannah
 * @version 1.0
 */
public class Player
{
    private String name;                                            // The player's name
    private Room currentRoom;                                       // The room the player is currently in                                        
    private Room previousRoom;                                      // The room the player was previously in
    private int currentWeight;                                      // Current weight of the items the player is carrying
    private int maxWeight;                                          // The maximum weight the player can carry
    private int jewelsCollected;                                    // The number of jewels the player has collected 
    private HashMap<String, Item> inventory;                        // Stores items in the inventory
    private ArrayList<Item> requiredJewels = new ArrayList<>();     // List of jewels that are required to win
    
    /**
     * Constructor to initialize the player with a name and the maximum carrying weight
     * 
     * @param name The name of the player
     * @param maxCarryWeight The maximum weight the player can carry
     */
    public Player(String name, int maxCarryWeight)
    {
        this.name = name;
        this.currentWeight = 0;
        this.maxWeight = maxCarryWeight;
        this.jewelsCollected = 0;
        this.inventory = new HashMap<>(); 
    }
    
    /**
     * @return The name of the player
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Sets the current room of the player
     * 
     * *param room The room to set the player's current location
     */
    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    /**
     * @return The room the player is currently in
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
    
    /**
     * @return The maximum carry weight
     */
    public int getMaxWeight() {
        return maxWeight;
    }

    /**
     * @return The total weight of the items in the inventory
     */
    public int getCurrentWeight() {
        return currentWeight;
    }

    /**
     * Adds an item to the player's inventor if it does not exceed the maximum carry weight
     * Otherwise prints out an error message
     * 
     * @param item The item to be added
     * @ return true if the item was added, else false
     */
    public boolean addItem(Item item) 
    {
        if (inventory.containsKey(item.getName())) {
            System.out.println("\nYou already have " + item.getName() + " in your inventory.\n");
            return false;
        }
    
        if (currentWeight + item.getWeight() > maxWeight) {
            System.out.println("\nYou can't carry that much weight!\n");
            return false;
        }
    
        inventory.put(item.getName(), item); 
        currentWeight += item.getWeight();  
        return true;
    }

    /**
     * Removes an item form the player's inventory
     * 
     * @param  itemName The name of the item to be removed
     * @return true if the item was removed, else false
     */
    public boolean removeItem(String itemName) {
        Item item = inventory.remove(itemName);
        if (item != null) {
            currentWeight -= item.getWeight();
            return true;
        }
        return false;
    }
    
    /**
     * Gets an item from the player's inventory
     * 
     * @param itemName The name of the item get
     * @return The itm if found
     */
    public Item getItem(String itemName) {
        return inventory.get(itemName); 
    }
    
    /**
     * Gets the player's inventory
     * 
     * @return A HashMap containing all the items in the player's inventory
     */
    public HashMap<String, Item> getInventory() {
        return inventory; 
    }
    
    /**
     * Prints the items in the player's inventory
     */
    public void printInventory() 
    {
        if (inventory.isEmpty()) {
            System.out.println("\nYour inventory is empty.\n");
        } else {
            System.out.println("\nYou are carrying:");
            for (Item item : inventory.values()) {
                System.out.println("- " + item.getName() + ": " + item.getDescription());
            }
            System.out.println("Total weight: " + currentWeight + "/" + maxWeight + ".\n");
        }
    }
    
    /**
     * Checks if the player has collected all the required jewels in their inventory 
     * 
     * @return true if all require jewels are in the player's inventory
     */
    public boolean hasAllJewels() 
    {
        return inventory.keySet().containsAll(requiredJewels);
    }
}

