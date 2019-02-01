package lc.gs;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LongerNextQueryStrategyImplTest {
    
    private LongerNextQueryStrategyImpl strategy = new LongerNextQueryStrategyImpl(); 
    
    @Test
    public void testHasMoreWords() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = strategy.getClass().getDeclaredMethod("hasMoreWords", String.class, String.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(null, "one:word", "oneword"));
        assertFalse((Boolean) method.invoke(null, "onelongerword", "oneword"));
        assertFalse((Boolean) method.invoke(null, "one:word", "one:word second"));
        
        assertTrue((Boolean) method.invoke(null, "one:word", ""));
    }
    
    @Test
    public void testDropFirstWord() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = strategy.getClass().getDeclaredMethod("dropFirstWord", String.class);
        method.setAccessible(true);
        assertEquals("", method.invoke(null, "one;word"));
        assertEquals("words", method.invoke(null, "two words"));
        assertEquals("", method.invoke(null, ""));
    }

}
