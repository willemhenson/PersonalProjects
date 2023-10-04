package selfish.deck;
import java.util.List;

import selfish.GameException;
/** text
 * @author will
 * @version 1
*/
public class SpaceDeck extends Deck {
    
    /** text*/
    public static final String ASTEROID_FIELD = "Asteroid field";
    /** text*/
    public static final String BLANK_SPACE = "Blank space";
    /** text*/
    public static final String COSMIC_RADIATION = "Cosmic radiation";
    /** text*/
    public static final String GRAVITATIONAL_ANOMALY = "Gravitational anomaly";
    /** text*/
    public static final String HYPERSPACE = "Hyperspace";
    /** text*/
    public static final String METEOROID = "Meteoroid";
    /** text*/
    public static final String MYSTERIOUS_NEBULA = "Mysterious nebula";
    /** text*/
    public static final String SOLAR_FLARE = "Solar flare";
    /** text*/
    public static final String USEFUL_JUNK = "Useful junk";
    /** text*/
    public static final String WORMHOLE = "Wormhole";
    /** text*/
    private static final long serialVersionUID = 1L;
    /** text*/
    public SpaceDeck() {
        super();
    }
    /**
     * A helper function to centre text in a longer String.
     * @param path The length of the return String.
     * @throws GameException text
     */
    public SpaceDeck(String path) throws GameException {
        super();
        List<Card> cards_as_list = (List<Card>) Deck.loadCards(path);
        this.add(cards_as_list);
    }

}