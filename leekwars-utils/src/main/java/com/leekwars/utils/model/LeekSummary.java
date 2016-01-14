package com.leekwars.utils.model;

/**
 * Informations minimales d'un poireau
 * @author Bezout
 */
public class LeekSummary extends Entity {
	//"id":18906,"name":"CapitaineFlirt","color":"#000000","capital":0,"level":301,"talent":2510,"skin":12,"hat":null
	private String color;
	private int capital;
	private int skin;
	private int hat;
	
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param pColor the color to set
	 */
	public void setColor(String pColor) {
		color = pColor;
	}
	/**
	 * @return the capital
	 */
	public int getCapital() {
		return capital;
	}
	/**
	 * @param pCapital the capital to set
	 */
	public void setCapital(int pCapital) {
		capital = pCapital;
	}
	/**
	 * @return the skin
	 */
	public int getSkin() {
		return skin;
	}
	/**
	 * @param pSkin the skin to set
	 */
	public void setSkin(int pSkin) {
		skin = pSkin;
	}
	/**
	 * @return the hat
	 */
	public int getHat() {
		return hat;
	}
	/**
	 * @param pHat the hat to set
	 */
	public void setHat(int pHat) {
		hat = pHat;
	}
}
