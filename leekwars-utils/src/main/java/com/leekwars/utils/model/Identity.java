package com.leekwars.utils.model;

/**
 * Classe générique pour stocker n'importe quel objet identifiable (nom + id), nom étant optionnel.
 * @author Bezout
 */
public class Identity {
	private long id;
	private String name;
	
	/**
	 * Constructeur
	 */
	public Identity() {
		super();
	}
	/**
	 * Constructeur avec parametres
	 * @param pId
	 * @param pName
	 */
	public Identity(long pId, String pName) {
		id= pId;
		name = pName;
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param pId the id to set
	 */
	public void setId(long pId) {
		id = pId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param pName the name to set
	 */
	public void setName(String pName) {
		name = pName;
	}

	@Override
	public String toString() {
		return "[" + id + "] " + name;
	}
}
