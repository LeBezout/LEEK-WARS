package com.leekwars.utils;

import com.leekwars.utils.enums.RankType;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.http.HttpException;
import com.leekwars.utils.http.HttpResponseWrapper;
import com.leekwars.utils.http.HttpUtils;
import com.leekwars.utils.io.GetFarmerJSONResponse;
import com.leekwars.utils.io.GetFarmerOpponentsJSONResponse;
import com.leekwars.utils.io.GetFarmerTrophiesJSONResponse;
import com.leekwars.utils.io.GetFunRankingsJSONResponse;
import com.leekwars.utils.io.GetGardenJSONResponse;
import com.leekwars.utils.io.GetLWVersionJSONResponse;
import com.leekwars.utils.io.GetLeekOpponentsJSONResponse;
import com.leekwars.utils.io.GetRankJSONResponse;
import com.leekwars.utils.io.GetRegistersJSONResponse;
import com.leekwars.utils.io.GetTeamJSONResponse;
import com.leekwars.utils.io.GetTeamOpponentsJSONResponse;
import com.leekwars.utils.io.LoginJSONResponse;
import com.leekwars.utils.io.StartFightJSONResponse;
import com.leekwars.utils.model.Entity;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.FarmerSummary;
import com.leekwars.utils.model.Fight;
import com.leekwars.utils.model.FunRanking;
import com.leekwars.utils.model.Garden;
import com.leekwars.utils.model.GardenEnemyTeamComposition;
import com.leekwars.utils.model.KeyValueCouple;
import com.leekwars.utils.model.LeekSummary;
import com.leekwars.utils.model.SimpleJSONResponse;
import com.leekwars.utils.model.Team;
import com.leekwars.utils.model.TeamComposition;
import com.leekwars.utils.model.TeamPrivate;
import com.leekwars.utils.model.Trophy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe mère permettant d'effectuer tous les appels à l'API LW
 * @author Bezout
 * @see <a href="http://leekwars.com/help/api">API</a>
 * @version 1.6
 */
public abstract class AbstractLeekWarsConnector {
	protected static final Logger LOGGER_TRACE = LoggerFactory.getLogger("JSON_TRACE");

	protected static final String LEEK_WARS_ROOT_URL = "https://leekwars.com/api/";
    protected static final String NO_TOKEN = null;
    protected static final String NO_PHPSESSID = null;

    protected final Logger mLogger = LoggerFactory.getLogger(getClass().getName());
	protected boolean mTrace;
	private String mUsername;
	private String mPassword;
	private String mToken;
	private String mPhpSessionId;
	private Farmer mFarmer;
	private String mLang = "fr";
	private int mVersion = -1;

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
	 * Validation basique de la réponse JSON. success=OK sinon exception
	 * @param pResponse infos de la réponse HTTP
	 * @param pType type du wrapper de réponse
	 * @param <T>
	 * @return objet Java créé depuis la réponse JSON au format texte
	 */
	protected <T> T parseResponse(final HttpResponseWrapper pResponse,  final Class<T> pType) {
		trace(pResponse, pType);
		T lResponse = LWUtils.parseJson(pResponse.getResponseText(), pType);

		// OK
		return lResponse;
	}

	/**
	 * Trace du flux JSON recu du serveur LW
	 * @param pResponse flux JSON brut
	 * @param pType type attendu en sortie (informatif)
	 */
	protected <T> void trace(final HttpResponseWrapper pResponse, final Class<T> pType) {
		if (mTrace) {
			try {
				LOGGER_TRACE.info(LWConst.LOG_SEPARATOR);
				if (pResponse.getUrlCalled() == null) {
					LOGGER_TRACE.info(pType.getCanonicalName());
				} else {
					LOGGER_TRACE.info(String.format("%s ==> %s :", pResponse.getUrlCalled(), pType.getCanonicalName()));
				}
				LOGGER_TRACE.info(LWUtils.formatJsonString(pResponse.getResponseText()));
			} catch (Exception e) {
				mLogger.error("Impossible d'effectuer la trace de " + pType.getCanonicalName(), e);
			}
		}
	}

