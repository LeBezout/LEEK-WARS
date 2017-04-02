package com.leekwars.utils;

import com.leekwars.utils.enums.RankType;
import com.leekwars.utils.io.*;
import com.leekwars.utils.model.*;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.http.HttpResponseWrapper;
import com.leekwars.utils.http.HttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe mère permettant d'effectuer tous les appels à l'API LW
 * @author Bezout
 * @see <a href="http://leekwars.com/help/api">API</a>
 * @version 1.4.1
 */
public abstract class AbstractLeekWarsConnector {
	protected static final Logger LOGGER_TRACE = Logger.getLogger("JSON_TRACE");

	protected static final String LEEK_WARS_ROOT_URL = "https://leekwars.com/api/";

	protected final Logger mLogger = Logger.getLogger(getClass().getName());
	protected boolean mTrace;
	private String mUsername;
	private String mPassword;
	private String mToken;
	private String mPhpSessionId;
	private Farmer mFarmer;
	private String mLang = "fr";
	private int mVersion = -1;
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	/**
	 * @return login du compte LW
	 */
	public final String getUsername() {
		return mUsername;
	}
	/**
	 * @return mot de passe en clair du compte LW
	 */
	protected final String getPassword() {
		return mPassword;
	}
	/**
	 * @return token en clair lu lors de l'appel à farmer/login-token
	 */
	protected final String getToken() {
		return mToken;
	}
	
