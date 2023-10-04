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

@Tag("structural")
@Tag("Astronaut")
public class AstronautMemberTest {

    static String FQCN = "selfish.Astronaut";
	static Field[] fields;
	static Method[] methods;

	@BeforeAll
	public static void setUp() {
		 fields = ClassMemberHelper.getFields(FQCN);
		 methods = ClassMemberHelper.getMethods(FQCN);
	}

	// Constructor

	@Test
	public void testConstructorExistsAndIsPublic() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		clazz.getConstructor(String.class, Class.forName("selfish.GameEngine"));
	}

	// Field exists

	@Test
	public void testGameExists() {
		assertTrue(ClassMemberHelper.fieldExists("game", fields));
	}

	@Test
	public void testActionsExists() {
		assertTrue(ClassMemberHelper.fieldExists("actions", fields));
	}

	@Test
	public void testOxygensExists() {
		assertTrue(ClassMemberHelper.fieldExists("oxygens", fields));
	}

	@Test
	public void testNameExists() {
		assertTrue(ClassMemberHelper.fieldExists("name", fields));
	}

	@Test
	public void testSerialVersionUIDExists() {
		assertTrue(ClassMemberHelper.classHasPublicStaticFinalSerialVersionUID(FQCN));
	}

	@Test
	public void testTrackExists() {
		assertTrue(ClassMemberHelper.fieldExists("track", fields));
	}

	// Field modifiers

	@Test
	public void testGameIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("game", fields, AccessType.PRIVATE));
	}

	@Test
	public void testActionsIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("actions", fields, AccessType.PRIVATE));
	}

	@Test
	public void testOxygensIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("oxygens", fields, AccessType.PRIVATE));
	}

	@Test
	public void testNameIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("name", fields, AccessType.PRIVATE));
	}

	@Test
	public void testTrackIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("track", fields, AccessType.PRIVATE));
	}

	// Field types

	@Test
	public void testGameIsGameEngine() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Class tmp = (Class)clazz.getDeclaredField("game").getType();
		assertEquals(Class.forName("selfish.GameEngine"), tmp);
	}


	@Test
	public void testActionsIsList() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Field field = clazz.getDeclaredField("actions");
		assertEquals(List.class, (Class)field.getType());
		Type fieldType = field.getGenericType();
		assertTrue(fieldType instanceof ParameterizedType);
		ParameterizedType paramType = (ParameterizedType)fieldType;
		assertEquals(Class.forName("selfish.deck.Card"), (Class)paramType.getActualTypeArguments()[0]);
	}

	@Test
	public void testOxygensIsList() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Class Oxygen = Class.forName("selfish.deck.Oxygen"); 
		Field field = clazz.getDeclaredField("oxygens");
		assertEquals(List.class, (Class)field.getType());
		Type fieldType = field.getGenericType();
		assertTrue(fieldType instanceof ParameterizedType);
		ParameterizedType paramType = (ParameterizedType)fieldType;
		assertEquals(Oxygen, (Class)paramType.getActualTypeArguments()[0]);
	}

	@Test
	public void testNameIsString() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Class tmp = (Class)clazz.getDeclaredField("name").getType();
		assertEquals(String.class, tmp);
	}

	@Test
	public void testTrackIsCollection() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Class Card = Class.forName("selfish.deck.Card");
		Field field = clazz.getDeclaredField("track");
		assertEquals(Collection.class, (Class)field.getType());
		Type fieldType = field.getGenericType();
		assertTrue(fieldType instanceof ParameterizedType);
		ParameterizedType paramType = (ParameterizedType)fieldType;
		assertEquals(Card, (Class)paramType.getActualTypeArguments()[0]);
	}

	// Method exists

	@Test
	public void testAddToHandExists() {
		assertTrue(ClassMemberHelper.methodExists("addToHand", methods));
	}

	@Test
	public void testAddToTrackExists() {
		assertTrue(ClassMemberHelper.methodExists("addToTrack", methods));
	}

	@Test
	public void testBreatheExists() {
		assertTrue(ClassMemberHelper.methodExists("breathe", methods));
	}

	@Test
	public void testDistanceFromShipExists() {
		assertTrue(ClassMemberHelper.methodExists("distanceFromShip", methods));
	}

	@Test
	public void testGetActionsExists() {
		assertTrue(ClassMemberHelper.methodExists("getActions", methods));
	}

	@Test
	public void testGetActionsStrExists() {
		assertTrue(ClassMemberHelper.methodExists("getActionsStr", methods));
	}

	@Test
	public void testGetHandExists() {
		assertTrue(ClassMemberHelper.methodExists("getHand", methods));
	}

	@Test
	public void testGetHandStrExists() {
		assertTrue(ClassMemberHelper.methodExists("getHandStr", methods));
	}

	@Test
	public void testGetTrackExists() {
		assertTrue(ClassMemberHelper.methodExists("getTrack", methods));
	}

	@Test
	public void testHackExists() {
		assertTrue(ClassMemberHelper.methodCount("hack", methods) >= 2);
	}

	@Test
	public void testHasCardExists() {
		assertTrue(ClassMemberHelper.methodExists("hasCard", methods));
	}

	@Test
	public void testHasMeltedEyeballsExists() {
		assertTrue(ClassMemberHelper.methodExists("hasMeltedEyeballs", methods));
	}

	@Test
	public void testHasWonExists() {
		assertTrue(ClassMemberHelper.methodExists("hasWon", methods));
	}

	@Test
	public void testIsAliveExists() {
		assertTrue(ClassMemberHelper.methodExists("isAlive", methods));
	}

	@Test
	public void testLaserBlastExists() {
		assertTrue(ClassMemberHelper.methodExists("laserBlast", methods));
	}

	@Test
	public void testOxygenRemainingExists() {
		assertTrue(ClassMemberHelper.methodExists("oxygenRemaining", methods));
	}

	@Test
	public void testPeekAtTrackExists() {
		assertTrue(ClassMemberHelper.methodExists("peekAtTrack", methods));
	}

	@Test
	public void testSiphonExists() {
		assertTrue(ClassMemberHelper.methodExists("siphon", methods));
	}

	@Test
	public void testStealExists() {
		assertTrue(ClassMemberHelper.methodExists("steal", methods));
	}

	@Test
	public void testSwapTrackExists() {
		assertTrue(ClassMemberHelper.methodExists("swapTrack", methods));
	}

	@Test
	public void testToStringExists() {
		assertTrue(ClassMemberHelper.methodExists("toString", methods));
	}

    // Method access types

	@Test
	public void testAddToHandIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("addToHand", methods, AccessType.PUBLIC));
	}

	@Test
	public void testAddToTrackIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("addToTrack", methods, AccessType.PUBLIC));
	}

	@Test
	public void testBreatheIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("breathe", methods, AccessType.PUBLIC));
	}

	@Test
	public void testDistanceFromShipIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("distanceFromShip", methods, AccessType.PUBLIC));
	}

	@Test
	public void testGetActionsIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("getActions", methods, AccessType.PUBLIC));
	}

	@Test
	public void testGetActionsStrIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("getActionsStr", methods, AccessType.PUBLIC));
	}

	@Test
	public void testGetHandIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("getHand", methods, AccessType.PUBLIC));
	}

	@Test
	public void testGetHandStrIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("getHandStr", methods, AccessType.PUBLIC));
	}

	@Test
	public void testGetTrackIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("getTrack", methods, AccessType.PUBLIC));
	}

	@Test
	public void testHackIsPublic() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		clazz.getMethod("hack", Class.forName("selfish.deck.Card"));
		clazz.getMethod("hack", String.class);
	}

	@Test
	public void testHasCardIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("hasCard", methods, AccessType.PUBLIC));
	}

	@Test
	public void testHasMeltedEyeballsIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("hasMeltedEyeballs", methods, AccessType.PUBLIC));
	}

	@Test
	public void testHasWonIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("hasWon", methods, AccessType.PUBLIC));
	}

	@Test
	public void testIsAliveIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("isAlive", methods, AccessType.PUBLIC));
	}

	@Test
	public void testLaserBlastIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("laserBlast", methods, AccessType.PUBLIC));
	}

	@Test
	public void testOxygenRemainingIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("oxygenRemaining", methods, AccessType.PUBLIC));
	}

	@Test
	public void testPeekAtTrackIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("peekAtTrack", methods, AccessType.PUBLIC));
	}

	@Test
	public void testSiphonIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("siphon", methods, AccessType.PUBLIC));
	}

	@Test
	public void testStealIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("steal", methods, AccessType.PUBLIC));
	}

	@Test
	public void testSwapTrackIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("swapTrack", methods, AccessType.PUBLIC));
	}

	@Test
	public void testToStringIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("toString", methods, AccessType.PUBLIC));
	}

	// Method param/return types

	@Test
	public void testAddToHandReturnsVoid() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Class Card = Class.forName("selfish.deck.Card");
		Method method = clazz.getMethod("addToHand", Card);
		assertEquals(Void.TYPE, method.getReturnType());
	}

	@Test
	public void testAddToTrackReturnsVoid() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Class Card = Class.forName("selfish.deck.Card");
		Method method = clazz.getMethod("addToTrack", Card);
		assertEquals(Void.TYPE, method.getReturnType());
	}

	@Test
	public void testBreatheReturnsInt() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("breathe");
		assertEquals(int.class, method.getReturnType());
	}

	@Test
	public void testDistanceFromShipReturnsInt() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("distanceFromShip");
		assertEquals(int.class, method.getReturnType());
	}

	@Test
	public void testGetActionsReturnsList() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Class Card = Class.forName("selfish.deck.Card");
		Method method = clazz.getMethod("getActions");
		Class returnClass = method.getReturnType();
		assertEquals(List.class, returnClass);
		Type returnType = method.getGenericReturnType();
		assertTrue(returnType instanceof ParameterizedType);
		ParameterizedType paramType = (ParameterizedType) returnType;
		assertEquals(Card, (Class)paramType.getActualTypeArguments()[0]);
	}

	@Test
	public void testGetActionsStrReturnsString() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("getActionsStr", boolean.class, boolean.class);
		assertEquals(String.class, method.getReturnType());
	}

	@Test
	public void testGetHandReturnsList() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Class Card = Class.forName("selfish.deck.Card");
		Method method = clazz.getMethod("getHand");
		Class returnClass = method.getReturnType();
		assertEquals(List.class, returnClass);
		Type returnType = method.getGenericReturnType();
		assertTrue(returnType instanceof ParameterizedType);
		ParameterizedType paramType = (ParameterizedType) returnType;
		assertEquals(Card, (Class)paramType.getActualTypeArguments()[0]);
	}

	@Test
	public void testGetHandStrReturnsString() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("getHandStr");
		assertEquals(String.class, method.getReturnType());
	}

	@Test
	public void testGetTrackReturnsCollection() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Class Card = Class.forName("selfish.deck.Card");
		Method method = clazz.getMethod("getTrack");
		Class returnClass = method.getReturnType();
		assertEquals(Collection.class, returnClass);
		Type returnType = method.getGenericReturnType();
		assertTrue(returnType instanceof ParameterizedType);
		ParameterizedType paramType = (ParameterizedType) returnType;
		assertEquals(Card, (Class)paramType.getActualTypeArguments()[0]);
	}

	@Test
	public void testHackCardParamReturnsVoid() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("hack", Class.forName("selfish.deck.Card"));
		assertEquals(Void.TYPE, method.getReturnType());
	}

	@Test
	public void testHackStringParamReturnsCard() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("hack", String.class);
		assertEquals(Class.forName("selfish.deck.Card"), method.getReturnType());
	}

	@Test
	public void testHasCardReturnsInt() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("hasCard", String.class);
		assertEquals(int.class, method.getReturnType());
	}

	@Test
	public void testHasMeltedEyeballsReturnsBoolean() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("hasMeltedEyeballs");
		assertEquals(boolean.class, method.getReturnType());
	}

	@Test
	public void testHasWonReturnsBoolean() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("hasWon");
		assertEquals(boolean.class, method.getReturnType());
	}

	@Test
	public void testIsAliveReturnsBoolean() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("isAlive");
		assertEquals(boolean.class, method.getReturnType());
	}

	@Test
	public void testLaserBlastReturnsCard() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("laserBlast");
		assertEquals(Class.forName("selfish.deck.Card"), method.getReturnType());
	}

	@Test
	public void testOxygenRemainingReturnsInt() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("oxygenRemaining");
		assertEquals(int.class, method.getReturnType());
	}

	@Test
	public void testPeekAtTrackReturnsCard() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("peekAtTrack");
		assertEquals(Class.forName("selfish.deck.Card"), method.getReturnType());
	}

	@Test
	public void testSiphonReturnsOxygen() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("siphon");
		assertEquals(Class.forName("selfish.deck.Oxygen"), method.getReturnType());
	}

	@Test
	public void testStealReturnsCard() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("steal");
		assertEquals(Class.forName("selfish.deck.Card"), method.getReturnType());
	}

	@Test
	public void testSwapTrackReturnsVoid() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("swapTrack", clazz);
		assertEquals(Void.TYPE, method.getReturnType());
	}

	@Test
	public void testToStringReturnsString() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("toString");
		assertEquals(String.class, method.getReturnType());
	}

}
