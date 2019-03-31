package com.leekwars.utils.exceptions;

/**
 * Exception générique
 * @author Bezout
 */
public class LWException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * @param pMessage message d'erreur
	 */
	public LWException(final String pMessage) {
		super(pMessage);
	}
	
	/**
	 * @param pMessage message d'erreur
	 * @param pCause cause
	 */
	public LWException(final String pMessage, final Throwable pCause) {
		super(pMessage, pCause);
	}
	
	/**
	 * @param pCause cause
	 */
	public LWException(final Throwable pCause) {
		super(pCause);
	}
}
