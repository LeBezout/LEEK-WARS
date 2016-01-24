package com.leekwars.utils;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.http.HttpResponseWrapper;
import com.leekwars.utils.http.HttpUtils;
import com.leekwars.utils.io.GetFarmerJSONResponse;
import com.leekwars.utils.io.GetFightJSONResponse;
import com.leekwars.utils.io.GetGardenJSONResponse;
import com.leekwars.utils.io.GetTeamJSONResponse;
import com.leekwars.utils.io.GetTeamPrivateJSONResponse;
import com.leekwars.utils.io.LoginJSONResponse;
import com.leekwars.utils.io.StartFightJSONResponse;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.Fight;
import com.leekwars.utils.model.Garden;
import com.leekwars.utils.model.LeekSummary;
import com.leekwars.utils.model.SimpleJSONResponse;
import com.leekwars.utils.model.Team;
import com.leekwars.utils.model.TeamComposition;
import com.leekwars.utils.model.TeamPrivate;

/**
 * Classe mère permettant d'effectuer tous les appels à l'API LW
 * @author Bezout
 * @see http://leekwars.com/help/api
 */
public abstract class AbstractLeekWarsConnector {
	protected final Logger LOGGER = Logger.getLogger(getClass().getName());
	protected final Logger LOGGER_TRACE = Logger.getLogger("JSON_TRACE");

	protected static final String LEEK_WARS_ROOT_URL = "http://leekwars.com/api/";
	protected static final String ENCODING = "UTF-8";
	
	protected boolean mTrace;
	private String mUsername;
	private String mPassword;
	private String mToken;
	private String mPhpSessionId;
	private Farmer mFarmer;
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	public final String getUsername() {
		return mUsername;
	}
	protected final String getPassword() {
		return mPassword;
	}
	protected final String getToken() {
		return mToken;
	}
	
	/**
	 * Permet d'activer les traces des flux JSON recus dans un logger dédié nommé JSON_TRACE
	 * @param pActiveTrace
	 */
	public void setTrace(final boolean pActiveTrace) {
		mTrace = pActiveTrace;
	}
	
	/**
	 * @return infos de l'éléveur
	 */
	public final Farmer getFarmer() {
		return mFarmer;
	}
	
	/**
	 * @param pUsername
	 */
	protected final void setUsername(final String pUsername) {
		mUsername = pUsername;
	}
	/**
	 * @param mPassword
	 */
	protected final void setPassword(final String pPassword) {
		mPassword = pPassword;
	}
	
	/*
	 * @param pResponse
	 * @param pDefaultMessage
	 * @throws LWException
	 */
//	private void validateResponse(final HttpResponse pResponse, final String pDefaultMessage) throws LWException {
//		SimpleJSONResponse lBasicResponse = gson.fromJson(pResponse.getResponseText(), SimpleJSONResponse.class);
//		if (!lBasicResponse.isSuccess()) {
//			throw new LWException(lBasicResponse.getError() == null ? pDefaultMessage : lBasicResponse.getError());
//		}
//		// OK
//	}
	
	protected <T extends SimpleJSONResponse> T validateResponse(final HttpResponseWrapper pResponse, final String pDefaultMessage, final Class<T> pType) throws LWException {
		trace(pResponse, pType);
		T lResponse = gson.fromJson(pResponse.getResponseText(), pType);
		if (!lResponse.isSuccess()) {
			throw new LWException(lResponse.getError() == null ? pDefaultMessage : lResponse.getError());
		}
		// OK
		return lResponse;
	}
	
	private boolean validateRegisterTournamentResponse(final HttpResponseWrapper pResponse, final String pDefaultMessage) throws LWException {
		trace(pResponse, SimpleJSONResponse.class);
		SimpleJSONResponse lBasicResponse = gson.fromJson(pResponse.getResponseText(), SimpleJSONResponse.class);
		if (!lBasicResponse.isSuccess()) {
			if (lBasicResponse.getError().equals("already_registered")) return false; // OK
			throw new LWException(lBasicResponse.getError() == null ? pDefaultMessage : lBasicResponse.getError());
		}
		// OK
		return true;
	}
	
