
Il programma democp estrae i dati di una classe presente i un file jar.


Sono presenti due progetti:
util : contiene solo una classe Message.java che ha una proprietà ed un metodo; serve come prova

democp : contiene l'applicazione da eseguire App.java


util$ ant clean compile jar 
democp$ ant clean compile jar run 

copiare il file util.jar nella directory dove si esegue democp

Eseguire democp passando come argomento il file jar e la classe da estrarre :

{directory che contiene democp.jar e util.jar}_$ java   -jar democp.jar util.jar com.antoiovi.util.Message
user.dir = /home/antoiovi/Documenti/JAVA_PROG/java_tutorial/Reflaction/democp/democp/build/jar
File jar= Arg[0]=util.jar
Nome classe = Arg[1]=com.antoiovi.util.Message
PATH completo = file:///home/antoiovi/Documenti/JAVA_PROG/java_tutorial/Reflaction/democp/democp/build/jar/util.jar
Child creato regolarmente.. java.net.URLClassLoader@75b84c92
Classload....
msg
class java.lang.String
antoiovi@BILX024691:~/Documenti/JAVA_PROG/java_tutorial/Reflaction/democp/democp/build/jar$ cd ../../

