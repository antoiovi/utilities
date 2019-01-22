package com.antoiovi.analizec;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URLClassLoader;
import java.util.List;

public class TaskOnClass  {

	 
	private Class selectedClass;

	public TaskOnClass(Class selectedClass) {
		this.selectedClass = selectedClass;
	}
	public void executeTaskOnSelectedClass( ) {
		log(" executeTaskOnSelectedCass \n Operazioni da eseguire su classe ");
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
