import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a non-playable character (NPC) in the game.
 * It holds information such as the character's name, description, dialogue and current room.
 * 
 *
 * @author Jawhara Jannah
 * @version 1.0
 */
public class Character {
    private String name;            //The name of the character
    private String description;     // A short description of the character
    private String dialogue;        // Dialogue that the character says
    private Room currentRoom;        // The room where the charcter is currently

    /**
     * Constructor to initialize the character with a name, description and dialogue
     * 
     * @param name The character's name.
     * @param description A short description of the character.
     * @param dialogue The character's dialogue.
     */
    public Character(String name, String description, String dialogue) 
    {
        this.name = name;                                
        this.description = description;     
        this.dialogue = dialogue;           
        this.currentRoom = null;            // initially there is no assigned room         
    }

    /**
     * @return The name of the character
     */
    public String getName() 
    {
        return name;
    }

    /**
     * @return The description of the character.
     */
    public String getDescription() 
    {
        return description;
    }

    /**
     * @return The dialogue that the character will say.
     */
    public String getDialogue() 
    {
        return dialogue;
    }

    /**
     * @return The room where the character is currently located.
     */
    public Room getCurrentRoom() 
    {
        return currentRoom;
    }

    /**
     * Set the room where the character is located
     * 
     * @param room The room where the character should be placed
     */
    public void setCurrentRoom(Room room) 
    {
        this.currentRoom = room;
    }

    /**
     * Set a new decription for the character
     * 
     * @param description The new description of the character
     */
    public void setDescription(String description) 
    {
        this.description = description;
    }

    /**
     * Set a new dialogue for the character
     * 
     * @param dialogue The new dialogue of the character
     */
    public void setDialogue(String dialogue) 
    {
        this.dialogue = dialogue;
    }
    
    /**
     * Update both the character's dialogue and description
     * 
     * @param newDialogue The new dialogue 
     * @param newDescription The new description 
     */
    public void updateDialogueAndDescription(String newDialogue, String newDescription) 
    {
        this.dialogue = newDialogue;
        this.description = newDescription;
    }
}



