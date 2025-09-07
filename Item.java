
/**
 * The Item class represents an obkect that exists in teh game's world
 * Items have properties such as name, description, weight and whetehr they can be picked up by the player.
 * 
 *
 * @author Jawhara Jannah
 * @version 1.0
 */
public class Item
{
    private String name;            // The name of the item
    private String description;     // A description of the item
    private int weight;             // The weight of the item
    private boolean isPickable;     // Whether the item can be picked up by the player.
    
    /**
     * Constructor to initalize the item's properties
     * 
     * @param name The name of the item.
     * @param description A  description of the item.
     * @param weight The weight of the item.
     * @param isPickable Whether the item can be picked up by the player.
     */
    public Item(String name, String description, int weight, boolean isPickable)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.isPickable = isPickable;
    }

    /**
     *  @return The name of the item.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @return The description of the item.
     */
    public String getDescription()
    {
        return description;
    }
     
    /**
     * @return The weight of the item.
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * @return true if the item can be picked up, else false
     */
    public boolean isPickable() 
    {
        return isPickable;
    }
}
