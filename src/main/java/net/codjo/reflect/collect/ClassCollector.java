/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
/**
 * Collecteur de classe.
 *
 * <p> Cette classe permet de scanner le CLASS_PATH (ou une partie) pour collecter les classes qui respecte
 * les filtres de collecte. </p>
 *
 * <p> Exemple d'utilisation, ce code retourne seulement les classes du package <code>java.lang</code> :
 * <pre>
 *      ClassCollector collector = new ClassCollector();
 *      collector.addClassFilter(new PackageFilter("java.lang", false));
 *      Class[] javaLangClasses = collector.collect();
 *  </pre>
 * </p>
 *
 * @author $Author: gonnot $
 * @version $Revision: 1.2 $
 * @see net.codjo.reflect.collect.InheritanceFilter
 * @see net.codjo.reflect.collect.PackageFilter
 * @see net.codjo.reflect.collect.ExcludeFilter
 */
public class ClassCollector extends AbstractCollector {
    private List<ClassFilter> filters = new ArrayList<ClassFilter>();
    private String[] searchPaths = null;


    /**
     * Constructeur par défaut. Recherche dans tous le CLASS_PATH.
     */
    public ClassCollector() {
        searchPaths = parseClassPath();
    }


    /**
     * Constructeur permettant de réduire le chemins de collecte au chemin contenant la classe
     * <code>fromPath</code>.
     *
     * @throws IllegalArgumentException Classe introuvable
     */
    public ClassCollector(Class fromPath) {
        String path = ReflectUtil.determinePathFrom(fromPath);
        if (path == null || "".equals(path.trim())) {
            throw new IllegalArgumentException("Classe introuvable : " + fromPath);
        }
        searchPaths = new String[]{path};
    }


    /**
     * Ajoute un filtre de collecte.
     *
     * @param cf Le ClassFilter à ajouter.
     *
     * @throws IllegalArgumentException Ajout d'un filtre null.
     */
    public void addClassFilter(ClassFilter cf) {
        if (cf == null) {
            throw new IllegalArgumentException();
        }
        filters.add(cf);
    }


    /**
     * Retourne toutes les classes se trouvant dans le chemin de recherche et respectant les filtres.
     *
     * @return tableau des classes
     *
     * @throws IOException erreur de lecture sur le disque (fichier se trouvant dans le classpath)
     */
    public Class[] collect() throws IOException {
        List<Class> classes = new ArrayList<Class>();
        for (String searchPath : searchPaths) {
            classes.addAll(Arrays.asList(collectFrom(searchPath)));
        }

        return classes.toArray(new Class[classes.size()]);
    }


    /**
     * Retourne <code>true</code> si la classe est accepté par les filtres.
     *
     * @return <code>true</code> si la classe est accepté par les filtres.
     */
    private boolean accept(String fullClassName) {
        try {
            for (ClassFilter filter : filters) {
                if (!filter.accept(fullClassName)) {
                    return false;
                }
            }
        }
        catch (NoClassDefFoundError ex) {
            // Cas normalement impossible (mais qui arrive quand meme !):
            //      Les noms de classe viennent du classPath.
            return false;
        }
        catch (ClassNotFoundException ex) {
            // Cas normalement impossible (mais qui arrive quand meme !):
            //      Les noms de classe viennent du classPath.
            return false;
        }

        return true;
    }


    private Class[] collectFrom(String path) throws IOException {
        if (!new java.io.File(path).exists()) {
            throw new IllegalArgumentException("Bad path " + path);
        }
        List<String> classes = new ArrayList<String>();
        for (Iterator iter = newIteratorForPath(path); iter.hasNext();) {
            String resourceName = (String)iter.next();

            if (ReflectUtil.isClassFileName(resourceName)) {
                String className = ReflectUtil.convertToClassName(resourceName);
                if (accept(className)) {
                    classes.add(className);
                }
            }
        }
        return toClassArray(classes);
    }


    /**
     * Transforme la liste de nom de classe en Object <code>Class</code>.
     *
     * @return un tableau de classe
     *
     * @throws IllegalArgumentException Classe non instantiable.
     */
    private Class[] toClassArray(List<String> classNames) {
        List<Class> classes = new ArrayList<Class>(classNames.size());
        for (String name : classNames) {
            try {
                classes.add(Class.forName(name));
            }
            catch (NoClassDefFoundError ex) {
                // Cas normalement impossible (mais qui arrive quand meme !):
                //      Les noms de classe viennent du classPath.
                throw new IllegalArgumentException(ex.getMessage());
            }
            catch (ClassNotFoundException ex) {
                // Cas normalement impossible :
                //      Les noms de classe viennent du classPath.
                throw new IllegalArgumentException(ex.getMessage());
            }
        }

        return classes.toArray(new Class[classes.size()]);
    }
}
