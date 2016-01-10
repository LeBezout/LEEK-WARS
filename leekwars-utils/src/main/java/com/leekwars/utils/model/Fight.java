package com.leekwars.utils.model;

import java.util.Map;

/**
 * Informations sur un combat.
 * @author Bezout
 */
public class Fight extends Identity {
	private long date;
	private int type;
	private int context;
	private int status; // 0=en cours, 1=terminé
	private int winner;
	
	private LeekSummary[] leeks1;
	private LeekSummary[] leeks2;
	
	private Map<String, Identity> farmers1;
	private Map<String, Identity> farmers2;
	
	private long team1;
	private long team2;
	private String team1_name;
	private String team2_name;
	
	private String[] comments;
	private FightReport report;
	
	private long tournament;
	
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
	 * @return the farmers1
	 */
	public Map<String, Identity> getFarmers1() {
		return farmers1;
	}
	/**
	 * @param pFarmers1 the farmers1 to set
	 */
	public void setFarmers1(Map<String, Identity> pFarmers1) {
		farmers1 = pFarmers1;
	}
	/**
	 * @return the farmers2
	 */
	public Map<String, Identity> getFarmers2() {
		return farmers2;
	}
	/**
	 * @param pFarmers2 the farmers2 to set
	 */
	public void setFarmers2(Map<String, Identity> pFarmers2) {
		farmers2 = pFarmers2;
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
//	/**
//	 * @return the farmer1_name
//	 */
//	public String getFarmer1_name() {
//		return farmer1_name;
//	}
//	/**
//	 * @param pFarmer1_name the farmer1_name to set
//	 */
//	public void setFarmer1_name(String pFarmer1_name) {
//		farmer1_name = pFarmer1_name;
//	}
//	/**
//	 * @return the farmer2_name
//	 */
//	public String getFarmer2_name() {
//		return farmer2_name;
//	}
//	/**
//	 * @param pFarmer2_name the farmer2_name to set
//	 */
//	public void setFarmer2_name(String pFarmer2_name) {
//		farmer2_name = pFarmer2_name;
//	}
	/**
	 * @return the comments
	 */
	public String[] getComments() {
		return comments;
	}
	/**
	 * @param pComments the comments to set
	 */
	public void setComments(String[] pComments) {
		comments = pComments;
	}
	/**
	 * @return the leeks1
	 */
	public LeekSummary[] getLeeks1() {
		return leeks1;
	}
	/**
	 * @param pLeeks1 the leeks1 to set
	 */
	public void setLeeks1(LeekSummary[] pLeeks1) {
		leeks1 = pLeeks1;
	}
	/**
	 * @return the leeks2
	 */
	public LeekSummary[] getLeeks2() {
		return leeks2;
	}
	/**
	 * @param pLeeks2 the leeks2 to set
	 */
	public void setLeeks2(LeekSummary[] pLeeks2) {
		leeks2 = pLeeks2;
	}
	/**
	 * @return the report
	 */
	public FightReport getReport() {
		return report;
	}
	/**
	 * @param pReport the report to set
	 */
	public void setReport(FightReport pReport) {
		report = pReport;
	}
	/**
	 * @return the team1_name
	 */
	public String getTeam1_name() {
		return team1_name;
	}
	/**
	 * @param pTeam1_name the team1_name to set
	 */
	public void setTeam1_name(String pTeam1_name) {
		team1_name = pTeam1_name;
	}
	/**
	 * @return the team2_name
	 */
	public String getTeam2_name() {
		return team2_name;
	}
	/**
	 * @param pTeam2_name the team2_name to set
	 */
	public void setTeam2_name(String pTeam2_name) {
		team2_name = pTeam2_name;
	}
	/**
	 * @return the tournament
	 */
	public long getTournament() {
		return tournament;
	}
	/**
	 * @param pTournament the tournament to set
	 */
	public void setTournament(long pTournament) {
		tournament = pTournament;
	}
	
}
