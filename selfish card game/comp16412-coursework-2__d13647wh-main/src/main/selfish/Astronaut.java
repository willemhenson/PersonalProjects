package selfish;
import selfish.deck.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
/** text
 * @author will
 * @version 1
*/
public class Astronaut  implements Serializable {

    private GameEngine game;
    private List<Card> actions;
    private List<Oxygen> oxygens;
    private String name;
    private Collection<Card> track;
    /** text */
    private static final long serialVersionUID = 1L;
    /** text
     * @param name text
     * @param game text
     */
    public Astronaut(String name, GameEngine game) {
        this.name = name;
        this.game = game;
        this.actions = new ArrayList<Card>();
        this.oxygens = new ArrayList<Oxygen>();
        this.track = new ArrayList<Card>();
    }
    /** text
     * @param card card
     */
    public void addToHand(Card card) {
        if (card instanceof Oxygen) {this.oxygens.add((Oxygen) card);}
        else {this.actions.add(card);}
    }
    /** text
     * @param card text
     */
    public void addToTrack(Card card) {
        this.track.add(card);
    }
    /** text
     * @return int
     */
    public int breathe() {
        if (this.isAlive()) {
            for (int i=0; i<this.oxygens.size(); i++) {
                if (this.oxygens.get(i).getValue() == 1) {
                    Oxygen oxygen_card = this.oxygens.remove(i);
                    this.game.getGameDiscard().add(oxygen_card);
                    if (this.oxygenRemaining() == 0) {
                        this.game.killPlayer(this);
                    }
                    return oxygenRemaining();
                }
            }
            // is alive and doesnt have any single so must have at least one dbl with rest dbl as well
            Oxygen dbl = this.oxygens.remove(0);
            ///////////////////// may need to implement for checking game dsicard also
            Oxygen[] oxygens = this.game.getGameDeck().splitOxygen(dbl);
            ///////////////////////
            this.game.getGameDiscard().add(oxygens[0]);
            this.addToHand(oxygens[1]);
            return oxygenRemaining();
        }
        throw new IllegalStateException();
    }
    /** text
     * @return int
     */
    public int distanceFromShip() {
        return 6-this.track.size();
    }
    /** text
     * @return list
     */
    public List<Card> getActions() {
        Collections.sort(this.actions);
        return this.actions;
    }
    /** text
     * @param enumerated text
     * @param excludeShields text
     * @return text
     */
    public String getActionsStr(boolean enumerated, boolean excludeShields) {
        if (this.actions.size() == 0) {
            return "";
        }
        String string = "";
        if (enumerated) {
            ArrayList<String> cards = new ArrayList<String>();
            for (int i=0; i<this.getActions().size(); i++) {
                boolean present_in_cards = false;
                for (int j=0; j<cards.size(); j++) {
                    if (this.getActions().get(i).toString().equals(cards.get(j))) {
                        present_in_cards = true;
                        break;
                    }
                }
                if (present_in_cards == false) {
                    cards.add(this.getActions().get(i).toString());
                }
            }
            int offset = 0;
            for (int i=0; i<cards.size(); i++) {
                if (excludeShields && cards.get(i).equals("Shield")){offset+=1;}
                else {
                    string += "[" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(i-offset) + "] " + cards.get(i) + ", ";
                }
            }
            return string.substring(0, string.length()-2);
        }
        else {
            ArrayList<String> cards = new ArrayList<String>();
            int[] times_appears = new int[100];
            for (int i=0; i<this.getActions().size(); i++) {
                boolean present_in_cards = false;
                int index = 0;
                for (int j=0; j<cards.size(); j++) {
                    if (this.getActions().get(i).toString().equals(cards.get(j))) {
                        present_in_cards = true;
                        index = cards.indexOf(this.getActions().get(i).toString());
                        break;
                    }
                }
                if (present_in_cards) {
                    times_appears[index]+=1;
                }
                else {
                    cards.add(this.getActions().get(i).toString());
                    int cur_index = 0;
                    while (times_appears[cur_index] != 0) {
                        cur_index+=1;
                    }
                    times_appears[cur_index] = 1;
                }
            }
            for (int i=0; i<cards.size(); i++) {
                if (excludeShields && cards.get(i).equals("Shield")){}
                else {
                    if (times_appears[i] != 1) {
                        string += times_appears[i] + "x ";
                    }
                    string += cards.get(i) + ", ";
                }
            }
            return string.substring(0, string.length()-2);
        }
    }
    

















