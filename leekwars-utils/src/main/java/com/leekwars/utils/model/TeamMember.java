package com.leekwars.utils.model;

/**
 * Infos d'un membre d'une équipe
 * @author Bezout
 */
public class TeamMember extends FarmerSummary {
	private String grade;
	private boolean connected;
	
	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * @param pGrade the grade to set
	 */
	public void setGrade(String pGrade) {
		grade = pGrade;
	}
	/**
	 * @return the connected
	 */
	public boolean isConnected() {
		return connected;
	}
	/**
	 * @param pConnected the connected to set
	 */
	public void setConnected(boolean pConnected) {
		connected = pConnected;
	}
}
