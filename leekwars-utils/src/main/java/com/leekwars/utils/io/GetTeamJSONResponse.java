package com.leekwars.utils.io;

import com.leekwars.utils.model.SimpleJSONResponse;
import com.leekwars.utils.model.Team;

/**
 * Informations recues suite à un appel à "team/get".
 * @author Bezout
 */
@Deprecated // FIXME A SUPPRIMER
public class GetTeamJSONResponse extends SimpleJSONResponse {
	private Team team;

	/**
	 * @return the team
	 */
	public Team getTeam() {
		return team;
	}
	/**
	 * @param pTeam the team to set
	 */
	public void setTeam(Team pTeam) {
		team = pTeam;
	}
}
