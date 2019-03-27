/**
 * Classe che estende la classe com.antoiovi.Serial per leggere i dati da una porta seriale
 *  
 * Viene sovrascritto  il metodo  protected void message(char[] chars, int length) 
 * 
 * Viene creato un buffer da cui si possono estrarre le ultime rifghe trasmesse;
 * 
 * 03/2019 Antonello Iovino 
 * Sito di riferimento www.antoiovi.com; www.worpress.antoiovi.com

 */

package com.antoiovi;

import java.util.ArrayList;
import java.util.List;

import jssc.SerialPortEvent;
 
public class SerialRead extends Serial {

	static final int MAX_BUFFER=100;
	List<String> buffer;

	
  public  SerialRead() throws SerialException {
		 super();
		  buffer=new ArrayList<String>();
	 }
	
	public  SerialRead(String iname) throws SerialException {
		 super(iname);
		  buffer=new ArrayList<String>();
	 }
	 
	
  public  SerialRead(String iname, int irate, int parityNone, int idatabits, double d, boolean setRTS, boolean setDTR) throws SerialException {
	  super(iname, irate, parityNone, idatabits, d, setRTS, setDTR);
	  buffer=new ArrayList<String>();
  }
  
  /**
   * Aggiunge una stringa al buffer tramite l'evento asincrono della classe padre :
   *   public synchronized void serialEvent(SerialPortEvent serialEvent) {...
   *
   */
 @Override
 protected void message(char[] chars, int length) {
	String line=String.valueOf(chars);
	if(buffer.size()==MAX_BUFFER)
		buffer.remove(0);
	if(line==null) return;
	buffer.add(line);
  }

/**
 * Legge dal buffer ( viene estratta ed eliminata per fare spazio)
 * @return la stringa più vecchia nel buffer
 */
public synchronized String getAnalogRead() {
	if(buffer.size()==0)return null;
	return buffer.remove(0);
	}
  
}
