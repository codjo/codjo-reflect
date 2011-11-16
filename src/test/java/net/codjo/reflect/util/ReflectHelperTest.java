/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.util;
import java.math.BigDecimal;
import java.util.Arrays;
import junit.framework.TestCase;
/**
 * Description of the Class
 *
 * @author $Author: coteg $
 * @version $Revision: 1.2 $
 */
public class ReflectHelperTest extends TestCase {
    private ReflectHelper helper;
    private ReflectHelper helperCplx;

    public void test_findFullPropertyName() throws Exception {
        assertEquals("id", helperCplx.findFullPropertyName("id"));
        assertEquals("bean.label", helperCplx.findFullPropertyName("label"));
    }


    public void test_getPropertyClass() throws Exception {
        assertEquals(Integer.class, helper.getPropertyClass("pimsCode"));
    }


    public void test_getPropertyClass_complexe() throws Exception {
        assertEquals(Integer.class, helperCplx.getPropertyClass("bean.pimsCode"));
    }


    public void test_getPropertyClass_complexe_byShortName()
            throws Exception {
        assertEquals(Integer.class, helperCplx.getPropertyClass("pimsCode"));
    }


    public void test_getPropertyClass_unknownProperty()
            throws Exception {
        try {
            helper.getPropertyClass("bobo");
            fail("La property bobo est inconnue");
        }
        catch (IllegalArgumentException ex) {}
    }


    public void test_getPropertyNames() throws Exception {
        assertEquals("[creationDatetime, label, pimsCode, rate]",
            Arrays.asList(helper.getPropertyNames()).toString());
    }


    public void test_getPropertyNames_complexe() throws Exception {
        assertEquals("[bean.creationDatetime, bean.label, bean.pimsCode, bean.rate, id]",
            Arrays.asList(helperCplx.getPropertyNames()).toString());
    }


    public void test_getPropertyValue() throws Exception {
        BasicBean bean = new BasicBean();
        bean.setLabel("bobo");
        assertEquals("bobo", helper.getPropertyValue("label", bean));
    }


    public void test_getPropertyValue_complexe() throws Exception {
        ComplexBean bean = new ComplexBean();
        bean.getBean().setLabel("bobo");
        assertEquals("bobo", helperCplx.getPropertyValue("bean.label", bean));
    }


    public void test_getPropertyValue_complexe_byShortName()
            throws Exception {
        ComplexBean bean = new ComplexBean();
        bean.getBean().setLabel("bobo");
        assertEquals("bobo", helperCplx.getPropertyValue("label", bean));
    }


    public void test_getPropertyValue_complexe_null()
            throws Exception {
        ComplexBean bean = new ComplexBean();
        bean.setBean(null);
        assertEquals(null, helperCplx.getPropertyValue("bean.label", bean));
    }


    public void test_getShortPropertyNames_complexe()
            throws Exception {
        assertEquals("[creationDatetime, label, pimsCode, rate, id]",
            helperCplx.getShortPropertyNames().toString());
    }


    public void test_getShortPropertyNames_complexe_excepts()
            throws Exception {
        assertEquals("[id]",
            helperCplx.getShortPropertyNames(new String[] {"bean"}).toString());
    }


    public void test_isComplexType() throws Exception {
        assertEquals(true, ReflectHelper.isComplexType(BasicBean.class));
        assertEquals(true, ReflectHelper.isComplexType(ComplexBean.class));
        assertEquals(false, ReflectHelper.isComplexType(String.class));
        assertEquals(false, ReflectHelper.isComplexType(java.sql.Timestamp.class));
        assertEquals(false, ReflectHelper.isComplexType(Double.TYPE));
    }


    public void test_setPropertyValue() throws Exception {
        BasicBean bean = new BasicBean();
        helper.setPropertyValue("label", bean, "bobo");
        assertEquals("bobo", bean.getLabel());
    }


    public void test_setPropertyValue_BigDecimalToScalarDouble()
            throws Exception {
        BasicBean bean = new BasicBean();
        helper.setPropertyValue("rate", bean, new BigDecimal("16.61"));
        assertEquals(16.61, bean.getRate(), 0);
    }


    public void test_setPropertyValue_Timestamp()
            throws Exception {
        ComplexBean bean = new ComplexBean();
        helper = new ReflectHelper(ComplexBean.class);

        helper.setPropertyValue("bean.creationDatetime", bean, new MyTimestamp(100));
        assertEquals(new java.sql.Timestamp(100), bean.getBean().getCreationDatetime());
    }


    public void test_setPropertyValue_complexe() throws Exception {
        ComplexBean bean = new ComplexBean();
        bean.getBean().setLabel("bobo");

        helperCplx.setPropertyValue("bean.label", bean, "other");
        assertEquals("other", bean.getBean().getLabel());
    }


    public void test_setPropertyValue_complexeValue()
            throws Exception {
        ComplexBean bean = new ComplexBean();
        bean.setBean(null);

        helperCplx.setPropertyValue("bean", bean, new BasicBean());
        assertNotNull("Le sous-bean est positionné", bean.getBean());
    }


    public void test_setPropertyValue_complexe_ByShortName()
            throws Exception {
        ComplexBean bean = new ComplexBean();
        bean.getBean().setLabel("bobo");

        helperCplx.setPropertyValue("label", bean, "other");
        assertEquals("other", bean.getBean().getLabel());
    }


    protected void setUp() throws Exception {
        helper = new ReflectHelper(BasicBean.class);
        helperCplx = new ReflectHelper(ComplexBean.class);
    }

    /**
     * Exemple d'entité pour les tests.
     *
     * @author $Author: coteg $
     * @version $Revision: 1.2 $
     */
    public class BasicBean {
        private java.sql.Timestamp creationDatetime;
        private String label;
        private Integer pimsCode;
        private double rate;

        public void setCreationDatetime(java.sql.Timestamp creationDatetime) {
            this.creationDatetime = creationDatetime;
        }


        public void setLabel(String label) {
            this.label = label;
        }


        public void setPimsCode(Integer pimscode) {
            this.pimsCode = pimscode;
        }


        public void setRate(double f) {
            this.rate = f;
        }


        public java.sql.Timestamp getCreationDatetime() {
            return this.creationDatetime;
        }


        public String getLabel() {
            return label;
        }


        public Integer getPimsCode() {
            return pimsCode;
        }


        public double getRate() {
            return rate;
        }
    }


    /**
     * Bean complexe avec un sous bean.
     */
    public class ComplexBean {
        private BasicBean bean = new BasicBean();
        private String id;

        public void setBean(BasicBean b) {
            this.bean = b;
        }


        public void setId(String id) {
            this.id = id;
        }


        public BasicBean getBean() {
            return bean;
        }


        public String getId() {
            return id;
        }
    }


    private class MyTimestamp extends java.sql.Timestamp {
        MyTimestamp(long v) {
            super(v);
        }
    }
}
