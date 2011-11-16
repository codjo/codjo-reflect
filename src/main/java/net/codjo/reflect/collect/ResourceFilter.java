/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
/**
 * Filtre de ressource. Cette classe permet de limiter les ressources selectionnées par
 * le <code>ResourceCollector</code>.
 *
 * @author $Author: GONNOT $
 * @version $Revision: 1.1.1.1 $
 *
 * @see net.codjo.reflect.collect.ResourceCollector
 */
public interface ResourceFilter {
    /**
     * Retourne <code>true</code> si le filtre accepte la ressource designé par
     * <code>resourcePath</code>.
     *
     * @param resourcePath
     *
     * @return true si la classe est selectionne
     */
    public boolean accept(String resourcePath);
}
