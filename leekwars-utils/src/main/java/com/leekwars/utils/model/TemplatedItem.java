package com.leekwars.utils.model;

/**
 * Classe générique pour un élément basé sur un template
 * @author Bezout
 */
public class TemplatedItem extends Identity {
	private int template;
	
	/**
	 * @return the template
	 */
	public int getTemplate() {
		return template;
	}
	/**
	 * @param pTemplate the template to set
	 */
	public void setTemplate(int pTemplate) {
		template = pTemplate;
	}
}
