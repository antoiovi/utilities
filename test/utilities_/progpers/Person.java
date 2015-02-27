package utilities_.progpers;

public class Person {
String nome;
String cognome;
public Person(){
	
}



public Person(String nome, String cognome) {
	super();
	this.nome = nome;
	this.cognome = cognome;
}



public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public String getCognome() {
	return cognome;
}
public void setCognome(String cognome) {
	this.cognome = cognome;
}


}
