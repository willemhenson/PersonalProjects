package selfish;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import selfish.deck.*;

import java.lang.IllegalStateException;
import java.lang.reflect.Array;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/** text text
 * @author will
 * @version 1
*/
public class GameEngine implements Serializable {

    private Collection<Astronaut> activePlayers;
    private List<Astronaut> corpses;
    private Astronaut currentPlayer;
    private boolean hasStarted;
    private Random random;
    private GameDeck gameDeck;
    private GameDeck gameDiscard;
    private SpaceDeck spaceDeck;
    private SpaceDeck spaceDiscard;
    private static final long serialVersionUID = 1L;


    /** text  text*/
    private GameEngine() {}
    /** text  text
     * @param seed text
     * @param gameDeck text
     * @param spaceDeck text
     * @throws GameException text
     */
    public GameEngine(long seed, String gameDeck, String spaceDeck) throws GameException {
        this.random = new Random(seed);
        this.gameDeck = new GameDeck(gameDeck);
        this.spaceDeck = new SpaceDeck(spaceDeck);
        this.gameDiscard = new GameDeck();
        this.spaceDiscard = new SpaceDeck();
        this.gameDeck.shuffle(this.random);
        this.spaceDeck.shuffle(this.random);
        this.activePlayers = new ArrayList<Astronaut>();
        this.corpses = new ArrayList<Astronaut>();
        this.currentPlayer = null;
        this.hasStarted = false;
    }
    /** text  text
     * @param player text
     * @return text
     */
    public int addPlayer(String player) {
        if (this.hasStarted == true) {
            throw new IllegalStateException();
        }
        if (this.activePlayers.size() == 5) {
            throw new IllegalStateException();
        }
        this.activePlayers.add(new Astronaut(player, this));
        return activePlayers.size();
    }
    /** text  text
     * @return text
     */
    public int endTurn() {
        if (this.currentPlayer.isAlive()) {this.activePlayers.add(currentPlayer);}
        this.currentPlayer = null;
        return activePlayers.size();
    }
    /** text
     * @return text
     */
    public boolean gameOver() {
        ArrayList<Astronaut> all_players_as_list = (ArrayList<Astronaut>) this.getAllPlayers();
        for (int i=0; i<all_players_as_list.size(); i++) {
            if (all_players_as_list.get(i).hasWon()) return true;
        }
        if (this.corpses.size() == this.getFullPlayerCount()) return true;
        return false;
    }
    /** text
     * @return text
     */
    public List<Astronaut> getAllPlayers() {
        ArrayList<Astronaut> all_players_as_list = new ArrayList<Astronaut>();
        if (this.currentPlayer != null) {
            boolean curr_player_is_dead = false;
            for (int i=0; i<this.corpses.size(); i++) {
                if (this.corpses.get(i) == this.currentPlayer) {
                    curr_player_is_dead = true;
                }
            }
            if (curr_player_is_dead == false) {all_players_as_list.add(this.currentPlayer);}
        }
        all_players_as_list.addAll(this.activePlayers);
        all_players_as_list.addAll(this.corpses);
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Astronaut> sorted = new ArrayList<Astronaut>();
        for (int i=0; i<all_players_as_list.size(); i++) {names.add(all_players_as_list.get(i).toString());}
        Collections.sort(names);
        for (int i=0; i<all_players_as_list.size(); i++) {
            for (int j=0; j<names.size(); j++) {
                if (names.get(j).toString().equals(all_players_as_list.get(i).toString())) {
                    sorted.add(all_players_as_list.get(i));
                    break;
                }
            }
        }
        return sorted;
    }
    /** text
     * @return text
     */
    public Astronaut getCurrentPlayer() {
        return this.currentPlayer;
    }
     /** text
     * @return text
     */ 
    public int getFullPlayerCount() {
        return this.getAllPlayers().size();
    }
    /** text
     * @return text
     */
    public GameDeck getGameDeck() {return this.gameDeck;}
        
