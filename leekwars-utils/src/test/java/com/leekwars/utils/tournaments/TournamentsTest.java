package com.leekwars.utils.tournaments;

import com.leekwars.utils.DefaultLeekWarsConnector;
import com.leekwars.utils.exceptions.LWException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class TournamentsTest {

	@Test
	@Disabled
	void registerAllTournaments() throws LWException {
		DefaultLeekWarsConnector lConnector = new DefaultLeekWarsConnector("TODO", "TODO");
		lConnector.connect();
		lConnector.registerAllTournaments();
	}

}
