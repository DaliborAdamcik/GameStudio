package sk.tsystems.gamestudio.services.sorm;

/* sorm by jaro*/
/* modified by dalik */

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.net.URL;
import java.net.URLDecoder;

public class SORM extends sk.tsystems.gamestudio.services.jdbc.jdbcConnector {
	public static final String URL = "jdbc:oracle:thin:@//localhost:1521/XE";
	public static final String USER = "register";
	public static final String PASSWORD = "p4ssw0rd";
	
	public SORM()
	{
		super();
		try {
			
			ClassFinder fi = new ClassFinder();
			for(Class c : fi.find("sk.tsystems.gamestudio.entity"))
			{
				System.out.println(c.getName());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}
	
/*	// http://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection	
	private static Class[] getClasses(String packageName)
	        throws ClassNotFoundException, IOException {
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    assert classLoader != null;
	    String path = packageName.replace('.', '/');
	    Enumeration<URL> resources = classLoader.getResources(path);
	    
	    List<File> dirs = new ArrayList<File>();
	    while (resources.hasMoreElements()) {
	        URL resource = resources.nextElement();
	        String filename = resource.getFile().replace("c5", "Š");
	        
	        dirs.add(new File(filename));
	        
	        System.out.println(filename);
	    }
	    ArrayList<Class> classes = new ArrayList<Class>();
	    for (File directory : dirs) {
	        classes.addAll(findClasses(directory, packageName));
	    }
	    return classes.toArray(new Class[classes.size()]);
	}*/

	/**
	 * Recursive method used to find all classes in a given directory and subdirs.
	 *
	 * @param directory   The base directory
	 * @param packageName The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
/*	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
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

	/*private void packbrowser()
	{
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		Reflections reflections = new Reflections(new ConfigurationBuilder()
		    .setScanners(new SubTypesScanner(false ), new ResourcesScanner()) // don't exclude Object.class 
		    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
		    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("org.your.package"))));
	}*/

	public String getCreateTableCommand(Class<?> clazz) {
		StringBuilder sb = new StringBuilder();

		sb.append("CREATE TABLE " + getTableName(clazz) + " (\n");

		boolean first = true;
		for (Field field : clazz.getDeclaredFields()) {
			if (!first)
				sb.append(",\n");
			first = false;
			sb.append(getColumnName(field) + " " + getSQLType(field.getType()));

			if (field.isAnnotationPresent(Id.class)) {
				sb.append(" PRIMARY KEY");
			}
		}

		sb.append("\n)\n");

		return sb.toString();
	}

	public String getSelectCommand(Class<?> clazz) {
		StringBuilder sb = new StringBuilder();

		sb.append("SELECT ");

		boolean first = true;
		for (Field field : clazz.getDeclaredFields()) {
			if (!first)
				sb.append(", ");
			first = false;
			sb.append(getColumnName(field));
		}

		sb.append(" FROM " + getTableName(clazz));

		return sb.toString();
	}

	public String getInsertCommand(Class<?> clazz) {
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();

		sb.append("INSERT INTO " + getTableName(clazz) + " (");

		boolean first = true;
		for (Field field : clazz.getDeclaredFields()) {
			if (!first) {
				sb.append(", ");
				sb2.append(", ");
			}
			first = false;
			sb.append(getColumnName(field));
			sb2.append("?");
		}

		sb.append(") VALUES (");
		sb.append(sb2);
		sb.append(")");

		return sb.toString();
	}

	public String getUpdateCommand(Class<?> clazz) {
		StringBuilder sb = new StringBuilder();

		sb.append("UPDATE " + getTableName(clazz) + " SET ");

		boolean first = true;
		for (Field field : clazz.getDeclaredFields()) {
			if (!field.isAnnotationPresent(Id.class)) {
				if (!first) {
					sb.append(", ");
				}
				first = false;
				sb.append(getColumnName(field) + "=?");
			}
		}
		if (first)
			throw new RuntimeException("@Id is missing on a field of a class " + clazz.getName());

		sb.append(" WHERE ");

		first = true;
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class)) {
				if (!first) {
					sb.append(" AND ");
				}
				first = false;
				sb.append(getColumnName(field) + "=?");
			}
		}

		return sb.toString();
	}

	public String getDeleteCommand(Class<?> clazz) {
		StringBuilder sb = new StringBuilder();

		sb.append("DELETE FROM " + getTableName(clazz) + " WHERE ");

		boolean first = true;
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class)) {
				if (!first) {
					sb.append(" AND ");
				}
				first = false;
				sb.append(getColumnName(field) + "=?");
			}
		}
		if (first)
			throw new RuntimeException("@Id is missing on a field of a class " + clazz.getName());

		return sb.toString();
	}

	public List<Object> select(Class<?> clazz) throws Exception {
		String command = getSelectCommand(clazz);
		List<Object> result = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(command)) {

			while (rs.next()) {
				Object object = clazz.newInstance();

				int index = 1;
				for (Field field : clazz.getDeclaredFields()) {
					field.setAccessible(true);
					Object value = rs.getObject(index);
					if(value instanceof BigDecimal) {
						value = ((BigDecimal)value).intValue();
					}
					field.set(object, value);
					index++;
				}
				
				result.add(object);
			}
		}

		return result;
	}

	public void insert(Object object) throws Exception {
		Class<?> clazz = object.getClass();
		String command = getInsertCommand(clazz);

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = con.prepareStatement(command)) {

			int index = 1;
			for (Field field : clazz.getDeclaredFields()) {
				field.setAccessible(true);
				Object value = field.get(object);
				stmt.setObject(index, value);
				index++;
			}
			stmt.executeUpdate();
		}
	}

	public void update(Object object) throws Exception {
		Class<?> clazz = object.getClass();
		String command = getUpdateCommand(clazz);

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = con.prepareStatement(command)) {

			int index = 1;
			for (Field field : clazz.getDeclaredFields()) {
				if (!field.isAnnotationPresent(Id.class)) {
					field.setAccessible(true);
					Object value = field.get(object);
					stmt.setObject(index, value);
					index++;
				}
			}

			for (Field field : clazz.getDeclaredFields()) {
				if (field.isAnnotationPresent(Id.class)) {
					field.setAccessible(true);
					Object value = field.get(object);
					stmt.setObject(index, value);
					index++;
				}
			}

			System.out.println(command);
			stmt.executeUpdate();
		}
	}

	public void delete(Object object) throws Exception {
		Class<?> clazz = object.getClass();
		String command = getDeleteCommand(clazz);

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = con.prepareStatement(command)) {

			int index = 1;
			for (Field field : clazz.getDeclaredFields()) {
				if (field.isAnnotationPresent(Id.class)) {
					field.setAccessible(true);
					Object value = field.get(object);
					stmt.setObject(index, value);
					index++;
				}
			}

			System.out.println(command);
			stmt.executeUpdate();
		}
	}

	private String getSQLType(Class<?> type) {
		if (type.equals(String.class)) {
			return "VARCHAR2(64)";
		} else if (type.equals(Integer.TYPE)) {
			return "INTEGER";
		} else if (type.equals(Date.class)) {
			return "DATE";
		}

		throw new IllegalArgumentException("Type " + type + " is not supported.");
	}

	private String getTableName(Class<?> clazz) {
		Table table = clazz.getAnnotation(Table.class);
		if (table != null) {
			return table.value();
		}
		return clazz.getSimpleName();
	}

	private String getColumnName(Field field) {
		Column column = field.getAnnotation(Column.class);
		if (column != null) {
			return column.value();
		}
		return field.getName();
	}
}
