package com.antoiovi.democp;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

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
				app.pathInwar();
			else
				exit();

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
				exit();
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

	private void pathInwar() {
		URL urlToWar;
		String dir = System.getProperty("user.dir");
		String path = "jar:file://" + dir + "/" + nomejar + "!/WEB-INF/classes/";
		System.out.println("PATH completo = " + path);
		InputStream instr;
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
		try {
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
					String name = ze.getName();
					System.out.println(name);
					// System.out.println("/WEB-INF/classes/ Non Ptresente...");
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

	}// pathInWar

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
			System.out.println("Errore creazoine class loader ");

		}
	}

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
			System.out.println("Errore getClass name ");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void setNomeclasse(String nomeclasse) {
		this.nomeclasse = nomeclasse;
	}

	public void setNomejar(String nomejar) {
		this.nomejar = nomejar;
	}

}