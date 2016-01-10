package com.leekwars.utils.model;

/**
 * Infos d'un chapeau de poireau
 * @author Bezout
 */
public class Hat extends TemplatedItem {
	private int level;
	private int hat_template;

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param pLevel the level to set
	 */
	public void setLevel(int pLevel) {
		level = pLevel;
	}
	/**
	 * @return the hat_template
	 */
	public int getHat_template() {
		return hat_template;
	}
	/**
	 * @param pHat_template the hat_template to set
	 */
	public void setHat_template(int pHat_template) {
		hat_template = pHat_template;
	}
}
