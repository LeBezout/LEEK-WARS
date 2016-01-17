package com.leekwars.utils.model;

/**
 * Infos d'une équipe
 * @author Bezout
 */
public class Team extends Entity {
	private long xp;
	private long up_xp;
	private long down_xp;
	private int remaining_xp;
	private int victories;
	private int draws;
	private int defeats;
	private long emblem_changed;
	private String description;
	private double ratio;
	private int member_count;
	private TeamMember[] members;
	private int leek_count;
	private boolean opened;
	private LeekSummary[] leeks;
	private boolean candidacy;
	
	/**
	 * @return the xp
	 */
	public long getXp() {
		return xp;
	}
	/**
	 * @param pXp the xp to set
	 */
	public void setXp(long pXp) {
		xp = pXp;
	}
	/**
	 * @return the up_xp
	 */
	public long getUp_xp() {
		return up_xp;
	}
	/**
	 * @param pUp_xp the up_xp to set
	 */
	public void setUp_xp(long pUp_xp) {
		up_xp = pUp_xp;
	}
	/**
	 * @return the down_xp
	 */
	public long getDown_xp() {
		return down_xp;
	}
	/**
	 * @param pDown_xp the down_xp to set
	 */
	public void setDown_xp(long pDown_xp) {
		down_xp = pDown_xp;
	}
	/**
	 * @return the remaining_xp
	 */
	public int getRemaining_xp() {
		return remaining_xp;
	}
	/**
	 * @param pRemaining_xp the remaining_xp to set
	 */
	public void setRemaining_xp(int pRemaining_xp) {
		remaining_xp = pRemaining_xp;
	}
	/**
	 * @return the victories
	 */
	public int getVictories() {
		return victories;
	}
	/**
	 * @param pVictories the victories to set
	 */
	public void setVictories(int pVictories) {
		victories = pVictories;
	}
	/**
	 * @return the draws
	 */
	public int getDraws() {
		return draws;
	}
	/**
	 * @param pDraws the draws to set
	 */
	public void setDraws(int pDraws) {
		draws = pDraws;
	}
	/**
	 * @return the defeats
	 */
	public int getDefeats() {
		return defeats;
	}
	/**
	 * @param pDefeats the defeats to set
	 */
	public void setDefeats(int pDefeats) {
		defeats = pDefeats;
	}
	/**
	 * @return the emblem_changed
	 */
	public long getEmblem_changed() {
		return emblem_changed;
	}
	/**
	 * @param pEmblem_changed the emblem_changed to set
	 */
	public void setEmblem_changed(long pEmblem_changed) {
		emblem_changed = pEmblem_changed;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param pDescription the description to set
	 */
	public void setDescription(String pDescription) {
		description = pDescription;
	}
	/**
	 * @return the ratio
	 */
	public double getRatio() {
		return ratio;
	}
	/**
	 * @param pRatio the ratio to set
	 */
	public void setRatio(double pRatio) {
		ratio = pRatio;
	}
	/**
	 * @return the member_count
	 */
	public int getMember_count() {
		return member_count;
	}
	/**
	 * @param pMember_count the member_count to set
	 */
	public void setMember_count(int pMember_count) {
		member_count = pMember_count;
	}
	/**
	 * @return the members
	 */
	public TeamMember[] getMembers() {
		return members;
	}
	/**
	 * @param pMembers the members to set
	 */
	public void setMembers(TeamMember[] pMembers) {
		members = pMembers;
	}
	/**
	 * @return the leek_count
	 */
	public int getLeek_count() {
		return leek_count;
	}
	/**
	 * @param pLeek_count the leek_count to set
	 */
	public void setLeek_count(int pLeek_count) {
		leek_count = pLeek_count;
	}
	/**
	 * @return the opened
	 */
	public boolean isOpened() {
		return opened;
	}
	/**
	 * @param pOpened the opened to set
	 */
	public void setOpened(boolean pOpened) {
		opened = pOpened;
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
	 * @return the candidacy
	 */
	public boolean isCandidacy() {
		return candidacy;
	}
	/**
	 * @param pCandidacy the candidacy to set
	 */
	public void setCandidacy(boolean pCandidacy) {
		candidacy = pCandidacy;
	}
	
}
