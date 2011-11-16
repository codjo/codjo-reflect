/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
/**
 * Description of the Class
 *
 * @author $Author: blazart $
 * @version $Revision: 1.3 $
 */
class AbstractCollector {
    /**
     * Retourne un iterateur adapté pour le chemin specifié.
     *
     * @return un iterateur
     */
    protected Iterator newIteratorForPath(String path) throws IOException {
        if (path.endsWith(".jar")) {
            try {
                return new JarIterator(path);
            }
            catch (IOException ex) {
                throw new IOException("Erreur d'acces sur le fichier : " + path + " - " + ex.getMessage());
            }
        }
        else {
            return new DirectoryIterator(path);
        }
    }


    /**
     * Parse Le CLASS_PATH, et le decoupe dans un tableau de String.
     *
     * @return Chaque ligne contient un chemin.
     */
    static String[] parseClassPath() {
        return parsePath(System.getProperty("java.class.path"));
    }


    /**
     * Parse La String contenant une liste de chemin, et le decoupe dans un tableau de String.
     *
     * @return Chaque ligne contient un chemin.
     */
    static String[] parsePath(String classPath) {
        StringTokenizer tokenizer = new StringTokenizer(classPath, java.io.File.pathSeparator);
        List<String> paths = new ArrayList<String>();
        while (tokenizer.hasMoreTokens()) {
            paths.add(tokenizer.nextToken());
        }
        return paths.toArray(new String[paths.size()]);
    }
}
