package test.structural;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Tag("structural")
@Tag("SpaceDeck")
public class SpaceDeckMemberTest {

    static String FQCN = "selfish.deck.SpaceDeck";
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
	public void testSringConstructorExistsAndIsPublic() throws ClassNotFoundException, NoSuchMethodException {
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
		assertTrue(ClassMemberHelper.fieldExists("ASTEROID_FIELD", fields));
		assertTrue(ClassMemberHelper.fieldExists("BLANK_SPACE", fields));
		assertTrue(ClassMemberHelper.fieldExists("COSMIC_RADIATION", fields));
		assertTrue(ClassMemberHelper.fieldExists("GRAVITATIONAL_ANOMALY", fields));
		assertTrue(ClassMemberHelper.fieldExists("HYPERSPACE", fields));
		assertTrue(ClassMemberHelper.fieldExists("METEOROID", fields));
		assertTrue(ClassMemberHelper.fieldExists("MYSTERIOUS_NEBULA", fields));
		assertTrue(ClassMemberHelper.fieldExists("SOLAR_FLARE", fields));
		assertTrue(ClassMemberHelper.fieldExists("USEFUL_JUNK", fields));
		assertTrue(ClassMemberHelper.fieldExists("WORMHOLE", fields));
	}

	// Field modifiers

	@Test
	public void testConstantsAreFinal() {
		assertTrue(ClassMemberHelper.fieldIsFinal("ASTEROID_FIELD", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("BLANK_SPACE", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("COSMIC_RADIATION", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("GRAVITATIONAL_ANOMALY", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("HYPERSPACE", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("METEOROID", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("MYSTERIOUS_NEBULA", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("SOLAR_FLARE", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("USEFUL_JUNK", fields));
		assertTrue(ClassMemberHelper.fieldIsFinal("WORMHOLE", fields));
	}

	@Test
	public void testConstantsArePublic() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("ASTEROID_FIELD", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("BLANK_SPACE", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("COSMIC_RADIATION", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("GRAVITATIONAL_ANOMALY", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("HYPERSPACE", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("METEOROID", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("MYSTERIOUS_NEBULA", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("SOLAR_FLARE", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("USEFUL_JUNK", fields, AccessType.PUBLIC));
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("WORMHOLE", fields, AccessType.PUBLIC));
	}

	@Test
	public void testConstantsAreStatic() {
		assertTrue(ClassMemberHelper.fieldIsStatic("ASTEROID_FIELD", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("BLANK_SPACE", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("COSMIC_RADIATION", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("GRAVITATIONAL_ANOMALY", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("HYPERSPACE", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("METEOROID", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("MYSTERIOUS_NEBULA", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("SOLAR_FLARE", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("USEFUL_JUNK", fields));
		assertTrue(ClassMemberHelper.fieldIsStatic("WORMHOLE", fields));
	}

	// Field types

	@Test
	public void testConstantsAreString() throws ClassNotFoundException, NoSuchFieldException {
    	Class clazz = Class.forName(FQCN);
		Class tmp = (Class)clazz.getDeclaredField("ASTEROID_FIELD").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("BLANK_SPACE").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("COSMIC_RADIATION").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("GRAVITATIONAL_ANOMALY").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("HYPERSPACE").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("METEOROID").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("MYSTERIOUS_NEBULA").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("SOLAR_FLARE").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("USEFUL_JUNK").getType();
		assertEquals(String.class, tmp);
		tmp = (Class)clazz.getDeclaredField("WORMHOLE").getType();
		assertEquals(String.class, tmp);
	}
	
}
