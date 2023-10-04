package test.structural;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


@Tag("structural")
public class ClassModifierTest {

	public boolean classIsPublic(String className){
		Class<?> clazz = ClassExistenceTest.getClass(className);
		if (clazz == null) return false;
		return Modifier.isPublic(clazz.getModifiers());
	}

	@Test
	public void testAstronautIsPublic() {
		assertTrue(classIsPublic("selfish.Astronaut"));
	}

	@Test
	public void testCardIsPublic() {
		assertTrue(classIsPublic("selfish.deck.Card"));
	}

	@Test
	public void testDeckIsPublic() {
		assertTrue(classIsPublic("selfish.deck.Deck"));
	}

	@Test
	public void testDeckIsAbstract() {
		Class<?> clazz = ClassExistenceTest.getClass("selfish.deck.Deck");
		assertNotNull(clazz);
		assertTrue(Modifier.isAbstract(clazz.getModifiers()));
	}

	@Test
	public void testGameDeckIsPublic() {
		assertTrue(classIsPublic("selfish.deck.GameDeck"));
	}

	@Test
	public void testGameEngineIsPublic() {
		assertTrue(classIsPublic("selfish.GameEngine"));
	}

	@Test
	public void testGameExceptionIsPublic() {
		assertTrue(classIsPublic("selfish.GameException"));
	}

	@Test
	public void testOxygenIsPublic() {
		assertTrue(classIsPublic("selfish.deck.Oxygen"));
	}

	@Test
	public void testSpaceDeckIsPublic() {
		assertTrue(classIsPublic("selfish.deck.SpaceDeck"));
	}

}