	/**
	 * Permet d'activer les traces des flux JSON recus dans un logger dédié nommé JSON_TRACE
	 * @param pActiveTrace true pour logger les flux
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
	 * @param pUsername login
	 */
	protected final void setUsername(final String pUsername) {
		mUsername = pUsername;
	}
	/**
	 * @param pPassword mot de passe
	 */
	protected final void setPassword(final String pPassword) {
		mPassword = pPassword;
	}
	/**
	 * @param pLang langue préférée (fr par défaut)
	 */
	public final void setLang(final String pLang) {
		mLang = pLang;
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------
	//-------------------- OUTILS
	//---------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Recherche un poireau de l'éleveur par  son id.
	 * @param pLeekId identifiant du poireau recherché
	 * @return LeekSummary
	 * @throws LWException si non connecté ou poireau non trouvé
	 */
	public LeekSummary getLeekById(final long pLeekId) throws LWException {
		checkConnected();
		for (LeekSummary lLeek : mFarmer.getLeeks().values()) {
			if (lLeek.getId() == pLeekId) {
				return lLeek;
			}
		}
		throw new LWException(String.format("Leek %d not found", pLeekId));
	}
	/**
	 * Recherche un poireau de l'éleveur par  son nom.
	 * @param pLeekName nom du poireau recherché
	 * @return LeekSummary
	 * @throws LWException si non connecté ou poireau non trouvé
	 */
	public LeekSummary getLeekByName(final String pLeekName) throws LWException {
		checkConnected();
		for (LeekSummary lLeek : mFarmer.getLeeks().values()) {
			if (lLeek.getName().equals(pLeekName)) {
				return lLeek;
			}
		}
		throw new LWException(String.format("Leek %s not found", pLeekName));
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------
	//-------------------- GENERIQUE
	//---------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Validation basique de la réponse JSON. success=OK sinon exeption
	 * @param pResponse
	 * @param pDefaultMessage
	 * @param pType
	 * @param <T>
	 * @return
	 * @throws LWException e
	 */
	protected <T extends SimpleJSONResponse> T validateResponse(final HttpResponseWrapper pResponse, final String pDefaultMessage, final Class<T> pType) throws LWException {
		trace(pResponse, pType);
		T lResponse = gson.fromJson(pResponse.getResponseText(), pType);
		if (!lResponse.isSuccess()) {
			throw new LWException(lResponse.getError() == null ? pDefaultMessage : lResponse.getError());
		}
		// OK
		return lResponse;
	}

	/**
	 * Pour les réponses simples OK/KO sans besoin de retour
     * @param pResponse
     * @param pDefaultMessage
     * @throws LWException e
	 * @since 1.4.0
	 */
	protected void validateResponse(final HttpResponseWrapper pResponse, final String pDefaultMessage) throws LWException {
		validateResponse(pResponse, pDefaultMessage, SimpleJSONResponse.class);
	}

	/**
	 * Spécifique à l'inscription aux tournois
	 * @param pResponse
	 * @param pDefaultMessage
	 * @return true si inscription réalisée, false si déjà inscrit
	 * @throws LWException e
	 */
	protected boolean validateRegisterTournamentResponse(final HttpResponseWrapper pResponse, final String pDefaultMessage) throws LWException {
		trace(pResponse, SimpleJSONResponse.class);
		SimpleJSONResponse lBasicResponse = gson.fromJson(pResponse.getResponseText(), SimpleJSONResponse.class);
		if (!lBasicResponse.isSuccess()) {
			if ("already_registered".equals(lBasicResponse.getError())) {
				return false; // déjà inscrit
			}
			throw new LWException(lBasicResponse.getError() == null ? pDefaultMessage : lBasicResponse.getError());
		}
		// OK
		return true;
	}
	
	/**
	 * Trace du flux JSON recu du serveur LW
	 * @param pResponse flux JSON brut
	 * @param pType type attendu en sortie (informatif)
	 */
	protected <T extends SimpleJSONResponse> void trace(final HttpResponseWrapper pResponse, final Class<T> pType) {
		if (mTrace) {
			try {
				LOGGER_TRACE.info(LWConst.LOG_SEPARATOR);
				if (pResponse.getUrlCalled() == null) {
					LOGGER_TRACE.info(pType.getCanonicalName());
				} else {
					LOGGER_TRACE.info(String.format("%s ==> %s :", pResponse.getUrlCalled(), pType.getCanonicalName()));
				}
				LOGGER_TRACE.info(gson.toJson(new JsonParser().parse(pResponse.getResponseText())));
			} catch (Exception e) {
				mLogger.error("Impossible d'effectuer la trace de " + pType.getCanonicalName(), e);
			}
		}
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------
	//-------------------- CONNEXION / DECONNEXION
	//---------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Connexion (récuparation du token et du php session id) via l'API
	 * @throws LWException e
	 */
	public final void connect() throws LWException {
		final String lUrl = LEEK_WARS_ROOT_URL + "farmer/login-token/"+mUsername+'/'+mPassword;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, null);
		final LoginJSONResponse lLoginResponse = validateResponse(lResponse, "Can't connect to LeekWars for " + mUsername, LoginJSONResponse.class);
		mPhpSessionId = lResponse.getCookie("PHPSESSID");
		mToken = lLoginResponse.getToken();
		mFarmer = lLoginResponse.getFarmer();
		mLogger.debug("TOKEN=" + mToken);
		mLogger.debug("PHPSESSID=" + mPhpSessionId);
		mLogger.info("FARMER=" + mFarmer);
		mLogger.info("TEAM=" + mFarmer.getTeam());
	}
	
	/**
	 * Connexion (récuparation du token et du php session id) via l'API seulement si pas déjà connecté.
	 * @throws LWException e
	 */
	public final void connectIfNeeded() throws LWException {
		if (mPhpSessionId == null || mToken == null) {
			connect();
		}
	}
	
	/**
	 * @throws LWException e
	 */
	public final void invalidateToken() throws LWException {
		checkConnected();
		final String lUrl = LEEK_WARS_ROOT_URL + "farmer/disconnect/" + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId); //TODO valider post/get
		validateResponse(lResponse, "Bad token", SimpleJSONResponse.class);
		// OK
		mToken = null;
		mPhpSessionId = null;
	}
	
	/**
	 * Leve une exception si non connecté (pas de token récupéré)
	 * @throws LWException e
	 */
	public final void checkConnected() throws LWException {
		if (mPhpSessionId == null || mToken == null) {
			throw new LWException("Not connected !");
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------
	//-------------------- DIVERS
	//---------------------------------------------------------------------------------------------------------------------------------

    /**
     * Permet d'obtenir la version courante de Leek Wars
     * @return version (ou -1 si pb)
     * @since 1.4.1
     */
    public final int getVersion() {
	    if (mVersion == -1) {
            final String lUrl = LEEK_WARS_ROOT_URL + "leek-wars/version";
            try {
                final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, null);
                final GetLWVersionJSONResponse lVersion = validateResponse(lResponse, "Cannot obtain the current LeekWars version", GetLWVersionJSONResponse.class);
                mVersion = lVersion.getVersion();
            } catch (LWException lwe) {
                mLogger.error("Impossible de récupérer la version de Leek Wars", lwe);
            }
        }
        return mVersion;
    }

    /**
     * Vérifie que la version de LW est-bien celle attendue, lève une LWException sinon
     * @param pExpectedVersion numéro de version attendue
     * @throws LWException e
     */
    public void checkForExpectedVersion(final int pExpectedVersion) throws LWException {
        final int lVersion = getVersion();
        if (lVersion > 0) {
            if (pExpectedVersion != lVersion) {
                throw new LWException(String.format("Expected version %d, obtain %d", pExpectedVersion, lVersion));
            }
        }
        // else version indisponible, on ne peut pas controller
    }

    /**
	 * Permet d'effectuer une action sur tous les poireaux de l'éleveur
	 * @param pVisitor visiteur
	 * @throws LWException e
	 * @since 1.2
	 */
	public final void iterateOnLeeks(final LeekVisitor pVisitor) throws LWException {
		pVisitor.beforeLeeks();
		for (LeekSummary leek : mFarmer.getLeeks().values()) {
			pVisitor.onLeek(leek);
		}
		pVisitor.afterLeeks();
	}

	//---------------------------------------------------------------------------------------------------------------------------------
	//-------------------- POIREAUX
	//---------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Positionne ou non un des poireaux de l'éleveur dans le potager
	 * @param pLeekId id du poireau de l'éleveur
	 * @param pInGarden true/false
	 * @throws LWException e
	 * @since 1.4
	 */
	public void setLeekInGarden(final long pLeekId, final boolean pInGarden) throws LWException {
		// leek/set-in-garden/<leek_id>/<in_garden>/<token>
		String lUrl = LEEK_WARS_ROOT_URL + "leek/set-in-garden/" + pLeekId + '/' + pInGarden + '/' + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		validateResponse(lResponse, "Cannot set or unset leek in garden");
	}
	/**
	 * Positionne ou non un poireau dans le potager
	 * @param pLeek poireau
	 * @param pInGarden true/false
	 * @throws LWException e
	 * @since 1.4
	 */
	public void setLeekInGarden(final Entity pLeek, final boolean pInGarden) throws LWException {
		setLeekInGarden(pLeek.getId(), pInGarden);
	}

	//---------------------------------------------------------------------------------------------------------------------------------
	//-------------------- CLASSEMENTS
	//---------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Permet de récupérer un classement de l'éleveur
	 * ! CONNEXION NON NECESSAIRE !
	 * @param pType parmis RankType.TALENT, RankType.NAME, RankType.TOTAL_LEVEL
	 * @return rank value
     * @throws LWException e
	 * @since 1.2
	 */
	public long getFarmerRank(final RankType pType) throws LWException {
		// ranking/get-farmer-rank/farmer_id/order
		String lUrl = LEEK_WARS_ROOT_URL + "ranking/get-farmer-rank/" + mFarmer.getId() + '/' + pType.getValue();
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		final GetRankJSONResponse lRank = validateResponse(lResponse, "Cannot obtain farmer rank value for " + pType, GetRankJSONResponse.class);
		return lRank.getRank();
	}

	/**
	 * Permet de récupérer un classement de l'éleveur
	 * ! CONNEXION NON NECESSAIRE !
	 * @param pType parmis RankType.TALENT, RankType.NAME, RankType.LEVEL
	 * @return rank value
     * @throws LWException e
	 * @since 1.2
	 */
	public long getLeekRank(final long pLeekId, final RankType pType) throws LWException {
		// ranking/get-leek-rank/leek_id/order → rank
		String lUrl = LEEK_WARS_ROOT_URL + "ranking/get-leek-rank/" + pLeekId + '/' + pType.getValue();
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		final GetRankJSONResponse lRank = validateResponse(lResponse, "Cannot obtain leek rank value for " + pType, GetRankJSONResponse.class);
		return lRank.getRank();
	}

	/**
	 * Permet de récupérer tous les classements "fun"
	 * @return tableau de FunRanking
     * @throws LWException e
	 * @since 1.2
	 */
	public FunRanking[] getFunRank()throws LWException {
		checkConnected();
		// ranking/fun/token
		String lUrl = LEEK_WARS_ROOT_URL + "ranking/fun/" + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		final GetFunRankingsJSONResponse lRankings = validateResponse(lResponse, "Cannot obtain fun ranking", GetFunRankingsJSONResponse.class);
		return lRankings.getRankings();
	}

	//---------------------------------------------------------------------------------------------------------------------------------
	//-------------------- TOURNOIS
	//---------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Permet d'inscrire tous les poireaux et l'éleveur aux prochains tournois
	 * @throws LWException e
	 */
	public void registerAllTournaments() throws LWException {
		checkConnected();
		mLogger.info(LWConst.LOG_SEPARATOR);
		mLogger.info(" INSCRIPTION AUX TOURNOIS POUR " +  mFarmer.getName());
		mLogger.info(LWConst.LOG_SEPARATOR);
		// 1. l'eleveur
		registerFarmerForNextTournament();
		
		// 2. chacun des poireaux
		registerLeeksForNextTournaments();

		mLogger.info(LWConst.LOG_SEPARATOR);
	}

	/**
	 * Permet d'inscrire tous les poireaux aux prochains tournois
	 * @throws LWException e
	 */
	public void registerFarmerForNextTournament() throws LWException {
		checkConnected();
		// farmer/register-tournament/token
		String lUrl = LEEK_WARS_ROOT_URL + "farmer/register-tournament/" + mToken;
		HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		boolean inscrit = validateRegisterTournamentResponse(lResponse, "Cannot register tournament for the farmer " + mFarmer.getName());
		mLogger.info("Eleveur " + mFarmer.getName() + (inscrit ? " inscrit" : " déjà inscrit") + " au tournoi");
	}

	/**
	 * Permet d'inscrire tous l'éleveur aux prochains tournois
	 * @throws LWException e
	 */
	public void registerLeeksForNextTournaments() throws LWException {
		checkConnected();
		String lUrl;
		boolean inscrit;
		HttpResponseWrapper lResponse;
		// chacun des poireaux : leek/register-tournament/leek_id/token
		final String urlPattern = LEEK_WARS_ROOT_URL + "leek/register-tournament/%d/" + mToken;
		for (LeekSummary lLeek : mFarmer.getLeeks().values()) {
			lUrl = String.format(urlPattern, lLeek.getId());
			lResponse = HttpUtils.get(lUrl, mPhpSessionId);
			inscrit = validateRegisterTournamentResponse(lResponse, "Cannot register tournament for the leek " + lLeek.getName());
			mLogger.info("Poireau " + lLeek.getName() + (inscrit ? " inscrit" : " déjà inscrit") + " au tournoi");
		}
	}

	
	/**
	 * Permet d'inscrire toutes les compositions de l'équipe aux prochains tournois
	 * @throws LWException e
	 */
	public void registerAllTeamCompositions() throws LWException {
		final TeamPrivate lTeamData = getTeamCompositions();
		mLogger.info(LWConst.LOG_SEPARATOR);
		mLogger.info(" INSCRIPTION AUX TOURNOIS POUR L'EQUIPE " +  lTeamData.getName());
		mLogger.info(LWConst.LOG_SEPARATOR);
		String lUrl; // team/register-tournament/composition_id/token
		HttpResponseWrapper lResponse;
		boolean inscrit;
		final String urlPattern = LEEK_WARS_ROOT_URL + "team/register-tournament/%d/" + mToken;
		for (TeamComposition lCompo : lTeamData.getCompositions()) {
			// Si la compo contient au moins 4 membres
			if (lCompo.getLeeks().length >= 4) {
				lUrl = String.format(urlPattern, lCompo.getId());
				lResponse = HttpUtils.get(lUrl, mPhpSessionId);
				inscrit = validateRegisterTournamentResponse(lResponse, "Cannot register tournament for the team composition " + lCompo.getName());
				mLogger.info("Composition " + lCompo.getName() + (inscrit ? " inscrite" : " déjà inscrite") + " au tournoi");
			} else {
				mLogger.warn("Composition " + lCompo.getName() + " ne peut être inscrite à un tournoi : " + lCompo.getLeeks().length + " poireau(x). 4 minimum attendus.");
			}
		}
		mLogger.info(LWConst.LOG_SEPARATOR);
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------
	//-------------------- EQUIPE
	//---------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Récupération de l'équipe du farmer.
	 * ! CONNEXION NON NECESSAIRE !
	 * @return Team
	 * @throws LWException e
	 */
	public Team getTeam() throws LWException {
		// team/get/team_id → team
		final String lUrl = LEEK_WARS_ROOT_URL + "team/get/" + mFarmer.getTeam().getId();
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		final GetTeamJSONResponse lTeam = validateResponse(lResponse, "Cannot obtain team", GetTeamJSONResponse.class);
		return lTeam.getTeam();
	}
	
	/**
	 * Récupération des informations privées (compositions par exemples) de l'équipe du farmer.
	 * @return TeamPrivate
	 * @throws LWException e
	 */
	public TeamPrivate getTeamCompositions() throws LWException {
		checkConnected();
		// team/get-private/team_id/token
		final String lUrl = LEEK_WARS_ROOT_URL + "team/get-private/" + mFarmer.getTeam().getId() + '/' + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		final GetTeamPrivateJSONResponse lTeam = validateResponse(lResponse, "Cannot obtain team private data", GetTeamPrivateJSONResponse.class);
		return lTeam.getTeam();
	}

	//---------------------------------------------------------------------------------------------------------------------------------
	//-------------------- POTAGER
	//---------------------------------------------------------------------------------------------------------------------------------
	
	/**
     * Obtention du potager
	 * @return Garden
	 * @throws LWException e
	 */
	public Garden getGarden() throws LWException {
		checkConnected();
		final String lUrl = LEEK_WARS_ROOT_URL + "garden/get/" + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		final GetGardenJSONResponse lGargen = validateResponse(lResponse, "Cannot obtain garden", GetGardenJSONResponse.class);
		return lGargen.getGarden();
	}
	/**
     * Obtention des adversaires de poireaux
	 * @return tableau de poireaux que l'on peut agresser
	 * @throws LWException e
	 */
	public LeekSummary[] getLeekOpponents(final long pLeekId) throws LWException {
		checkConnected();
		// garden/get-leek-opponents/leek_id/token → opponents
		final String lUrl = LEEK_WARS_ROOT_URL + "garden/get-leek-opponents/" + pLeekId + '/' + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		final GetLeekOpponentsJSONResponse lWrapper = validateResponse(lResponse, "Cannot obtain opponnents for leek", GetLeekOpponentsJSONResponse.class);
		return lWrapper.getOpponents();
	}
	/**
     * Obtention des adversaires d'éleveur
	 * @return tableau d'éleveurs que l'on peut agresser
	 * @throws LWException e
	 */
	public FarmerSummary[] getFarmerOpponents() throws LWException {
		checkConnected();
		// garden/get-farmer-opponents/token → opponents
		final String lUrl = LEEK_WARS_ROOT_URL + "garden/get-farmer-opponents/" + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		final GetFarmerOpponentsJSONResponse lWrapper = validateResponse(lResponse, "Cannot obtain opponnents for famer", GetFarmerOpponentsJSONResponse.class);
		return lWrapper.getOpponents();
	}
    /**
     * Obtention des adversaires de compositions d'équipe
     * @return tableau de compos que l'on peut agresser
     * @throws LWException e
     */
    public GardenEnemyTeamComposition[] getTeamOpponents(final long pCompoId) throws LWException {
        checkConnected();
        // garden/get-composition-opponents/composition/token → opponents
        final String lUrl = LEEK_WARS_ROOT_URL + "garden/get-composition-opponents/" + pCompoId + '/' + mToken;
        final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
        final GetTeamOpponentsJSONResponse lWrapper = validateResponse(lResponse, "Cannot obtain opponnents for team composition", GetTeamOpponentsJSONResponse.class);
        return lWrapper.getOpponents();
    }

//	public void getFarmerChallenge(final long pFarmerId) throws LWException {
//		checkConnected();
//		// garden/get-farmer-challenge/token → farmer
//		final String lUrl = LEEK_WARS_ROOT_URL + "garden/get-farmer-challenge/" + pFarmerId + '/' + mToken;
//		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
//
//		trace(lResponse, SimpleJSONResponse.class);
//	}

	/*
	 * Retourne l'id du combat demandé ou lève une exception en cas d'erreur
	 * @param pResponse
	 * @return id
	 * @throws LWException e
	 */
	private long genericStartFight(final HttpResponseWrapper pResponse) throws LWException {
		final StartFightJSONResponse lFightResponse = validateResponse(pResponse, "Cannot start fight", StartFightJSONResponse.class);
		return lFightResponse.getFight();
	}

	/**
	 * Démarre un combat de poireau et retourne son id si OK
	 * @param pLeekId
	 * @param pEnemyLeekId
	 * @return fight id
	 * @throws LWException e
	 */
	public long startSoloFight(final long pLeekId, final long pEnemyLeekId) throws LWException {
		checkConnected();
		// garden/start-solo-fight/leek_id/target_id/token → fight_id
		final String lUrl = LEEK_WARS_ROOT_URL + "garden/start-solo-fight/" + pLeekId + '/' + pEnemyLeekId + '/' + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		return genericStartFight(lResponse);
	}
	
	/**
	 * Démarre un combat d'éléveur et retourne son id si OK
	 * @param pEnemyId
	 * @return fight id
	 * @throws LWException e
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
	 * @throws LWException e
	 */
	public long startTeamFight(final long pCompoId, final long pTargetTeamCompoId) throws LWException {
		checkConnected();
		// garden/start-team-fight/composition_id/target_id/token → fight_id
		final String lUrl = LEEK_WARS_ROOT_URL + "garden/start-team-fight/" + pCompoId + '/' + pTargetTeamCompoId + '/' + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		return genericStartFight(lResponse);
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------
	//-------------------- COMBATS
	//---------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Récupère les informations d'un combat, utile pour connaitre le resultat après avoir lancé le combat en asynchrone.
	 * ! CONNEXION NON NECESSAIRE !
	 * @param pFightId id du combat
	 * @throws LWException e
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
//	 * @throws LWException e
//	 */
//	public void getFightLogs(final long pFightId) throws LWException {
//		checkConnected();
//		final String lUrl = LEEK_WARS_ROOT_URL + "fight/get-logs/" + pFightId + '/' + mToken;
//		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
//		System.out.println("fight-logs : \n" + lResponse.getResponseText());
//	}
	
	//---------------------------------------------------------------------------------------------------------------------------------
	//-------------------- ELEVEUR
	//---------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Met à jour l'éleveur (et tous les poireaux).
	 * Suite à déclenchement de combats par exemple (evolution du talent, level, ...)
	 * ! CONNEXION NON NECESSAIRE !
	 * @throws LWException e
	 */
	public void updateFarmer() throws LWException {
		// farmer/get/farmer_id → farmer
		final String lUrl = LEEK_WARS_ROOT_URL + "farmer/get/" + mFarmer.getId();
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		final GetFarmerJSONResponse lFarmerResponse = validateResponse(lResponse, "Cannot update farmer", GetFarmerJSONResponse.class);
		mFarmer = lFarmerResponse.getFarmer();
	}

	/**
	 * Récupère les infos concernant les trophées obtenus par l'éleveur connecté
	 * @return liste des trophées débloqués de l'éleveur connecté
	 * @since 1.1
	 * @throws LWException e
	 */
	public List<Trophy> getUnlockedFarmerTrophies() throws LWException {
		// trophy/get-farmer-trophies/farmer_id/lang/token → trophies
		final String lUrl = LEEK_WARS_ROOT_URL + "trophy/get-farmer-trophies/" + mFarmer.getId() + '/' + mLang + '/' + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		final GetFarmerTrophiesJSONResponse lTrophiesResponse = validateResponse(lResponse, "Cannot get farmer trophies", GetFarmerTrophiesJSONResponse.class);
		final List<Trophy> lTrophies = new ArrayList<>(lTrophiesResponse.getCount());
		Trophy lTrophy;
		for (final Map.Entry<String, Trophy> lEntry : lTrophiesResponse.getTrophies().entrySet()) {
			lTrophy = lEntry.getValue();
			if (lTrophy.isUnlocked()) {
				lTrophies.add(lTrophy);
			}
		}
		return lTrophies;
	}

	/**
	 * Permet de positionner ou d'enlever l'éleveur du potager
	 * @param pInGarden
	 * @throws LWException e
	 * @since 1.4
	 */
	public boolean setFarmerInGarden(final boolean pInGarden) throws LWException {
		if (mFarmer.isInGarden() == pInGarden) {
			return false;
		}
		// farmer/set-in-garden/<in_garden(true/false)>/<token>
		final String lUrl = LEEK_WARS_ROOT_URL + "farmer/set-in-garden/" + pInGarden + '/' + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		validateResponse(lResponse, "Cannot set or unset farmer in garden");
		return true;
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------
	//-------------------- REGISTRES
	//---------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Récupère les registrers d'un poireau
	 * @param pLeekId identifiant du poireau
	 * @return KeyValueCouple[]
	 * @throws LWException e
	 */
	public KeyValueCouple[] getRegisters(final long pLeekId) throws LWException {
		checkConnected();
		// leek/get-registers/leek_id/token → registers
		final String lUrl = LEEK_WARS_ROOT_URL + "leek/get-registers/" + pLeekId + '/' + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		final GetRegistersJSONResponse lRegistersResponse = validateResponse(lResponse, "Cannot get registers", GetRegistersJSONResponse.class);
		return lRegistersResponse.getRegisters();
	}
	
	/**
	 * Récupère la valeur d'un registre
	 * @param pLeekId identifiant du poireau
	 * @param pKey clef de registre
	 * @return valeur de registre
	 * @throws LWException e
	 */
	public String getRegisterValue(final long pLeekId, final String pKey) throws LWException {
		final KeyValueCouple[] lRegisters = getRegisters(pLeekId);
		// recherche du registre
		for (KeyValueCouple lRegister : lRegisters) {
			if (pKey.equals(lRegister.getKey())) {
				return lRegister.getValue();
			}
		}
		return null; // non trouvé
	}

	/**
	 * Ajoute un élément au registre
	 * @param pLeekId identifiant du poireau
	 * @param pRegister KeyValueCouple
	 * @throws LWException e
	 */
	public void setRegister(final long pLeekId, final KeyValueCouple pRegister) throws LWException {
		checkConnected();
		// REGLE : 
		//	- La clé, chaîne qui doit contenir 100 caractères au maximum
		//	- La valeur, chaîne qui doit contenir 5000 caractères au maximum
		pRegister.validateForRegister();
		// leek/set-register/leek_id/key/value/token
		final String lUrl = LEEK_WARS_ROOT_URL + "leek/set-register/" + pLeekId + '/' + HttpUtils.encodeUrlParam(pRegister.getKey()) + '/' + HttpUtils.encodeUrlParam(pRegister.getValue()) + '/' + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		validateResponse(lResponse, "Cannot set register", SimpleJSONResponse.class);
	}
	
	/**
	 * Supprime un registre
	 * @param pLeekId identifiant du poireau
	 * @param pKey clef de registre
	 * @return valeur du registre supprimé
	 * @throws LWException e
	 */
	public String deleteRegister(final long pLeekId, final String pKey) throws LWException {
		checkConnected();
		final String lValue = getRegisterValue(pLeekId, pKey);
		if (lValue == null) {
			return null; // ce registre n'existe pas
		}
		// leek/delete-register/leek_id/key/token
		final String lUrl = LEEK_WARS_ROOT_URL + "leek/delete-register/" + pLeekId + '/' + HttpUtils.encodeUrlParam(pKey) + '/' + mToken;
		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId);
		validateResponse(lResponse, "Cannot delete register", SimpleJSONResponse.class);
		return lValue;
	}
	
	/**
	 * Suppression de tous les registres d'un poireau
	 * @param pLeekId identifiant du poireau
	 * @throws LWException e
	 */
	public void deleteAllRegisters(final long pLeekId) throws LWException {
		final KeyValueCouple[] lRegisters = getRegisters(pLeekId);
		final int len = lRegisters.length;
		for (KeyValueCouple lRegister : lRegisters) {
			deleteRegister(pLeekId, lRegister.getKey());
			mLogger.info("Registre " + lRegister + " supprimé");
		}
		mLogger.info(String.valueOf(len) + " registres supprimés pour le poireau d'id " + pLeekId);
	}
}
