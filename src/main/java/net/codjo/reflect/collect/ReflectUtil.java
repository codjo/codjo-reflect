/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
import java.io.File;
/**
 * Regroupement de classe utilitaire pour l'intropection de package.
 */
public final class ReflectUtil {
    private ReflectUtil() {
    }


    /**
     * Indique si le nom de fichier est un fichier d'une classe.
     *
     * @param fileName Nom de fichier
     *
     * @return <code>true</code> si nom de fichier d'une classe
     */
    public static boolean isClassFileName(String fileName) {
        return fileName.endsWith(".class");
    }


    /**
     * Convertie un nom de fichier en nom de classe JAVA.
     *
     * @param classFileName Nom de fichier d'une classe (ex : "java/lang/String.class")
     *
     * @return Le nom complet de la classe ("java.lang.String")
     *
     * @throws IllegalArgumentException classFileName n'est pas un nom de classe
     */
    public static String convertToClassName(String classFileName) {
        if (!isClassFileName(classFileName)) {
            throw new IllegalArgumentException();
        }
        return classFileName.substring(0, classFileName.length() - ".class".length())
              .replace('/', '.')
              .replace('\\', '.');
    }


    /**
     * Determine le chemin contenant la definition de la classe.
     *
     * @return retourne le chemin contenant la definition de la classe aClass
     */
    public static String determinePathFrom(Class aClass) {
        String classFile = toClassFile(aClass);
        String urlStr = aClass.getResource(classFile).toString();

        urlStr = urlStr.substring(0, urlStr.length() - classFile.length());
        urlStr = urlStr.replaceAll("%20", " ");

        if (urlStr.startsWith("file")) {
            return new File(urlStr.substring("file:".length())).toString();
        }
        else if (urlStr.startsWith("zip:")) {
            int to = urlStr.indexOf("!");
            return urlStr.substring("zip:".length(), to);
        }
        else {
            int from = "jar:file:".length();
            int to = urlStr.indexOf("!");
            return new File(urlStr.substring(from, to)).getAbsolutePath();
        }
    }


    /**
     * Retourne le nom du fichier contenant la definition de la classe.
     *
     * @return un nom de fichier ".class" (ex : "/java/lang/String.class")
     */
    public static String toClassFile(Class aClass) {
        String className = aClass.getName();
        return "/" + className.replace('.', '/') + ".class";
    }
}
