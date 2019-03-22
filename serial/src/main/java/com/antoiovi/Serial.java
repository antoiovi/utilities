package com.antoiovi;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.MissingResourceException;


public class Serial implements SerialPortEventListener {

  //PApplet parent;

  // properties can be passed in for default values
  // otherwise defaults to 9600 N81

  // these could be made static, which might be a solution
  // for the classloading problem.. because if code ran again,
  // the static class would have an object that could be closed

  private SerialPort port;

  private CharsetDecoder bytesToStrings;
  private static final int IN_BUFFER_CAPACITY = 128;
  private static final int OUT_BUFFER_CAPACITY = 128;
  private ByteBuffer inFromSerial = ByteBuffer.allocate(IN_BUFFER_CAPACITY);
  private CharBuffer outToMessage = CharBuffer.allocate(OUT_BUFFER_CAPACITY);

  public Serial() throws SerialException {
   /* this(PreferencesData.get("serial.port"),
      PreferencesData.getInteger("serial.debug_rate", 9600),
      PreferencesData.getNonEmpty("serial.parity", "N").charAt(0),
      PreferencesData.getInteger("serial.databits", 8),
      PreferencesData.getFloat("serial.stopbits", 1),
      !BaseNoGui.getBoardPreferences().getBoolean("serial.disableRTS"),
      !BaseNoGui.getBoardPreferences().getBoolean("serial.disableDTR"));*/
  }

