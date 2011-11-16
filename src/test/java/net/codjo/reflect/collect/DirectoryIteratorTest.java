/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import junit.framework.TestCase;
/**
 * Class de test de <code>DirectoryIterator</code>
 *
 * @author Boris
 * @version $Revision: 1.2 $
 */
public class DirectoryIteratorTest extends TestCase {
    private String classesDir;

    public void test_pathWithoutEndingSeparator()
            throws Exception {
        // Test le cas ou il n'y a pas de separateur (/) a la fin du chemin.
        List expectedClass = new ArrayList();
        expectedClass.add(new File("net/codjo/reflect/collect/DirectoryIteratorTest.class")
            .toString());

        List foundClass = new ArrayList();
        for (Iterator iter =
                new DirectoryIterator(classesDir.substring(0, classesDir.length()));
                iter.hasNext();) {
            String obj = (String)iter.next();
            if (obj.indexOf("DirectoryIterator") != -1 && obj.endsWith("class")) {
                foundClass.add(obj);
            }
        }

        Collections.sort(expectedClass);
        Collections.sort(foundClass);
        assertEquals(expectedClass, foundClass);
    }


    public void test_readProject_class() throws Exception {
        List expectedClass = new ArrayList();
        expectedClass.add(new File("net/codjo/reflect/collect/DirectoryIteratorTest.class")
            .toString());

        List foundClass = new ArrayList();
        for (Iterator iter = new DirectoryIterator(classesDir); iter.hasNext();) {
            String obj = (String)iter.next();
            if (obj.indexOf("collect" + File.separator + "DirectoryIterator") != -1) {
                foundClass.add(obj);
            }
        }

        Collections.sort(expectedClass);
        Collections.sort(foundClass);
        assertEquals(expectedClass, foundClass);
    }


    public void test_readProject_ressource() throws Exception {
        List expectedClass = new ArrayList();
        expectedClass.add(new File("net/codjo/reflect/collect/DirectoryIteratorTest.class")
            .toString());

        List foundClass = new ArrayList();
        for (Iterator iter = new DirectoryIterator(classesDir); iter.hasNext();) {
            String obj = (String)iter.next();
            if (obj.indexOf("collect" + File.separator + "DirectoryIteratorTest.class") != -1) {
                foundClass.add(obj);
            }
        }

        assertEquals(expectedClass, foundClass);
    }


    public void test_invalidPath() throws Exception {
        Iterator iter = new DirectoryIterator("z:/bobo/Invalid");

        assertFalse(iter.hasNext());
    }


    protected void setUp() throws java.lang.Exception {
        URL url = DirectoryIteratorTest.class.getResource("/net/codjo/reflect/collect/DirectoryIteratorTest.class");
        String urlStr = url.toString();
        int from = "file:".length();
        int to = urlStr.indexOf("net/");
        classesDir = new File(urlStr.substring(from, to)).toString();
    }
}
