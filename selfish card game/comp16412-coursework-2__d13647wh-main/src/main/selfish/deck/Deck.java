package selfish.deck;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.List;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import selfish.GameException;
/** text
 * @author will
 * @version 1
*/
public abstract class Deck implements Serializable {

    private Collection<Card> cards;
    private static final long serialVersionUID = 1L;
    /**
     * A helper function to centre text in a longer String.
     */
    protected Deck() {
        this.cards = new ArrayList<Card>();
    }
    /**
     * A helper function to centre text in a longer String.
     * @param path The length of the return String.
     * @return A longer string with the specified text centred.
     * @throws GameException text
     */
    protected static List<Card> loadCards(String path) throws GameException {
        ArrayList<Card> cards_as_list = new ArrayList<Card>();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                Card[] card_set = Deck.stringToCards(scanner.nextLine());
                for (int i=0; i<card_set.length; i++) {
                    cards_as_list.add(card_set[i]);
                }
            }
            scanner.close();
            return cards_as_list;
        } catch (FileNotFoundException e) {
            throw new GameException("cannot find file", e);
        }
    }
    /**
     * A helper function to centre text in a longer String.
     * @param str The length of the return String.
     * @return A longer string with the specified text centred.
     */
    protected static Card[] stringToCards(String str) {
        String[] card_set_data = str.split(";");
        card_set_data[1] = card_set_data[1].substring(1);
        card_set_data[2] = card_set_data[2].substring(1); //remove starting whitespace
        Integer card_set_quant = Integer.parseInt(card_set_data[2]);
        Card[] cards_as_array = new Card[card_set_quant];
        for (int i=0; i<card_set_quant; i++) {
            cards_as_array[i] = new Card(card_set_data[0],card_set_data[1]);
        }
        return cards_as_array;
    }
    /**
     * A helper function to centre text in a longer String.
     * @param card The length of the return String.
     * @return A longer string with the specified text centred.
     */
    public int add(Card card) {
        this.cards.add(card);
        return this.cards.size();
    }
        /**
     * A helper function to centre text in a longer String.
     * @param cards The length of the return String.
     * @return A longer string with the specified text centred.
     */
    protected int add(List<Card> cards) {
        for (int i=0; i<cards.size(); i++) {this.cards.add(cards.get(i));}
        return this.cards.size();
    }
    /**
     * A helper function to centre text in a longer String.
     * @return A longer string with the specified text centred.
     */
    public Card draw() {
        if (this.cards.size() == 0) {
            throw new IllegalStateException();
        }
        List<Card> cards_as_list = (List<Card>) this.cards;
        return cards_as_list.remove(cards_as_list.size()-1);
    }
    /**
     * A helper function to centre text in a longer String.
     * @param card The length of the return String.
     */
    public void remove(Card card) {
        List<Card> cards_as_list = (List<Card>) this.cards;
        cards_as_list.remove(card);
    }
    /**
     * A helper function to centre text in a longer String.
     * @param random The length of the return String.
     */
    public void shuffle(Random random) {
        List<Card> cards_as_list = (List<Card>) this.cards;
        Collections.shuffle(cards_as_list, random);
    }
    /**
     * A helper function to centre text in a longer String.
     * @return A longer string with the specified text centred.
     */
    public int size() {
        return this.cards.size();
   }

}