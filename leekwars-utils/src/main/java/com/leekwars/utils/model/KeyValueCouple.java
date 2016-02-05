package com.leekwars.utils.model;

/**
 * Représente un couple clef/valeur, par exemple un registre
 * @author Bezout
 */
public class KeyValueCouple {
	private String key;
	private String value;
	
	/**
	 * Constreucteur par défaut
	 */
	public KeyValueCouple() {
		
	}
	
	/**
	 * Constructeur
	 * @param pKey
	 * @param pValue
	 */
	public KeyValueCouple(String pKey, String pValue) {
		key = pKey;
		value= pValue;
	}
	
	@Override
	public String toString() {
		return key + '=' + value;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param pKey the key to set
	 */
	public void setKey(String pKey) {
		key = pKey;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param pValue the value to set
	 */
	public void setValue(String pValue) {
		value = pValue;
	}
	
}
