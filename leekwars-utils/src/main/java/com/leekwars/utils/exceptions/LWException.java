package com.leekwars.utils.exceptions;

/**
 * Exception générique
 * @author Bezout
 */
public class LWException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * @param pMessage
	 */
	public LWException(final String pMessage) {
		super(pMessage);
	}
	
	/**
	 * @param pMessage
	 * @param pCause
	 */
	public LWException(final String pMessage, final Throwable pCause) {
		super(pMessage, pCause);
	}
	
	/**
	 * @param pCause
	 */
	public LWException(final Throwable pCause) {
		super(pCause);
	}
}
