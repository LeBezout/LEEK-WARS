package com.leekwars.utils.model;

/**
 * Infos d'un tournoi d'équipe
 * @author Bezout
 */
public class TeamTournament {
	private boolean registered;
	private Tournament current;
	
	/**
	 * @return the registered
	 */
	public boolean isRegistered() {
		return registered;
	}
	/**
	 * @param pRegistered the registered to set
	 */
	public void setRegistered(boolean pRegistered) {
		registered = pRegistered;
	}
	/**
	 * @return the current
	 */
	public Tournament getCurrent() {
		return current;
	}
	/**
	 * @param pCurrent the current to set
	 */
	public void setCurrent(Tournament pCurrent) {
		current = pCurrent;
	}
}
