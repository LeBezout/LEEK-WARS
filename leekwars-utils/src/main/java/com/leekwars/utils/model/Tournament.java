package com.leekwars.utils.model;

/**
 * Infos d'un tournoi
 * @author Bezout
 */
public class Tournament extends Identity {
	private long date;

	/**
	 * @return the date
	 */
	public long getDate() {
		return date;
	}

	/**
	 * @param pDate the date to set
	 */
	public void setDate(long pDate) {
		date = pDate;
	}
	
}
