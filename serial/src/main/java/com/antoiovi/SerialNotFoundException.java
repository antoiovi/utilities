package com.antoiovi;

 @SuppressWarnings("serial")
public class SerialNotFoundException extends SerialException {
  public SerialNotFoundException() {
    super();
  }

  public SerialNotFoundException(String message) {
    super(message);
  }

  public SerialNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public SerialNotFoundException(Throwable cause) {
    super(cause);
  }
}
