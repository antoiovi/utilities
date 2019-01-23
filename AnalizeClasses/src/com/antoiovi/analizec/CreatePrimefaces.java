package com.antoiovi.analizec;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URLClassLoader;
import java.util.List;
import javax.validation.constraints.Size;

public class CreatePrimefaces {

	private Class selectedClass;
	private String className;

	Field field[];
	String managedBeanClassName;
	String managedBeanName;
	String strListAll;
	String var;

	public CreatePrimefaces(Class selectedClass) {
		this.selectedClass = selectedClass;
		field = selectedClass.getDeclaredFields();
		String s = String.format("Name Class %s \n Canonicalname %s", selectedClass.getName(),
				selectedClass.getCanonicalName());
		log(s);
		String[] tokens = selectedClass.getName().split("\\.");

		className = tokens[tokens.length - 1];
		managedBeanClassName = className + "Bean";
		log(className);
		char c[] = className.toCharArray();
		c[0] = Character.toLowerCase(c[0]);
		var = new String(c);
		managedBeanName = var + "Bean";
		log(managedBeanName);
		log(var);
		strListAll = "all" + className;
		log(strListAll);

	}

	public void executeTaskOnSelectedClass() {
		log(" executeTaskOnSelectedCass \n Operazioni da eseguire su classe ");
		// Field field[] = selectedClass.getDeclaredFields();
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

	static final String[] header = { "<!DOCTYPE html>\n",
			"<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\"\n"
					+ "		xmlns:ui=\"http://java.sun.com/jsf/facelets\"\n"
					+ "		xmlns:h=\"http://java.sun.com/jsf/html\"\n"
					+ "		xmlns:f=\"http://java.sun.com/jsf/core\"\n" + "		xmlns:p=\"http://primefaces.org/ui>\n",
			" <ui:define name=\"content\">\n", "" };

	public void printHeader(PrintWriter writer) {
		for (int x = 0; x < header.length; x++) {
			writer.print(header[x]);
		}
	}

	public void createTable() {
		String value;
		PrintWriter writer;
		try {
			writer = new PrintWriter("Table.xhtml", "UTF-8");
			printHeader(writer);

			String t1 = String.format("\t" + "<p:dataTable var=\"%s\" value=\"#{%s.%s}\"> ", var, managedBeanName,
					strListAll);

			writer.println(t1);

			for (int x = 0; x < field.length; x++) {
				Field f = field[x];
				int width = findFieldWidth(f);
				String s = String.format("\t" + "\tField name : %s Type %s", f.getName(), f.getType().toString());
				// log(s);
				String s1 = String.format("\t\t" + "<p:column headerText=\"%s\" width=\"%d\">", f.getName(), width);
				String s2 = String.format("\t\t\t" + "<h:outputText value=\"#{%s.%s}\"/>", var, f.getName());
				writer.println(s1);
				writer.println(s2);
				writer.println("\t\t" + "</p:column>");

			}
			writer.println("\t" + "</p:dataTable>");
			writer.println(" </ui:define>\n" + "</ui:composition>");

			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void createForm() {
		String panelHeader="";
		String newFormName="";
		
		
	String s1="<p:panel id=\"basic\" header=\""+panelHeader+"\" style=\"margin-bottom:20px\">\n" + 
				"			<h:form id=\""+newFormName+"\">\n" + 
				"			    <p:messages showDetail=\"true\" autoUpdate=\"true\" closable=\"true\"/>\n" + 
				"				<h:panelGrid columns=\"3\">\n";
		 				
	String s2=  "				</h:panelGrid>\n" + 
				"				<h:commandButton value=\"Save\" action=\"#{personbean.saveNewPerson}\" />\n" + 
				"			</h:form>\n" + 
				"\n" + 
				"		</p:panel>\n";
				PrintWriter writer;
				try {
					writer = new PrintWriter("New.xhtml", "UTF-8");
					printHeader(writer);
					writer.println(s1);
 
					for (int x = 0; x < field.length; x++) {
						Field f = field[x];
						int width = findFieldWidth(f);
						String s = String.format("\t" + "\tField name : %s Type %s", f.getName(), f.getType().toString());
						// log(s);
						String id=f.getName();//username
						char c[] = f.getName().toCharArray();
						c[0] = Character.toUpperCase(c[0]);
						String label=new String(c);
						String newEntity="new"+var;//newperson methodp
						
						
				String	format=  "					<h:outputLabel value=\"%s:\" for=\"%s\" />\n" + 
						"					<h:inputText id=\"%s\" label=\"%s\"\n" + 
						"						value=\"#{%s.%s.%s}\" />\n" + 
						"					<h:message for=\"%s\" style=\"color:red\" />\n" ; 
						
						writer.println(	String.format(format,label,id,id,label,this.managedBeanName,newEntity,id,id) );
						

					}
					writer.println(s2);

					writer.println("\t" + "</p:dataTable>");
					writer.println(" </ui:define>\n" + "</ui:composition>");

					writer.close();
				} catch (FileNotFoundException | UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		
	}

	int findFieldWidth(Field f) {
		int val = 25;
		Annotation a[] = f.getDeclaredAnnotations();
		Size sizeAnn = f.getAnnotation(Size.class);
		if (sizeAnn != null) {
			log("MAX  " + sizeAnn.max());
			return sizeAnn.max() == 0 ? val : sizeAnn.max();
			// System.out.println(""+sizeAnn.toString());
		} else {
			log("max =null");
			return val;
		}

	}

	protected static void log(String s) {
		System.out.println(s);
	}

	protected static void logDebug(String s) {
		System.out.println(s);
	}
}
