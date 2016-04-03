package com.leekwars.utils;

/**
 * Classe par défaut permettant de définir Login et MDP
 * @author Bezout
 */
public class DefaultLeekWarsConnector extends AbstractLeekWarsConnector {
	
	/**
	 * Constructeur utilisable pour appeler des services qui n'ont pas besoin d'authentification
	 * Exemple : fight/get
	 */
	public DefaultLeekWarsConnector() {
		this("ignored", "ignored");
	}
	
	/**
	 * Constructeur utilisable pour appeler des services qui necessitent la récuparation du token d'authentification
	 * @param pLogin
	 * @param pPassword
	 */
	public DefaultLeekWarsConnector(final String pLogin, final String pPassword) {
		setUsername(pLogin);
		setPassword(pPassword);
	}
}
