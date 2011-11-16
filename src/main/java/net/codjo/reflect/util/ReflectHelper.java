/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.util;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
/**
 * Ensemble de méthodes utilitaires pour faciliter l'uitilisation de la reflection sur
 * une classe particulière.
 *
 * @author $Author: coteg $
 * @version $Revision: 1.4 $
 */
public class ReflectHelper {
    private static final String[] EMPTY_STRING_ARRAY = new String[] {};
    private static final Object[] NO_ARG = {};
    private Map beanPropertyMap = new TreeMap();
    private Map entityPropertyMap = new TreeMap();
    private Map subHelpers = new TreeMap();
    private Class beanClass;

    public ReflectHelper(Class beanClass) throws IntrospectionException {
        this.beanClass = beanClass;
        buildDescriptors();
    }

    public void setPropertyValue(final String propsName, final Object bean,
        final Object value) {
        try {
            String fullName = findFullPropertyName(propsName);
            int idx = fullName.indexOf('.');
            if (idx == -1) {
                Class propsClass = getPropertyClass(fullName);
                getPropertyDescriptor(fullName).getWriteMethod().invoke(bean,
                    valueToArgs(value, propsClass));
            }
            else {
                String beanPropertyName = fullName.substring(0, idx);
                ReflectHelper subHelp = (ReflectHelper)subHelpers.get(beanPropertyName);
                subHelp.setPropertyValue(fullName.substring(idx + 1, fullName.length()),
                    getBeanPropertyDescriptor(beanPropertyName).getReadMethod().invoke(bean,
                        NO_ARG), value);
            }
        }
        catch (IllegalAccessException ex) {
            throw new IllegalArgumentException("Setter inaccessible " + propsName);
        }
        catch (InvocationTargetException ex) {
            throw new IllegalArgumentException("Erreur lors du " + "set'" + propsName
                + "' (" + value + ")" + ": " + ex.getTargetException());
        }
        catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Erreur lors du " + "set'" + propsName
                + "' (" + value + ")" + ": " + ex);
        }
    }


    public Class getBeanClass() {
        return beanClass;
    }


    /**
     * Retourne la classe de la property.
     *
     * @param propertyName le nom de la property
     *
     * @return la classe
     */
    public Class getPropertyClass(final String propertyName) {
        return getPropertyDescriptor(findFullPropertyName(propertyName)).getPropertyType();
    }


    /**
     * Les nom des property de la classe (définie au niveau de la classe).
     *
     * @return les property de la classe.
     */
    public String[] getPropertyNames() {
        return (String[])entityPropertyMap.keySet().toArray(EMPTY_STRING_ARRAY);
    }


    /**
     * Retourne la valeur de la property stockée dans l'objet.
     *
     * @param propertyName nom
     * @param bean instance
     *
     * @return la valeur
     *
     * @exception IllegalArgumentException la property n'existe pas, ou erreur dans le
     *            get.
     */
    public Object getPropertyValue(final String propertyName, final Object bean) {
        if (bean == null) {
            return null;
        }
        try {
            String fullName = findFullPropertyName(propertyName);
            int idx = fullName.indexOf('.');
            if (idx == -1) {
                return getPropertyDescriptor(fullName).getReadMethod().invoke(bean, NO_ARG);
            }
            else {
                String beanPropertyName = fullName.substring(0, idx);
                ReflectHelper subHelp = (ReflectHelper)subHelpers.get(beanPropertyName);
                return subHelp.getPropertyValue(fullName.substring(idx + 1,
                        fullName.length()),
                    getBeanPropertyDescriptor(beanPropertyName).getReadMethod().invoke(bean,
                        NO_ARG));
            }
        }
        catch (IllegalAccessException ex) {
            throw new IllegalArgumentException("Getter inaccessible " + propertyName);
        }
        catch (InvocationTargetException ex) {
            throw new IllegalArgumentException("Erreur lors du " + "get'" + propertyName
                + "' : " + ex.getTargetException());
        }
    }


    /**
     * Les nom court des property de la classe (définie au niveau de la classe). un nom
     * court de property n'est pas prefixe par le nom de l'attribut qui le contient.
     * 
     * <p>
     * Exemple : Soit un bean A contenant un Bean B dans une property nommé beanB, alors
     * la property de B nommé propDeB est accessible par beanB.propDeB .
     * </p>
     *
     * @return les property de la classe.
     */
    public List getShortPropertyNames() {
        return getShortPropertyNames(EMPTY_STRING_ARRAY);
    }


    /**
     * Les nom court des property de la classe (définie au niveau de la classe). un nom
     * court de property n'est pas prefixe par le nom de l'attribut qui le contient.
     * 
     * <p>
     * Exemple : Soit un bean A contenant un Bean B dans une property nommé beanB, alors
     * la property de B nommé propDeB est accessible par beanB.propDeB .
     * </p>
     *
     * @param exceptProperties ne mets pas a plat les property definit dans ce tableau
     *
     * @return les property de la classe.
     */
    public List getShortPropertyNames(String[] exceptProperties) {
        List excepts = Arrays.asList(exceptProperties);
        List list = new ArrayList();
        for (Iterator i = propertyNames(); i.hasNext();) {
            String propertyName = (String)i.next();
            int idx = propertyName.lastIndexOf('.');
            if (idx != -1) {
                if (!excepts.contains(propertyName.substring(0, idx))) {
                    list.add(propertyName.substring(idx + 1));
                }
            }
            else if (!excepts.contains(propertyName)) {
                list.add(propertyName);
            }
        }
        return list;
    }


    /**
     * Les nom court des property de la classe (définie au niveau de la classe).
     *
     * @return un iterateur.
     */
    public Iterator shortPropertyNames() {
        return getShortPropertyNames().iterator();
    }


    public Iterator shortPropertyNames(String[] exceptProperties) {
        return getShortPropertyNames(exceptProperties).iterator();
    }


    public String toString() {
        return "Helper(" + getBeanClass() + " / " + Arrays.asList(getPropertyNames())
        + ")";
    }


    static boolean isComplexType(Class clazz) {
        if (clazz.isPrimitive()) {
            return false;
        }
        return clazz.getPackage() == null
        || !clazz.getPackage().getName().startsWith("java");
    }


    String findFullPropertyName(String shortName) {
        for (Iterator i = propertyNames(); i.hasNext();) {
            String fullName = (String)i.next();
            int point = fullName.indexOf('.');
            if (point != -1 && fullName.substring(0, point).equals(shortName)) {
                return shortName;
            }
            if (fullName.endsWith(shortName)) {
                return fullName;
            }
        }
        throw new IllegalArgumentException("Propriété " + shortName + " inconnue sur "
            + getBeanClass());
    }


    private PropertyDescriptor getBeanPropertyDescriptor(String propertyName) {
        PropertyDescriptor props = (PropertyDescriptor)beanPropertyMap.get(propertyName);
        if (props == null) {
            throw new IllegalArgumentException("La propriété " + propertyName
                + " n'est pas défini dans " + beanClass);
        }
        return props;
    }


    private PropertyDescriptor getPropertyDescriptor(String propertyName) {
        PropertyDescriptor props =
            (PropertyDescriptor)entityPropertyMap.get(propertyName);
        if (props == null) {
            props = (PropertyDescriptor)beanPropertyMap.get(propertyName);
            if (props == null) {
                throw new IllegalArgumentException("La propriété " + propertyName
                    + " n'est pas défini dans " + beanClass);
            }
        }
        return props;
    }


    private void buildDescriptors() throws IntrospectionException {
        BeanInfo info = Introspector.getBeanInfo(beanClass);

        PropertyDescriptor[] props = info.getPropertyDescriptors();
        for (int i = 0; i < props.length; i++) {
            String propertyName = props[i].getName();
            if ("class".equals(propertyName)) {
                ;
            }
            else if (isComplexType(props[i].getPropertyType())) {
                ReflectHelper subHelp = new ReflectHelper(props[i].getPropertyType());
                subHelpers.put(propertyName, subHelp);
                beanPropertyMap.put(propertyName, props[i]);
                for (Iterator subI = subHelp.entityPropertyMap.entrySet().iterator();
                        subI.hasNext();) {
                    Map.Entry item = (Map.Entry)subI.next();
                    entityPropertyMap.put(propertyName + "." + item.getKey(),
                        item.getValue());
                }
            }
            else {
                entityPropertyMap.put(propertyName, props[i]);
            }
        }
    }


    private Object convertValue(final Object value, final Class destClass) {
        if (value.getClass() == java.math.BigDecimal.class && destClass == double.class) {
            return new Double(((BigDecimal)value).doubleValue());
        }
        else if (value.getClass() == java.lang.Boolean.class
                && destClass == boolean.class) {
            return value;
        }
        else if (value.getClass() == java.lang.Integer.class && destClass == int.class) {
            return value;
        }
        else if (value instanceof java.sql.Timestamp && destClass == java.sql.Date.class) {
            return new java.sql.Date(((java.sql.Timestamp)value).getTime());
        }
        else {
            throw new IllegalArgumentException("Conversion de type inconnu, " + " de "
                + value.getClass() + " vers " + destClass);
        }
    }


    private boolean needsConversion(final Object value, final Class propsClass) {
        if (value == null) {
            return false;
        }
        else if (value.getClass() == propsClass) {
            return false;
        }
        else {
            return !propsClass.isAssignableFrom(value.getClass());
        }
    }


    /**
     * Les nom des property de la classe (définie au niveau de la classe).
     *
     * @return un iterateur.
     */
    private Iterator propertyNames() {
        return entityPropertyMap.keySet().iterator();
    }


    private Object[] valueToArgs(final Object value, Class propsClass) {
        if (needsConversion(value, propsClass)) {
            return new Object[] {convertValue(value, propsClass)};
        }
        else {
            return new Object[] {value};
        }
    }
}
