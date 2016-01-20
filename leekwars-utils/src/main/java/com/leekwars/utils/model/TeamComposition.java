package com.leekwars.utils.model;

/**
 * Infos d'une cmposition d'équipe
 * @author Bezout
 */
public class TeamComposition extends Entity {
	private int total_level;
	private LeekSummary[] leeks;
	private TeamTournament tournament;
	private LeekSummary[] unengaged_leeks;
	
	
	/**
	 * @return the total_level
	 */
	public int getTotal_level() {
		return total_level;
	}
	/**
	 * @param pTotal_level the total_level to set
	 */
	public void setTotal_level(int pTotal_level) {
		total_level = pTotal_level;
	}

	/**
	 * @return the leeks
	 */
	public LeekSummary[] getLeeks() {
		return leeks;
	}
	/**
	 * @param pLeeks the leeks to set
	 */
	public void setLeeks(LeekSummary[] pLeeks) {
		leeks = pLeeks;
	}

	/**
	 * @return the tournament
	 */
	public TeamTournament getTournament() {
		return tournament;
	}
	/**
	 * @param pTournament the tournament to set
	 */
	public void setTournament(TeamTournament pTournament) {
		tournament = pTournament;
	}

	/**
	 * @return the unengaged_leeks
	 */
	public LeekSummary[] getUnengaged_leeks() {
		return unengaged_leeks;
	}
	/**
	 * @param pUnengaged_leeks the unengaged_leeks to set
	 */
	public void setUnengaged_leeks(LeekSummary[] pUnengaged_leeks) {
		unengaged_leeks = pUnengaged_leeks;
	}

	@Override
	public int getLevel() {
		return total_level;
	}
}
