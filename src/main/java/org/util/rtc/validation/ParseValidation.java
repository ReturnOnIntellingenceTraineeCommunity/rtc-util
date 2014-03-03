package org.util.rtc.validation;

import org.util.rtc.context.Context;
import org.util.rtc.converter.Converter;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

public class ParseValidation implements Serializable {
    private ArrayList<Class> classList = new ArrayList<Class>();

    private ArrayList<Package> packageList = new ArrayList<Package>();

    private Context context = new Context();

    private void fromClassToJSON(Class clazz, Locale locale){
        try {
            context.add(clazz, Converter.toJSON(clazz));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ParseValidation(){}

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    public void makeValidationsFromClasses(Locale locale){
        for(Class cl : classList){
            fromClassToJSON(cl, locale);
        }
    }

    public void makeValidationsFromPackages(Locale locale) throws IOException, ClassNotFoundException {
        for(Package pack : packageList){
        Class[] classes = getClasses(pack.getName());
        for(Class cl : classes){
                fromClassToJSON(cl, locale);
            }
        }
    }

    public ArrayList<Class> getClassList() {
        return classList;
    }

    public void setClassList(ArrayList<Class> classList) {
        this.classList = classList;
    }

    public ArrayList<Package> getPackageList() {
        return packageList;
    }

    public void setPackageList(ArrayList<Package> packageList) {
        this.packageList = packageList;
    }
}
