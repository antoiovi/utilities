package com.antoiovi;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
/**
 * Hello world!
 *
 */
public class App
{
  SerialPort serialPort ;
  String name="/dev/ttyUSB0";
  Serial serial;

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
  			serial=new Serial(name, 9600, 1,8,1.0,true,true);
        if(serial.portIsOpened())
          System.out.println("Porta e apera..");
          else
          System.out.println("Porta NON e aperta !!!");
  		} catch (SerialException e) {
   			e.printStackTrace();
  		}
    }
}
