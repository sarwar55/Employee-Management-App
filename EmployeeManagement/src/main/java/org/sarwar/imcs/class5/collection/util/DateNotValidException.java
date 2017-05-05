package org.sarwar.imcs.class5.collection.util;

public class DateNotValidException extends Exception {

	private static final long serialVersionUID = 6426028648355901213L;

	public DateNotValidException() {
		super();
	}

	public DateNotValidException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DateNotValidException(String message, Throwable cause) {
		super(message, cause);
	}

	public DateNotValidException(String message) {
		super(message);
	}

	public DateNotValidException(Throwable cause) {
		super(cause);
	}

}
