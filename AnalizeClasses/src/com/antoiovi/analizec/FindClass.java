package com.antoiovi.analizec;

import com.antoiovi.analizec.TaskOnClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class FindClass {
	String workingDir;
	static final String ERR_NO_PARAMETER = "ERR_NON_PARAMETER";

	public static String checkExtension(String[] args) {
		String ext;
		if (args.length > 0) {
			if (args[0].length() > 3) {
				ext = args[0].substring(args[0].length() - 3);
				if (!ext.equals("jar") && !ext.equals("war")) {
					return null;
				}
				return ext.toLowerCase();
			}

		}
		return null;
	}

	/**
	 * Load the JAR file registerd in field nomejar and it creates the parameter
	 * URLclassloader urlclsloaderChild If problems exit program
	 * 
	 * @throws MalformedURLException
	 */
	public URLClassLoader pathInjar(String fileJAR) throws MalformedURLException {
		String dir = System.getProperty("user.dir");
		String path = "file://" + dir + "/" + fileJAR;
		URLClassLoader urlclsLoader = null;
		try {
			urlclsLoader = new URLClassLoader(new URL[] { new URL(path) }, FindClass.class.getClassLoader());
			return urlclsLoader;
		} catch (MalformedURLException e1) {
			throw e1;
		}
	}

	/**
	 * Create URLClassLoader urlclsloaderChild refered to directory
	 * /WEB-INF/classes/ of the war file (field nomejar)
	 * 
	 * @throws MalformedURLException
	 */
	public URLClassLoader pathInwar(String fileWAR) throws MalformedURLException {
		URLClassLoader urlclsLoader = null;
		;
		String dir = System.getProperty("user.dir");
		String path = "jar:file://" + dir + "/" + fileWAR + "!/WEB-INF/classes/";
		logDebug("Full PATH   = " + path);
		try {
			URL warUrl = new URL(path);
			urlclsLoader = new URLClassLoader(new URL[] { new URL(path) }, FindClass.class.getClassLoader());
			return urlclsLoader;

		} catch (MalformedURLException e1) {
			throw e1;
		}

	}// pathInWar

	/**
	 * Called when the first parameter is a file JAR, and second parameter is not
	 * present
	 * 
	 * @throws IOException
	 */
	public List<String> analizeJar(String fileJAR) throws IOException, MalformedURLException, Exception {
		List<String> foundedClasses = new ArrayList<String>();
		try {
			InputStream instr;
			String dir = System.getProperty("user.dir");
			URL urlToJar;
			String path = "file://" + dir + "/" + fileJAR;

			urlToJar = new URL(path);
			instr = urlToJar.openStream();
			ZipInputStream zipinstr = new ZipInputStream(instr);
			ZipEntry ze = zipinstr.getNextEntry();
			while (ze != null) {
				if (!ze.isDirectory()) {
					String name = ze.getName();
					if (name.endsWith(".class")) {
						name = name.substring(0, name.indexOf(".class"));
						name = name.replaceAll("/", ".");
						foundedClasses.add(name);
					}
				}
				ze = zipinstr.getNextEntry();
			}
			return foundedClasses;
		} catch (MalformedURLException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

	}// analizeJar

	/**
	 * Called when the first parameter of the command line is a WAR file and second
	 * parameter is not present (classname)
	 * 
	 * @return
	 */
	private List<String> analizeWar(String fileWAR) throws IOException, MalformedURLException, Exception {
		List<String> foundedClasses = new ArrayList<String>();

		try {
			InputStream instr;
			String prefix = "WEB-INF/classes/";

			String dir = System.getProperty("user.dir");
			URL urlToWar;
			String path = "file://" + dir + "/" + fileWAR;
			urlToWar = new URL(path);
			instr = urlToWar.openStream();
			ZipInputStream zipinstr = new ZipInputStream(instr);
			ZipEntry ze = zipinstr.getNextEntry();
			while (ze != null) {
				if (!ze.isDirectory()) {
					String name = ze.getName();
					if (name.startsWith(prefix)) {
						if (name.endsWith(".class")) {
							name = name.substring(prefix.length());
							name = name.substring(0, name.indexOf(".class"));
							name = name.replaceAll("/", ".");
							foundedClasses.add(name);
						}
					}
				}
				ze = zipinstr.getNextEntry();
			}
			return foundedClasses;
		} catch (MalformedURLException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Load the class (nomeclasse) and put its value into field setectedClass
	 * 
	 * @throws ClassNotFoundException
	 * 
	 */
	public Class loadclass(String className, URLClassLoader urlClassLoader) throws ClassNotFoundException {
		return Class.forName(className, true, urlClassLoader);
	}

	/**
	 * Propone la lista di classi trovate nel file analizzto e chiede di sceglierne
	 * uno
	 * 
	 * @param classeNames
	 */
	public String selectClass(List<String> classeNames) {
		int QUIT = 0;
		boolean test = true;
		int input = 0;
		String className = null;
		do {
			for (int x = 0; x < classeNames.size(); x++) {
				log(String.format("%3d) %s", x + 1, classeNames.get(x)));
			}
			log(String.format("%3d) %s", QUIT, "Exit"));

			try {
				Scanner sc = new Scanner(System.in);
				input = sc.nextInt();
				if (input < 0 || input > (classeNames.size() + 1)) {
					log("Inout noit valid !!");
					continue;
				}
			} catch (java.util.InputMismatchException e) {
				log("Input not valid !! Type a number ");
				continue;
			}

			if (input == QUIT)
				break;

			className = classeNames.get(input - 1);
			break;

		} while (test);
		return className;
	}

	/**
	 * This method can be overridden an one can manipulate the field selectedClass;
	 */
	protected void executeTaskOnSelectedClass(Class selectedClass) {
		log("Operazioni da eseguire su classe ");
		Field field[] = selectedClass.getDeclaredFields();
		Method[] methods = selectedClass.getDeclaredMethods();
		log(String.format("Classe %s", selectedClass.getName()));
		log("Fields :");
		for (int x = 0; x < field.length; x++) {
			Field f = field[x];
			String s = String.format("\tField name : %s Type %s", f.getName(), f.getType().toString());
			log(s);
		}
		log("Methods :");

		for (int y = 0; y < methods.length; y++) {
			Method m = methods[y];
			String s = String.format("\tMethod name : %s Returned Type %s", m.getName(), m.getReturnType().toString());
			log(s);
			// Type t[] = m.getGenericParameterTypes();
			Type t[] = m.getParameterTypes();
			if (t.length > 0) {
				log("\t\tParamters types");
				for (int x = 0; x < t.length; x++) {
					log(String.format("\t\t\t Parameter[%d] Type %s", (x + 1), t[x].getTypeName()));
				}
			} else {
				log("\t\t\t No parameters");
			}

		}
	}

	protected static void log(String s) {
		System.out.println(s);
	}

	protected static void logDebug(String s) {
		System.out.println(s);
	}

}