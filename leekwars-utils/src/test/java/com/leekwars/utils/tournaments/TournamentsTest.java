package com.leekwars.utils.tournaments;

import org.junit.Test;

import com.leekwars.utils.DefaultLeekWarsConnector;
import com.leekwars.utils.exceptions.LWException;

public class TournamentsTest {

	@Test
	@org.junit.Ignore
	public void registerAllTournaments() throws LWException {
		DefaultLeekWarsConnector lConnector = new DefaultLeekWarsConnector("TODO", "TODO");
		lConnector.connect();
		lConnector.registerAllTournaments();
	}

}