    /** text
     * @return text
     */
    public GameDeck getGameDiscard() {return this.gameDiscard;}
    /** text
     * @return text
     */
    public SpaceDeck getSpaceDeck() {return this.spaceDeck;}
    /** text
     * @return text
     */
    public SpaceDeck getSpaceDiscard() {return this.spaceDiscard;}
    /** text
     * @return text
     */
    public Astronaut getWinner() {
        ArrayList<Astronaut> all_players_as_list = (ArrayList<Astronaut>) this.getAllPlayers();
        for (int i=0; i<all_players_as_list.size(); i++) {
            if (all_players_as_list.get(i).hasWon()) {
                return all_players_as_list.get(i);
            }
        }
        return null;
    }
    /** text
     * @param corpse text
     */
    public void killPlayer(Astronaut corpse) {
        ///////////// add dead player hand to discard
        this.corpses.add(corpse);
    }
    /** text
     * @param path text
     * @return text
     * @throws GameException ge
     */
    public static GameEngine loadState(String path) throws GameException {
        try {
            FileInputStream file_in = new FileInputStream(path);
            ObjectInputStream obj_in = new ObjectInputStream(file_in);
            GameEngine GE_new = (GameEngine) obj_in.readObject();
            obj_in.close();
            return GE_new;
        } catch (Exception e) {
            throw new GameException("cannot open file", e);
        }
    }
    /** text
     * @param path text
     */
    public void saveState(String path) {
        try {

            FileOutputStream file_out = new FileOutputStream(path);
            ObjectOutputStream obj_out = new ObjectOutputStream(file_out);
            obj_out.writeObject(this);
            obj_out.close();
        } catch (Exception e) {
            
        }
    }
    /** text
     * @param deck1 text
     * @param deck2 text
     */    
    public void mergeDecks(Deck deck1, Deck deck2) {
        ArrayList<Card> cards = new ArrayList<Card>();
        int num_deck1 = deck1.size();
        for (int i=0; i<num_deck1; i++) {
            cards.add(deck1.draw());
        }
        int num_deck2 = deck2.size();
        for (int i=0; i<num_deck2; i++) {
            cards.add(deck2.draw());
        }
        //shuffle cards array
        Collections.shuffle(cards,this.random);
        //insert back into deck1
        for (int i=0; i<num_deck2+num_deck1; i++) {
            deck1.add(cards.get(i));
        }
    }
    /** text
     * @param dbl text
     * @return text
     */   
    public Oxygen[] splitOxygen(Oxygen dbl) {
        if (dbl.getValue() == 1) {
            throw new IllegalArgumentException();
        }
        this.getGameDeck().add(dbl);
        ArrayList<Oxygen> oxygens_list = new ArrayList<Oxygen>();
        try {
            oxygens_list.add(this.getGameDeck().drawOxygen(1));
            oxygens_list.add(this.getGameDeck().drawOxygen(1));
        } catch (Exception e) {
            while (oxygens_list.size() < 2) {
                oxygens_list.add(this.getGameDiscard().drawOxygen(1));
            }
        }
        Oxygen[] oxygens = new Oxygen[2];
        oxygens[0] = oxygens_list.get(0);
        oxygens[1] = oxygens_list.get(1);
        return oxygens;
    }
    /** text
     */   
    public void startGame() {
        if (this.activePlayers.size() < 2 || this.hasStarted == true || this.activePlayers.size() > 5) {
            throw new IllegalStateException();
        }
        this.hasStarted = true;
        ArrayList<Astronaut> players_as_list = (ArrayList<Astronaut>) this.activePlayers;
        for (int i=0; i<players_as_list.size(); i++) {
            players_as_list.get(i).addToHand(this.gameDeck.drawOxygen(2));
            players_as_list.get(i).addToHand(this.gameDeck.drawOxygen(1));
            players_as_list.get(i).addToHand(this.gameDeck.drawOxygen(1));
            players_as_list.get(i).addToHand(this.gameDeck.drawOxygen(1));
            players_as_list.get(i).addToHand(this.gameDeck.drawOxygen(1));}
        for (int i=0; i<players_as_list.size(); i++) {players_as_list.get(i).addToHand(this.gameDeck.draw());}
        for (int i=0; i<players_as_list.size(); i++) {players_as_list.get(i).addToHand(this.gameDeck.draw());}
        for (int i=0; i<players_as_list.size(); i++) {players_as_list.get(i).addToHand(this.gameDeck.draw());}
        for (int i=0; i<players_as_list.size(); i++) {players_as_list.get(i).addToHand(this.gameDeck.draw());}
    }
    /** text
     */   
    public void startTurn() {
        if (this.activePlayers.size() == 0 || this.hasStarted == false || this.currentPlayer != null) {
            throw new IllegalStateException();
        }
        ArrayList<Astronaut> players_as_list = (ArrayList<Astronaut>) this.activePlayers;
        for (int i=0; i<players_as_list.size(); i++) {
            if (players_as_list.get(i).hasWon() == true) {
                throw new IllegalStateException();
            }
        }
        this.currentPlayer = players_as_list.get(0);
        this.activePlayers.remove(currentPlayer);
    }
    /** text
     * @param traveller text
     * @return text
     */
    public Card travel(Astronaut traveller) {
        if (traveller.oxygenRemaining() < 2) {
            throw new IllegalStateException();
        }
        boolean travelled = false;
        for (int i=0; i<traveller.getHand().size(); i++) {
            if (traveller.getHand().get(i) instanceof Oxygen) {
                Oxygen oxy_card = (Oxygen) traveller.getHand().get(i);
                if (oxy_card.getValue() == 2) {
                    this.gameDiscard.add(oxy_card);
                    traveller.hack(oxy_card);
                    travelled = true;
                }
            }
        }
        if (travelled == false) {
            // no dbl oxygens
            ArrayList<Card> to_remove = new ArrayList<Card>();
            for (int i=0; i<traveller.getHand().size(); i++) {
                if (traveller.getHand().get(i) instanceof Oxygen) {
                    Card card = traveller.getHand().get(i);
                    this.gameDiscard.add(card);
                    to_remove.add(card);
                    if (to_remove.size() == 2) {break;}
                }
            }
            traveller.hack(to_remove.get(0));
            traveller.hack(to_remove.get(1));
        }
        if (traveller.oxygenRemaining() < 1) {killPlayer(traveller);}
        Card drawn_space_card = this.spaceDeck.draw();
        if (drawn_space_card.toString().equals("Gravitational anomaly")) {
            this.gameDiscard.add(drawn_space_card);
            return drawn_space_card;
        }
        else {
            traveller.addToTrack(drawn_space_card);
            return drawn_space_card;
        }
    }

}