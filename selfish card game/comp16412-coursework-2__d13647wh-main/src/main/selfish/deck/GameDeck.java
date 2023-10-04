package selfish.deck;
import java.util.List;

import selfish.GameException;

import java.lang.IllegalStateException;
import java.util.Collections;
import java.util.ArrayList;
/** text
 * @author will
 * @version 1
*/
public class GameDeck extends Deck {

    /** The session that will be used. */
    public static final String HACK_SUIT = "Hack suit";
    /** The session that will be used. */
    public static final String HOLE_IN_SUIT = "Hole in suit";
    /** The session that will be used. */
    public static final String LASER_BLAST = "Laser blast";
    /** The session that will be used. */
    public static final String OXYGEN = "Oxygen";
    /** The session that will be used. */
    public static final String OXYGEN_1 = "Oxygen(1)";
    /** The session that will be used. */
    public static final String OXYGEN_2 = "Oxygen(2)";
    /** The session that will be used. */
    public static final String OXYGEN_SIPHON = "Oxygen siphon";
    /** The session that will be used. */
    public static final String ROCKET_BOOSTER = "Rocket booster";
    /** The session that will be used. */
    public static final String SHIELD = "Shield";
    /** The session that will be used. */
    public static final String TETHER = "Tether";
    /** The session that will be used. */
    public static final String TRACTOR_BEAM = "Tractor beam";
    /** The session that will be used. */
    private static final long serialVersionUID = 1L;

    /** text*/
    public GameDeck() {
        super();
    }
    /**
     * A helper function to centre text in a longer String.
     * @param path The length of the return String.
     * @throws GameException text
     */
    public GameDeck(String path) throws GameException {
        super();
        List<Card> cards_as_list = (List<Card>) Deck.loadCards(path);
        this.add(cards_as_list);
        for (int i=0; i<10; i++) {this.add(new Oxygen(2));}
        for (int i=0; i<38; i++) {this.add(new Oxygen(1));}
    }
    /**
     * A helper function to centre text in a longer String.
     * @param value The length of the return String.
     * @return A longer string with the specified text centred.
     */
    public Oxygen drawOxygen(int value) {
        ArrayList<Card> cards_drawn = new ArrayList<Card>();
        boolean run = true;
        while (run) {
            Card card_drawn = this.draw();
            if (card_drawn instanceof Oxygen) {
                Oxygen oxygen_card = (Oxygen) card_drawn;
                if (oxygen_card.getValue() == value) {
                    Collections.reverse(cards_drawn);
                    this.add(cards_drawn);
                    return oxygen_card;
                }
            }
            cards_drawn.add(card_drawn);
            if (this.size() == 0) {
                Collections.reverse(cards_drawn);
                this.add(cards_drawn);
                run = false;
            }
        }
        throw new IllegalStateException();
    }
        /**
     * A helper function to centre text in a longer String.
     * @param dbl The length of the return String.
     * @return A longer string with the specified text centred.
     */
    public Oxygen[] splitOxygen(Oxygen dbl) {
        if (dbl.getValue() == 1) {
            throw new IllegalArgumentException();
        }
        this.add(dbl);
        Oxygen[] oxygens = new Oxygen[2];
        oxygens[1] = drawOxygen(1);
        oxygens[0] = drawOxygen(1);
        return oxygens;
    }

}