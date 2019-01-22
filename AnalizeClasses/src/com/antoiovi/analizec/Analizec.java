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

public class Analizec {
	String nomeclasse = null;
	String nomejar = null;
	URLClassLoader urlclsloaderChild = null;;
	List<String> foundedClasses;
	protected Class selectedClass = null;
	String extension = "";
 
	public static void main(String[] args) {
		String ext = "";
		Analizec app = new Analizec();
		final String dir = System.getProperty("user.dir");
		System.out.println("user.dir = " + dir);
		// Controllo validita estensione primo argomento :
		// file.jar oppure file.war
		if (args.length > 0) {
			if (args[0].length() > 3) {
				ext = args[0].substring(args[0].length() - 3);
				logDebug("Extension :  " + ext);
				if (!ext.equals("jar") && !ext.equals("war")) {
					exit();
				}
				app.setExtension(ext.toLowerCase());
			}

		} else {
			exit("Use : \n java -jar analizec.jar file.jar[.war] [com.package.Classname]");
		}

		if (args.length == 1) {
			app.setNomejar(args[0]);
			logDebug("File jar= Arg[0]=" + args[0]);
			if (ext.equals("jar"))
				app.analizeJar();
			else if (ext.equals("war"))
				app.analizeWar();
			else
				exit("First parameter must have  jar or war extension!");

		} else if (args.length == 2) {
			app.setNomejar(args[0]);
			logDebug("File jar= Arg[0]=" + args[0]);
			app.setNomeclasse((args[1]));
			logDebug("Nome classe = Arg[1]=" + args[1]);

			if (ext.equals("jar")) {
				app.loadjar();
				app.loadclass();
				//executeTaskOnSelectedClass();

			} else if (ext.equals("war")) {
				app.pathInwar();
				app.loadclass();
				//app.executeTaskOnSelectedClass();

			} else {
				exit("First parameter must have  jar or war extension!");
			}

		} else {
			exit("use analizec file.jar[war] [class]");
		}

	}

	static private void exit() {
		System.exit(0);
	}

	static private void exit(String msg) {
		System.out.println(msg);
		System.exit(0);
	}

	/**
	 * Create URLClassLoader urlclsloaderChild refered to directory
	 * /WEB-INF/classes/ of the war file (field nomejar)
	 */
	private void pathInwar() {
		String dir = System.getProperty("user.dir");
		String path = "jar:file://" + dir + "/" + nomejar + "!/WEB-INF/classes/";
		logDebug("Full PATH   = " + path);
		try {
			final URL jarUrl = new URL(path);
			urlclsloaderChild = new URLClassLoader(new URL[] { new URL(path) }, App.class.getClassLoader());
			if (urlclsloaderChild != null) {
				log("pathInwar---- Child created.. " + urlclsloaderChild.toString());
			} else {
				log("pathInwar---- Child=null....: ");
			}
		} catch (MalformedURLException e1) {
 			e1.printStackTrace();
			exit("MakformedUrlExcetin, Verify the path of the jar(war) file..");
		}

	}// pathInWar

	/**
	 * Called when the first parameter is a file JAR,
	 * and second parameter is not present
	 */
	private void analizeJar() {
		foundedClasses = new ArrayList<String>();
		try {
			InputStream instr;
			String dir = System.getProperty("user.dir");
			System.out.println("Analizza il JAR ");
			URL urlToJar;
			String path = "file://" + dir + "/" + nomejar;

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

			if (foundedClasses.size() > 0)
				this.selectClass(foundedClasses);
			else
				exit("Nesssuna classe presente nl file JAR" + this.nomejar);

		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException :Errore creazoine class loader ");
			System.out.println("Errore creazoine class loader ");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IOExcepion Errore creazoine class loader ");
			e.printStackTrace();
			System.exit(0);
		} catch (Exception e) {
			System.out.println("Errore creazoine class loader ");
			e.printStackTrace();
			System.exit(0);
		}

	}//analizeJar

	/**
	 * Called when the first parameter of the command line is a WAR file
	 * and second parameter is not present (classname)
	 */
	private void analizeWar() {
		foundedClasses = new ArrayList<String>();
		try {
			InputStream instr;
			String prefix = "WEB-INF/classes/";

			String dir = System.getProperty("user.dir");
			logDebug("Analizza il war ");
			URL urlToWar;
			String path = "file://" + dir + "/" + nomejar;
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
			if (foundedClasses.size() > 0)
				this.selectClass(foundedClasses);
			else
				exit("No class is present in the directory  " + prefix + " of file " + this.nomejar);

		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException :Errore creazione class loader ");
			System.out.println("Errore creazoine class loader ");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IOExcepion Errore creazoine class loader ");
			e.printStackTrace();
			System.exit(0);
		} catch (Exception e) {
			System.out.println("Errore creazoine class loader ");
			e.printStackTrace();
			System.exit(0);
		}

	}

	/**
	 * Load the JAR file registerd in field nomejar and
	 * it creates the parameter URLclassloader urlclsloaderChild
	* If problems exit program
	 */
	private void loadjar() {
		String dir = System.getProperty("user.dir");
		String path = "file://" + dir + "/" + nomejar;
		logDebug("Full PATH  = " + path);
		try {
			urlclsloaderChild = new URLClassLoader(new URL[] { new URL(path) }, App.class.getClassLoader());
			if (urlclsloaderChild != null)
				System.out.println("Child creato regolarmente.. " + urlclsloaderChild.toString());
			else
				System.out.println("Child=null....: ");
		} catch (Exception e) {
			exit("Errore creazine class loader ");

		}
	}

	/**
	 * Load the class (nomeclasse) and put its value into field setectedClass
	 * 
	 */
	private void loadclass() {

		try {
			Class classToLoad = Class.forName(nomeclasse, true, urlclsloaderChild);
			this.selectedClass = classToLoad;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			exit("Error getting  getClass name ");

		}
	}

	/**
	 * Propone la lista di classi trovate nel file analizzto e chiede di sceglierne
	 * uno
	 * 
	 * @param classeNames
	 */
	private void selectClass(List<String> classeNames) {
		// int QUIT=classeNames.size()+1;
		int QUIT = 0;
		boolean test = true;
		int input = 0;
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

			nomeclasse = classeNames.get(input - 1);
			log("Selected class   : " + nomeclasse);
			if (extension.equals("war")) {
				// Create the URLClassLoader to file.war!WEB-INF/classes/
				this.pathInwar();
			} else {
				this.loadjar();
			}

			// Load the class nomeclasse in the field Class selectedClass
			this.loadclass();
			if (this.selectedClass != null)
				executeTaskOnSelectedClass();

		} while (test);
		exit("");
	}

	/**
	 * This method can be overridden an one can manipulate the field selectedClass;
	 */
	protected void executeTaskOnSelectedClass() {
		TaskOnClass task= new TaskOnClass(selectedClass);
		task.executeTaskOnSelectedClass();
	}

	protected static void log(String s) {
		System.out.println(s);
	}

	protected static void logDebug(String s) {
		System.out.println(s);
	}

	public void setNomeclasse(String nomeclasse) {
		this.nomeclasse = nomeclasse;
	}

	public void setNomejar(String nomejar) {
		this.nomejar = nomejar;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

 

}