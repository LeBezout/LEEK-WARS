package com.leekwars.utils;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.http.HttpResponseWrapper;
import com.leekwars.utils.http.HttpUtils;
import com.leekwars.utils.io.FightJSONResponse;
import com.leekwars.utils.io.GardenJSONResponse;
import com.leekwars.utils.io.LoginJSONResponse;
import com.leekwars.utils.io.StartFightJSONResponse;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.Fight;
import com.leekwars.utils.model.Garden;
import com.leekwars.utils.model.LeekSummary;
import com.leekwars.utils.model.SimpleJSONResponse;

/**
 * Classe mère permettant d'effectuer tous les appels à l'API LW
 * @author Bezout
 * @see http://leekwars.com/help/api
 */
public abstract class AbstractLeekWarsConnector {
	protected final Logger LOGGER = Logger.getLogger(getClass().getName());

	protected static final String LEEK_WARS_ROOT_URL = "http://leekwars.com/api/";
	protected static final String ENCODING = "UTF-8";
	
	private String mUsername;
	private String mPassword;
	private String mToken;
	private String mPhpSessionId;
	private Farmer mFarmer;
	private final Gson gson = new GsonBuilder().create();
	
	protected final String getUsername() {
		return mUsername;
	}
	protected final String getPassword() {
		return mPassword;
	}
	protected final String getToken() {
		return mToken;
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
	
	private <T extends SimpleJSONResponse> T validateResponse(final HttpResponseWrapper pResponse, final String pDefaultMessage, final Class<T> pType) throws LWException {
		T lResponse = gson.fromJson(pResponse.getResponseText(), pType);
		if (!lResponse.isSuccess()) {
			throw new LWException(lResponse.getError() == null ? pDefaultMessage : lResponse.getError());
		}
		// OK
		return lResponse;
	}
	
	private boolean validateRegisterTournamentResponse(final HttpResponseWrapper pResponse, final String pDefaultMessage) throws LWException {
		SimpleJSONResponse lBasicResponse = gson.fromJson(pResponse.getResponseText(), SimpleJSONResponse.class);
		if (!lBasicResponse.isSuccess()) {
			if (lBasicResponse.getError().equals("already_registered")) return false; // OK
			throw new LWException(lBasicResponse.getError() == null ? pDefaultMessage : lBasicResponse.getError());
		}
		// OK
		return true;
	}
	
	/**
	 * Connexion via l'API
	 * @throws LWException
	 */
	public final void connect() throws LWException {
		final String lUrl = LEEK_WARS_ROOT_URL + "farmer/login-token/"+mUsername+'/'+mPassword;
		//final String json = "{\"login\":\""+mUsername+"\",\"password\":\""+mPassword+"\"}";
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
//		validateResponse(lResponse, "Cannot obtain garden");
//		GardenJSONResponse lGargen = gson.fromJson(lResponse.getResponseText(), GardenJSONResponse.class);
		final GardenJSONResponse lGargen = validateResponse(lResponse, "Cannot obtain garden", GardenJSONResponse.class);
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
	
	
	/*
	 * Retourne l'id du combnat demandé ou lève une exception en cas d'erreur
	 * @param pResponse
	 * @return id
	 * @throws LWException
	 */
	private long genericStartFight(final HttpResponseWrapper pResponse) throws LWException {
		final StartFightJSONResponse lFightResponse = validateResponse(pResponse, "Cannot start fight", StartFightJSONResponse.class);
//		final StartFightJSONResponse lFightResponse = gson.fromJson(pResponse.getResponseText(), StartFightJSONResponse.class);
//		if (!lFightResponse.isSuccess()) {
//			throw new LWException(lFightResponse.getError() == null ? "Cannot start fight" : lFightResponse.getError());
//		}
		return lFightResponse.getFight();
	}
	
	/**
	 * Démarre un combat d'éléveur et retourne son id si OK
	 * @param pEnemyId
	 * @return id
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
	 * Démarre un combat de poireau et retourne son id si OK
	 * @param pLeekId
	 * @param pEnemyLeekId
	 * @return id
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
	 * Récupère les informations d'un combat, utile pour connaitre le resultat après avoir lancé le combat en asynchrone
	 * @param pFightId
	 * @throws LWException
	 */
	public Fight getFight(final long pFightId) throws LWException {
		//checkConnected();
		// 	fight/get/fight_id → fight
		final String lUrl = LEEK_WARS_ROOT_URL + "fight/get/" + pFightId;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		//validateResponse(lResponse, "Cannot get fight " + pFightId);
		final FightJSONResponse lFightResponse = validateResponse(lResponse, "Cannot get fight " + pFightId, FightJSONResponse.class);
		return lFightResponse.getFight();
	}
}