  public Serial(int irate) throws SerialException {
   /* this(PreferencesData.get("serial.port"), irate,
      PreferencesData.getNonEmpty("serial.parity", "N").charAt(0),
      PreferencesData.getInteger("serial.databits", 8),
      PreferencesData.getFloat("serial.stopbits", 1),
      !BaseNoGui.getBoardPreferences().getBoolean("serial.disableRTS"),
      !BaseNoGui.getBoardPreferences().getBoolean("serial.disableDTR"));*/
  }





public static boolean touchForCDCReset(String iname) throws SerialException {
    SerialPort serialPort = new SerialPort(iname);
    try {
      serialPort.openPort();
      serialPort.setParams(1200, 8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
      serialPort.setDTR(false);
      serialPort.closePort();
      return true;
    } catch (SerialPortException e) {
      throw new SerialException(format(tr("Error touching serial port ''{0}''."), iname), e);
    } finally {
      if (serialPort.isOpened()) {
        try {
          serialPort.closePort();
        } catch (SerialPortException e) {
          // noop
        }
      }
    }
  }

  public  Serial(String iname, int irate, int parityNone, int idatabits, double d, boolean setRTS, boolean setDTR) throws SerialException {


/*		this.Serial(iname, SerialPort.BAUDRATE_9600, SerialPort.PARITY_NONE, SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1, true, true);*/

	  //if (port != null) port.close();
    //this.parent = parent;
    //parent.attach(this);

    resetDecoding(StandardCharsets.UTF_8);

    int parity = SerialPort.PARITY_NONE;
    if (parityNone == 'E') parity = SerialPort.PARITY_EVEN;
    if (parityNone == 'O') parity = SerialPort.PARITY_ODD;

    int stopbits = SerialPort.STOPBITS_1;
    if (d == 1.5f) stopbits = SerialPort.STOPBITS_1_5;
    if (d == 2) stopbits = SerialPort.STOPBITS_2;

    try {
      port = new SerialPort(iname);
      port.openPort();
      boolean res = port.setParams(irate, idatabits, stopbits, parity, setRTS, setDTR);
      if (!res) {
        System.err.println(format(tr("Error while setting serial port parameters: {0} {1} {2} {3}"),
                                  irate, parityNone, idatabits, d));
      }
      port.addEventListener(this);
    } catch (SerialPortException e) {
      if (e.getPortName().startsWith("/dev") && SerialPortException.TYPE_PERMISSION_DENIED.equals(e.getExceptionType())) {
        throw new SerialException(format(tr("Error opening serial port ''{0}''. Try consulting the documentation at http://playground.arduino.cc/Linux/All#Permission"), iname));
      }
      throw new SerialException(format(tr("Error opening serial port ''{0}''."), iname), e);
    }

    if (port == null) {
      throw new SerialNotFoundException(format(tr("Serial port ''{0}'' not found. Did you select the right one from the Tools > Serial Port menu?"), iname));
    }
  }

  public void setup() {
    //parent.registerCall(this, DISPOSE);
  }

  public void dispose() throws IOException {
    if (port != null) {
      try {
        if (port.isOpened()) {
          port.closePort();  // close the port
        }
      } catch (SerialPortException e) {
        throw new IOException(e);
      } finally {
        port = null;
      }
    }
  }

 // @Override
  public synchronized void serialEvent(SerialPortEvent serialEvent) {
	  System.out.println("Serial event ...");
	  if (serialEvent.isRXCHAR()) {
    	System.out.println("Serial event isRXCHAR");
      try {
        byte[] buf = port.readBytes(serialEvent.getEventValue());
        
        System.out.println("serialEventGetValue= "+String.valueOf(buf));
        int next = 0;
        while(next < buf.length) {
          while(next < buf.length && outToMessage.hasRemaining()) {
            int spaceInIn = inFromSerial.remaining();
            int copyNow = buf.length - next < spaceInIn ? buf.length - next : spaceInIn;
            inFromSerial.put(buf, next, copyNow);
            next += copyNow;
            inFromSerial.flip();
            bytesToStrings.decode(inFromSerial, outToMessage, false);
            inFromSerial.compact();
          }
          outToMessage.flip();
          System.out.println("outToMessage= "+String.valueOf(outToMessage));

          if(outToMessage.hasRemaining()) {
            char[] chars = new char[outToMessage.remaining()];
            outToMessage.get(chars);
            message(chars, chars.length);
          }
          outToMessage.clear();
        }
      } catch (SerialPortException e) {
        errorMessage("serialEvent", e);
      }
    }
  }

  /**
   * Prima modifica
   */
  protected void message(char[] chars, int length) {
	  }
  
public String readString(int byteCount, int timeout) throws SerialPortException, SerialPortTimeoutException {
	  char buf[]=new char[256];
	byte b[]=new byte[1];
	int count=0;
	do {
		b=port.readBytes();

		//System.out.print(b[0]);
		buf[count]=(char)b[0];
		
		count++;
	}while(b[0]!='\n'|| count<256);
	System.out.print("Value of buf ..."+String.valueOf(buf));
	return port.readString(byteCount, timeout);
	  
  }

public boolean portIsOpened(){
  return port.isOpened();
}
  /**
   * This will handle both ints, bytes and chars transparently.
   */
  public void write(int what) {  // will also cover char
    try {
      port.writeInt(what & 0xff);
    } catch (SerialPortException e) {
      errorMessage("write", e);
    }
  }


  public void write(byte bytes[]) {
    try {
      port.writeBytes(bytes);
    } catch (SerialPortException e) {
      errorMessage("write", e);
    }
  }


  /**
   * Write a String to the output. Note that this doesn't account
   * for Unicode (two bytes per char), nor will it send UTF8
   * characters.. It assumes that you mean to send a byte buffer
   * (most often the case for networking and serial i/o) and
   * will only use the bottom 8 bits of each char in the string.
   * (Meaning that internally it uses String.getBytes)
   * <p>
   * If you want to move Unicode data, you can first convert the
   * String to a byte stream in the representation of your choice
   * (i.e. UTF8 or two-byte Unicode data), and send it as a byte array.
   */
  public void write(String what) {
    write(what.getBytes());
  }

  public void setDTR(boolean state) {
    try {
      port.setDTR(state);
    } catch (SerialPortException e) {
      errorMessage("setDTR", e);
    }
  }

  public void setRTS(boolean state) {
    try {
      port.setRTS(state);
    } catch (SerialPortException e) {
      errorMessage("setRTS", e);
    }
  }

  /**
   * Reset the encoding used to convert the bytes coming in
   * before they are handed as Strings to {@Link #message(char[], int)}.
   */
  public synchronized void resetDecoding(Charset charset) {
    bytesToStrings = charset.newDecoder()
                      .onMalformedInput(CodingErrorAction.REPLACE)
                      .onUnmappableCharacter(CodingErrorAction.REPLACE)
                      .replaceWith("\u2e2e");
  }

  /**
   * General error reporting, all corraled here just in case
   * I think of something slightly more intelligent to do.
   */
  private static void errorMessage(String where, Throwable e) {
    System.err.println(format(tr("Error inside Serial.{0}()"), where));
    e.printStackTrace();
  }
  public static String tr(String s) {
	  return s;
  }
  public static String format(String fmt, Object... args) {
	    // Single quote is used to escape curly bracket arguments.

	    // - Prevents strings fixed at translation time to be fixed again
	    fmt = fmt.replace("''", "'");
	    // - Replace ' with the escaped version ''
	    fmt = fmt.replace("'", "''");

	    return MessageFormat.format(fmt, args);
	  }
  /*public static String tr(String s) {
	    String res;
	    try {
	      if (i18n == null)
	        res = s;
	      else
	        res = i18n.getString(s);
	    } catch (MissingResourceException e) {
	      res = s;
	    }

	    // The single % is the arguments selector in .PO files.
	    // We must put double %% inside the translations to avoid
	    // getting .PO processing in the way.
	    res = res.replace("%%", "%");

	    return res;
	  }*/
}
