package com.leekwars.utils.model;

import com.leekwars.utils.exceptions.LWException;

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
	
	/**
	 * @throws LWException si non conforme à la règle des registres
	 */
	public void validate() throws LWException {
		if (key == null || key.isEmpty()) {
			throw new LWException("key is null or empty");
		}
		if (value == null || value.isEmpty()) {
			throw new LWException("value is null or empty");
		}
	}
	/**
	 * @throws LWException si non conforme à la règle des registres
	 */
	public void validateForRegister() throws LWException {
		// Validation de base
		validate();
		// REGLE POUR LES REGISTRES : 
		//	- La clé, chaîne qui doit contenir 100 caractères au maximum
		//	- La valeur, chaîne qui doit contenir 5000 caractères au maximum
		if (key.length() > 100) {
			throw new LWException("key's length is greater than 100 characters");
		}
		if (value.length() > 5000) {
			throw new LWException("value's length is greater than 5 000 characters");
		}
	}
}
