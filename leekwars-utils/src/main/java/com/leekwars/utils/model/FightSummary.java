package com.leekwars.utils.model;

/**
 * Informations minimales sur un combat.
 * @author Bezout
 */
public class FightSummary extends Identity {
	// "id":15435057,"date":1451254810,"type":1,"context":2,"status":1,"winner":1,"farmer_team":1,"result":"win","farmer1":16748,"farmer2":8750,"farmer1_name":"Bezout","farmer2_name":"vinc"
	private long date;
	private int type;
	private int context;
	private int status; // 0=en cours, 1=terminé
	private int winner;
	private int farmer_team;
	private String result;
	
	private long farmer1;
	private long farmer2;
	
	private long team1;
	private long team2;
	
	private String farmer1_name;
	private String farmer2_name;
	
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
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param pType the type to set
	 */
	public void setType(int pType) {
		type = pType;
	}
	/**
	 * @return the context
	 */
	public int getContext() {
		return context;
	}
	/**
	 * @param pContext the context to set
	 */
	public void setContext(int pContext) {
		context = pContext;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param pStatus the status to set
	 */
	public void setStatus(int pStatus) {
		status = pStatus;
	}
	/**
	 * @return the winner
	 */
	public int getWinner() {
		return winner;
	}
	/**
	 * @param pWinner the winner to set
	 */
	public void setWinner(int pWinner) {
		winner = pWinner;
	}
	/**
	 * @return the farmer_team
	 */
	public int getFarmer_team() {
		return farmer_team;
	}
	/**
	 * @param pFarmer_team the farmer_team to set
	 */
	public void setFarmer_team(int pFarmer_team) {
		farmer_team = pFarmer_team;
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param pResult the result to set
	 */
	public void setResult(String pResult) {
		result = pResult;
	}
	/**
	 * @return the farmer1
	 */
	public long getFarmer1() {
		return farmer1;
	}
	/**
	 * @param pFarmer1 the farmer1 to set
	 */
	public void setFarmer1(long pFarmer1) {
		farmer1 = pFarmer1;
	}
	/**
	 * @return the farmer2
	 */
	public long getFarmer2() {
		return farmer2;
	}
	/**
	 * @param pFarmer2 the farmer2 to set
	 */
	public void setFarmer2(long pFarmer2) {
		farmer2 = pFarmer2;
	}
	/**
	 * @return the team1
	 */
	public long getTeam1() {
		return team1;
	}
	/**
	 * @param pTeam1 the team1 to set
	 */
	public void setTeam1(long pTeam1) {
		team1 = pTeam1;
	}
	/**
	 * @return the team2
	 */
	public long getTeam2() {
		return team2;
	}
	/**
	 * @param pTeam2 the team2 to set
	 */
	public void setTeam2(long pTeam2) {
		team2 = pTeam2;
	}
	/**
	 * @return the farmer1_name
	 */
	public String getFarmer1_name() {
		return farmer1_name;
	}
	/**
	 * @param pFarmer1_name the farmer1_name to set
	 */
	public void setFarmer1_name(String pFarmer1_name) {
		farmer1_name = pFarmer1_name;
	}
	/**
	 * @return the farmer2_name
	 */
	public String getFarmer2_name() {
		return farmer2_name;
	}
	/**
	 * @param pFarmer2_name the farmer2_name to set
	 */
	public void setFarmer2_name(String pFarmer2_name) {
		farmer2_name = pFarmer2_name;
	}

}
