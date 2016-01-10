package com.leekwars.utils.model;

/**
 * Infos d'une potion
 * @author Bezout
 */
public class Potion extends TemplatedItem {
	
	private int quantity;
	
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param pQuantity the quantity to set
	 */
	public void setQuantity(int pQuantity) {
		quantity = pQuantity;
	}
}
