/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
import junit.framework.TestCase;
/**
 * Class de test de <code>ImageFilter</code>
 *
 * @author Boris
 * @version $Revision: 1.1.1.1 $
 */
public class ImageFilterTest extends TestCase {
    public void test_accept() throws Exception {
        ImageFilter filter = new ImageFilter();

        assertTrue(filter.accept("/java/lang/toto.gif"));
        assertTrue(filter.accept("/java/lang/toto.GIF"));
        assertTrue(filter.accept("/java/lang/toto.jpeg"));
        assertTrue(filter.accept("/java/lang/toto.JPEG"));
        assertTrue(filter.accept("toto.jpg"));
        assertTrue(filter.accept("toto.JPG"));
        assertFalse(filter.accept("/java/lang/bad/toto.properties"));
    }
}
