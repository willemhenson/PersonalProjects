package test.structural;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Tag("structural")
@Tag("Oxygen")
public class OxygenMemberTest {

	static String FQCN = "selfish.deck.Oxygen";
	static Field[] fields;
	static Method[] methods;

	@BeforeAll
	public static void setUp() throws ClassNotFoundException {
		 fields = ClassMemberHelper.getFields(FQCN);
		 methods = ClassMemberHelper.getMethods(FQCN);
	}

	// Constructor

	@Test
	public void testConstructorExistsAndIsPublic() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
	    Constructor c = clazz.getConstructor(int.class);
	}

	// Field exists

	@Test
	public void testSerialVersionUIDExists() {
		assertTrue(ClassMemberHelper.classHasPublicStaticFinalSerialVersionUID(FQCN));
	}

	@Test
	public void testValueExists() {
		assertTrue(ClassMemberHelper.fieldExists("value", fields));
	}

	// Field access types

	@Test
	public void testValueIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("value", fields, AccessType.PRIVATE));
	}

	// Field types

	@Test
	public void testValueIsInt() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Class tmp = (Class)clazz.getDeclaredField("value").getType();
		assertEquals(int.class, tmp);
	}

	// Method exists

	@Test
	public void testGetValueExists() {
		assertTrue(ClassMemberHelper.methodExists("getValue", methods));
	}

	@Test
	public void testToStringExists() {
		assertTrue(ClassMemberHelper.methodExists("toString", methods));
	}

    // Method access types

	@Test
	public void testGetValueIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("getValue", methods, AccessType.PUBLIC));
	}

	@Test
	public void testToStringIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("toString", methods, AccessType.PUBLIC));
	}
	
	// Method param/return types

	@Test
	public void testGetValueReturnsInt() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("getValue");
		assertEquals(int.class, method.getReturnType());
	}

	@Test
	public void testToStringReturnsString() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("toString");
		assertEquals(String.class, method.getReturnType());
	}

}
