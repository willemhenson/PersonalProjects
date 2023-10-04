import selfish.*;
import selfish.deck.*;

public class GameDriver {

    /**
     * A helper function to centre text in a longer String.
     * @param width The length of the return String.
     * @param s The text to centre.
     * @return A longer string with the specified text centred.
     */

    public static String centreString (int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    public GameDriver() {
    }

    public static void main(String[] args) throws GameException {
		GameEngine engine = new GameEngine(69,  "io/ActionCards.txt", "io/SpaceCards.txt");
        engine.addPlayer("Kalpana Chawla");
        engine.addPlayer("poopy");
        engine.startGame();
        engine.startTurn();
        engine.getCurrentPlayer().addToHand(new Oxygen(2));
        engine.getCurrentPlayer().addToHand(new Oxygen(2));
        engine.getCurrentPlayer().addToHand(new Oxygen(2));
        engine.getCurrentPlayer().addToHand(new Oxygen(2));
        engine.getCurrentPlayer().addToHand(new Oxygen(2));
        engine.getCurrentPlayer().addToHand(new Oxygen(2));
        engine.getCurrentPlayer().addToHand(new Oxygen(2));
        engine.getCurrentPlayer().addToHand(new Oxygen(2));
        engine.getCurrentPlayer().addToHand(new Oxygen(2));
        engine.getCurrentPlayer().addToHand(new Oxygen(2));
        engine.travel(engine.getCurrentPlayer());
        engine.travel(engine.getCurrentPlayer());
        engine.travel(engine.getCurrentPlayer());
        engine.travel(engine.getCurrentPlayer());
        engine.travel(engine.getCurrentPlayer());
        while (engine.getCurrentPlayer().breathe() > 2);
        System.out.println(engine.getCurrentPlayer().oxygenRemaining());
        engine.travel(engine.getCurrentPlayer());
        System.out.println(engine.getCurrentPlayer().oxygenRemaining());
        System.out.println(engine.getWinner());
        
		//assertNotSame(topmostTrackCard, track.get(track.size()-1));
		//assertSame(topmostSpaceDeckCard, track.get(track.size()-1));

    }
}