package net.codjo.reflect.collect;
import net.codjo.reflect.collect.PreloadClassesMainWrapper.ClassLoader;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
/**
 *
 */
public class PreloadClassesMainWrapperTest {
    private static final StringBuilder LOG_STRING = new StringBuilder();
    private static final ClassLoaderMock CLASS_LOADER_MOCK = new ClassLoaderMock();


    @Test
    public void test_main() throws Exception {
        LOG_STRING.setLength(0);
        PreloadClassesMainWrapper.main(
              new String[]{"net.codjo.truc",
                           PreloadClassesMainWrapperTest.class.getName(),
                           "arg0", "arg1"});

        assertThat(LOG_STRING.toString(), is("[arg0, arg1]"));
    }


    @Test
    public void test_mainWithNoArgument() throws Exception {
        LOG_STRING.setLength(0);
        PreloadClassesMainWrapper.main(
              new String[]{"net.codjo.truc",
                           PreloadClassesMainWrapperTest.class.getName()});

        assertThat(LOG_STRING.toString(), is("[]"));
    }


    @Test
    public void test_mainWithAPackageToExclude() throws Exception {
        LOG_STRING.setLength(0);
        PreloadClassesMainWrapper.setClassLoader(CLASS_LOADER_MOCK);
        PreloadClassesMainWrapper.main(
              new String[]{"net.codjo.truc",
                           "-exclude",
                           "net.codjo.truc.exclude",
                           PreloadClassesMainWrapperTest.class.getName(),
                           "arg0", "arg1"});

        assertThat(LOG_STRING.toString(), is("[arg0, arg1]"));
        assertThat(CLASS_LOADER_MOCK.getPackageToInclude(), is("net.codjo.truc"));
        assertThat(CLASS_LOADER_MOCK.getPackagesToExclude().toString(), is("[net.codjo.truc.exclude]"));
    }


    @Test
    public void test_mainWithSeveralPackagesToExclude() throws Exception {
        LOG_STRING.setLength(0);
        PreloadClassesMainWrapper.setClassLoader(CLASS_LOADER_MOCK);
        PreloadClassesMainWrapper.main(
              new String[]{"net.codjo.truc",
                           "-exclude",
                           "net.codjo.truc.exclude1;net.codjo.truc.exclude2;net.codjo.truc.exclude3.toto",
                           PreloadClassesMainWrapperTest.class.getName(),
                           "arg0", "arg1"});

        assertThat(LOG_STRING.toString(), is("[arg0, arg1]"));
        assertThat(CLASS_LOADER_MOCK.getPackageToInclude(), is("net.codjo.truc"));
        assertThat(CLASS_LOADER_MOCK.getPackagesToExclude().toString(),
                   is("[net.codjo.truc.exclude1, net.codjo.truc.exclude2, net.codjo.truc.exclude3.toto]"));
    }


    public static void main(String[] args) {
        LOG_STRING.append(Arrays.asList(args));
    }


    private static class ClassLoaderMock implements ClassLoader {
        private String packageToInclude;
        private List<String> packagesToExclude;


        public void loadClasses(String packageToInclude, List<String> packagesToExclude) throws Exception {
            this.packageToInclude = packageToInclude;
            this.packagesToExclude = packagesToExclude;
        }


        public String getPackageToInclude() {
            return packageToInclude;
        }


        public List<String> getPackagesToExclude() {
            return packagesToExclude;
        }
    }
}
