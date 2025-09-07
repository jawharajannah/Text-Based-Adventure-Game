/**
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @authot Jawhara Jannah
 * @version 2.0
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = 
    {"go", "quit", "help", "take", "drop", "inventory", "back"  , "give"};

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        aString = aString.toLowerCase();    // Convert input to lowercase for case-insensitive comparison
        
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll() 
    {
        for(String command: validCommands) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
    
}
