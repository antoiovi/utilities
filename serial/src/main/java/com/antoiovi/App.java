/**
 * Programma per provare la libreria jscc per la lettura dati da porta seriale
 * Serve solo per mostrare l'utilizzo della libreria, che vien richiamata dalla 
 * classe Serial, che ricopre le chiamate alle funzioni della libreia
 * Viene utilizzata la classe Serial che esegue i copitti sulla porta
 * 
 * Viene inzzializata ed aperta la porta
 * vengono lette un certo numero di righe e poi chiude l'applicazione
 * 
 * Compilazione con maven : 
 * 		
 * 		serial$ mvn clean package
 *  
 * Esecuzione :
 *		
 *		serial$ java -jar target/serial-1.jar 		
 *
 * In caso di errori con la connessione alla porta verificare i privilegi di accesso;
 * 
 * 	I dati di default sono :
 * 
  * String  name="/dev/ttyUSB0";
 *  int baudRate =9600;
 *  int parity=SerialPort.PARITY_NONE;
 *  int databits=8;
 *  double d =1.0;
 *  boolean RTS=true;
 *  boolean DTR=true;
 *  
 *  Per usare altri parametri usare :
 * 		  Per campiare solo il nome della porta  
 *  			serial = new SerialRead(name); 
 * 		  Per campiare tutti i parametri :  
 *  			serial = new  Serial(String iname, int irate, int parityNone, 
 *  			int idatabits, double d, boolean setRTS, boolean setDTR);
 *  
 *    
 * 03/2019 Antonello Iovino 
 * Sito di riferimento www.antoiovi.com; www.worpress.antoiovi.com
 *
 */
package com.antoiovi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;


public class App {
	String name = "/dev/ttyUSB0";
	SerialRead serial;
 
	public static void main(String[] args) {
		System.out.println("Hello World!");
		App p = new App();
		p.init();
	}

	private void init() {
		
		try {

			serial = new SerialRead(name);
			if (serial.portIsOpened()) {
				System.out.println("Porta e apera..");
				TimeUnit.SECONDS.sleep(1);
				System.out.println("Lettura dati ..");
				
				readString();

				System.out.println("Chiusura porta seriale..");
				serial.dispose();

			} else
				System.out.println("Porta NON e aperta !!!");
		} catch (SerialException e) {
			e.printStackTrace();
		} catch (IOException e) {
 			e.printStackTrace();
		} catch (InterruptedException e) {
			// Timeunit.Seconds.sleep(..) exception
 			e.printStackTrace();
		}
 	}

	/**
	 * Legge un certo numero di righe e poi chiude l'applicazione
	 * 
	 */
	
	private void readString() throws  InterruptedException {
		int count = 0;
		do {
			count++;
			TimeUnit.SECONDS.sleep(2);
			String msg = serial.getFirst();
			System.out.println(msg);
		} while (count < 10);

	}

     
}
