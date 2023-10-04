package test.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import selfish.deck.Card;
import selfish.deck.Oxygen;
import selfish.Astronaut;
import selfish.GameEngine;

@Tag("functional")
@Tag("Astronaut")
public class AstronautTest {

	@Test
	public void testConstructor() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Fred Haise", engine);
		Field nameField = Astronaut.class.getDeclaredField("name");
		Field engineField = Astronaut.class.getDeclaredField("game");
		nameField.setAccessible(true);
		engineField.setAccessible(true);
		assertEquals("Fred Haise", (String)nameField.get(astronaut));
		assertSame(engine, (GameEngine)engineField.get(astronaut));

		Field actionsField = Astronaut.class.getDeclaredField("actions");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		Field trackField = Astronaut.class.getDeclaredField("track");
		actionsField.setAccessible(true);
		oxygensField.setAccessible(true);
		trackField.setAccessible(true);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		List<Card> oxygens = (List<Card>)oxygensField.get(astronaut);
		List<Card> track = (List<Card>)trackField.get(astronaut);
		assertEquals(0, actions.size());
		assertEquals(0, oxygens.size());
		assertEquals(0, track.size());
	}

	@Test
	public void testAddToHandOxygensOnly() throws NoSuchFieldException, IllegalAccessException {
		Astronaut astronaut = new Astronaut("Fred Haise", null);
		Card o2 = new Oxygen(2);
		Oxygen o1o = new Oxygen(1);
		Card o1c = new Oxygen(1);
		astronaut.addToHand(o2);
		astronaut.addToHand(o1o);
		astronaut.addToHand(o1c);

		Field actionsField = Astronaut.class.getDeclaredField("actions");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		actionsField.setAccessible(true);
		oxygensField.setAccessible(true);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		List<Card> oxygens = (List<Card>)oxygensField.get(astronaut);

		assertEquals(3, oxygens.size());
		assertEquals(0, actions.size());
		boolean combo1 = oxygens.get(0).equals(o2) && oxygens.get(1).equals(o1c) && oxygens.get(2).equals(o1o);
		boolean combo2 = oxygens.get(0).equals(o2) && oxygens.get(1).equals(o1o) && oxygens.get(2).equals(o1c);
		boolean combo3 = oxygens.get(2).equals(o2) && oxygens.get(1).equals(o1c) && oxygens.get(0).equals(o1o);
		boolean combo4 = oxygens.get(2).equals(o2) && oxygens.get(1).equals(o1o) && oxygens.get(0).equals(o1c);
		boolean combo5 = oxygens.get(1).equals(o2) && oxygens.get(0).equals(o1c) && oxygens.get(2).equals(o1o);
		boolean combo6 = oxygens.get(1).equals(o2) && oxygens.get(0).equals(o1o) && oxygens.get(2).equals(o1c);
		assertTrue(combo1 || combo2 || combo3 || combo4 || combo5 || combo6);
	}

	@Test
	public void testAddToHandActionsAndOxygens() throws Exception {
		GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Fred Haise", engine);
		Card siphon = new Card("Oxygen siphon", "Take a deep breath and relax, literally, because you can steal 2 oxygens from your chosen victim");
		Card tractor = new Card("Tractor beam", "Steal a card from another players hand at random");
		Card o2 = new Oxygen(2);
		Oxygen o1o = new Oxygen(1);
		Card o1c = new Oxygen(1);
		astronaut.addToHand(o2);
		astronaut.addToHand(siphon);
		astronaut.addToHand(tractor);
		astronaut.addToHand(o1o);
		astronaut.addToHand(o1c);

		Field actionsField = Astronaut.class.getDeclaredField("actions");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		actionsField.setAccessible(true);
		oxygensField.setAccessible(true);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		List<Card> oxygens = (List<Card>)oxygensField.get(astronaut);

		assertEquals(3, oxygens.size());
		assertEquals(2, actions.size());
		boolean combo1 = oxygens.get(0).equals(o2) && oxygens.get(1).equals(o1c) && oxygens.get(2).equals(o1o);
		boolean combo2 = oxygens.get(0).equals(o2) && oxygens.get(1).equals(o1o) && oxygens.get(2).equals(o1c);
		boolean combo3 = oxygens.get(2).equals(o2) && oxygens.get(1).equals(o1c) && oxygens.get(0).equals(o1o);
		boolean combo4 = oxygens.get(2).equals(o2) && oxygens.get(1).equals(o1o) && oxygens.get(0).equals(o1c);
		boolean combo5 = oxygens.get(1).equals(o2) && oxygens.get(0).equals(o1c) && oxygens.get(2).equals(o1o);
		boolean combo6 = oxygens.get(1).equals(o2) && oxygens.get(0).equals(o1o) && oxygens.get(2).equals(o1c);
		assertTrue(combo1 || combo2 || combo3 || combo4 || combo5 || combo6);
		assertTrue(actions.get(0).equals(siphon) || actions.get(1).equals(siphon));
		assertTrue(actions.get(0).equals(tractor) || actions.get(1).equals(tractor));
	}

	@Test
	public void testAddToTrack() throws NoSuchFieldException, IllegalAccessException {
		Astronaut astronaut = new Astronaut("Fred Haise", null);
		Card blank = new Card("Blank space", "This card is blank. Nothing to see here.");
		Card wormhole = new Card("Wormhole", "Oh dear. You have been sucked into another dimension. Swap places with another player of your choice.");
		Card meteoroid = new Card("Meteoroid", "Lose items to narrowly avoid being obliterated.");
		astronaut.addToTrack(blank);
		astronaut.addToTrack(wormhole);
		astronaut.addToTrack(meteoroid);

		Field trackField = Astronaut.class.getDeclaredField("track");
		trackField.setAccessible(true);
		List<Card> track = (List<Card>)trackField.get(astronaut);
		assertEquals(3, track.size());
		assertSame(blank, track.get(0));
		assertSame(wormhole, track.get(1));
		assertSame(meteoroid, track.get(2));
	}

	@Test
	public void testBreathe() throws Exception  {
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		oxygensField.setAccessible(true);
		GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Fyodor Yurchikhin", engine);
		for (int i=0; i<4; ++i) astronaut.addToHand(new Oxygen(1));
		List<Card> oxygens = (List<Card>)oxygensField.get(astronaut);
		int oldSize = oxygens.size();
		astronaut.breathe();
		assertEquals(oldSize-1, oxygens.size());
	}

	@Test
	public void testBreatheResultsInSplitOxygen() throws Exception  {
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		oxygensField.setAccessible(true);

		GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Fyodor Yurchikhin", engine);
		List<Oxygen> oxygens = (List<Oxygen>)oxygensField.get(astronaut);
		for (int i=0; i<2; ++i) astronaut.addToHand(new Oxygen(2));
		int oldSize = oxygens.size();
		int oldVal = 0;
		for (int i=0; i<2; ++i) oldVal += oxygens.get(i).getValue();

		astronaut.breathe();
		int newVal = 0;
		for (int i=0; i<2; ++i) newVal += oxygens.get(i).getValue();
		assertEquals(oldSize, oxygens.size());
		assertEquals(oldVal-1, newVal);
	}

	@Test
	public void testBreatheLast() throws Exception  {
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		Field actionsField = Astronaut.class.getDeclaredField("actions");
		oxygensField.setAccessible(true);
		actionsField.setAccessible(true);
		GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Fyodor Yurchikhin", engine);
		astronaut.addToHand(new Oxygen(1));
		List<Card> oxygens = (List<Card>)oxygensField.get(astronaut);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		astronaut.breathe();
		assertEquals(0, actions.size());
		assertEquals(0, oxygens.size());
	}

	@Test
	public void testDistanceFromShipMax() throws NoSuchFieldException, IllegalAccessException {
		Astronaut astronaut = new Astronaut("Helen Sharman", null);
		assertEquals(6, astronaut.distanceFromShip());
	}

	@Test
	public void testDistanceFromShipMin() throws NoSuchFieldException, IllegalAccessException {
		Astronaut astronaut = new Astronaut("Helen Sharman", null);
		astronaut.addToTrack(new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		astronaut.addToTrack(new Card("Wormhole", "Oh dear. You have been sucked into another dimension. Swap places with another player of your choice."));
		astronaut.addToTrack(new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		assertEquals(0, astronaut.distanceFromShip());
	}

	@Test
	public void testDistanceFromShipMidpoint() throws NoSuchFieldException, IllegalAccessException {
		Astronaut astronaut = new Astronaut("Helen Sharman", null);
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		astronaut.addToTrack(new Card("Wormhole", "Oh dear. You have been sucked into another dimension. Swap places with another player of your choice."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		assertEquals(3, astronaut.distanceFromShip());
	}

	@Test
	public void testGetActions() {
		Astronaut astronaut = new Astronaut("Sally Ride", null);
		Card o2 = new Oxygen(1);
		Card shield = new Card("Shield", "Block an attack from another astronaut.");
		Card hack = new Card("Hack suit", "Steal a card of your choosing.");
		astronaut.addToHand(o2);
		astronaut.addToHand(shield);
		astronaut.addToHand(hack);
		List<Card> actions = astronaut.getActions();
		assertEquals(2, actions.size());
		
		// returned actions are sorted on card name
		assertSame(hack, actions.get(0));
		assertSame(shield, actions.get(1));
	}

	@Test
	public void testGetActionsEmpty() {
		Astronaut astronaut = new Astronaut("Sally Ride", null);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Oxygen(2));
		List<Card> actions = astronaut.getActions();
		assertEquals(0, actions.size());
	}

	@Test
	public void testGetActionsEmptyHand() {
		Astronaut astronaut = new Astronaut("Sally Ride", null);
		List<Card> actions = astronaut.getActions();
		assertEquals(0, actions.size());
	}

	@Test
	public void testGetActionsStr() {
		Astronaut astronaut = new Astronaut("Kalpana Chawla", null);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Card("Tether", "Move forward 1 space, and knock another player back 1 space."));
		astronaut.addToHand(new Card("Shield", "Block an attack from another astronaut."));
		astronaut.addToHand(new Card("Rocket booster", "Move forward 1 space."));
		assertEquals("Rocket booster, Shield, Tether", astronaut.getActionsStr(false, false));
	}

	@Test
	public void testGetActionsStrEmpty() {
		Astronaut astronaut = new Astronaut("Kalpana Chawla", null);
		astronaut.addToHand(new Oxygen(1));
		assertEquals("", astronaut.getActionsStr(true, true));
		assertEquals("", astronaut.getActionsStr(false, false));
		assertEquals("", astronaut.getActionsStr(true, false));
		assertEquals("", astronaut.getActionsStr(false, true));
	}

	@Test
	public void testGetActionsStrEnumerated()  throws Exception {
		GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Kalpana Chawla", engine);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Card("Tether", "Move forward 1 space, and knock another player back 1 space."));
		astronaut.addToHand(new Card("Shield", "Block an attack from another astronaut."));
		assertEquals("[A] Shield, [B] Tether", astronaut.getActionsStr(true, false));
	}

	@Test
	public void testGetActionsStrEnumeratedExcludeSheilds()  throws Exception {
		GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Kalpana Chawla", engine);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Card("Tether", "Move forward 1 space, and knock another player back 1 space."));
		astronaut.addToHand(new Card("Shield", "Block an attack from another astronaut."));
		assertEquals("[A] Tether", astronaut.getActionsStr(true, true));
	}

	@Test
	public void testGetActionsStrEnumeratedExcludeSheieldRepeatedElement() throws Exception {
		GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Kalpana Chawla", engine);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Card("Shield", "Block an attack from another astronaut."));
		astronaut.addToHand(new Card("Tether", "Move forward 1 space, and knock another player back 1 space."));
		astronaut.addToHand(new Card("Tether", "Move forward 1 space, and knock another player back 1 space."));
		assertEquals("[A] Tether", astronaut.getActionsStr(true, true));
	}

	@Test
	public void testGetActionsStrEnumeratedRepeatedElement() throws Exception {
		GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Kalpana Chawla", engine);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Card("Shield", "Block an attack from another astronaut."));
		astronaut.addToHand(new Card("Rocket booster", "Move forward 1 space."));
		astronaut.addToHand(new Card("Rocket booster", "Move forward 1 space."));
		assertEquals("[A] Rocket booster, [B] Shield", astronaut.getActionsStr(true, false));
	}

	@Test
	public void testGetActionsStrExcludeShields() throws Exception {
		GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Kalpana Chawla", engine);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Card("Shield", "Block an attack from another astronaut."));
		astronaut.addToHand(new Card("Rocket booster", "Move forward 1 space."));
		assertEquals("Rocket booster", astronaut.getActionsStr(false, true));
	}

	@Test
	public void testGetActionsStrExcludeShieldsRepeatedElements() throws Exception {
		GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Kalpana Chawla", engine);
		astronaut.addToHand(new Card("Shield", "Block an attack from another astronaut."));
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Card("Rocket booster", "Move forward 1 space."));
		astronaut.addToHand(new Card("Shield", "Block an attack from another astronaut."));
		astronaut.addToHand(new Card("Rocket booster", "Move forward 1 space."));
		astronaut.addToHand(new Card("Shield", "Block an attack from another astronaut."));
		assertEquals("2x Rocket booster", astronaut.getActionsStr(false, true));
	}

	@Test
	public void testGetActionsStrRepeatedElement() throws Exception {
		GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Kalpana Chawla", engine);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Card("Shield", "Block an attack from another astronaut."));
		astronaut.addToHand(new Card("Rocket booster", "Move forward 1 space."));
		astronaut.addToHand(new Card("Shield", "Block an attack from another astronaut."));
		astronaut.addToHand(new Card("Rocket booster", "Move forward 1 space."));
		astronaut.addToHand(new Card("Shield", "Block an attack from another astronaut."));
		assertEquals("2x Rocket booster, 3x Shield", astronaut.getActionsStr(false, false));
	}

	@Test
	public void testGetHandOrdered() {
		Astronaut astronaut = new Astronaut("Sally Ride", null);
		Card o2 = new Oxygen(1);
		Card hack = new Card("Hack suit", "Steal a card of your choosing.");
		astronaut.addToHand(o2);
		astronaut.addToHand(hack);
		List<Card> hand = astronaut.getHand();
		assertEquals(2, hand.size());

		// returned hand is sorted on card name
		assertSame(hack, hand.get(0));
		assertSame(o2, hand.get(1));
	}

	@Test
	public void testGetHand() {
		Astronaut astronaut = new Astronaut("Sally Ride", null);
		Card o2 = new Oxygen(1);
		Card shield = new Card("Shield", "Block an attack from another astronaut.");
		astronaut.addToHand(o2);
		astronaut.addToHand(shield);
		List<Card> hand = astronaut.getHand();
		assertEquals(2, hand.size());

		// returned hand is sorted on card name
		assertSame(o2, hand.get(0));
		assertSame(shield, hand.get(1));
	}

	@Test
	public void testGetHandEmpty() {
		Astronaut astronaut = new Astronaut("Sally Ride", null);
		List<Card> hand = astronaut.getHand();
		assertEquals(0, hand.size());
	}

	@Test
	public void testGetHandStr() {
		Astronaut astronaut = new Astronaut("Jessica Meir", null);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Card("Shield", "a shield"));
		assertEquals("Oxygen(1); Shield", astronaut.getHandStr());
	}

	@Test
	public void testGetHandStrJustOxygens() {
		Astronaut astronaut = new Astronaut("Jessica Meir", null);
		astronaut.addToHand(new Oxygen(2));
		String hand = astronaut.getHandStr().strip();
		assertTrue(hand.equals("Oxygen(2)") || hand.equals("Oxygen(2);"));
		astronaut.addToHand(new Oxygen(1));
		hand = astronaut.getHandStr().strip();
		assertTrue(hand.equals("Oxygen(2), Oxygen(1)") || hand.equals("Oxygen(2), Oxygen(1);"));
	}

	@Test
	public void testGetHandStrRepeatedOxygens() {
		Astronaut astronaut = new Astronaut("Jessica Meir", null);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Oxygen(2));
		astronaut.addToHand(new Card("Tractor beam", "Steal a card from another players hand at random"));
		astronaut.addToHand(new Oxygen(2));
		assertEquals("2x Oxygen(2), Oxygen(1); Tractor beam", astronaut.getHandStr());
	}

	@Test
	public void testGetTrack() {
		Astronaut astronaut = new Astronaut("Sally Ride", null);
		Card blank = new Card("Blank space", "This card is blank. Nothing to see here");
		Card wormhole = new Card("Wormhole", "Oh dear. You have been sucked into another dimension. Swap places with another player of your choice.");
		Card blank2 = new Card("Blank space", "This card is blank. Nothing to see here");
		astronaut.addToTrack(blank);
		astronaut.addToTrack(wormhole);
		astronaut.addToTrack(blank2);
		List<Card> track = (List<Card>)astronaut.getTrack();
		assertEquals(3, track.size());
		assertSame(blank, track.get(0));
		assertSame(wormhole, track.get(1));
		assertSame(blank2, track.get(2));
	}

	@Test
	public void testGetTrackEmpty() {
		Astronaut astronaut = new Astronaut("Sally Ride", null);
		List<Card> track = (List<Card>)astronaut.getTrack();
		assertEquals(0, track.size());
	}

	@Test
	public void testHackCardParamLastOxygenKillsPlayer() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Leonid Kadeniuk", engine);
		Oxygen o2 = new Oxygen(2);
		astronaut.addToHand(o2);
		astronaut.addToHand(new Card("Laser blast", "Pick another player and knock them back 1 space."));
		astronaut.addToHand(new Card("Hole in suit", "Punch a hole in a rival's suit."));

		Field actionsField = Astronaut.class.getDeclaredField("actions");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		actionsField.setAccessible(true);
		oxygensField.setAccessible(true);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		List<Card> oxygens = (List<Card>)oxygensField.get(astronaut);

		astronaut.hack(o2);
		assertEquals(0, actions.size());
		assertEquals(0, oxygens.size());
	}

	@Test
	public void testHackCardParam() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Leonid Kadeniuk", engine);
		Card blast = new Card("Laser blast", "Pick another player and knock them back 1 space.");
		astronaut.addToHand(new Oxygen(2));
		astronaut.addToHand(blast);
		astronaut.addToHand(new Card("Hole in suit", "Punch a hole in a rival's suit."));

		Field actionsField = Astronaut.class.getDeclaredField("actions");
		actionsField.setAccessible(true);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		int numActions = actions.size();
		astronaut.hack(blast);
		assertEquals(numActions-1, actions.size());
		assertFalse(actions.contains(blast));
	}

	@Test
	public void testHackCardParamLastAction() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Leonid Kadeniuk", engine);
		Card blast = new Card("Laser blast", "Pick another player and knock them back 1 space.");
		Card hole = new Card("Hole in suit", "Punch a hole in a rival's suit.");
		astronaut.addToHand(new Oxygen(2));
		astronaut.addToHand(blast);
		astronaut.addToHand(hole);

		Field actionsField = Astronaut.class.getDeclaredField("actions");
		actionsField.setAccessible(true);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		int numActions = actions.size();
		astronaut.hack(blast);
		astronaut.hack(hole);
		assertEquals(numActions-2, actions.size());
		assertEquals(0, actions.size());
	}

	@Test
	public void testHackCardParamMultiple() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Leonid Kadeniuk", engine);
		Card blast = new Card("Laser blast", "Pick another player and knock them back 1 space.");
		astronaut.addToHand(new Oxygen(2));
		astronaut.addToHand(blast);
		astronaut.addToHand(new Card("Hole in suit", "Punch a hole in a rival's suit."));
		astronaut.addToHand(new Card("Laser blast", "Pick another player and knock them back 1 space."));

		Field actionsField = Astronaut.class.getDeclaredField("actions");
		actionsField.setAccessible(true);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		int numActions = actions.size();
		int numBlastsBefore = 0;
		for (int i=0; i<actions.size(); ++i) {
			if (actions.get(i).toString().equals("Laser blast")) ++numBlastsBefore;
		}
		astronaut.hack(blast);
		assertEquals(numActions-1, actions.size());
		assertFalse(actions.contains(blast));
		int numBlastsAfter = 0;
		for (int i=0; i<actions.size(); ++i) {
			if (actions.get(i).toString().equals("Laser blast")) ++numBlastsAfter;
		}
		assertEquals(numBlastsBefore-1, numBlastsAfter);
	}

	@Test
	public void testHackStringParamLastAction() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Leonid Kadeniuk", engine);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Card("Hole in suit", "Punch a hole in a rival's suit."));
		astronaut.addToHand(new Card("Tether", "Move forward 1 space, and knock another player back 1 space."));

		Field actionsField = Astronaut.class.getDeclaredField("actions");
		actionsField.setAccessible(true);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		int numActions = actions.size();
		astronaut.hack("Hole in suit");
		astronaut.hack("Tether");
		assertEquals(numActions-2, actions.size());
		assertEquals(0, actions.size());
	}


	@Test
	public void testHackString() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Leonid Kadeniuk", engine);
		astronaut.addToHand(new Oxygen(2));
		astronaut.addToHand(new Card("Laser blast", "Pick another player and knock them back 1 space."));
		astronaut.addToHand(new Card("Hole in suit", "Punch a hole in a rival's suit."));

		Field actionsField = Astronaut.class.getDeclaredField("actions");
		actionsField.setAccessible(true);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		int numActions = actions.size();
		astronaut.hack("Laser blast");
		assertEquals(numActions-1, actions.size());
	}

	@Test
	public void testHackStringParamMultiple() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Leonid Kadeniuk", engine);
		astronaut.addToHand(new Oxygen(2));
		astronaut.addToHand(new Card("Laser blast", "Pick another player and knock them back 1 space."));
		astronaut.addToHand(new Card("Hole in suit", "Punch a hole in a rival's suit."));
		astronaut.addToHand(new Card("Laser blast", "Pick another player and knock them back 1 space."));
		astronaut.addToHand(new Card("Laser blast", "Pick another player and knock them back 1 space."));

		Field actionsField = Astronaut.class.getDeclaredField("actions");
		actionsField.setAccessible(true);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		int numActions = actions.size();
		int numBlastsBefore = 0;
		for (int i=0; i<numActions; ++i) {
			if (actions.get(i).toString().equals("Laser blast")) ++numBlastsBefore;
		}
		astronaut.hack("Laser blast");
		assertEquals(numActions-1, actions.size());
		int numBlastsAfter = 0;
		for (int i=0; i<actions.size(); ++i) {
			if (actions.get(i).toString().equals("Laser blast")) ++numBlastsAfter;
		}
		assertEquals(numBlastsBefore-1, numBlastsAfter);
	}

	@Test
	public void testHackStringParamLastOxygenKillsPlayer() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Leonid Kadeniuk", engine);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Card("Hole in suit", "Punch a hole in a rival's suit."));
		astronaut.addToHand(new Card("Tether", "Move forward 1 space, and knock another player back 1 space."));
		astronaut.addToHand(new Card("Shield", "Block an attack from another astronaut."));
		astronaut.addToHand(new Card("Rocket booster", "Move forward 1 space."));

		Field actionsField = Astronaut.class.getDeclaredField("actions");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		actionsField.setAccessible(true);
		oxygensField.setAccessible(true);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		List<Card> oxygens = (List<Card>)oxygensField.get(astronaut);

		astronaut.hack("Oxygen(1)");
		assertEquals(0, actions.size());
		assertEquals(0, oxygens.size());
	}

	@Test
	public void testHasCardOxygen1() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Guy Bluford", engine);
		astronaut.addToHand(new Oxygen(1));
		assertEquals(1, astronaut.hasCard("Oxygen(1)"));
		assertEquals(0, astronaut.hasCard("Oxygen(2)"));
		assertEquals(0, astronaut.hasCard("Oxygen siphon"));

		astronaut = new Astronaut("Guy Bluford", engine);
		astronaut.addToHand(new Oxygen(2));
		assertEquals(0, astronaut.hasCard("Oxygen(1)"));
		assertEquals(1, astronaut.hasCard("Oxygen(2)"));
		assertEquals(0, astronaut.hasCard("Oxygen siphon"));

		astronaut = new Astronaut("Guy Bluford", engine);
		astronaut.addToHand(new Oxygen(2));
		astronaut.addToHand(new Card("Oxygen siphon", "Take a deep breath and relax, literally, because you can steal 2 oxygens from your chosen victim"));
		assertEquals(0, astronaut.hasCard("Oxygen(1)"));
		assertEquals(1, astronaut.hasCard("Oxygen siphon"));
	}

	@Test
	public void testHasCardReturnsPositive() {
		Astronaut astronaut = new Astronaut("Guy Bluford", null);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Oxygen(2));
		astronaut.addToHand(new Card("Hack suit", "Steal a card of your choosing."));
		astronaut.addToHand(new Card("Hole in suit", "Punch a hole in another player's suit."));
		astronaut.addToHand(new Card("Hole in suit", "Punch a hole in another player's suit."));
		assertEquals(1, astronaut.hasCard("Hack suit"));
		assertEquals(2, astronaut.hasCard("Hole in suit"));
	}

	@Test
	public void testHasCardReturnsZero() {
		Astronaut astronaut = new Astronaut("Guy Bluford", null);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Oxygen(2));
		astronaut.addToHand(new Card("Hack suit", "Steal a card of your choosing."));
		astronaut.addToHand(new Card("Hole in suit", "Punch a hole in another player's suit."));
		astronaut.addToHand(new Card("Hole in suit", "Punch a hole in another player's suit."));
		assertEquals(0, astronaut.hasCard("Shield"));
		assertEquals(0, astronaut.hasCard("Tether"));
	}

	@Test
	public void testHasCardEmptyActions() {
		Astronaut astronaut = new Astronaut("Guy Bluford", null);
		astronaut.addToHand(new Oxygen(1));
		assertEquals(0, astronaut.hasCard("Shield"));
	}

	@Test
	public void testHasCardEmptyHand() {
		Astronaut astronaut = new Astronaut("Guy Bluford", null);
		assertEquals(0, astronaut.hasCard("Shield"));
	}

	@Test
	public void testHasMeltedEyeballsAtZeroTrue() {
		Astronaut astronaut = new Astronaut("Guy Bluford", null);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToTrack(new Card("Solar flare", "Uh-oh."));
		assertTrue(astronaut.hasMeltedEyeballs());
	}

	@Test
	public void testHasMeltedEyeballsAtZeroFalse() {
		Astronaut astronaut = new Astronaut("Guy Bluford", null);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToTrack(new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."));
		assertFalse(astronaut.hasMeltedEyeballs());
	}

	@Test
	public void testHasMeltedEyeballsAtSixTrue() {
		Astronaut astronaut = new Astronaut("Guy Bluford", null);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToTrack(new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		astronaut.addToTrack(new Card("Wormhole", "Oh dear. You have been sucked into another dimension. Swap places with another player of your choice."));
		astronaut.addToTrack(new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		astronaut.addToTrack(new Card("Solar flare", "Uh-oh."));
		assertTrue(astronaut.hasMeltedEyeballs());
	}

	@Test
	public void testHasMeltedEyeballsAtSixFalse() {
		Astronaut astronaut = new Astronaut("Guy Bluford", null);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToTrack(new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		astronaut.addToTrack(new Card("Wormhole", "Oh dear. You have been sucked into another dimension. Swap places with another player of your choice."));
		astronaut.addToTrack(new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		astronaut.addToTrack(new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."));
		assertFalse(astronaut.hasMeltedEyeballs());
	}

	@Test
	public void testHasWon() throws NoSuchFieldException, IllegalAccessException {
		Astronaut astronaut = new Astronaut("Helen Sharman", null);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToTrack(new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		astronaut.addToTrack(new Card("Wormhole", "Oh dear. You have been sucked into another dimension. Swap places with another player of your choice."));
		astronaut.addToTrack(new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		assertTrue(astronaut.hasWon());
	}

	@Test
	public void testHasWonNotAtShip() throws NoSuchFieldException, IllegalAccessException {
		Astronaut astronaut = new Astronaut("Helen Sharman", null);
		astronaut.addToHand(new Oxygen(2));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		astronaut.addToTrack(new Card("Wormhole", "Oh dear. You have been sucked into another dimension. Swap places with another player of your choice."));
		astronaut.addToTrack(new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		assertFalse(astronaut.hasWon());
	}

	@Test
	public void testHasWonNoOxygen() throws NoSuchFieldException, IllegalAccessException {
		Astronaut astronaut = new Astronaut("Helen Sharman", null);
		astronaut.addToTrack(new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		astronaut.addToTrack(new Card("Wormhole", "Oh dear. You have been sucked into another dimension. Swap places with another player of your choice."));
		astronaut.addToTrack(new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		assertFalse(astronaut.hasWon());
	}

	@Test
	public void testIsAlive() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("John McFall", engine);
		assertEquals(false, astronaut.isAlive());
		astronaut.addToHand(new Card("Tractor beam", "Steal a card from another players hand at random"));
		assertEquals(false, astronaut.isAlive());
		astronaut.addToHand(new Oxygen(1));
		assertEquals(true, astronaut.isAlive());
	}

	@Test
	public void testLaserBlast() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Ellen Ochoa", engine);
		astronaut.addToHand(new Oxygen(2));
		astronaut.addToTrack(new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		astronaut.addToTrack(new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		Card last = new Card("Solar flare", "Uh-oh.");
		astronaut.addToTrack(last);

		Field trackField = Astronaut.class.getDeclaredField("track");
		trackField.setAccessible(true);
		List<Card> track = (List<Card>)trackField.get(astronaut);
		int trackSize = track.size();

		Card rtn = astronaut.laserBlast();
		assertEquals(trackSize-1, track.size());
		assertSame(last, rtn);
	}

	@Test
	public void testLaserBlastMovesPlayerBackToStart() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Ellen Ochoa", engine);
		astronaut.addToHand(new Oxygen(2));
		Card last = new Card("Solar flare", "Uh-oh.");
		astronaut.addToTrack(last);

		Field trackField = Astronaut.class.getDeclaredField("track");
		trackField.setAccessible(true);
		List<Card> track = (List<Card>)trackField.get(astronaut);

		Card rtn = astronaut.laserBlast();
		assertEquals(0, track.size());
		assertSame(last, rtn);
	}

	@Test
	public void testOxygenRemainingAllOnes() {
		Astronaut astronaut = new Astronaut("John McFall", null);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Oxygen(1));
		assertEquals(4, astronaut.oxygenRemaining());
	}

	@Test
	public void testOxygenRemainingMixedValue() {
		Astronaut astronaut = new Astronaut("John McFall", null);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Oxygen(2));
		astronaut.addToHand(new Card("Tractor beam", "Steal a card from another players hand at random"));
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Oxygen(2));
		assertEquals(6, astronaut.oxygenRemaining());
	}

	@Test
	public void testOxygenRemainingWithSiphon() {
		Astronaut astronaut = new Astronaut("John McFall", null);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Oxygen(2));
		astronaut.addToHand(new Card("Oxygen siphon", "Take a deep breath and relax, literally, because you can steal 2 oxygens from your chosen victim"));
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Oxygen(2));
		assertEquals(6, astronaut.oxygenRemaining());
	}

	@Test
	public void testPeekAtTrackEmpty() {
		Astronaut astronaut = new Astronaut("Guy Bluford", null);
		astronaut.addToHand(new Oxygen(1));
		assertEquals(null, astronaut.peekAtTrack());
	}

	@Test
	public void testPeekAtTrackZero() {
		Astronaut astronaut = new Astronaut("Guy Bluford", null);
		astronaut.addToHand(new Oxygen(1));
		Card meteoroid = new Card("Meteoroid", "Lose items to narrowly avoid being obliterated.");
		astronaut.addToTrack(meteoroid);
		assertSame(meteoroid, astronaut.peekAtTrack());
	}

	@Test
	public void testPeekAtTrackSix() {
		Astronaut astronaut = new Astronaut("Guy Bluford", null);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToTrack(new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		astronaut.addToTrack(new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."));
		astronaut.addToTrack(new Card("Blank space", "This card is blank. Nothing to see here."));
		astronaut.addToTrack(new Card("Solar flare", "Uh-oh."));
		Card wormhole = new Card("Wormhole", "Oh dear. You have been sucked into another dimension. Swap places with another player of your choice.");
		astronaut.addToTrack(wormhole);
		assertSame(wormhole, astronaut.peekAtTrack());
	}

	@Test
	public void testSiphon() throws Exception  {
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		Field valueField = Oxygen.class.getDeclaredField("value");
		oxygensField.setAccessible(true);
		valueField.setAccessible(true);

		GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Fyodor Yurchikhin", engine);
		for (int i=0; i<4; ++i) astronaut.addToHand(new Oxygen(1));
		List<Card> oxygens = (List<Card>)oxygensField.get(astronaut);
		int oldSize = oxygens.size();
		Card rtn = astronaut.siphon();
		assertEquals(Integer.valueOf(1), (Integer)valueField.get(rtn));
		assertEquals(oldSize-1, oxygens.size());
	}

	@Test
	public void testSiphonResultsInSplitOxygen() throws Exception  {
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		Field valueField = Oxygen.class.getDeclaredField("value");
		oxygensField.setAccessible(true);
		valueField.setAccessible(true);

		GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Fyodor Yurchikhin", engine);
		List<Oxygen> oxygens = (List<Oxygen>)oxygensField.get(astronaut);
		for (int i=0; i<2; ++i) astronaut.addToHand(new Oxygen(2));
		int oldSize = oxygens.size();
		int oldVal = 0;
		for (int i=0; i<2; ++i) oldVal += oxygens.get(i).getValue();

		Oxygen rtn = astronaut.siphon();
		assertEquals(Integer.valueOf(1), (Integer)valueField.get(rtn));
		int newVal = 0;
		for (int i=0; i<2; ++i) newVal += oxygens.get(i).getValue();
		assertEquals(oldSize, oxygens.size());
		assertEquals(oldVal-1, newVal);
	}

	@Test
	public void testSiphonLast() throws Exception  {
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		Field actionsField = Astronaut.class.getDeclaredField("actions");
		oxygensField.setAccessible(true);
		actionsField.setAccessible(true);
		GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Fyodor Yurchikhin", engine);
		Oxygen o2 = new Oxygen(1);
		astronaut.addToHand(o2);
		List<Card> oxygens = (List<Card>)oxygensField.get(astronaut);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		Oxygen rtn = astronaut.siphon();
		assertEquals(0, actions.size());
		assertEquals(0, oxygens.size());
		assertSame(o2, rtn);
	}

	// steal
	//steal method removes and returns a random Card from an astronaut's hand

	@Test
	public void testSteal() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Anousheh Ansari", engine);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Card("Shield", "Block an attack from another astronaut."));
		astronaut.addToHand(new Card("Rocket booster", "Move forward 1 space."));
		astronaut.addToHand(new Oxygen(1));

		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		Field actionsField = Astronaut.class.getDeclaredField("actions");
		oxygensField.setAccessible(true);
		actionsField.setAccessible(true);
		List<Card> oxygens = (List<Card>)oxygensField.get(astronaut);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		List<Card> oldHand = new ArrayList<Card>();
		oldHand.addAll(oxygens);
		oldHand.addAll(actions);
		int oldHandSize = oldHand.size();

		Card rtn = astronaut.steal();
		assertEquals(oldHandSize-1, oxygens.size() + actions.size());
		assertTrue(oldHand.contains(rtn));
	}


	@Test
	public void testStealIsRandom() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Anousheh Ansari", engine);
		Card[] cards = new Card[] {
			new Oxygen(1),
			new Card("Shield", "Block an attack from another astronaut."),
			new Card("Tether", "Move forward 1 space, and knock another player back 1 space."),
			new Card("Shield", "Block an attack from another astronaut."),
			new Card("Rocket booster", "Move forward 1 space."),
            new Card("Oxygen siphon", "Take a deep breath and relax, literally, because you can steal 2 oxygens from your chosen victim"),
			new Card("Hack suit", "Steal a card of your choosing."),
			new Oxygen(1),
			new Oxygen(2),
			new Card("Hole in suit", "Punch a hole in another player's suit."),
			new Card("Hole in suit", "Punch a hole in another player's suit."),
			new Oxygen(2),
		};	
		for (Card c: cards) astronaut.addToHand(c);

		Card last = null;
		boolean returnsAllSame = true;
		for (int i=0; i<1000; ++i) {
			Card rtn = astronaut.steal();
		    assertTrue(Arrays.asList(cards).contains(rtn));
			if (last != null && last != rtn) {
				returnsAllSame = false;
				break;
			}
			astronaut.addToHand(rtn);
			last = rtn;
		}
		assertFalse(returnsAllSame);
	}

	@Test
	public void testStealLastCard() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Anousheh Ansari", engine);
		Oxygen o2 = new Oxygen(2);
		astronaut.addToHand(o2);

		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		oxygensField.setAccessible(true);
		List<Card> oxygens = (List<Card>)oxygensField.get(astronaut);

		Card rtn = astronaut.steal();
		assertEquals(0, oxygens.size());
		assertSame(o2, rtn);
	}

	@Test
	public void testSwapTrackWithPlayerAtStart() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut1 = new Astronaut("Ellen Ochoa", engine);
		Astronaut astronaut2 = new Astronaut("Sarah Gorelick", engine);
		astronaut1.addToHand(new Oxygen(2));
		astronaut2.addToHand(new Oxygen(2));

		Card[] cards = new Card[] {
			new Card("Asteroid field", "Things are getting rocky."),
			new Card("Blank space", "This card is blank. Nothing to see here."),
			new Card("Solar flare", "Uh-oh."),
		};
		for (Card card: cards) astronaut1.addToTrack(card);
		astronaut1.swapTrack(astronaut2);

		Field trackField = Astronaut.class.getDeclaredField("track");
		trackField.setAccessible(true);
		List<Card> track1 = (List<Card>)trackField.get(astronaut1);
		List<Card> track2 = (List<Card>)trackField.get(astronaut2);

		assertEquals(0, track1.size());
		assertEquals(cards.length, track2.size());
		for (int i=0; i<cards.length; ++i) assertSame(cards[i], track2.get(i));
	}

	@Test
	public void testSwapTrackWithAlivePlayer() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut1 = new Astronaut("Ellen Ochoa", engine);
		Astronaut astronaut2 = new Astronaut("Sarah Gorelick", engine);
		astronaut1.addToHand(new Oxygen(2));
		astronaut2.addToHand(new Oxygen(2));

		Card[] cards1 = new Card[] {
			new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."),
			new Card("Blank space", "This card is blank. Nothing to see here."),
			new Card("Asteroid field", "Things are getting rocky."),
			new Card("Solar flare", "Uh-oh."),
			new Card("Blank space", "This card is blank. Nothing to see here."),
		};
		Card[] cards2 = new Card[] {
			new Card("Wormhole", "Oh dear. You have been sucked into another dimension. Swap places with another player of your choice."),
			new Card("Blank space", "This card is blank. Nothing to see here."),
		};
		for (Card card: cards1) astronaut1.addToTrack(card);
		for (Card card: cards2) astronaut2.addToTrack(card);
		astronaut1.swapTrack(astronaut2);

		Field trackField = Astronaut.class.getDeclaredField("track");
		trackField.setAccessible(true);
		List<Card> track1 = (List<Card>)trackField.get(astronaut1);
		List<Card> track2 = (List<Card>)trackField.get(astronaut2);

		assertEquals(cards2.length, track1.size());
		assertEquals(cards1.length, track2.size());
		for (int i=0; i<cards2.length; ++i) assertEquals(cards2[i], track1.get(i));
		for (int i=0; i<cards1.length; ++i) assertEquals(cards1[i], track2.get(i));
	}

	@Test
	public void testSwapTrackWithDeadPlayer() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut1 = new Astronaut("Ellen Ochoa", engine);
		Astronaut astronaut2 = new Astronaut("Sarah Gorelick", engine);
		astronaut1.addToHand(new Oxygen(2));
		astronaut2.addToHand(new Oxygen(1));
		astronaut2.breathe();

		Card[] cards1 = new Card[] {
			new Card("Meteoroid", "Lose items to narrowly avoid being obliterated."),
			new Card("Blank space", "This card is blank. Nothing to see here."),
			new Card("Asteroid field", "Things are getting rocky."),
			new Card("Solar flare", "Uh-oh."),
			new Card("Blank space", "This card is blank. Nothing to see here."),
		};
		Card[] cards2 = new Card[] {
			new Card("Wormhole", "Oh dear. You have been sucked into another dimension. Swap places with another player of your choice."),
			new Card("Blank space", "This card is blank. Nothing to see here."),
		};
		for (Card card: cards1) astronaut1.addToTrack(card);
		for (Card card: cards2) astronaut2.addToTrack(card);
		astronaut1.swapTrack(astronaut2);

		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		Field trackField = Astronaut.class.getDeclaredField("track");
		oxygensField.setAccessible(true);
		trackField.setAccessible(true);
		List<Card> oxygens1 = (List<Card>)oxygensField.get(astronaut1);
		List<Card> oxygens2 = (List<Card>)oxygensField.get(astronaut2);
		List<Card> track1 = (List<Card>)trackField.get(astronaut1);
		List<Card> track2 = (List<Card>)trackField.get(astronaut2);

		assertEquals(cards2.length, track1.size());
		assertEquals(cards1.length, track2.size());
		for (int i=0; i<cards2.length; ++i) assertSame(cards2[i], track1.get(i));
		for (int i=0; i<cards1.length; ++i) assertSame(cards1[i], track2.get(i));
		assertEquals(oxygens1.size(), 1);
		assertEquals(oxygens2.size(), 0);
	}

	@Test
	public void testToStringPlayerAlive() {
		Astronaut astronaut = new Astronaut("Guy Bluford", null);
		astronaut.addToHand(new Oxygen(1));
		assertEquals("Guy Bluford", astronaut.toString());
	}

	@Test
	public void testToStringPlayerDead() {
		Astronaut astronaut = new Astronaut("Michael Collins", null);
		assertEquals("Michael Collins (is dead)", astronaut.toString());
	}

}
