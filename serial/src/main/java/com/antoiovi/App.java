package com.antoiovi;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;
/**
 * Hello world!
 *
 */
public class App
{
  SerialPort serialPort ;
  String name="/dev/ttyUSB0";
  Serial serial;
  int timeout=5000;

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        		App p=new App();
        		p.init();

    }

    private void init() {
  		/*String iname,
  		int irate,
  		int parityNone,
  		int idatabits,
  		float istopbits,
  		boolean setRTS,
  		boolean setDTR) throws SerialException {*/

  		  try {
  			serial=new Serial(name, 9600,SerialPort.PARITY_NONE ,8,1.0,true,true);
        if(serial.portIsOpened()) {
        	System.out.println("Porta e apera..");
        	TimeUnit.SECONDS.sleep(1);
        	int count=0;
        	do {
        		readString();
        		count++;
        	}while(count<10);
        	serial.dispose();
        	
        }else
          System.out.println("Porta NON e aperta !!!");
  		} catch (SerialException e) {
   			e.printStackTrace();
  		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SerialPortTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void readString() throws SerialPortException, SerialPortTimeoutException {
    	char buffer[]=new char[256];
    	
    	//String msg=serial.readString(256,timeout);
    	String msg=serial.readString();
    	if(msg!=null)
    	System.out.println("Letto "+msg);
    	else 
        	System.out.println("Letto null..");

     }
}