	/**
	 * @param pResponse flux JSON brut
	 * @param pType type attendu en sortie (informatif)
	 */
	protected <T extends SimpleJSONResponse> void trace(final HttpResponseWrapper pResponse, final Class<T> pType) {
		if (mTrace) {
			try {
				LOGGER_TRACE.info("--------------------------------------------------------------------------------------");
				if (pResponse.getUrlCalled() == null) {
					LOGGER_TRACE.info(pType.getCanonicalName());
				} else {
					LOGGER_TRACE.info(String.format("%s ==> %s :", pResponse.getUrlCalled(), pType.getCanonicalName()));
				}
				LOGGER_TRACE.info(gson.toJson(new JsonParser().parse(pResponse.getResponseText())));
			} catch (Exception e) {
				LOGGER.error("Impossible d'effectuer la trace de " + pType.getCanonicalName());
			}
		}
	}
	
	/**
	 * Connexion (récuparation du token et du php session id) via l'API
	 * @throws LWException
	 */
	public final void connect() throws LWException {
		final String lUrl = LEEK_WARS_ROOT_URL + "farmer/login-token/"+mUsername+'/'+mPassword;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, null);
		final LoginJSONResponse lLoginResponse = validateResponse(lResponse, "Can't connect to LeekWars for " + mUsername, LoginJSONResponse.class);
		mPhpSessionId = lResponse.getCookie("PHPSESSID");
		mToken = lLoginResponse.getToken();
		mFarmer = lLoginResponse.getFarmer();
		LOGGER.debug("TOKEN=" + mToken);
		LOGGER.debug("PHPSESSID=" + mPhpSessionId);
		LOGGER.info("FARMER=" + mFarmer);
		LOGGER.info("TEAM=" + mFarmer.getTeam());
	}
	
	/**
	 * Connexion (récuparation du token et du php session id) via l'API seulement si pas déjà connecté.
	 * @throws LWException
	 */
	public final void connectIfNeeded() throws LWException {
		if (mPhpSessionId == null || mToken == null) {
			connect();
		}
	}
	
	/**
	 * @throws LWException
	 */
	public final void invalidateToken() throws LWException {
		checkConnected();
		final String lUrl = LEEK_WARS_ROOT_URL + "farmer/disconnect/" + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.post(lUrl, "", mPhpSessionId);
		validateResponse(lResponse, "Bad token", SimpleJSONResponse.class);
		// OK
		mToken = null;
		mPhpSessionId = null;
	}
	
	public final void checkConnected() throws LWException {
		if (mPhpSessionId == null || mToken == null) {
			throw new LWException("Not connected !");
		}
	}
	
	/**
	 * @return Garden
	 * @throws LWException
	 */
	public Garden getGarden() throws LWException {
		checkConnected();
		final String lUrl = LEEK_WARS_ROOT_URL + "garden/get/" + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		final GetGardenJSONResponse lGargen = validateResponse(lResponse, "Cannot obtain garden", GetGardenJSONResponse.class);
		return lGargen.getGarden();
	}
	
	/**
	 * Permet d'inscrire tous les poireaux et l'éleveur aux prochains tournois
	 * @throws LWException
	 */
	public void registerAllTournaments() throws LWException {
		checkConnected();
		LOGGER.info("-------------------------------------------------------------");
		LOGGER.info(" INSCRIPTION AUX TOURNOIS POUR " +  mFarmer.getName());
		LOGGER.info("-------------------------------------------------------------");
		// 1. l'eleveur : farmer/register-tournament/token
		String lUrl = LEEK_WARS_ROOT_URL + "farmer/register-tournament/" + mToken;
		HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		boolean inscrit = validateRegisterTournamentResponse(lResponse, "Cannot register tournament for the farmer " + mFarmer.getName());
		LOGGER.info("Eleveur " + mFarmer.getName() + (inscrit ? " inscrit" : " déjà inscrit") + " au tournoi");
		
		// 2. chacun des poireaux : leek/register-tournament/leek_id/token
		for (LeekSummary lLeek : mFarmer.getLeeks().values()) {
			lUrl = LEEK_WARS_ROOT_URL + "leek/register-tournament/" + lLeek.getId() + '/' + mToken;
			lResponse = HttpUtils.get(lUrl, mPhpSessionId);
			inscrit = validateRegisterTournamentResponse(lResponse, "Cannot register tournament for the leek " + lLeek.getName());
			LOGGER.info("Poireau " + lLeek.getName() + (inscrit ? " inscrit" : " déjà inscrit") + " au tournoi");
		}

		LOGGER.info("-------------------------------------------------------------");
	}
	
	/**
	 * Récupération de l'équipe du farmer.
	 * ! CONNEXION NON NECESSAIRE !
	 * @return Team
	 * @throws LWException
	 */
	public Team getTeam() throws LWException {
		// team/get/team_id → team
		String lUrl = LEEK_WARS_ROOT_URL + "team/get/" + mFarmer.getTeam().getId();
		HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		final GetTeamJSONResponse lTeam = validateResponse(lResponse, "Cannot obtain team", GetTeamJSONResponse.class);
		return lTeam.getTeam();
	}
	
	/**
	 * Permet d'inscrire toutes les compositions de l'équipe aux prochains tournois
	 * @throws LWException
	 */
	public void registerAllTeamCompositions() throws LWException {
		final TeamPrivate lTeamData = getTeamCompositions();
		LOGGER.info("-------------------------------------------------------------");
		LOGGER.info(" INSCRIPTION AUX TOURNOIS POUR L'EQUIPE " +  lTeamData.getName());
		LOGGER.info("-------------------------------------------------------------");
		String lUrl; // team/register-tournament/composition_id/token
		HttpResponseWrapper lResponse;
		boolean inscrit;
		for (TeamComposition lCompo :  lTeamData.getCompositions()) {
			// Si la compo contient au moins 4 membres
			if (lCompo.getLeeks().length >= 4) {
				lUrl = LEEK_WARS_ROOT_URL + "team/register-tournament/" + lCompo.getId() + '/' + mToken;
				lResponse = HttpUtils.get(lUrl, mPhpSessionId);
				inscrit = validateRegisterTournamentResponse(lResponse, "Cannot register tournament for the team composition " + lCompo.getName());
				LOGGER.info("Composition " + lCompo.getName() + (inscrit ? " inscrite" : " déjà inscrite") + " au tournoi");
			} else {
				LOGGER.warn("Composition " + lCompo.getName() + " ne peut être inscrite à un tournoi : " + lCompo.getLeeks().length + " poireau(x). 4 minimum attendus.");
			}
		}
		LOGGER.info("-------------------------------------------------------------");
	}
	
	/**
	 * Récupération des informations privées (compositions par exemples) de l'équipe du farmer.
	 * @return TeamPrivate
	 * @throws LWException
	 */
	public TeamPrivate getTeamCompositions() throws LWException {
		checkConnected();
		// team/get-private/team_id/token
		String lUrl = LEEK_WARS_ROOT_URL + "team/get-private/" + mFarmer.getTeam().getId() + '/' + mToken;
		HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		final GetTeamPrivateJSONResponse lTeam = validateResponse(lResponse, "Cannot obtain team private data", GetTeamPrivateJSONResponse.class);
		return lTeam.getTeam();
	}
	
	/*
	 * Retourne l'id du combnat demandé ou lève une exception en cas d'erreur
	 * @param pResponse
	 * @return id
	 * @throws LWException
	 */
	private long genericStartFight(final HttpResponseWrapper pResponse) throws LWException {
		final StartFightJSONResponse lFightResponse = validateResponse(pResponse, "Cannot start fight", StartFightJSONResponse.class);
		return lFightResponse.getFight();
	}
	
	/**
	 * Démarre un combat d'éléveur et retourne son id si OK
	 * @param pEnemyId
	 * @return fight id
	 * @throws LWException
	 */
	public long startFarmerFight(final long pEnemyId) throws LWException {
		checkConnected();
		// garden/start-farmer-fight/target_id/token
		final String lUrl = LEEK_WARS_ROOT_URL + "garden/start-farmer-fight/" + pEnemyId + '/' + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		return genericStartFight(lResponse);
	}
	
	/**
	 * Démarre un combat d'équipe et retourne son id si OK
	 * @param pCompoId
	 * @param pTargetTeamCompoId
	 * @return fight id
	 * @throws LWException
	 */
	public long startTeamFight(final long pCompoId, final long pTargetTeamCompoId) throws LWException {
		checkConnected();
		// garden/start-team-fight/composition_id/target_id/token → fight_id
		final String lUrl = LEEK_WARS_ROOT_URL + "garden/start-team-fight/" + pCompoId + '/' + pTargetTeamCompoId + '/' + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		return genericStartFight(lResponse);
	}
	
	/**
	 * Démarre un combat de poireau et retourne son id si OK
	 * @param pLeekId
	 * @param pEnemyLeekId
	 * @return fight id
	 * @throws LWException
	 */
	public long startSoloFight(final long pLeekId, final long pEnemyLeekId) throws LWException {
		checkConnected();
		// garden/start-solo-fight/leek_id/target_id/token → fight_id
//		return startSoloFight_GET(pLeekId, pEnemyLeekId);
//	}
//	private long startSoloFight_GET(final long pLeekId, final long pEnemyLeekId) throws LWException {
		final String lUrl = LEEK_WARS_ROOT_URL + "garden/start-solo-fight/" + pLeekId + '/' + pEnemyLeekId + '/' + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		return genericStartFight(lResponse);
	}
