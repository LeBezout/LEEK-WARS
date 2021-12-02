package com.leekwars.utils.model;

/**
 * Informations minimales d'un poireau
 * @author Bezout
 */
public class LeekSummary extends Entity {
	private String color;
	private int capital;
	private int skin;
    //private int ai;
    //private int weapon;
    //private int life;
    //private int strength;
    //private int wisdom;
    //private int agility;
    //private int resistance;
    //private int science;
    //private int magic;
    //private int frequency;
    //private int tp;
    //private int mp;
	//private Hat hat; // since 2.27 => (avant int) {
    //          "id": 1011300,
    //          "template": 69,
    //          "hat_template": 1,
    //          "quantity": 1
    //        }

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
//	/**
//	 * @return the hat
//	 */
//	public Hat getHat() {
//		return hat;
//	}
//	/**
//	 * @param pHat the hat to set
//	 */
//	public void setHat(Hat pHat) {
//		hat = pHat;
//	}
}
