Collection of utilities to be used in java programs or by java programmers;

## AnalizeClasses
This program can be used to manipulate a class present in jar file or war file, for example to create graphics user interfaces based on that class .


Thi is a simple app that take as parameter :
- A file jar or war
- or a file.jar or file.war and a full qualified class name (com.package.Classname)

then it explores the jar(war) and :
If there is only the jar(war) file as parameter , analizes it and looks for all the classes which are included, shows the result and asks the user to selct one; then are presented the fields and methods of the class (method: protected void executeTaskOnSelectedClass() );

if the second option is present (class name) looks for that in the file, and executes the method executeTaskOnSelectedClass() as before;

In the war files the search is performed in the WEB-INF/classes/ directory of the war file.

Is possible to Override (or rewrite) the method  "protected void executeTaskOnSelectedClass()" to perform tasks on the "field 	protected Class selectedClass ;".




### Compilation an building :

` AnalizeClasses$ ant clean compile jar`
### Use
Copy the jar file analizec.jar in the same directory of the file to analize

`$ java -jar  analizec.jar  file.jar`

or

`$ java -jar  analizec.jar  file.war`

or

`$ java -jar  analizec.jar  file.jar  xxxmypackage.Myclass`

or

`$ java -jar  analizec.jar  file.war  xxxmypackage.Myclass`


## Utility-lib

A few utilities class


## Serial

Author Antonello Iovino
Date 03/2019

A few class created as example of connecting to serial port using lib jssc-2.8.0.jar

Also wrapper classes to be used for connection scope.

To change port configuration see the class file com.antoiovi.App.java

Compilazione con maven :
```sh  		
	serial$ mvn clean package
```  
 Esecuzione :
```sh		
		serial$ sudo java -jar target/serial-1.jar 		
```

If you have permisions on /dev/ttyXX you don't need sudo;