    /** text
     * @return text
     */
    public List<Card> getHand() {
        ArrayList<Card> hand_as_list = new ArrayList<Card>();
        hand_as_list.addAll(this.oxygens);
        hand_as_list.addAll(this.actions);
        Collections.sort(hand_as_list);
        return hand_as_list;
    }




























    /** text
     * @return text
     */ 
    public String getHandStr() {
        if (this.isAlive() == false) {
            return "dead astro have no cards";
        }
        String string = "";
        Collections.sort(this.actions);
        Collections.sort(this.oxygens);
        Collections.reverse(this.oxygens);
        ArrayList<String> added_card_names = new ArrayList<String>();
        for (int i=0; i<this.oxygens.size(); i++) {
            Card current_card = this.oxygens.get(i);
            boolean already_added = false;
            for (int j=0; j<added_card_names.size(); j++) {if (added_card_names.get(j).toString().equals(current_card.toString())) {already_added = true;}}
            if (already_added == false) {
                int times_appears = this.hasCard(current_card.toString());
                if (times_appears == 1) {string += current_card.toString() + ", ";}
                else {string += times_appears + "x " + current_card.toString() + ", ";}
                added_card_names.add(current_card.toString());
            }
        }
        string = string.substring(0, string.length()-2);//removes final comma and space
        string += "; ";
        for (int i=0; i<this.actions.size(); i++) {
            Card current_card = this.actions.get(i);
            boolean already_added = false;
            for (int j=0; j<added_card_names.size(); j++) {if (added_card_names.get(j).toString().equals(current_card.toString())) {already_added = true;}}
            if (already_added == false) {
                int times_appears = this.hasCard(current_card.toString());
                if (times_appears == 1) {string += current_card.toString() + ", ";}
                else {string += times_appears + "x " + current_card.toString() + ", ";}
                added_card_names.add(current_card.toString());
            }
        }
        string = string.substring(0, string.length()-2);//removes final comma and space
        return string;
    }
    
















