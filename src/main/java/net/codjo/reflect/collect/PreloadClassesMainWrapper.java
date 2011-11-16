package net.codjo.reflect.collect;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 *
 */
public class PreloadClassesMainWrapper {

    interface ClassLoader {
        void loadClasses(String packageToInclude, List<String> packagesToExclude) throws Exception;
    }

    private static ClassLoader classLoader = new DefaultClassLoader();


    private PreloadClassesMainWrapper() {
    }


    public static void main(String[] args) throws Exception {
        String packageName = args[0];
        String mainClass = null;

        List<String> packagesToExclude = new ArrayList<String>();
        if (args.length > 3) {
            if ("-exclude".equals(args[1])) {
                String packages = args[2];
                packagesToExclude = Arrays.asList(packages.split(";"));
                mainClass = args[3];
            }
        }

        if (mainClass == null) {
            mainClass = args[1];
        }

        List<String> arguments = new ArrayList<String>(Arrays.asList(args));
        arguments.remove(0);
        arguments.remove(0);

        if (!packagesToExclude.isEmpty()) {
            arguments.remove(0);
            arguments.remove(0);
        }

        classLoader.loadClasses(packageName, packagesToExclude);

        Class clazz = Class.forName(mainClass);
        Method meth = clazz.getMethod("main", String[].class);
        meth.invoke(null, new Object[]{arguments.toArray(new String[arguments.size()])});
    }


    public static void setClassLoader(ClassLoader classLoader) {
        PreloadClassesMainWrapper.classLoader = classLoader;
    }


    private static class DefaultClassLoader implements ClassLoader {
        public void loadClasses(final String packageToInclude, final List<String> packagesToExclude)
              throws Exception {
            ClassCollector classCollector = new ClassCollector();

            classCollector.addClassFilter(new ClassFilter() {
                public boolean accept(String fullClassName) throws ClassNotFoundException {
                    for (String packageToExclude : packagesToExclude) {
                        if (fullClassName.startsWith(packageToExclude)) {
                            return false;
                        }
                    }
                    if (fullClassName.startsWith(packageToInclude)) {
                        Class.forName(fullClassName);
                    }
                    return false;
                }
            });

            classCollector.collect();
        }
    }
}
