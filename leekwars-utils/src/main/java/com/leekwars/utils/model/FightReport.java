package com.leekwars.utils.model;

public class FightReport {
	private int bonus;
	private int duration;
    //farmer1
    //farmer2
    //leeks1
    //leeks2
	private int win;
	private int[] flags1;
	private int[] flags2;
	/**
	 * @return the bonus
	 */
	public int getBonus() {
		return bonus;
	}
	/**
	 * @param pBonus the bonus to set
	 */
	public void setBonus(int pBonus) {
		bonus = pBonus;
	}
	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}
	/**
	 * @param pDuration the duration to set
	 */
	public void setDuration(int pDuration) {
		duration = pDuration;
	}
	/**
	 * @return the win
	 */
	public int getWin() {
		return win;
	}
	/**
	 * @param pWin the win to set
	 */
	public void setWin(int pWin) {
		win = pWin;
	}
	/**
	 * @return the flags1
	 */
	public int[] getFlags1() {
		return flags1;
	}
	/**
	 * @param pFlags1 the flags1 to set
	 */
	public void setFlags1(int[] pFlags1) {
		flags1 = pFlags1;
	}
	/**
	 * @return the flags2
	 */
	public int[] getFlags2() {
		return flags2;
	}
	/**
	 * @param pFlags2 the flags2 to set
	 */
	public void setFlags2(int[] pFlags2) {
		flags2 = pFlags2;
	}
}
