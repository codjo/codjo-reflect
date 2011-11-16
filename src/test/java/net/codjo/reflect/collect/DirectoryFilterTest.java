/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
import junit.framework.TestCase;
/**
 * Class de test de <code>DirectoryFilter</code>
 *
 * @author Boris
 * @version $Revision: 1.2 $
 */
public class DirectoryFilterTest extends TestCase {
    public void test_accept_NotIncludeSubPackage()
            throws Exception {
        DirectoryFilter filter = new DirectoryFilter("/java/lang", false);

        assertTrue(filter.accept("/java/lang/toto.properties"));
        assertFalse(filter.accept("/java/lang/bad/toto.properties"));
    }


    public void test_accept_includeSubPackage() throws Exception {
        DirectoryFilter filter = new DirectoryFilter("/java/lang");

        assertTrue(filter.accept("/java/lang/toto.properties"));
        assertTrue(filter.accept("/java/lang/bad/toto.properties"));
    }
}
