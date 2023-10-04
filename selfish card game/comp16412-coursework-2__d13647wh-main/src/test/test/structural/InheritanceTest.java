package test.structural;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Serializable;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("structural")
public class InheritanceTest {

    public boolean isComparable(String className) {
        Class<?> clazz = ClassExistenceTest.getClass(className);
        if (clazz == null) return false;
        return Comparable.class.isAssignableFrom(clazz);
    }

    public boolean isDeckSubclass(String className) {
        Class<?> superclass = ClassExistenceTest.getClass("selfish.deck.Deck");
        Class<?> subclass = ClassExistenceTest.getClass(className);
        if (subclass == null || superclass == null) return false;
        return superclass.isAssignableFrom(subclass);
    }

    public boolean isSerializable(String className) {
        Class<?> clazz = ClassExistenceTest.getClass(className);
        if (clazz == null) return false;
        return Serializable.class.isAssignableFrom(clazz);
    }

    @Test
	public void testAstronautImplementsSerializable() {
        assertTrue(isSerializable("selfish.Astronaut"));
	}

    @Test
	public void testCardImplementsComparable() {
		assertTrue(isComparable("selfish.deck.Card"));
	}

    @Test
	public void testCardImplementsSerializable() {
        assertTrue(isSerializable("selfish.deck.Card"));
	}

    @Test
	public void testDeckImplementsSerializable() {
        assertTrue(isSerializable("selfish.deck.Deck"));
	}

    @Test
	public void testGameDeckIsDeck() {
        assertTrue(isDeckSubclass("selfish.deck.GameDeck"));
	}

    @Test
	public void testGameEngineImplementsSerializable() {
        assertTrue(isSerializable("selfish.GameEngine"));
	}

    @Test
	public void testGameExceptionIsException() {
		Class<?> clazz = ClassExistenceTest.getClass("selfish.GameException");
        assertTrue(Exception.class.isAssignableFrom(clazz));
	}

    @Test
	public void testOxygenIsCard() {
		Class<?> superclass = ClassExistenceTest.getClass("selfish.deck.Card");
		Class<?> subclass = ClassExistenceTest.getClass("selfish.deck.Oxygen");
        if (subclass == null || superclass == null) fail();
        assertTrue(superclass.isAssignableFrom(subclass));
	}

    @Test
	public void testOxygenImplementsComparable() {
		assertTrue(isComparable("selfish.deck.Oxygen"));
	}

    @Test
	public void testSpaceDeckIsDeck() {
		assertTrue(isDeckSubclass("selfish.deck.SpaceDeck"));
	}

}