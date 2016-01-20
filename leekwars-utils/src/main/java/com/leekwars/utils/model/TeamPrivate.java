package com.leekwars.utils.model;

/**
 * Infos privées d'une équipe
 * @author Bezout
 */
public class TeamPrivate extends Team {
	// private FarmerSummary?[] candidacies;
	private long forum;
	private TeamComposition[] compositions;
	
	/**
	 * @return the forum
	 */
	public long getForum() {
		return forum;
	}
	/**
	 * @param pForum the forum to set
	 */
	public void setForum(long pForum) {
		forum = pForum;
	}
	/**
	 * @return the compositions
	 */
	public TeamComposition[] getCompositions() {
		return compositions;
	}
	/**
	 * @param pCompositions the compositions to set
	 */
	public void setCompositions(TeamComposition[] pCompositions) {
		compositions = pCompositions;
	}
}
