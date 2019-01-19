package com.antoiovi.democp;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import org.apache.commons.lang3.StringUtils ;

public class App {
	String nomeclasse;
	String nomejar;
	URLClassLoader urlclsloaderChild = null;;

	public static void main(String[] args) {
		String ext = "";
		App app = new App();
		final String dir = System.getProperty("user.dir");
		System.out.println("user.dir = " + dir);
		if (args.length > 0) {
			if (args[0].length() > 3) {
				ext = args[0].substring(args[0].length() - 3);
				System.out.println("Estensione :  " + ext);

				if (!ext.equals("jar") && !ext.equals("war")) {

					exit();
				}
			}

		} else {
			exit();
		}

		if (args.length == 1) {
			app.setNomejar(args[0]);
			System.out.println("File jar= Arg[0]=" + args[0]);
			if (ext.equals("jar"))
				app.loadjar();
			else if (ext.equals("war"))
				app.analizeWar();
			else
				exit("Il primo parametro deve essere un file jar o war ");

		} else if (args.length == 2) {
			app.setNomejar(args[0]);
			System.out.println("File jar= Arg[0]=" + args[0]);
			app.setNomeclasse((args[1]));
			System.out.println("Nome classe = Arg[1]=" + args[1]);
			
			
			if (ext.equals("jar")) {
				app.loadjar();
				app.loadclass();

			}
			else if (ext.equals("war")) {
				app.pathInwar();
				app.loadclass();

			}
			else {
				exit("Il primo parametro deve essere un file jar o war");
			}
			
		} else {
			exit();
		}

	}

	static private void exit() {
		System.out.println("Mancano parametri ; usare democp  file.jar nomeclasse");
		System.out.println("\t nomeclasse è facoltativo");
		System.exit(0);
	}

	static private void exit(String msg) {
		System.out.println(msg);
		System.exit(0);
	}

	private void pathInwar() {
		String dir = System.getProperty("user.dir");
		String path = "jar:file://" + dir + "/" + nomejar + "!/WEB-INF/classes/";
		System.out.println("PATH completo = " + path);
		try {
			final URL jarUrl = new URL(path);
			urlclsloaderChild = new URLClassLoader(new URL[] { new URL(path) }, App.class.getClassLoader());
			if (urlclsloaderChild != null) {
				System.out.println("pathInwar---- Child creato regolarmente.. " + urlclsloaderChild.toString());
				/*this.nomeclasse = "com.antoiovi.primef.model.Person";
				this.loadclass();*/
			} else {
				System.out.println("pathInwar---- Child=null....: ");
			}
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}// pathInWar
	/**
	 * 
	 */
	private void analizeWar() {
		try {
			InputStream instr;

			String dir = System.getProperty("user.dir");
			System.out.println("Analizza il war ");
			URL urlToWar;
			String path = "file://" + dir + "/" + nomejar;

			urlToWar = new URL(path);
			instr = urlToWar.openStream();
			ZipInputStream zipinstr = new ZipInputStream(instr);
			ZipEntry ze = zipinstr.getNextEntry();
			while (ze != null) {
				if (ze.isDirectory()) {
					String namedir = ze.getName();

					if (namedir.equals("WEB-INF/classes/")) {
						System.out.println("++++MATCH++++++++  " + ze.getName());
						// URL classURL = ze.getClass().getClassLoader().getResource(namedir);
						// urlclsloaderChild = new URLClassLoader(new URL[] { classURL },
						// App.class.getClassLoader());

						/*
						 * if (classURL != null) { urlclsloaderChild = new URLClassLoader(new URL[] {
						 * classURL }, App.class.getClassLoader()); try { if (urlclsloaderChild != null)
						 * System.out.println( "pathInWar : Child creato regolarmente.. " +
						 * urlclsloaderChild.toString()); else
						 * System.out.println("pathInWar  Child=null....: "); } catch (Exception e) {
						 * System.out.println("pathInWar   :Errore creazoine class loader "); } if
						 * (nomeclasse != null) this.loadclass();
						 */
					} // namedir.equals("WEB-INF/classes/")

				} // ze.isDirectory
				else {
					String prefix="WEB-INF/";
					String name = ze.getName();
					//System.out.println(name);
					//System.out.println(name);

					if(StringUtils.startsWith(name, prefix)) {
						name=name.substring(prefix.length());
					//	System.out.println("\t"+name);

						prefix="classes";
						if(StringUtils.startsWith(name, prefix)) {

							name=name.substring(prefix.length()+1);
						System.out.println(name.replaceAll("/", "."));
						}
					}
 				}
				ze = zipinstr.getNextEntry();
			}

			/*
			 * /WEB-INF/classes/ com.antoiovi.primef.model.Person
			 */
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
	
		
		
		
	}
/**
 * 
 */
	private void loadjar() {
		String dir = System.getProperty("user.dir");
		String path = "file://" + dir + "/" + nomejar;
		System.out.println("PATH completo = " + path);
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
 * 
 */
	private void loadclass() {
		try {
			System.out.println("Classload....");
			Class classToLoad = Class.forName(nomeclasse, true, urlclsloaderChild);
			Field field[] = classToLoad.getDeclaredFields();

			for (int x = 0; x < field.length; x++) {
				Field f = field[x];
				System.out.println(f.getName());
				System.out.println(f.getType().toString());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			exit("Errore getClass name ");

		}
	}

	
	
	
	public void setNomeclasse(String nomeclasse) {
		this.nomeclasse = nomeclasse;
	}

	public void setNomejar(String nomejar) {
		this.nomejar = nomejar;
	}

}