/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
import junit.framework.TestCase;
/**
 * Class de test de <code>AbstractCollector</code>
 *
 * @version $Revision: 1.1.1.1 $
 */
public class AbstractCollectorTest extends TestCase {
    public void test_determinePathFrom() {
        assertTrue("Chemin pour une classe java.lang (Dans un jar)",
                   ReflectUtil.determinePathFrom(String.class).endsWith("rt.jar"));

        assertTrue("Chemin pour une classe hors un jar",
                   !ReflectUtil.determinePathFrom(AbstractCollector.class).endsWith(".jar"));
    }


    public void test_parsePath() throws Exception {
        String[] classPaths = AbstractCollector.parsePath("a;b;c");
        assertEquals("Nombre de chemin", 3, classPaths.length);
        assertEquals("a", classPaths[0]);
        assertEquals("b", classPaths[1]);
        assertEquals("c", classPaths[2]);
    }


    public void test_toClassFile() {
        assertEquals("/java/lang/String.class",
                     ReflectUtil.toClassFile(String.class));
    }
}
