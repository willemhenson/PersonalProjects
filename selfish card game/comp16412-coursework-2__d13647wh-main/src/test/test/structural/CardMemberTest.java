package test.structural;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


@Tag("structural")
@Tag("Card")
public class CardMemberTest {

	static String FQCN = "selfish.deck.Card";
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
	    Constructor c = clazz.getConstructor(String.class, String.class);
	}

	// Field exists

	@Test
	public void testNameExists() {
		assertTrue(ClassMemberHelper.fieldExists("name", fields));
	}

	@Test
	public void testDescriptionExists() {
		assertTrue(ClassMemberHelper.fieldExists("description", fields));
	}

	@Test
	public void testSerialVersionUIDExists() {
		assertTrue(ClassMemberHelper.classHasPublicStaticFinalSerialVersionUID(FQCN));
	}


	// Field modifiers

	@Test
	public void testNameIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("name", fields, AccessType.PRIVATE));
	}

	@Test
	public void testDescriptionIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("description", fields, AccessType.PRIVATE));
	}

	// Field types

	@Test
	public void testDescriptionIsString() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Class tmp = (Class)clazz.getDeclaredField("description").getType();
		assertEquals(String.class, tmp);
	}

	@Test
	public void testNameIsString() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Class tmp = (Class)clazz.getDeclaredField("name").getType();
		assertEquals(String.class, tmp);
	}

	// Method exists

	@Test
	public void testGetDescriptionExists() {
		assertTrue(ClassMemberHelper.methodExists("getDescription", methods));
	}

	@Test
	public void testToStringExists() {
		assertTrue(ClassMemberHelper.methodExists("toString", methods));
	}

    // Method access types

	@Test
	public void testGetDescriptionIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("getDescription", methods, AccessType.PUBLIC));
	}

	@Test
	public void testToStringIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("toString", methods, AccessType.PUBLIC));
	}

	// Method param/return types

	@Test
	public void testGetDescriptionReturnsString() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("getDescription");
		assertEquals(String.class, method.getReturnType());
	}

	@Test
	public void testToStringReturnsString() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("toString");
		assertEquals(String.class, method.getReturnType());
	}

}