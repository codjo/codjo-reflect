/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
/**
 * Filtre de ressource des images .
 *
 * @author $Author: gonnot $
 * @version $Revision: 1.1.1.1 $
 */
public class ImageFilter implements ResourceFilter {
    public ImageFilter() {}

    public boolean accept(String fullResourceName) {
        return fullResourceName.endsWith("gif") || fullResourceName.endsWith("GIF")
        || fullResourceName.endsWith("jpeg") || fullResourceName.endsWith("JPEG")
        || fullResourceName.endsWith("jpg") || fullResourceName.endsWith("JPG");
    }
}
