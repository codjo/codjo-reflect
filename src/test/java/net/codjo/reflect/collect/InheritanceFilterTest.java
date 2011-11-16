/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
/**
 * Class de test de <code>InheritanceFilter</code>
 *
 * @author Boris
 * @version $Revision: 1.3 $
 */
public class InheritanceFilterTest extends TestCase {
    public void test_abstractClass_No() throws Exception {
        InheritanceFilter filter = new InheritanceFilter(ClassFilter.class, false);

        assertTrue(filter.accept(InheritanceFilter.class.getName()));

        assertFalse(filter.accept(AbstractClassFilter.class.getName()));
    }


    public void test_abstractClass_Yes() throws Exception {
        InheritanceFilter filter = new InheritanceFilter(ClassFilter.class, true);

        assertTrue(filter.accept(InheritanceFilter.class.getName()));

        assertTrue("Abstrait", filter.accept(AbstractClassFilter.class.getName()));
    }


    public void test_directInheritance() throws Exception {
        InheritanceFilter filter = new InheritanceFilter(Object.class);
        assertTrue(filter.accept(ReflectUtil.class.getName()));
    }


    public void test_directInheritance_interface()
          throws Exception {
        InheritanceFilter filter = new InheritanceFilter(ClassFilter.class);
        assertTrue(filter.accept(InheritanceFilter.class.getName()));
    }


    public void test_undirectInheritance() throws Exception {
        InheritanceFilter filter = new InheritanceFilter(Assert.class);
        assertTrue(filter.accept(InheritanceFilterTest.class.getName()));
    }


    public void test_undirectInheritance_interface()
          throws Exception {
        InheritanceFilter filter = new InheritanceFilter(Test.class);
        assertTrue(filter.accept(InheritanceFilterTest.class.getName()));
    }


    static interface AbstractClassFilter extends ClassFilter {
    }
}