	//---------------------------------------------------------------------------------------------------------------------------------
	//-------------------- CONNEXION / DECONNEXION
	//---------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Connexion (récupération du token et du php session id) via l'API
	 * @throws LWException e
	 */
	public final void connect() throws LWException {
		final String lUrl = LEEK_WARS_ROOT_URL + "farmer/login-token/"+mUsername+'/'+mPassword;
		try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, NO_PHPSESSID, NO_TOKEN);
            final LoginJSONResponse lLoginResponse = parseResponse(lResponse, LoginJSONResponse.class);
            mPhpSessionId = lResponse.getCookie("PHPSESSID");
            mToken = lLoginResponse.getToken();
            mFarmer = lLoginResponse.getFarmer();
            mLogger.info("TOKEN=" + mToken);
            mLogger.info("PHPSESSID=" + mPhpSessionId);
            mLogger.info("FARMER=" + mFarmer);
            mLogger.info("TEAM=" + mFarmer.getTeam());
        } catch (HttpException e) {
		    throw new LWException(e.getErrorMessage("Can't connect to LeekWars for user " + mUsername));
        }
	}

	/**
	 * Connexion (récupération du token et du php session id) via l'API seulement si pas déjà connecté.
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
		final String lUrl = LEEK_WARS_ROOT_URL + "farmer/disconnect";
		try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, mToken); //TODO valider post/get
            SimpleJSONResponse reponse = parseResponse(lResponse, SimpleJSONResponse.class);
            // OK
            mToken = null;
            mPhpSessionId = null;
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Bad token for user " + mUsername));
        }
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
                final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, NO_PHPSESSID, NO_TOKEN);
                final GetLWVersionJSONResponse lVersion = parseResponse(lResponse, GetLWVersionJSONResponse.class);
                mVersion = lVersion.getVersion();
            } catch (LWException | HttpException e) {
                mLogger.error("Impossible de récupérer la version de Leek Wars", e);
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
        final String lUrl = LEEK_WARS_ROOT_URL + "leek/set-in-garden/" + pLeekId + '/' + pInGarden;
		try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, mToken);
            //validateResponse(lResponse, "Cannot set or unset leek in garden", "leek/set-in-garden");
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't set or unset leek in garden for user " + mUsername));
        }
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
        final String lUrl = LEEK_WARS_ROOT_URL + "ranking/get-farmer-rank/" + mFarmer.getId() + '/' + pType.getValue();
		try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, NO_TOKEN);
            final GetRankJSONResponse lRank = parseResponse(lResponse, GetRankJSONResponse.class);
            return lRank.getRank();
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't obtain farmer rank value for " + pType));
        }
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
		final String lUrl = LEEK_WARS_ROOT_URL + "ranking/get-leek-rank/" + pLeekId + '/' + pType.getValue();
		try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, NO_TOKEN);
            final GetRankJSONResponse lRank = parseResponse(lResponse, GetRankJSONResponse.class);
            return lRank.getRank();
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't obtain obtain rank value for " + pType));
        }
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
        final String lUrl = LEEK_WARS_ROOT_URL + "ranking/fun";
        try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, mToken);
            final GetFunRankingsJSONResponse lRankings = parseResponse(lResponse, GetFunRankingsJSONResponse.class);
            return lRankings.getRankings();
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't obtain fun ranking"));
        }
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
		final String lUrl = LEEK_WARS_ROOT_URL + "farmer/register-tournament";
        try {
		    /*HttpResponseWrapper lResponse = */HttpUtils.get(lUrl, mPhpSessionId, mToken);
            mLogger.info("Eleveur " + mFarmer.getName() + " inscrit au tournoi");
        } catch (HttpException e) {
            if ("already_registered".equals(e.getErrorMessage(""))) {
                mLogger.info("Eleveur " + mFarmer.getName() + " déjà inscrit au tournoi");
            } else {
                throw new LWException(e.getErrorMessage("Can't register tournament for the farmer " + mFarmer.getName()));
            }
        }
	}

	/**
	 * Permet d'inscrire tous les poireaux de l'éleveur aux prochains tournois
	 * @throws LWException e
	 */
	public void registerLeeksForNextTournaments() throws LWException {
		checkConnected();
        mLogger.info(LWConst.LOG_SEPARATOR);
        mLogger.info(" INSCRIPTION AUX TOURNOIS POUR L'ELEVEUR " +  mFarmer.getName());
        mLogger.info(LWConst.LOG_SEPARATOR);
		// chacun des poireaux : leek/register-tournament/leek_id/token
		final String urlPattern = LEEK_WARS_ROOT_URL + "leek/register-tournament/%d";
		for (LeekSummary lLeek : mFarmer.getLeeks().values()) {
			try {
                /*HttpResponseWrapper lResponse = */HttpUtils.get(String.format(urlPattern, lLeek.getId()), mPhpSessionId, mToken);
		        mLogger.info("Poireau " + lLeek.getName() +" inscrit au tournoi");
            } catch (HttpException e) {
                if ("already_registered".equals(e.getErrorMessage(""))) {
                    mLogger.info("Poireau " + lLeek.getName() + " déjà inscrit au tournoi");
                } else {
                    mLogger.error("Impossible d'inscrire le poireau " + lLeek.getName() + " au tournoi", e);
                }
            }
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
		final String urlPattern = LEEK_WARS_ROOT_URL + "team/register-tournament/%d";
		for (TeamComposition lCompo : lTeamData.getCompositions()) {
			// Si la compo contient au moins 4 membres
			if (lCompo.getLeeks().length >= 4) {
				lUrl = String.format(urlPattern, lCompo.getId());
				try {
                    /*HttpResponseWrapper lResponse = */HttpUtils.get(lUrl, mPhpSessionId, mToken);
                    mLogger.info("Composition " + lCompo.getName() + " inscrite au tournoi");
                } catch (HttpException e) {
                    if ("already_registered".equals(e.getErrorMessage(""))) {
                        mLogger.info("Composition " + lCompo.getName() + " déjà inscrite au tournoi");
                    } else {
                        mLogger.error("Composition " + lCompo.getName() + " ne peut être inscrite à un tournoi", e);
                    }
                }
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
		try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, NO_TOKEN);
            final GetTeamJSONResponse lTeam = parseResponse(lResponse, GetTeamJSONResponse.class);
            return lTeam.getTeam();
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't obtain team"));
        }
	}

	/**
	 * Récupération des informations privées (compositions par exemples) de l'équipe du farmer.
	 * @return TeamPrivate
	 * @throws LWException e
	 */
	public TeamPrivate getTeamCompositions() throws LWException {
		checkConnected();
		// team/get-private/team_id/token
		final String lUrl = LEEK_WARS_ROOT_URL + "team/get-private/" + mFarmer.getTeam().getId();
		try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, mToken);
            final TeamPrivate lTeam = parseResponse(lResponse, TeamPrivate.class);
            return lTeam;
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't obtain team private data"));
        }
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
		final String lUrl = LEEK_WARS_ROOT_URL + "garden/get";
		try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, mToken);
            final GetGardenJSONResponse lGargen = parseResponse(lResponse, GetGardenJSONResponse.class);
            return lGargen.getGarden();
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't obtain garden"));
        }
	}
	/**
     * Obtention des adversaires de poireaux
	 * @return tableau de poireaux que l'on peut agresser
	 * @throws LWException e
	 */
	public LeekSummary[] getLeekOpponents(final long pLeekId) throws LWException {
		checkConnected();
		// garden/get-leek-opponents/leek_id/token → opponents
		final String lUrl = LEEK_WARS_ROOT_URL + "garden/get-leek-opponents/" + pLeekId;
		try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, mToken);
            final GetLeekOpponentsJSONResponse lWrapper = parseResponse(lResponse, GetLeekOpponentsJSONResponse.class);
            return lWrapper.getOpponents();
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't obtain opponents for leek " + pLeekId));
        }
	}
	/**
     * Obtention des adversaires de l'éleveur
	 * @return tableau d'éleveurs que l'on peut agresser
	 * @throws LWException e
	 */
	public FarmerSummary[] getFarmerOpponents() throws LWException {
		checkConnected();
		// garden/get-farmer-opponents/token → opponents
		final String lUrl = LEEK_WARS_ROOT_URL + "garden/get-farmer-opponents";
		try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, mToken);
            final GetFarmerOpponentsJSONResponse lWrapper = parseResponse(lResponse, GetFarmerOpponentsJSONResponse.class);
            return lWrapper.getOpponents();
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't obtain opponents for farmer " + mFarmer.getName()));
        }
	}
    /**
     * Obtention des adversaires de compositions d'équipe
     * @return tableau de compos que l'on peut agresser
     * @throws LWException e
     */
    public GardenEnemyTeamComposition[] getTeamOpponents(final long pCompoId) throws LWException {
        checkConnected();
        // garden/get-composition-opponents/composition/token → opponents
        final String lUrl = LEEK_WARS_ROOT_URL + "garden/get-composition-opponents/" + pCompoId;
        try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, mToken);
            final GetTeamOpponentsJSONResponse lWrapper = parseResponse(lResponse,  GetTeamOpponentsJSONResponse.class);
            return lWrapper.getOpponents();
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't obtain opponents for team composition " + pCompoId));
        }
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
	 */
	private long genericStartFight(final HttpResponseWrapper pResponse) {
		final StartFightJSONResponse lFightResponse = parseResponse(pResponse, StartFightJSONResponse.class);
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
		final String lUrl = LEEK_WARS_ROOT_URL + "garden/start-solo-fight/" + pLeekId + '/' + pEnemyLeekId;
		try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, mToken);
            return genericStartFight(lResponse);
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't start fight for leek " + pLeekId + " against leek " + pEnemyLeekId));
        }
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
		final String lUrl = LEEK_WARS_ROOT_URL + "garden/start-farmer-fight/" + pEnemyId;
		try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, mToken);
            return genericStartFight(lResponse);
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't start fight for farmer " + mFarmer.getName() + " against farmer " + pEnemyId));
        }
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
		final String lUrl = LEEK_WARS_ROOT_URL + "garden/start-team-fight/" + pCompoId + '/' + pTargetTeamCompoId;
		try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, mToken);
            return genericStartFight(lResponse);
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't start fight for team " + pCompoId + " against team " + pTargetTeamCompoId));
        }
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
		try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, NO_TOKEN);
