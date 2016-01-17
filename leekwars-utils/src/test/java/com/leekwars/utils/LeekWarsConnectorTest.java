package com.leekwars.utils;

import org.junit.Test;

import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.model.Fight;

public class LeekWarsConnectorTest {

	@Test(expected=LWException.class)
	public void testConnection_ErrPassword() throws LWException {
		ErrorPasswordLeekWarsConnector lConnector = new ErrorPasswordLeekWarsConnector();
		lConnector.connect();
	}
	
	@Test
	//@org.junit.Ignore
	public void testConnection() throws LWException {
		DefaultLeekWarsConnector lConnector = new DefaultLeekWarsConnector("Bezout", "BleLeek01");
		
		lConnector.connect();
		System.out.println("[JUNIT] Connecté ...");
		
		System.out.println("[JUNIT] " + lConnector.getFarmer().getLeeks());
		
		//lConnector.getGarden();
		//lConnector.startSoloFight(18906, 18428);
		//lConnector.startFarmerFight(693);
		
		//Fight lFight = lConnector.getFight(15512269);
		//System.out.println(LWUtils.getFightResult(lConnector.getFarmer(), lFight));
		
		//lConnector.registerAllTournaments();
		
		lConnector.getTeam();
		
		System.out.println("[JUNIT] Fin ...");
	}
}
