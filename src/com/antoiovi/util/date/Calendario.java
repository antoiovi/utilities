package com.antoiovi.util.date;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Calendario {


public static final String day_name[]={"Domenica","Lunedi","Martedi","Mercoledi","Giovedi","Venerdi","Sabato"};
public static final String month_name[]={"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno",
    "Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
	
public Giorno[]  createAWeek(Date day){
	Giorno settimana[]=new Giorno[7];
    Calendar cal=new GregorianCalendar();
    cal.setTime(day);
    for(int x=0;x<7;x++){
       Date d=cal.getTime();
    	Giorno giorno=new Giorno(d);
    	settimana[x]=giorno;
        cal.add(Calendar.DATE,1);
    }
    return settimana;
}



}
