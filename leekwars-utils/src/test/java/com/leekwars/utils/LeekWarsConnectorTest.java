package com.leekwars.utils;

import com.leekwars.utils.model.Fight;
import org.junit.Test;

import com.leekwars.utils.exceptions.LWException;

public class LeekWarsConnectorTest {

	@Test(expected=LWException.class)
	public void testConnection_ErrPassword() throws LWException {
		ErrorPasswordLeekWarsConnector lConnector = new ErrorPasswordLeekWarsConnector();
		lConnector.connect();
	}

	@Test
	@org.junit.Ignore
	public void testGetVersion() throws LWException {
		DefaultLeekWarsConnector lConnector = new DefaultLeekWarsConnector("ignored", "ignored");

		System.out.println("[JUNIT] Version courante de LW = " +  lConnector.getVersion());
	}
	
	@Test
	@org.junit.Ignore
	public void testConnection() throws LWException {
		DefaultLeekWarsConnector lConnector = new DefaultLeekWarsConnector("TODO", "TODO");
		
		lConnector.connect();
		System.out.println("[JUNIT] Connecté ...");

		System.out.println("[JUNIT] " + lConnector.getFarmer().getLeeks());
		
		//lConnector.getGarden();
		//lConnector.startSoloFight(18906, 18428);
		//lConnector.startFarmerFight(693);

		//lConnector.registerAllTournaments();
		
		//lConnector.getTeamCompositions();
		
		System.out.println("[JUNIT] Fin ...");
	}


    @Test
    @org.junit.Ignore
    public void tesGetFight() throws LWException {
        DefaultLeekWarsConnector lConnector = new DefaultLeekWarsConnector();
        Fight lFight = lConnector.getFight(30998653);
        if (lFight == null) {
            System.out.println("NULL");
        } else {
            System.out.println(LWUtils.getFightResult(lConnector.getFarmer(), lFight)); //TODO Farmer bidon
        }
    }
}
