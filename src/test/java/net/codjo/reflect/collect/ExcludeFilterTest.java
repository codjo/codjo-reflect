/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
import junit.framework.TestCase;
/**
 * DOCUMENT ME!
 *
 * @version $Revision: 1.1.1.1 $
 */
public class ExcludeFilterTest extends TestCase {
    public void test_filter_error() throws Exception {
        try {
            new ExcludeFilter((Class)null);
            fail("filtre null est interdit");
        }
        catch (Exception e) {}

        try {
            new ExcludeFilter((String)null);
            fail("filtre null est interdit");
        }
        catch (Exception e) {}
    }


    public void test_filter_byClass() throws Exception {
        ExcludeFilter filter = new ExcludeFilter(TestCase.class);
        assertFalse("La classe TestCase est filtre",
            filter.accept(TestCase.class.getName()));
        assertTrue("La classe Object n'est pas filtre",
            filter.accept(Object.class.getName()));
    }


    public void test_filter_byRessource() throws Exception {
        ExcludeFilter filter = new ExcludeFilter("/junit/toto.gif");
        assertFalse("La ressource 'toto.gif' est filtre", filter.accept("/junit/toto.gif"));
        assertTrue("La ressource 'noemi.png' n'est pas filtre", filter.accept("noemi.png"));
    }
}
