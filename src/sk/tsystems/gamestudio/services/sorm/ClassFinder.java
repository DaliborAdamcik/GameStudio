package sk.tsystems.gamestudio.services.sorm;
//http://stackoverflow.com/questions/15519626/how-to-get-all-classes-names-in-a-package
// modified
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ClassFinder {

    private static final char PKG_SEPARATOR = '.';

    private static final char DIR_SEPARATOR = '/';

    private static final String CLASS_FILE_SUFFIX = ".class";

    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

    public static List<Class<?>> find(String scannedPackage) {
        String scannedPath = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
        }
        String name = "èuuus" ;
        try { // addeded by dalik
        	name = URLDecoder.decode(scannedUrl.getFile(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
        
        File scannedDir = new File(name);
        if(!scannedDir.exists())
        	throw new RuntimeException("dir '"+scannedDir.getPath()+"' not exists.");
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(find(file, scannedPackage));
        }
        return classes;
    }

    private static List<Class<?>> find(File file, String scannedPackage) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        String resource = scannedPackage + PKG_SEPARATOR + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(find(child, resource));
            }
        } else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
            int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }
    
    // http://stackoverflow.com/questions/13944633/java-reflection-get-list-of-packages
    private static List<String> findPackageNamesStartingWith(String prefix) {
        List<String> result = new ArrayList<>();
        for(Package p : Package.getPackages()) {
            if (p.getName().startsWith(prefix)) {
               result.add(p.getName());
            }
        }
        return result;
    }
    
    // my own
    public static List<Class<?>> classesInSubPackage(String packagename)
    {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        
        for(String pname: findPackageNamesStartingWith(packagename))
        {
        	classes.addAll(find(packagename));
        }
    	return classes;
    }

}