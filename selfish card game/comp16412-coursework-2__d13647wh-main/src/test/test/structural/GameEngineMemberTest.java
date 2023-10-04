package test.structural;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Tag("structural")
@Tag("GameEngine")
public class GameEngineMemberTest {

    static String FQCN = "selfish.GameEngine";
	static Field[] fields;
	static Method[] methods;

	@BeforeAll
	public static void setUp() throws ClassNotFoundException {
		fields = ClassMemberHelper.getFields(FQCN);
		methods = ClassMemberHelper.getMethods(FQCN);
	}

	// Constructors

	@Test
	public void testEmptyConstructorIsPrivate() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Exception exception = assertThrows(NoSuchMethodException.class, () -> {
		    clazz.getConstructor();
		});
	    Constructor c = clazz.getDeclaredConstructor();
		assertTrue(ClassMemberHelper.isAccessType(c.getModifiers(), AccessType.PRIVATE));
	}

	@Test
	public void testParamConstructorExistsAndIsPublic() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
	    Constructor c = clazz.getConstructor(long.class, String.class, String.class);
	}

	// Field exists

	@Test
	public void testActivePlayersExists() {
		assertTrue(ClassMemberHelper.fieldExists("activePlayers", fields));
	}

	@Test
	public void testCorpsesExists() {
		assertTrue(ClassMemberHelper.fieldExists("corpses", fields));
	}

	@Test
	public void testCurrentPlayerExists() {
		assertTrue(ClassMemberHelper.fieldExists("currentPlayer", fields));
	}

	@Test
	public void testHasStartedExists() {
		assertTrue(ClassMemberHelper.fieldExists("hasStarted", fields));
	}

	@Test
	public void testRandomExists() {
		assertTrue(ClassMemberHelper.fieldExists("random", fields));
	}

	@Test
	public void testGameDeckExists() {
		assertTrue(ClassMemberHelper.fieldExists("gameDeck", fields));
	}

	@Test
	public void testGameDiscardExists() {
		assertTrue(ClassMemberHelper.fieldExists("gameDiscard", fields));
	}

	@Test
	public void testSerialVersionUIDExists() {
		assertTrue(ClassMemberHelper.classHasPublicStaticFinalSerialVersionUID(FQCN));
	}

	@Test
	public void testSpaceDeckExists() {
		assertTrue(ClassMemberHelper.fieldExists("spaceDeck", fields));
	}

	@Test
	public void testSpaceDiscardExists() {
		assertTrue(ClassMemberHelper.fieldExists("spaceDiscard", fields));
	}

	// Field modifiers

	@Test
	public void testActivePlayersIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("activePlayers", fields, AccessType.PRIVATE));
	}

	@Test
	public void testCorpsesIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("corpses", fields, AccessType.PRIVATE));
	}

	@Test
	public void testCurrentPlayerIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("currentPlayer", fields, AccessType.PRIVATE));
	}

	@Test
	public void testHasStartedIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("hasStarted", fields, AccessType.PRIVATE));
	}

	@Test
	public void testRandomIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("random", fields, AccessType.PRIVATE));
	}

	@Test
	public void testGameDeckIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("gameDeck", fields, AccessType.PRIVATE));
	}

	@Test
	public void testGameDiscardIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("gameDiscard", fields, AccessType.PRIVATE));
	}

	@Test
	public void testSpaceDeckIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("spaceDeck", fields, AccessType.PRIVATE));
	}

	@Test
	public void testSpaceDiscardIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("spaceDiscard", fields, AccessType.PRIVATE));
	}
	
	// Field types

	@Test
	public void testActivePlayersIsCollection() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Field field = clazz.getDeclaredField("activePlayers");
		assertEquals(Collection.class, (Class)field.getType());
		Type fieldType = field.getGenericType();
		assertTrue(fieldType instanceof ParameterizedType);
		ParameterizedType paramType = (ParameterizedType)fieldType;
		assertEquals(Class.forName("selfish.Astronaut"), (Class)paramType.getActualTypeArguments()[0]);
	}

	@Test
	public void testCorpsesIsList() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Field field = clazz.getDeclaredField("corpses");
		assertEquals(List.class, (Class)field.getType());
		Type fieldType = field.getGenericType();
		assertTrue(fieldType instanceof ParameterizedType);
		ParameterizedType paramType = (ParameterizedType)fieldType;
		assertEquals(Class.forName("selfish.Astronaut"), (Class)paramType.getActualTypeArguments()[0]);
	}

	@Test
	public void testCurrentPlayerIsAstronaut() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Class tmp = (Class)clazz.getDeclaredField("currentPlayer").getType();
		assertEquals(Class.forName("selfish.Astronaut"), tmp);
	}

	@Test
	public void testHasStartedIsBoolean() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Class tmp = (Class)clazz.getDeclaredField("hasStarted").getType();
		assertEquals(boolean.class, tmp);
	}

	@Test
	public void testRandomIsRandom() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Class tmp = (Class)clazz.getDeclaredField("random").getType();
		assertEquals(Random.class, tmp);
	}

	@Test
	public void testGameDeckIsGameDeck() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Class tmp = (Class)clazz.getDeclaredField("gameDeck").getType();
		assertEquals(Class.forName("selfish.deck.GameDeck"), tmp);
	}

	@Test
	public void testGameDiscardIsGameDeck() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Class tmp = (Class)clazz.getDeclaredField("gameDiscard").getType();
		assertEquals(Class.forName("selfish.deck.GameDeck"), tmp);
	}

	@Test
	public void testSpaceDeckIsSpaceDeck() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Class tmp = (Class)clazz.getDeclaredField("spaceDeck").getType();
		assertEquals(Class.forName("selfish.deck.SpaceDeck"), tmp);
	}

	@Test
	public void testSpaceDiscardIsSpaceDeck() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Class tmp = (Class)clazz.getDeclaredField("spaceDiscard").getType();
		assertEquals(Class.forName("selfish.deck.SpaceDeck"), tmp);
	}
	
	// Method exists

	@Test
	public void testAddPlayerExists() {
		assertTrue(ClassMemberHelper.methodExists("addPlayer", methods));
	}

	@Test
	public void testEndTurnExists() {
		assertTrue(ClassMemberHelper.methodExists("endTurn", methods));
	}

	@Test
	public void testGameOverExists() {
		assertTrue(ClassMemberHelper.methodExists("gameOver", methods));
	}

	@Test
	public void testGetAllPlayersExists() {
		assertTrue(ClassMemberHelper.methodExists("getAllPlayers", methods));
	}

	@Test
	public void testGetCurrentPlayerExists() {
		assertTrue(ClassMemberHelper.methodExists("getCurrentPlayer", methods));
	}

	@Test
	public void testGetFullPlayerCountExists() {
		assertTrue(ClassMemberHelper.methodExists("getFullPlayerCount", methods));
	}

	@Test
	public void testGetGameDeckExists() {
		assertTrue(ClassMemberHelper.methodExists("getGameDeck", methods));
	}

	@Test
	public void testGetGameDiscardExists() {
		assertTrue(ClassMemberHelper.methodExists("getGameDiscard", methods));
	}

	@Test
	public void testGetSpaceDeckExists() {
		assertTrue(ClassMemberHelper.methodExists("getSpaceDeck", methods));
	}

	@Test
	public void testGetSpaceDiscardExists() {
		assertTrue(ClassMemberHelper.methodExists("getSpaceDiscard", methods));
	}
	@Test
	public void testGetWinnerExists() {
		assertTrue(ClassMemberHelper.methodExists("getWinner", methods));
	}

	@Test
	public void testKillPlayerExists() {
		assertTrue(ClassMemberHelper.methodExists("killPlayer", methods));
	}

	@Test
	public void testLoadStateExists() {
		assertTrue(ClassMemberHelper.methodExists("loadState", methods));
	}

	@Test
	public void testMergeDecksExists() { 
		assertTrue(ClassMemberHelper.methodExists("mergeDecks", methods));
	}

	@Test
	public void testSaveStateExists() {
		assertTrue(ClassMemberHelper.methodExists("saveState", methods));
	}

	@Test
	public void testSplitOxygenExists() {
		assertTrue(ClassMemberHelper.methodExists("splitOxygen", methods));
	}

	@Test
	public void testStartGameExists() {
		assertTrue(ClassMemberHelper.methodExists("startGame", methods));
	}

	@Test
	public void testStartTurnExists() {
		assertTrue(ClassMemberHelper.methodExists("startTurn", methods));
	}

	@Test
	public void testTravelExists() {
		assertTrue(ClassMemberHelper.methodExists("travel", methods));
	}

    // Method modifiers

	@Test
	public void testAddPlayerIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("addPlayer", methods, AccessType.PUBLIC));
	}

	@Test
	public void testEndTurnIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("endTurn", methods, AccessType.PUBLIC));
	}

	@Test
	public void testGameOverIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("getAllPlayers", methods, AccessType.PUBLIC));
	}

	@Test
	public void testGetAllPlayersIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("getAllPlayers", methods, AccessType.PUBLIC));
	}

	@Test
	public void testGetCurrentPlayerIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("getCurrentPlayer", methods, AccessType.PUBLIC));
	}

	@Test
	public void testGetFullPlayerCountIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("getFullPlayerCount", methods, AccessType.PUBLIC));
	}

	@Test
	public void testGetGameDeckIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("getGameDeck", methods, AccessType.PUBLIC));
	}

	@Test
	public void testGetGameDiscardIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("getGameDiscard", methods, AccessType.PUBLIC));
	}

	@Test
	public void testGetSpaceDeckIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("getSpaceDeck", methods, AccessType.PUBLIC));
	}

	@Test
	public void testGetSpaceDiscardIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("getSpaceDiscard", methods, AccessType.PUBLIC));
	}

	@Test
	public void testGetWinnerIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("getWinner", methods, AccessType.PUBLIC));
	}

	@Test
	public void testKillPlayerIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("killPlayer", methods, AccessType.PUBLIC));
	}
	
	@Test
	public void testLoadStateIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("loadState", methods, AccessType.PUBLIC));
	}

	@Test
	public void testLoadStateIsStatic() {
		assertTrue(ClassMemberHelper.methodIsStatic("loadState", methods));
	}

	@Test
	public void testMergeDecksIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("mergeDecks", methods, AccessType.PUBLIC));
	}

	@Test
	public void testSaveStateIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("saveState", methods, AccessType.PUBLIC));
	}

	@Test
	public void testSplitOxygenIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("splitOxygen", methods, AccessType.PUBLIC));
	}

	@Test
	public void testStartGameIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("startGame", methods, AccessType.PUBLIC));
	}

	@Test
	public void testStartTurnIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("startTurn", methods, AccessType.PUBLIC));
	}

	@Test
	public void testTravelIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("travel", methods, AccessType.PUBLIC));
	}

	// Method param/return types

	@Test
	public void testAddPlayerReturnsInt() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("addPlayer", String.class);
		assertEquals(int.class, method.getReturnType());
	}

	@Test
	public void testEndTurnReturnsInt() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("endTurn");
		assertEquals(int.class, method.getReturnType());
	}

	@Test
	public void testGameOverReturnsBoolean() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("gameOver");
		assertEquals(boolean.class, method.getReturnType());
	}

	@Test
	public void testGetAllPlayersReturnsList() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("getAllPlayers");
		Class returnClass = method.getReturnType();
		assertEquals(List.class, returnClass);
		Type returnType = method.getGenericReturnType();
		assertTrue(returnType instanceof ParameterizedType);
		ParameterizedType paramType = (ParameterizedType) returnType;
		assertEquals(Class.forName("selfish.Astronaut"), (Class)paramType.getActualTypeArguments()[0]);
	}

	@Test
	public void testGetCurrentPlayerReturnsAstronaut() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("getCurrentPlayer");
		assertEquals(Class.forName("selfish.Astronaut"), method.getReturnType());
	}

	@Test
	public void testGetFullPlayerCountReturnsInt() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("getFullPlayerCount");
		assertEquals(int.class, method.getReturnType());
	}

	@Test
	public void testGetGameDeckReturnsSpaceDeck() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("getGameDeck");
		assertEquals(Class.forName("selfish.deck.GameDeck"), method.getReturnType());
	}

	@Test
	public void testGetGameDiscardReturnsSpaceDeck() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("getGameDiscard");
		assertEquals(Class.forName("selfish.deck.GameDeck"), method.getReturnType());
	}

	@Test
	public void testGetSpaceDeckReturnsSpaceDeck() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("getSpaceDeck");
		assertEquals(Class.forName("selfish.deck.SpaceDeck"), method.getReturnType());
	}

	@Test
	public void testGetSpaceDiscardReturnsSpaceDeck() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("getSpaceDiscard");
		assertEquals(Class.forName("selfish.deck.SpaceDeck"), method.getReturnType());
	}

	@Test
	public void testGetWinnerReturnsAstronaut() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("getWinner");
		assertEquals(Class.forName("selfish.Astronaut"), method.getReturnType());
	}

	@Test
	public void testKillPlayerReturnsVoid() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("killPlayer", Class.forName("selfish.Astronaut"));
		assertEquals(Void.TYPE, method.getReturnType());
	}

	@Test
	public void testLoadStateReturnsVoid() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("loadState", String.class);
		assertEquals(clazz, method.getReturnType());
	}

	@Test
	public void testMergeDecksReturnsVoid() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Class Deck = Class.forName("selfish.deck.Deck");
		Method method = clazz.getMethod("mergeDecks", Deck, Deck);
		assertEquals(Void.TYPE, method.getReturnType());
	}

	@Test
	public void testSaveStateReturnsVoid() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("saveState", String.class);
		assertEquals(Void.TYPE, method.getReturnType());
	}

	@Test
	public void testSplitOxygenReturnsArray() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Class Oxygen = Class.forName("selfish.deck.Oxygen");
		Method method = clazz.getMethod("splitOxygen", Oxygen);
		Class returns = method.getReturnType();
		assertTrue(returns.isArray());
		assertEquals(Oxygen, returns.getComponentType());
	}

	@Test
	public void testStartGameReturnsVoid() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("startGame");
		assertEquals(Void.TYPE, method.getReturnType());
	}

	@Test
	public void testStartTurnReturnsVoid() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("startTurn");
		assertEquals(Void.TYPE, method.getReturnType());
	}

	@Test
	public void testTravelReturnsCard() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("travel", Class.forName("selfish.Astronaut"));
		assertEquals(Class.forName("selfish.deck.Card"), method.getReturnType());
	}

}
