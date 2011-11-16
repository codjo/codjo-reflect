/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Iterateur sur les Classes contenu dans un repertoire.
 *
 * @author Boris
 * @version $Revision: 1.2 $
 */
class DirectoryIterator implements Iterator {
    private List allFiles = new ArrayList();
    private Iterator iter;
    private String rootPath;

    /**
     * Constructeur.
     *
     * @param directory om du fichier objs (ex : "/D:/classes)
     *
     * @exception IOException Fichier introuvable
     */
    DirectoryIterator(String directory) throws IOException {
        rootPath = directory;
        if (!rootPath.endsWith("\\") && !rootPath.endsWith("/")) {
            rootPath = rootPath + File.separator;
        }
        addAllFilesFrom(new File(directory));
        iter = allFiles.iterator();
    }

    public boolean hasNext() {
        return iter.hasNext();
    }


    public Object next() {
        return iter.next();
    }


    public void remove() {
        throw new java.lang.UnsupportedOperationException();
    }


    private void addAllFilesFrom(File path) throws IOException {
        if (!path.exists()) {
            return;
        }
        if (path.isFile()) {
            allFiles.add(path.toString().substring(rootPath.length()));
            return;
        }

        File[] files = path.listFiles();
        if (files == null) {
            throw new IOException("Impossible de lire (" + path + ")");
        }
        for (int i = 0; i < files.length; i++) {
            addAllFilesFrom(files[i]);
        }
    }
}
