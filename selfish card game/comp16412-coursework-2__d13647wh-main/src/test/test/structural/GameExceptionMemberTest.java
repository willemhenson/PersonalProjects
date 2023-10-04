package test.structural;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

@Tag("structural")
@Tag("GameException")
public class GameExceptionMemberTest {

	// Constructor

	@Test
	public void testConstructorExistsAndIsPublic() throws ClassNotFoundException, NoSuchMethodException {
    	Class clazz = Class.forName("selfish.GameException");
	    Constructor c = clazz.getConstructor( String.class, Throwable.class);
	}

}
