/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
/**
 * Filtre de classe par exclusion d'une classe, ou d'une ressource.
 *
 * @author $Author: gonnot $
 * @version $Revision: 1.1.1.1 $
 */
public class ExcludeFilter implements ClassFilter, ResourceFilter {
    private String className;

    public ExcludeFilter(Class excludedClass) {
        if (excludedClass == null) {
            throw new IllegalArgumentException();
        }
        this.className = excludedClass.getName();
    }


    public ExcludeFilter(String excludedResources) {
        if (excludedResources == null) {
            throw new IllegalArgumentException();
        }
        this.className = excludedResources;
    }

    public boolean accept(String fullClassName) {
        return !fullClassName.equals(className);
    }
}
