import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

/**
 * The "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * Each room has a name, a description, and exits, There is also methods to manage the 
 * items and charcters in the room
 * 
 * @author  Michael Kölling and David J. Barnes
 * @author Jawhara Jannah
 * @version 2.0
 */

public class Room 
{
    private String description;                 // The description of the room                
    private String name;                        // The name of the room
    private HashMap<String, Room> exits;        // Stores exits of this room.
    private HashMap<String, Item> items;        // Stores items
    private Character character;                // A charcter in the room
    private Set<Character> characters;          // A set of all characters in the room
    
    /**
     * Creates a room described "description". 
     * 
     * @param name The name of the room
     * @param description The room's description.
     */
    public Room(String name, String description) 
    {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new HashMap<>();

    }
    
    /**
     * @return The naem of the room
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @return The short description of the room
     */
    public String getShortDescription()
    {
        return description;
    }
    
    /**
     * Return a description of the room
     * 
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return this.description + ".\n" + getItemDescriptions();
    }
    
    /**
     * Define an exit from this room.
     * 
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * 
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) {
        String normalizedDirection = direction.toLowerCase();
        return exits.get(normalizedDirection);
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * 
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
    
    /**
     * Add an item to the room
     * 
     * @param itemName The name of the item to add.
     */
    public void addItem(Item item) 
    {
        items.put(item.getName().toLowerCase(), item);
    }

    /**
     * Remove an item from the room
     * 
     * @param itemName The name of the item to remove.
     */
    public Item removeItem(String itemName) 
    {
        return items.remove(itemName.toLowerCase());
    }
   
    /**
     * Get an item from the room
     * 
     * @param itemName The name of the item to get
     * @return The item if found, or null if not found
     */
    public Item getItem(String itemName) {
        for (Item item : items.values()) {
            if (item.getName().equalsIgnoreCase(itemName)) { 
                return item;
            }
        }
        return null; 
    }
       
    /**
     *  Get all items in the room
     *  
     *  @return A map of items in the room
     */
    public HashMap<String, Item> getItems() 
    {
        return items; 
    }
    
    /**
     * A description of all items in the room
     * Returns a message if the room is empty
     * 
     * @return A description of the room's items 
     */
    public String getItemDescriptions() {
        if (items.isEmpty()) {
            return "\nThis room is empty.\n";
        }
        StringBuilder descriptions = new StringBuilder("\nItems\n");
        for (Item item : items.values()) {
            descriptions.append("- ").append(item.getName()).append(": ").append(item.getDescription()).append("\n");
        }
        return descriptions.toString();
    }

    /**
     * Set a character in the room.
     * 
     * @param character The charcter to set
     */
    public void setCharacter(Character character) 
    {
        this.character = character;
    }
    
    /**
     * Add a character in the room.
     * 
     * @param character The charcter to be added
     */
    public void addCharacter(Character character) {
        characters.add(character);
    }
    
    /**
     * Get the character in the room.
     * 
     * @param character The character to remove
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Get all rooms that are connected to the room via exits.
     * 
     * @return A list of all connecting rooms.
     */
    public ArrayList<Room> getAllExits() 
    {
        return new ArrayList<>(exits.values()); 
    }   
}