//            final GetFightJSONResponse lFightResponse = parseResponse(lResponse, GetFightJSONResponse.class);
//            return lFightResponse.getFight();
            return parseResponse(lResponse, Fight.class);
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't get fight " + pFightId));
        }
	}

//	/**
//	 * @param pFightId id du combat
//	 * @throws LWException e
//	 */
//	public void getFightLogs(final long pFightId) throws LWException {
//		checkConnected();
//		final String lUrl = LEEK_WARS_ROOT_URL + "fight/get-logs/" + pFightId + '/' + mToken;
//		final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, NO_TOKEN);
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
		try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, NO_TOKEN);
            final GetFarmerJSONResponse lFarmerResponse = parseResponse(lResponse,  GetFarmerJSONResponse.class);
            mFarmer = lFarmerResponse.getFarmer();
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't update farmer " + mFarmer.getName()));
        }
	}

	/**
	 * Récupère les infos concernant les trophées obtenus par l'éleveur connecté
	 * @return liste des trophées débloqués de l'éleveur connecté
	 * @since 1.1
	 * @throws LWException e
	 */
	public List<Trophy> getUnlockedFarmerTrophies() throws LWException {
		// trophy/get-farmer-trophies/farmer_id/lang/token → trophies
		final String lUrl = LEEK_WARS_ROOT_URL + "trophy/get-farmer-trophies/" + mFarmer.getId() + '/' + mLang;
		try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, mToken);
            final GetFarmerTrophiesJSONResponse lTrophiesResponse = parseResponse(lResponse,  GetFarmerTrophiesJSONResponse.class);
            final List<Trophy> lTrophies = new ArrayList<>(lTrophiesResponse.getCount());
            Trophy lTrophy;
            for (final Map.Entry<String, Trophy> lEntry : lTrophiesResponse.getTrophies().entrySet()) {
                lTrophy = lEntry.getValue();
                if (lTrophy.isUnlocked()) {
                    lTrophies.add(lTrophy);
                }
            }
            return lTrophies;
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't get farmer throphies"));
        }
	}

	/**
	 * Permet de positionner ou d'enlever l'éleveur du potager
	 * @param pInGarden true pour positionner, false pour retirer du potager
	 * @throws LWException e
	 * @since 1.4
	 */
	public boolean setFarmerInGarden(final boolean pInGarden) throws LWException {
		if (mFarmer.isInGarden() == pInGarden) {
			return false;
		}
		// farmer/set-in-garden/<in_garden(true/false)>/<token>
		final String lUrl = LEEK_WARS_ROOT_URL + "farmer/set-in-garden/" + pInGarden;
		try {
            /*final HttpResponseWrapper lResponse = */HttpUtils.get(lUrl, mPhpSessionId, mToken);
            return true;
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't set or unset farmer in garden"));
        }
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
		final String lUrl = LEEK_WARS_ROOT_URL + "leek/get-registers/" + pLeekId;
		try {
            final HttpResponseWrapper lResponse = HttpUtils.get(lUrl, mPhpSessionId, mToken);
            final GetRegistersJSONResponse lRegistersResponse = parseResponse(lResponse, GetRegistersJSONResponse.class);
            return lRegistersResponse.getRegisters();
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't get registers for leek " + pLeekId));
        }
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
		final String lUrl = LEEK_WARS_ROOT_URL + "leek/set-register/" + pLeekId + '/' + HttpUtils.encodeUrlParam(pRegister.getKey()) + '/' + HttpUtils.encodeUrlParam(pRegister.getValue());
		try {
            /*final HttpResponseWrapper lResponse = */HttpUtils.get(lUrl, mPhpSessionId, mToken);
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't set register for leek " + pLeekId));
        }
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
		final String lUrl = LEEK_WARS_ROOT_URL + "leek/delete-register/" + pLeekId + '/' + HttpUtils.encodeUrlParam(pKey);
		try {
            /*final HttpResponseWrapper lResponse = */HttpUtils.get(lUrl, mPhpSessionId, mToken);
            return lValue;
        } catch (HttpException e) {
            throw new LWException(e.getErrorMessage("Can't delete register for leek " + pLeekId));
        }
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
