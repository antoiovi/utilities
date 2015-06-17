package utilities_.progpers;

import java.util.Date;

public class Prenotazione {
Date giorno;
Person person;
String note;




public Prenotazione(Date giorno, Person person, String note) {
	super();
	this.giorno = giorno;
	this.person = person;
	this.note = note;
}
public Date getGiorno() {
	return giorno;
}
public void setGiorno(Date giorno) {
	this.giorno = giorno;
}
public Person getPerson() {
	return person;
}
public void setPerson(Person person) {
	this.person = person;
}
public String getNote() {
	return note;
}
public void setNote(String note) {
	this.note = note;
}



}
