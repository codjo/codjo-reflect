/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
/**
 * Iterateur sur les Classes contenu dans un fichier jar.
 *
 * @author Boris
 * @version $Revision: 1.2 $
 */
class JarIterator implements Iterator {
    private String current = null;
    private Enumeration enumEntries;

    JarIterator(String jarFileName) throws IOException {
        JarFile jarFile = new JarFile(new File(jarFileName), false);
        enumEntries = jarFile.entries();
    }

    public boolean hasNext() {
        return getCurrent() != null;
    }


    public Object next() {
        if (hasNext()) {
            Object next = getCurrent();
            current = null;
            return next;
        }
        else {
            throw new NoSuchElementException();
        }
    }


    public void remove() {
        throw new java.lang.UnsupportedOperationException();
    }


    /**
     * Retourne l'attribut current de JarClassIterator
     *
     * @return La valeur de current
     */
    private String getCurrent() {
        while (current == null && enumEntries.hasMoreElements()) {
            JarEntry entry = (JarEntry)enumEntries.nextElement();
            if (!entry.isDirectory()) {
                current = entry.getName();
            }
        }
        return current;
    }
}
