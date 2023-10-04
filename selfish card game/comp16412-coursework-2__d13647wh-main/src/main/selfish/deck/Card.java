package selfish.deck;
import java.io.Serializable;
import java.lang.Comparable;
/** text
 * @author will
 * @version 1
*/
public class Card implements Serializable, Comparable<Card> {
    
    private String name;
    private String description;
    private static final long serialVersionUID = 1L;
    /**
     * A helper function to centre text in a longer String.
     * @param name The length of the return String.
     * @param description The text to centre.
     */
    public Card(String name, String description) {
        this.name = name;
        this.description = description;
    }
    /**
     * A helper function to centre text in a longer String.
     * @return A longer string with the specified text centred.
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * A helper function to centre text in a longer String.
     * @return A longer string with the specified text centred.
     */
    public String toString() {
        return this.name;
    }
    /**
     * A helper function to centre text in a longer String.
     * @param card The length of the return String.
     * @return A longer string with the specified text centred.
     */
    @Override
    public int compareTo(Card card) {
        return this.name.compareTo(card.toString());
    }

}