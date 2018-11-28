package utilities_.progpers;

import java.util.Date;
import java.util.List;

import com.antoiovi.util.date.AiDate;
/**
 * Decorator pattern allows a user to add new functionality to an 
 * existing object without altering its structure. 
 * This type of design pattern comes under structural pattern as 
 * this pattern acts as a wrapper to existing class.
 * @author antoiovi
 *
 */
public class DecoratorPrenot {
Date day;
String name;
String cognome;
String note;
AiDate giorno;

public DecoratorPrenot(Date day, String name, String cognome, String note) {
	super();
	this.day = day;
	this.name = name;
	this.cognome = cognome;
	this.note = note;
	giorno=new AiDate(day);
}


public AiDate getGiorno() {
	return giorno;
}


public Date getDay() {
	return day;
}


public void setDay(Date day) {
	this.day = day;
}


public String getName() {
	return name;
}


public void setName(String name) {
	this.name = name;
}


public String getCognome() {
	return cognome;
}


public void setCognome(String cognome) {
	this.cognome = cognome;
}


public String getNote() {
	return note;
}


public void setNote(String note) {
	this.note = note;
}


	
	
}
