/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
/**
 * Filtre de ressource par repertoire et sous-repertoire (optionnel).
 *
 * @author $Author: gonnot $
 * @version $Revision: 1.1.1.1 $
 */
public class DirectoryFilter implements ResourceFilter {
    private boolean includeSubFolder;
    private String folderName;

    public DirectoryFilter(String folderName) {
        this(folderName, true);
    }


    /**
     * Constructeur.
     *
     * @param folderName Nom de repertoire accepte (ex: "/java/lang")
     * @param includeSubFolder
     *
     * @throws IllegalArgumentException folderName incorrecte.
     */
    public DirectoryFilter(String folderName, boolean includeSubFolder) {
        if (folderName == null) {
            throw new IllegalArgumentException();
        }
        this.folderName = folderName;
        this.includeSubFolder = includeSubFolder;
    }

    public boolean accept(String fullResourceName) {
        if (includeSubFolder) {
            return fullResourceName.startsWith(folderName);
        }
        else {
            if (fullResourceName.startsWith(folderName)) {
                return fullResourceName.substring(folderName.length() + 1).indexOf('/') == -1;
            }
            return false;
        }
    }
}
