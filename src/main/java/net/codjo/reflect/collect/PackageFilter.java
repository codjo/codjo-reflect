/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
/**
 * Filtre de classe par package et sous-package (optionnel).
 *
 * @author $Author: gonnot $
 * @version $Revision: 1.1.1.1 $
 */
public class PackageFilter implements ClassFilter {
    private boolean includeSubPackage;
    private String packageName;

    public PackageFilter(String packageName) {
        this(packageName, true);
    }


    /**
     * Constructeur.
     *
     * @param packageName Nom de package accepte (ex: "java.lang")
     * @param includeSubPackage
     *
     * @throws IllegalArgumentException packageName non fournit
     */
    public PackageFilter(String packageName, boolean includeSubPackage) {
        if (packageName == null) {
            throw new IllegalArgumentException();
        }
        this.packageName = packageName;
        this.includeSubPackage = includeSubPackage;
    }

    public boolean accept(String fullClassName) {
        if (includeSubPackage) {
            return fullClassName.startsWith(packageName);
        }
        else {
            if (fullClassName.startsWith(packageName)) {
                return fullClassName.substring(packageName.length() + 1).indexOf('.') == -1;
            }
            return false;
        }
    }
}
