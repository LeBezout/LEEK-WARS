package com.leekwars.utils;

/**
 * Classe par défaut permettant de définir Login et MDP
 * @author Bezout
 */
public class DefaultLeekWarsConnector extends AbstractLeekWarsConnector {
	
	/**
	 * @param pLogin
	 * @param pPassword
	 */
	public DefaultLeekWarsConnector(final String pLogin, final String pPassword) {
		setUsername(pLogin);
		setPassword(pPassword);
	}

}
