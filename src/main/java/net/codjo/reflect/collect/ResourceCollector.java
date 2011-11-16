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
 * Collecteur de classe .
 *
 * <p> Cette classe permet de scanner le CLASS_PATH (ou une partie) pour collecter les resources qui
 * respectent les filtres de collecte. </p>
 *
 * <p> Exemple d'utilisation, ce code retourne seulement les images du packages <code>java.lang</code> :
 * <pre>
 *      ResourceCollector collector = new ResourceCollector();
 *      collector.addResourceFilter(new PackageFilter("/java/lang", false));
 *      String[] javaLangClasses = collector.collect();
 *  </pre>
 * </p>
 *
 * @author $Author: duclosm $
 * @version $Revision: 1.3 $
 * @see net.codjo.reflect.collect.InheritanceFilter
 * @see net.codjo.reflect.collect.PackageFilter
 * @see net.codjo.reflect.collect.ExcludeFilter
 */
public class ResourceCollector extends AbstractCollector {
    private List<ResourceFilter> filters = new ArrayList<ResourceFilter>();
    private String[] searchPaths = null;
    private boolean excludeClassFile = true;


    /**
     * Constructeur par défaut. Recherche dans tous le CLASS_PATH.
     */
    public ResourceCollector() {
        searchPaths = parseClassPath();
    }


    /**
     * Constructeur permettant de réduire le chemins de collecte au chemin contenant la classe
     * <code>fromPath</code>.
     */
    public ResourceCollector(Class<String> fromPath) {
        searchPaths = new String[]{ReflectUtil.determinePathFrom(fromPath)};
    }


    public void setExcludeClassFile(boolean exclude) {
        excludeClassFile = exclude;
    }


    /**
     * Ajoute un filtre de collecte.
     *
     * @param cf Le ClassFilter à ajouter.
     *
     * @throws IllegalArgumentException filter vide
     */
    public void addResourceFilter(ResourceFilter cf) {
        if (cf == null) {
            throw new IllegalArgumentException();
        }
        filters.add(cf);
    }


    /**
     * Retourne toutes les resources se trouvant dans le chemin de recherche et respectant les filtres.
     *
     * @return tableau des chemin vers des resources
     */
    public String[] collect() throws IOException {
        List<String> ressources = new ArrayList<String>();
        for (String searchPath : searchPaths) {
            if (new File(searchPath).exists()) {
                ressources.addAll(collectFrom(searchPath));
            }
        }

        return ressources.toArray(new String[ressources.size()]);
    }


    /**
     * Retourne <code>true</code> si la classe est accepté par les filtres.
     *
     * @return True si la classe est accepté par les filtres.
     */
    private boolean accept(String resourceName) {
        if (excludeClassFile && ReflectUtil.isClassFileName(resourceName)) {
            return false;
        }
        for (ResourceFilter filter : filters) {
            if (!filter.accept(resourceName)) {
                return false;
            }
        }
        return true;
    }


    private List<String> collectFrom(String path) throws IOException {
        List<String> classes = new ArrayList<String>();
        for (Iterator iter = newIteratorForPath(path); iter.hasNext();) {
            String resourceName = "/" + iter.next();
            if (accept(resourceName)) {
                classes.add(resourceName);
            }
        }
        return classes;
    }
}