    /** text
     * @return text
     */
    public Collection<Card> getTrack() {
        return this.track;
    }
    /** text
     * @param card text
     */
    public void hack(Card card) {
        boolean removed = false;
        if (card instanceof Oxygen) {
            removed = this.oxygens.remove(card);
            if (this.oxygens.size() == 0) {
                this.game.killPlayer(this);
                int size = this.actions.size();
                for (int j=0; j<size; j++) {
                    Card card_to_retire = this.actions.get(0);
                    this.game.getGameDiscard().add(card_to_retire);
                    this.actions.remove(card_to_retire);
                }
            }
        }
        if (card instanceof Card && removed == false) {
            removed = this.actions.remove(card);
        }
        if (removed == false) { 
            throw new IllegalArgumentException();
        }
    }
    /** text
     * @param card text
     * @return text
     */
    public Card hack(String card) {
        if (this.hasCard(card) == 0) {
            throw new IllegalArgumentException();
        }
        for (int i=0; i<this.oxygens.size(); i++) {
            if (this.oxygens.get(i).toString().equals(card)) {
                Card to_return = this.oxygens.get(i);
                this.oxygens.remove(i);
                if (this.oxygens.size() == 0) {
                    this.game.killPlayer(this);
                    int size = this.actions.size();
                    for (int j=0; j<size; j++) {
                        Card card_to_retire = this.actions.get(0);
                        this.game.getGameDiscard().add(card_to_retire);
                        this.actions.remove(card_to_retire);
                    }
                }
                return to_return;
            }
        }
        for (int i=0; i<this.actions.size(); i++) {
            if (this.actions.get(i).toString().equals(card)) {
                Card to_return = this.actions.get(i);
                this.actions.remove(i);
                return to_return;
            }
        }
        throw new IllegalArgumentException();
    }
    /** text
     * @param card text
     * @return text
     */
    public int hasCard(String card) {
        int num = 0;
        for (int i=0; i<this.oxygens.size(); i++) {if (this.oxygens.get(i).toString().equals(card)) {num += 1;}}
        for (int i=0; i<this.actions.size(); i++) {if (this.actions.get(i).toString().equals(card)) {num += 1;}}
        return num;
    }
    /** text
     * @return text
     */
    public boolean hasMeltedEyeballs() {
        List<Card> track_as_list = (List<Card>) this.track;
        if (track_as_list.get(track_as_list.size()-1).toString().equals("Solar flare")) {return true;}
        return false;
    }
    /** text
     * @return text
     */
    public boolean hasWon() {
        if (this.distanceFromShip() == 0 && this.isAlive()) {return true;}
        return false;
    }
    /** text
     * @return text
     */
    public boolean isAlive() {
        if (this.oxygenRemaining() >= 1) {return true;}
        return false;
    }
    /** text
     * @return text
     */
    public Card laserBlast() {
        if (this.track.size() == 0) {throw new IllegalArgumentException();}
        List<Card> track_as_list = (List<Card>) this.track;
        return track_as_list.remove(track_as_list.size()-1);
    }
    /** text
     * @return text
     */
    public int oxygenRemaining() {
        int remaining = 0;
        for (int i=0; i<this.oxygens.size(); i++) {remaining += this.oxygens.get(i).getValue();}
        return remaining;
    }
    /** text
     * @return text
     */
    public Card peekAtTrack() {
        if (this.track.size() == 0 ) {return null;}
        List<Card> track_as_list = (List<Card>) this.track;
        return track_as_list.get(track_as_list.size()-1);
    }
    /** text
     * @return text
     */
    public Oxygen siphon() {
        for (int i=0; i<this.oxygens.size(); i++) {
            if (this.oxygens.get(i).getValue() == 1) {
                Oxygen oxygen = this.oxygens.remove(i);
                if (this.oxygenRemaining() == 0) {
                    this.game.killPlayer(this);
                }
                return oxygen;
            }
        }
        //// same logic as breathe, must have at least one dbl and all be dbl to be alive and have no singles
        Oxygen dbl = this.oxygens.remove(0);
        ///////////////////// may need to implement for checking game dsicard also
        Oxygen[] oxygens = this.game.getGameDeck().splitOxygen(dbl);
        ///////////////////////
        this.addToHand(oxygens[1]);
        return oxygens[0];
    }
    /** text
     * @return text
     */
    public Card steal() {
        Random random = new Random();
        int min = 0;
        int max = this.getHand().size()-1;
        int index_to_steal = random.nextInt(max - min + 1) + min;
        Card card_to_be_stolen = this.getHand().remove(index_to_steal);
        if (card_to_be_stolen instanceof Oxygen) {
            this.oxygens.remove(card_to_be_stolen);
            if (this.oxygenRemaining() == 0) {
                this.game.killPlayer(this);
            }
            return card_to_be_stolen;
        }
        else {
            this.actions.remove(card_to_be_stolen);
            return card_to_be_stolen;
        }
    }
    /** text
     * @param swapee text
     */
    public void swapTrack(Astronaut swapee) {
        List<Card> track_in = (List<Card>) swapee.getTrack();
        List<Card> track_out = (List<Card>) this.getTrack();
        swapee.track = track_out;
        this.track = track_in;
    }
    /** text
     * @return text
     */
    public String toString() {
        if (this.isAlive()) {return this.name;}
        return this.name + " (is dead)";
    }

}