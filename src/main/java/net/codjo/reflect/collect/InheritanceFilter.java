/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
import java.lang.reflect.Modifier;
/**
 * Filtre de classe par heritage.
 *
 * @author $Author: gonnot $
 * @version $Revision: 1.2 $
 */
public class InheritanceFilter implements ClassFilter {
    private Class rootClass;
    private boolean withAbstractClasses;

    /**
     * Constructeur de InheritanceFilter
     *
     * @param rootClass Classe / Interface racine
     */
    public InheritanceFilter(Class rootClass) {
        this(rootClass, true);
    }


    /**
     * Constructeur de InheritanceFilter
     *
     * @param rootClass Classe / Interface racine
     * @param withAbstractClasses indique si les classes abstraite sont filtre
     *
     * @throws IllegalArgumentException rootClass non fournit
     */
    public InheritanceFilter(Class rootClass, boolean withAbstractClasses) {
        if (rootClass == null) {
            throw new IllegalArgumentException();
        }
        this.rootClass = rootClass;
        this.withAbstractClasses = withAbstractClasses;
    }

    public boolean accept(String fullClassName) throws ClassNotFoundException {
        Class currentClass = Class.forName(fullClassName);

        if (!withAbstractClasses && Modifier.isAbstract(currentClass.getModifiers())) {
            return false;
        }

        return rootClass.isAssignableFrom(currentClass);
    }
}
