package com.antoiovi.util.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Giorno {
	private Date date_day;
/**
 * Nome del giorno della settimana (lunedi..giovedi..)
 */	private String str_day_name;
 /**
  * Stesso giorno come Calendar 
  */
    private Calendar calendar_day;
    /**
     * Giorno nel formato dd-MM-yyyy
     */
    private String str_date;

static final  DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

    
    
    
    
    public Giorno(Date date_day) {
		super();
		this.date_day = date_day;
		init();
	}

    public void init(){
        DateFormat formatter ; 
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        this.str_date= formatter.format(date_day);
        calendar_day=new GregorianCalendar();
        calendar_day.setTime(date_day);
        int d=calendar_day.get(Calendar.DAY_OF_WEEK);
        
        str_day_name=Calendario.day_name[d-1];
        str_date=formatter.format(date_day);;
       
    }
    /**
     * 
     * @return data nel formato : Lunedi 20-10-2013
     */
	public String getDateAsString() {
        DateFormat formatter ; 
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String dateString = formatter.format(date_day);
        return dateString;
    }

    
    
    
    /**************************************************************************************
     * 
     * 		GETTERS AND SETTERS
     * 
     ***************************************************************************************/
	
    public Date getDate_day() {
		return date_day;
	}

	public void setDate_day(Date date_day) {
		this.date_day = date_day;
		init();
	}

	
	
	public String getStr_day_name() {
		return str_day_name;
	}

	public Calendar getCalendar_day() {
		return calendar_day;
	}

	public void setCalendar_day(Calendar calendar_day) {
		this.calendar_day = calendar_day;
	}

	public String getStr_date() {
		return str_date;
	}

	public void setStr_date(String str_date) {
		this.str_date = str_date;
	}

   
    
    
    
	
	
    
    
    
}
