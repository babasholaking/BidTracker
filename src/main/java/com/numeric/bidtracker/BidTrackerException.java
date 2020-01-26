package com.numeric.bidtracker;

public class BidTrackerException extends Exception {
	private static final long serialVersionUID = 1380841808914566967L;

	public BidTrackerException(String message) { super(message); }
    public BidTrackerException(Throwable cause) { super(cause); }
    public BidTrackerException(String message, Throwable cause) { super(message, cause); }
}
