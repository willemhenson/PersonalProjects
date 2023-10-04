package test.structural;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Tag("structural")
@Tag("GameDeck")
public class GameDeckMemberTest {

    static String FQCN = "selfish.deck.GameDeck";
	static Field[] fields;
	static Method[] methods;

	@BeforeAll
	public static void setUp() throws ClassNotFoundException {
		 fields = ClassMemberHelper.getFields(FQCN);
		 methods = ClassMemberHelper.getMethods(FQCN);
	}
	
	// Constructor

	@Test
	public void testEmptyConstructorExistsAndIsPublic() throws ClassNotFoundException, NoSuchMethodException {
    	Class clazz = Class.forName(FQCN);
	    Constructor c = clazz.getConstructor();
	}

	@Test
	public void testStringConstructorExistsAndIsPublic() throws ClassNotFoundException, NoSuchMethodException {
    	Class clazz = Class.forName(FQCN);
	    Constructor c = clazz.getConstructor(String.class);
	}

	// Field exists

	@Test
	public void testSerialVersionUIDExists() {
		assertTrue(ClassMemberHelper.classHasPublicStaticFinalSerialVersionUID(FQCN));
	}

	@Test
	public void testConstantsExist() {
		assertTrue(ClassMemberHelper.fieldExists("HACK_SUIT", fields));
		assertTrue(ClassMemberHelper.fieldExists("HOLE_IN_SUIT", fields));
		assertTrue(ClassMemberHelper.fieldExists("LASER_BLAST", fields));
		assertTrue(ClassMemberHelper.fieldExists("OXYGEN", fields));
		assertTrue(ClassMemberHelper.fieldExists("OXYGEN_1", fields));
		assertTrue(ClassMemberHelper.fieldExists("OXYGEN_2", fields));
		assertTrue(ClassMemberHelper.fieldExists("OXYGEN_SIPHON", fields));
		assertTrue(ClassMemberHelper.fieldExists("ROCKET_BOOSTER", fields));
		assertTrue(ClassMemberHelper.fieldExists("SHIELD", fields));
		assertTrue(ClassMemberHelper.fieldExists("TETHER", fields));
		assertTrue(ClassMemberHelper.fieldExists("TRACTOR_BEAM", fields));
	}

	// Field modifiers

	@Test
	public void testConstantsAreFinal() {
		assertTrue(ClassMemberHelper.fieldIsFinal("HACK_SUIT", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("HOLE_IN_SUIT", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("LASER_BLAST", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("OXYGEN", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("OXYGEN_1", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("OXYGEN_2", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("OXYGEN_SIPHON", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("ROCKET_BOOSTER", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("SHIELD", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("TETHER", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("TRACTOR_BEAM", fields));
	}

	@Test
	public void testConstantsArePublic() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("HACK_SUIT", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("HOLE_IN_SUIT", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("LASER_BLAST", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("OXYGEN", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("OXYGEN_1", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("OXYGEN_2", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("OXYGEN_SIPHON", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("ROCKET_BOOSTER", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("SHIELD", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("TETHER", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("TRACTOR_BEAM", fields, AccessType.PUBLIC));
	}

	@Test
	public void testConstantsAreStatic() {
		assertTrue(ClassMemberHelper.fieldIsStatic("HACK_SUIT", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("HOLE_IN_SUIT", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("LASER_BLAST", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("OXYGEN", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("OXYGEN_1", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("OXYGEN_2", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("OXYGEN_SIPHON", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("ROCKET_BOOSTER", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("SHIELD", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("TETHER", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("TRACTOR_BEAM", fields));
	}

	// Field types

	@Test
	public void testConstantsAreString() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Class tmp = (Class)clazz.getDeclaredField("HACK_SUIT").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("HOLE_IN_SUIT").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("LASER_BLAST").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("OXYGEN").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("OXYGEN_1").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("OXYGEN_2").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("OXYGEN_SIPHON").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("ROCKET_BOOSTER").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("SHIELD").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("TETHER").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("TRACTOR_BEAM").getType();
		assertEquals(String.class, tmp);
	}

	// Method exists

	@Test
	public void testDrawOxygenExists() {
		assertTrue(ClassMemberHelper.methodExists("drawOxygen", methods));
	}

	@Test
	public void testSplitOxygenExists() {
		assertTrue(ClassMemberHelper.methodExists("splitOxygen", methods));
	}

    // Method modifiers

	@Test
	public void testDrawOxygenIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("drawOxygen", methods, AccessType.PUBLIC));
	}

	@Test
	public void testSplitOxygenIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("splitOxygen", methods, AccessType.PUBLIC));
	}

    // Method param/return types

	@Test
	public void testDrawOxygenreturnsOxygen() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("drawOxygen", int.class);
		assertEquals(Class.forName("selfish.deck.Oxygen"), method.getReturnType());
	}

	@Test
	public void testSplitOxygenreturnsArray() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Class Oxygen =  Class.forName("selfish.deck.Oxygen");
		Method method = clazz.getMethod("splitOxygen", Oxygen);
		Class returns = method.getReturnType();
		assertTrue(returns.isArray());
		assertEquals(Oxygen, returns.getComponentType());
	}

}
