/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
import junit.framework.TestCase;
/**
 * Class de test de <code>PackageFilter</code>
 *
 * @author Boris
 * @version $Revision: 1.2 $
 */
public class PackageFilterTest extends TestCase {
    public void test_accept_NotIncludeSubPackage()
            throws Exception {
        PackageFilter filter = new PackageFilter("java.lang", false);

        assertTrue(filter.accept("java.lang.String"));
        assertFalse(filter.accept("java.lang.reflect.Field"));
    }


    public void test_accept_includeSubPackage() throws Exception {
        PackageFilter filter = new PackageFilter("java.lang");

        assertTrue(filter.accept("java.lang.String"));
        assertTrue(filter.accept("java.lang.reflect.Field"));
    }
}
