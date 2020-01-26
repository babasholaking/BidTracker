package com.numeric.bidtracker;

// General purpose bid exception-  wraps more specific service-related exceptions
public class InvalidBidException extends Exception {
   private static final long serialVersionUID = -9058512231276460341L;
   public InvalidBidException(String message) { super(message); }
   public InvalidBidException(Throwable cause) { super(cause); }
   public InvalidBidException(String message, Throwable cause) { super(message, cause); }
}
