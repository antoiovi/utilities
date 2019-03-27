/**
 * Programma per provare la libreria jscc per la lettura dati da porta seriale
 * Serve solo per mostrare l'utilizzo della libreria, che vien richiamata dalla 
 * classe Serial, che ricopre le chiamate alle funzioni della libreia
 * Viene utilizzata la classe Serial che esegue i copitti sulla porta
 * 
 * Viene inzzializata ed aperta la porta
 * vengono lette un certo numero di righe e poi chiude l'applicazione
 * 
 * 03/2019 Antonello IOvino 
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
		} catch (SerialPortException e) {
 			e.printStackTrace();
		} catch (SerialPortTimeoutException e) {
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
	
	private void readString() throws SerialPortException, SerialPortTimeoutException, InterruptedException {
		int count = 0;
		do {
			count++;
			TimeUnit.SECONDS.sleep(2);
			String msg = serial.getAnalogRead();
			System.out.println(msg);

		} while (count < 10);

	}
}
