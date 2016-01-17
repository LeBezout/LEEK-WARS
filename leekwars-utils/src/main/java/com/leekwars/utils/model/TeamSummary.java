package com.leekwars.utils.model;

/**
 * Infos minimales d'une equipe
 * @author Bezout
 */
public class TeamSummary extends Identity {
	private int level;
	private long emblem_changed;
	
	@Override
	public String toString() {
		return "[" + getId() + "] " + getName() + ". Level " + level;
	}
	
	/**
	 * @return the level
	 */
	public long getLevel() {
		return level;
	}
	/**
	 * @param pLevel the level to set
	 */
	public void setLevel(int pLevel) {
		level = pLevel;
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
}
