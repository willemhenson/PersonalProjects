package test.structural;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
@Tag("Deck")
public class DeckMemberTest {

	static String FQCN = "selfish.deck.Deck";
	static Field[] fields;
	static Method[] methods;

	@BeforeAll
	public static void setUp() {
        fields = ClassMemberHelper.getFields(FQCN);
		methods = ClassMemberHelper.getMethods(FQCN);
	}

	// Constructor

	@Test
	public void testConstructorIsProtected() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Exception exception = assertThrows(NoSuchMethodException.class, () -> {
		    clazz.getConstructor();
		});
		exception = assertThrows(NoSuchMethodException.class, () -> {
		    clazz.getConstructor(String.class);
		});
	    Constructor c = clazz.getDeclaredConstructor();
		assertTrue(ClassMemberHelper.isAccessType(c.getModifiers(), AccessType.PROTECTED));
	}

	// Field exists

	@Test
	public void testCardsExists() {
		assertTrue(ClassMemberHelper.fieldExists("cards", fields));
	}

	@Test
	public void testSerialVersionUIDExists() {
		assertTrue(ClassMemberHelper.classHasPublicStaticFinalSerialVersionUID(FQCN));
	}

	// Field access types

	@Test
	public void testCardsIsPrivate() {
		assertTrue(ClassMemberHelper.fieldAccessIsAccessType("cards", fields, AccessType.PRIVATE));
	}

	// Field types

	@Test
	public void testCardsIsCollection() throws ClassNotFoundException, NoSuchFieldException {
		Class clazz = Class.forName(FQCN);
		Class Card = Class.forName("selfish.deck.Card");
		Field field = clazz.getDeclaredField("cards");
		assertEquals(Collection.class, (Class)field.getType());
		Type fieldType = field.getGenericType();
		assertTrue(fieldType instanceof ParameterizedType);
		ParameterizedType paramType = (ParameterizedType)fieldType;
		assertEquals(Card, (Class)paramType.getActualTypeArguments()[0]);
	}

	// Method exists

	@Test
	public void testLoadCardsExists() {
		assertTrue(ClassMemberHelper.methodExists("loadCards", methods));
	}

	@Test
	public void testStringToCardsExists() {
		assertTrue(ClassMemberHelper.methodExists("stringToCards", methods));
	}

	@Test
	public void testAddExists() {
		assertTrue(ClassMemberHelper.methodExists("add", methods));
	}

	@Test
	public void testMultipleAddMethodsExist() {
		assertTrue(ClassMemberHelper.methodCount("add", methods) >= 2);
	}

	@Test
	public void testDrawExists() {
		assertTrue(ClassMemberHelper.methodExists("draw", methods));
	}

	@Test
	public void testRemoveExists() {
		assertTrue(ClassMemberHelper.methodExists("remove", methods));
	}

	@Test
	public void testShuffleExists() {
		assertTrue(ClassMemberHelper.methodExists("shuffle", methods));
	}

	@Test
	public void testSizeExists() {
		assertTrue(ClassMemberHelper.methodExists("size", methods));
	}

    // Method modifiers

	@Test
	public void testLoadCardsIsStatic() {
		assertTrue(ClassMemberHelper.methodIsStatic("loadCards", methods));
	}

	@Test
	public void testLoadCardsIsProtected() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("loadCards", methods, AccessType.PROTECTED));
	}

	@Test
	public void testStringToCardsIsProtected() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("stringToCards", methods, AccessType.PROTECTED));
	}

	@Test
	public void testStringToCardsIsStatic() {
		assertTrue(ClassMemberHelper.methodIsStatic("stringToCards", methods));
	}

	@Test
	public void testAddIsProtected() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("add", methods, AccessType.PROTECTED));
	}

	@Test
	public void testAddIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("add", methods, AccessType.PUBLIC));
	}

	@Test
	public void testDrawIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("draw", methods, AccessType.PUBLIC));
	}

	@Test
	public void testRemoveIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("remove", methods, AccessType.PUBLIC));
	}

	@Test
	public void testShuffleIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("shuffle", methods, AccessType.PUBLIC));
	}

	@Test
	public void testSizeIsPublic() {
		assertTrue(ClassMemberHelper.methodAccessIsAccessType("size", methods, AccessType.PUBLIC));
	}

	// Method param/return types

	@Test
	public void testLoadCardsReturnsList() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Class Card = Class.forName("selfish.deck.Card");
		Method method = clazz.getDeclaredMethod("loadCards", String.class);
		Class returnClass = method.getReturnType();
		assertEquals(List.class, returnClass);
		Type returnType = method.getGenericReturnType();
		assertTrue(returnType instanceof ParameterizedType);
		ParameterizedType paramType = (ParameterizedType) returnType;
		assertEquals(Card, (Class)paramType.getActualTypeArguments()[0]);
	}

	@Test
	public void testStringToCardsReturnsArray() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Class Card = Class.forName("selfish.deck.Card");
		Method method = clazz.getDeclaredMethod("stringToCards", String.class);
		Class returns = method.getReturnType();
		assertTrue(returns.isArray());
		assertEquals(Card, returns.getComponentType());
	}

	@Test
	public void testAddCardParamReturnsInt() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Class Card = Class.forName("selfish.deck.Card");
		Method method = clazz.getMethod("add", Card);
		assertEquals(int.class, method.getReturnType());
	}

	@Test
	public void testAddListParamReturnsInt() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Class Card = Class.forName("selfish.deck.Card");
		Method method = clazz.getDeclaredMethod("add", List.class);
		ParameterizedType listType = (ParameterizedType)method.getGenericParameterTypes()[0];
		assertEquals(Card, (Class)listType.getActualTypeArguments()[0]);
		assertEquals(int.class, method.getReturnType());
	}

	@Test
	public void testDrawReturnsCard() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Class Card = Class.forName("selfish.deck.Card");
		Method method = clazz.getMethod("draw");
		assertEquals(Card, method.getReturnType());
	}

	@Test
	public void testRemoveReturnsVoid() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Class Card = Class.forName("selfish.deck.Card");
		Method method = clazz.getMethod("remove", Card);
		assertEquals(Void.TYPE, method.getReturnType());
	}

	@Test
	public void testShuffleReturnsVoid() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("shuffle", Random.class);
		assertEquals(Void.TYPE, method.getReturnType());
	}

	@Test
	public void testSizeReturnsInt() throws ClassNotFoundException, NoSuchMethodException {
		Class clazz = Class.forName(FQCN);
		Method method = clazz.getMethod("size");
		assertEquals(int.class, method.getReturnType());
	}

}