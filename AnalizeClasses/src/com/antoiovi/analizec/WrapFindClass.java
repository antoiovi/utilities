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

public class WrapFindClass {
	FindClass findclass;
	
	
	String nomeclasse = null;
	String nomejar = null;
	URLClassLoader urlclsloaderChild = null;;
	List<String> foundedClasses;
	protected Class selectedClass = null;
	String extension = "";
 	
	
	public WrapFindClass(FindClass findclass) {
		super();
		this.findclass = findclass;
	}
	
	
	
	
}