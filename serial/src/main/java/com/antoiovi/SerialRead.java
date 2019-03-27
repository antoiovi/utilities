package com.antoiovi;

import java.util.ArrayList;
import java.util.List;
 
public class SerialRead extends Serial {

	static final int MAX_BUFFER=10;
	List<String> buffer;

  public  SerialRead(String iname, int irate, int parityNone, int idatabits, double d, boolean setRTS, boolean setDTR) throws SerialException {
	  super(iname, irate, parityNone, idatabits, d, setRTS, setDTR);
	  buffer=new ArrayList<String>();
  }

  
  /**
   * 
   */
@Override
 protected void message(char[] chars, int length) {
	String line=String.valueOf(chars);
	if(buffer.size()==MAX_BUFFER)
		buffer.remove(0);
	buffer.add(line);
  }


public String getAnalogRead() {
	if(buffer.size()==0)return null;
	return buffer.remove(0);
	}
  
}
