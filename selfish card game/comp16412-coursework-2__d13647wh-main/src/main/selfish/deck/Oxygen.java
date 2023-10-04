package selfish.deck;
/** text
 * @author will
 * @version 1
*/
public class Oxygen extends Card {    
    
    private int value;
    private static final long serialVersionUID = 1L;
    /**
     * A helper function to centre text in a longer String.
     * @param value The length of the return String.
     */
    public Oxygen(int value) {
        super("Oxygen","this is an oxygen card");
        this.value = value;
    }
    /**
     * A helper function to centre text in a longer String.
     * @return A longer string with the specified text centred.
     */
    public int getValue() {
        return this.value;
    }
    /**
     * A helper function to centre text in a longer String.
     * @return A longer string with the specified text centred.
     */
    public String toString() {
        return "Oxygen(" + this.value + ")";
    }
///////overrides
    /**
     * A helper function to centre text in a longer String.
     * @param oxygen text
     * @return A longer string with the specified text centred.
     */
    public int compareTo(Oxygen oxygen) {
        Integer value = (Integer) this.value;
        return value.compareTo(oxygen.getValue());
    }
    /**
     * A helper function to centre text in a longer String.
     * @param card text
     * @return A longer string with the specified text centred.
     */
    @Override
    public int compareTo(Card card) {
        return this.toString().compareTo(card.toString());
    }

}