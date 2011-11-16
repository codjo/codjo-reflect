/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.reflect.collect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Class de test de <code>ResourceCollector</code>
 *
 * @author Boris
 * @version $Revision: 1.2 $
 */
public class ResourceCollectorTest extends AbstractCollectorTest {
    public void test_addFilter() throws Exception {
        List<String> expectedResourcesFiles = new ArrayList<String>();
        expectedResourcesFiles.add("/javax/swing/plaf/metal/icons/Error.gif");
        expectedResourcesFiles.add("/javax/swing/plaf/metal/icons/Inform.gif");
        expectedResourcesFiles.add("/javax/swing/plaf/metal/icons/Question.gif");
        expectedResourcesFiles.add("/javax/swing/plaf/metal/icons/Warn.gif");

        ResourceCollector collector = new ResourceCollector(String.class);
        collector.addResourceFilter(new FakeResourceFilter());
        List result = Arrays.asList(collector.collect());

        assertContains(expectedResourcesFiles, result);
    }


    public void test_addClassFilter() throws Exception {
        List<String> expectedResourcesFiles = new ArrayList<String>();
        expectedResourcesFiles.add("/javax/swing/JComboBox$1.class");
        expectedResourcesFiles.add("/javax/swing/JComboBox$2.class");
        expectedResourcesFiles.add("/javax/swing/JComboBox$AccessibleJComboBox.class");
        expectedResourcesFiles.add("/javax/swing/JComboBox$DefaultKeySelectionManager.class");
        expectedResourcesFiles.add("/javax/swing/JComboBox$KeySelectionManager.class");
        expectedResourcesFiles.add("/javax/swing/JComboBox.class");

        ResourceCollector collector = new ResourceCollector(String.class);
        collector.setExcludeClassFile(false);
        collector.addResourceFilter(new FakeResourceClassFilter());
        List<String> result = Arrays.asList(collector.collect());

        assertContains(expectedResourcesFiles, result);
    }


    private void assertContains(List<String> expectedResourcesFiles, List result) {
        expectedResourcesFiles.removeAll(result);
        assertEquals(0, expectedResourcesFiles.size());
    }


    static class FakeResourceFilter implements ResourceFilter {
        public boolean accept(String resourceName) {
            return resourceName.startsWith("/javax/swing/plaf") && resourceName.endsWith(".gif");
        }
    }

    static class FakeResourceClassFilter implements ResourceFilter {
        public boolean accept(String resourceName) {
            return resourceName.startsWith("/javax/swing/JComboBox") && resourceName.endsWith(".class");
        }
    }
}
