package com.antoiovi;

import java.io.IOException;

@SuppressWarnings("serial")
public class SerialException extends IOException {
  public SerialException() {
    super();
  }

  public SerialException(String message) {
    super(message);
  }

  public SerialException(String message, Throwable cause) {
    super(message, cause);
  }

  public SerialException(Throwable cause) {
    super(cause);
  }
}
