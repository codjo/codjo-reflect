/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
/**
 * Filtre de classe. Cette classe permet de limiter les classes selectionner par le
 * <code>ClassCollector</code>.
 *
 * @author $Author: gonnot $
 * @version $Revision: 1.1.1.1 $
 *
 * @see net.codjo.reflect.collect.ClassCollector
 */
public interface ClassFilter {
    /**
     * Retourne <code>true</code> si le filtre accepte la classe designé par
     * <code>fullClassName</code>.
     *
     * @param fullClassName Nom complet de classe.
     *
     * @return true si la classe est selectionne
     *
     * @exception ClassNotFoundException
     */
    public boolean accept(String fullClassName) throws ClassNotFoundException;
}
