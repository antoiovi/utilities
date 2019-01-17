package com.antoiovi.democp;

import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Field; 


public class App 
{
 String nomeclasse;
 String nomejar;
 URLClassLoader child=null; ;

	
	public static void main( String[] args )
    {
		
		App app=new App();
		final String dir = System.getProperty("user.dir");
        System.out.println("user.dir = " + dir);
		if(args.length==1) {
			app.setNomejar(args[0]);	
			System.out.println( "File jar= Arg[0]="+args[0] );
			app.loadjar();
		}else if(args.length==2) {
			app.setNomejar(args[0]);	
			System.out.println( "File jar= Arg[0]="+args[0] );
			app.setNomeclasse((args[1]));	
			System.out.println( "Nome classe = Arg[1]="+args[1] );
			app.loadjar();
			app.loadclass();
		}else {
			System.out.println( "Mancano parametri ; usare democp  file.jar nomeclasse" );
			System.out.println( "\t nomeclasse è facoltativo" );
			System.exit(0);
		}
		
    }
      
    private void loadjar() {
    	String dir = System.getProperty("user.dir");
    String path="file://"+dir+"/"+nomejar;
    System.out.println("PATH completo = "+path);
    	  try{
       child = new URLClassLoader (new URL[] {new URL(path)}, App.class.getClassLoader());
       if(child!=null)
       System.out.println("Child creato regolarmente.. "+child.toString());
       else
    	   System.out.println("Child=null....:w"
    	   		+ "");
   }catch(Exception e){
           System.out.println("Errore creazoine class loader ");
      

   }
    }
    private void loadclass() {
    	try{
   	    System.out.println("Classload....");
    	    Class classToLoad = Class.forName(nomeclasse, true, child);
    	    Field field[]=classToLoad.getDeclaredFields();

    	    for(int x=0;x<field.length;x++) {
    	    	Field f=field[x];
    	    	System.out.println(f.getName());
    	    	System.out.println(f.getType().toString());
    	    }
    	}catch(Exception e){
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