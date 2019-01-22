package com.antoiovi.analizec;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URLClassLoader;
import java.util.List;

public class CreatePrimefaces {

	private Class selectedClass;

	Field field[];

	public CreatePrimefaces(Class selectedClass) {
		this.selectedClass = selectedClass;
		field = selectedClass.getDeclaredFields();

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
			"<ui:define name=\"content\">\n", "" };

	public void printHeader(PrintWriter writer) {
		for (int x = 0; x < header.length; x++) {
			writer.print(header[x]);
		}
	}

	public void createTable() {
		PrintWriter writer;
		try {
			writer = new PrintWriter("Table.xhtml", "UTF-8");
			printHeader(writer);
			for (int x = 0; x < field.length; x++) {
				Field f = field[x];
				String s = String.format("\tField name : %s Type %s", f.getName(), f.getType().toString());
				log(s);
				String s1 = String.format("<p:column headerText=\"%s width=\"100\">", f.getName());
				String s2 = String.format("<h:outputText value=\"#{%s}\"/>", f.getName());
				writer.println(s1);
				writer.println(s2);
				writer.println("</p:column>");

			}

			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected static void log(String s) {
		System.out.println(s);
	}

	protected static void logDebug(String s) {
		System.out.println(s);
	}
}
