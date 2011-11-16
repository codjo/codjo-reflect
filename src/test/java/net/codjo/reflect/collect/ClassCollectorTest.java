/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Class de test de <code>ClassCollector</code>
 *
 * @author Boris
 * @version $Revision: 1.1.1.1 $
 */
public class ClassCollectorTest extends AbstractCollectorTest {
    public void test_addFilter() throws Exception {
        List expectedClassFiles = new ArrayList();
        expectedClassFiles.add(JarIterator.class);

        ClassCollector collector = new ClassCollector(JarIterator.class);
        collector.addClassFilter(new FakeClassFilter());
        List result = Arrays.asList(collector.collect());

        assertEquals(expectedClassFiles, result);
    }

    static class FakeClassFilter implements ClassFilter {
        public boolean accept(String fullClassName) {
            return fullClassName.startsWith("net.codjo.reflect.collect")
            && (fullClassName.indexOf("Jar") != -1 && fullClassName.indexOf("Test") == -1);
        }
    }
}
