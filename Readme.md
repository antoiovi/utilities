Collection of utilities to be uses in java programs or by java programmers;

## AnalizeClasses
This program can be used to create user interfaces ,base on class present in jar file or war file.


Thi is a simple app that take as parameter :
- A file jar or war 
- or a file.jar or file.war and a full qualified class name (com.package.Classname)

then it explores the jar(war) and :
If there is only the jar(war) file as parameter , analizes it and looks for all the classes which are included, shows the result and ask to selct one; then are presented the fields and methods of the class (method protected void executeTaskOnSelectedClass() );

if the second option is present (class name) looks for that in the file, and executes the method executeTaskOnSelectedClass() as before; 

In the war files the search is performed in the WEB-INF/classes/ directory in the war file.

Is possible to Override (or rewrite) the method  protected void executeTaskOnSelectedClass() to perform tasks on the field 	protected Class selectedClass ;




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