//	private long startSoloFight_POST(final long pLeekId, final long pEnemyLeekId) throws LWException {
//		final String lUrl = LEEK_WARS_ROOT_URL + "garden/start-solo-fight";
//		final HttpResponse lResponse = HttpUtils.post(lUrl, "leek_id="+pLeekId+"&target_id="+pEnemyLeekId+"&token="+mToken, mPhpSessionId);
//		return genericStartFight(lResponse);
//	}
	
	/**
	 * Récupère les informations d'un combat, utile pour connaitre le resultat après avoir lancé le combat en asynchrone.
	 * ! CONNEXION NON NECESSAIRE !
	 * @param pFightId id du combat
	 * @throws LWException
	 */
	public Fight getFight(final long pFightId) throws LWException {
		// fight/get/fight_id → fight
		final String lUrl = LEEK_WARS_ROOT_URL + "fight/get/" + pFightId;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		final GetFightJSONResponse lFightResponse = validateResponse(lResponse, "Cannot get fight " + pFightId, GetFightJSONResponse.class);
		return lFightResponse.getFight();
	}
	
//	/**
//	 * @param pFightId id du combat
//	 * @throws LWException
//	 */
//	public void getFightLogs(final long pFightId) throws LWException {
//		checkConnected();
//		final String lUrl = LEEK_WARS_ROOT_URL + "fight/get-logs/" + pFightId + '/' + mToken;
//		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
//		System.out.println("fight-logs : \n" + lResponse.getResponseText());
//	}
	
	/**
	 * Met à jour l'éleveur (et tous les poireaux).
	 * Suite à déclenchement de combats par exemple (evolution du talent, level, ...)
	 * ! CONNEXION NON NECESSAIRE !
	 * @throws LWException
	 */
	public void updateFarmer() throws LWException {
		// farmer/get/farmer_id → farmer
		final String lUrl = LEEK_WARS_ROOT_URL + "farmer/get/" + mFarmer.getId();
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		final GetFarmerJSONResponse lFarmerResponse = validateResponse(lResponse, "Cannot update farmer", GetFarmerJSONResponse.class);
		mFarmer = lFarmerResponse.getFarmer();
	}
	
}
