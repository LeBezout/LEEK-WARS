package com.leekwars.utils.fastgarden;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leekwars.utils.AbstractLeekWarsConnector;
import com.leekwars.utils.LWUtils;
import com.leekwars.utils.enums.EntityType;
import com.leekwars.utils.enums.FightResult;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.model.Entity;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.FarmerSummary;
import com.leekwars.utils.model.Fight;
import com.leekwars.utils.model.Garden;
import com.leekwars.utils.model.GardenEnemyTeamComposition;
import com.leekwars.utils.model.GardenFarmerTeamComposition;
import com.leekwars.utils.model.LeekSummary;
import com.leekwars.utils.model.TeamComposition;
import com.leekwars.utils.model.TeamPrivate;
import com.leekwars.utils.wrappers.FightWrapper;
import com.leekwars.utils.wrappers.GardenStatsWrapper;
import com.leekwars.utils.wrappers.MessageWrapper;

/**
 * UltraFastGarden : lance tous les combats disponibles
 * @author Bezout
 */
public abstract class UltraFastGarden {
	private static final Logger LOGGER = Logger.getLogger(UltraFastGarden.class.getName());
	
	private static FastGardenParam PARAMS = new FastGardenParam(); 

	/**
	 * Permet de modifier les paramètres par défaut :
	 * maxRetryForFightResult = 10
	 * waitTimeToGetResults = 10sec
	 * maxFarmerAttacks = 2
	 * talentDiffAcceptance = 20%
	 * maxStartFightErrors = 20
	 * @param pParams
	 */
	public static void setParams(final FastGardenParam pParams) {
		PARAMS = pParams;
	}
	/**
	 * Restore les paramètres par défaut.
	 */
	public static void setDefaultParams() {
		PARAMS = new FastGardenParam(); 
	}
	
	/**
	 * Execution du fastGarden pour l'eleveur uniquement.
	 * @param pConnector
	 * @param pVisitor visiteur pour intercepter tous les evenements.
	 * @throws LWException
	 */
	public static void forFamer(final AbstractLeekWarsConnector pConnector, final FastGardenVisitor pVisitor) throws LWException {
		// récupération du token si besoin
		pConnector.connectIfNeeded();
		final Farmer lFarmer = pConnector.getFarmer();
		/*
		 * ON_INIT(Farmer)
		 */
		pVisitor.onInit(lFarmer);
		List<FightWrapper> lFights = new ArrayList<FightWrapper>(20);
		// on commence par faire un 1er appel au potager
		Garden lPotager = pConnector.getGarden();
		// Combats d'éleveur (le potager est moins variés, il faut éviter de taper les talents trop élevés, et trop souvent les trop faibles)
		if (lPotager.isFarmer_enabled()) {
			List<FarmerSummary> lFamerEnemies = lPotager.getFarmer_enemies();
			if (lFamerEnemies.isEmpty()) {
				pVisitor.onMessage(new MessageWrapper(lFarmer, "Aucun combat possible pour l'éleveur : aucun ennemi retourné", "No fight possible for the farmer: no enemy returned"));
				LOGGER.warn("PAS DE COMBATS POSSIBLE POUR L'ELEVEUR" + lFarmer.getId() + " : aucun ennemi");
			} else {
				LWUtils.sleep(1);
				lFights.addAll(fastGardenForFarmer(pConnector, pVisitor));
			}
			// Statistiques
			computeStats(pConnector, lFights, pVisitor, false);
		} else {
			pVisitor.onMessage(new MessageWrapper(lFarmer, MessageWrapper.MESSAGE_TYPE_INFO, "Aucun combat possible pour l'éleveur : plus de combats possibles", "No fighting is possible for the farmer: no more fights"));
			LOGGER.warn("PAS DE COMBATS POSSIBLE POUR L'ELEVEUR" + lFarmer.getId() + " : plus de combats possibles");
		}
		/*
		 * ON_END
		 */
		pVisitor.onEnd();
	}
	
	/**
	 * Execution du fastGarden pour les poireaux uniquement.
	 * @param pConnector
	 * @param pVisitor visiteur pour intercepter tous les evenements.
	 * @throws LWException
	 */
	public static void forLeeks(final AbstractLeekWarsConnector pConnector, final FastGardenVisitor pVisitor) throws LWException {
		// récupération du token si besoin
		pConnector.connectIfNeeded();
		final Farmer lFarmer = pConnector.getFarmer();
		/*
		 * ON_INIT(Farmer)
		 */
		pVisitor.onInit(lFarmer);
		// on commence par faire un 1er appel au potager
		Garden lPotager = pConnector.getGarden();
		List<FightWrapper> lFights = new ArrayList<FightWrapper>(80); // 100 = max (4*20)
		Map<String, LeekSummary[]> lEnemies = lPotager.getSolo_enemies();
		// Pour chaque poireau de l'éléveur
		if (lEnemies.isEmpty()) {
			LOGGER.warn("PAS DE COMBATS POSSIBLE POUR LES POIREAUX");
			pVisitor.onMessage(MessageWrapper.info("Aucun combat possible pour les poireaux", "No more fights for leeks"));
		} else {
			for (LeekSummary lLeek : lFarmer.getLeeks().values()) {
				LWUtils.sleep(1);
				lFights.addAll(fastGardenForLeek(pConnector, lLeek.getId(), pVisitor));
			}
			// Statistiques
			computeStats(pConnector, lFights, pVisitor, false);
		}
		/*
		 * ON_END
		 */
		pVisitor.onEnd();
	}
	
	/**
	 * Execution du fastGarden pour 1 poireau uniquement.
	 * @param pConnector
	 * @param pLeekID id du poireau
	 * @param pVisitor visiteur pour intercepter tous les evenements.
	 * @throws LWException
	 */
	public static void forLeek(final AbstractLeekWarsConnector pConnector, final long pLeekID, final FastGardenVisitor pVisitor) throws LWException {
		// récupération du token si besoin
		pConnector.connectIfNeeded();
		final Farmer lFarmer = pConnector.getFarmer();
		/*
		 * ON_INIT(Farmer)
		 */
		pVisitor.onInit(lFarmer);
		// on commence par faire un 1er appel au potager
		Garden lPotager = pConnector.getGarden();
		List<FightWrapper> lFights = new ArrayList<FightWrapper>(80); // 100 = max (4*20)
		Map<String, LeekSummary[]> lEnemies = lPotager.getSolo_enemies();
		// Pour chaque poireau de l'éléveur
		if (lEnemies.isEmpty()) {
			LOGGER.warn("PAS DE COMBATS POSSIBLE POUR LE POIREAU");
			pVisitor.onMessage(new MessageWrapper("Aucun combat possible pour le poireau", "No more fights for leek"));
		} else {
			LWUtils.sleep(1);
			fastGardenForLeek(pConnector, pLeekID, pVisitor);

			// Statistiques
			computeStats(pConnector, lFights, pVisitor, false);
		}
		/*
		 * ON_END
		 */
		pVisitor.onEnd();
	}
	
	/**
	 * Execution du fastGarden pour l'eleveur et tous ses poireaux.
	 * @param pConnector
	 * @param pVisitor visiteur pour intercepter tous les evenements.
	 * @throws LWException
	 */
	public static void forAll(final AbstractLeekWarsConnector pConnector, final FastGardenVisitor pVisitor) throws LWException {
		// récupération du token si besoin
		pConnector.connectIfNeeded();
		final Farmer lFarmer = pConnector.getFarmer();
		/*
		 * ON_INIT(Farmer)
		 */
		pVisitor.onInit(lFarmer);
		// on commence par faire un 1er appel au potager
		Garden lPotager = pConnector.getGarden();
		List<FightWrapper> lFights = new ArrayList<FightWrapper>(120); // 120 = max (4*20+2*20)
		Map<String, LeekSummary[]> lEnemies = lPotager.getSolo_enemies();
		// Pour chaque poireau de l'éléveur
		if (lEnemies.isEmpty()) {
			LOGGER.warn("PAS DE COMBATS POSSIBLE POUR LES POIREAUX");
			pVisitor.onMessage(new MessageWrapper("Aucun combat possible pour les poireaux", "No more fights for leeks"));
		} else {
			for (LeekSummary lLeek : lFarmer.getLeeks().values()) {
				LWUtils.sleep(1);
				lFights.addAll(fastGardenForLeek(pConnector, lLeek.getId(), pVisitor));
			}
		}
		
		// Combats d'éleveur (le potager est moins variés, il faut éviter de taper les talents trop élevés, et trop souvent les trop faibles)
		if (lPotager.isFarmer_enabled()) {
			List<FarmerSummary> lFamerEnemies = lPotager.getFarmer_enemies();
			if (lFamerEnemies.isEmpty()) {
				pVisitor.onMessage(new MessageWrapper(lFarmer, "Aucun combat possible pour l'éleveur : aucun ennemi retourné", "No fight possible for the farmer: no enemy returned"));
				LOGGER.warn("PAS DE COMBATS POSSIBLE POUR L'ELEVEUR" + lFarmer.getId() + " : aucun ennemi");
			} else {
				LWUtils.sleep(1);
				lFights.addAll(fastGardenForFarmer(pConnector, pVisitor));
			}
		} else {
			pVisitor.onMessage(new MessageWrapper(lFarmer, "Aucun combat possible pour l'éleveur : plus de combats possibles", "No fighting is possible for the farmer: no more fights"));
			LOGGER.warn("PAS DE COMBATS POSSIBLE POUR L'ELEVEUR" + lFarmer.getId() + " : plus de combats possibles");
		}

		if (!lFights.isEmpty()) {
			// Statistiques
			computeStats(pConnector, lFights, pVisitor, false);
		}
		/*
		 * ON_END
		 */
		pVisitor.onEnd();
	}
	
	/**
	 * Execution du fastGarden pour toutes compos d'équipe dans lesquelles apparaissent des poireaux de l'eleveur.
	 * @param pConnector
	 * @param pVisitor visiteur pour intercepter tous les evenements.
	 * @throws LWException
	 */
	public static void forTeam(final AbstractLeekWarsConnector pConnector, final FastGardenVisitor pVisitor) throws LWException {
		// récupération du token si besoin
		pConnector.connectIfNeeded();
		final Farmer lFarmer = pConnector.getFarmer();
		/*
		 * ON_INIT(Farmer)
		 */
		pVisitor.onInit(lFarmer);
		// Données privées de l'équipe
		final TeamPrivate lTeamCompos = pConnector.getTeamCompositions();
		// on commence par faire un 1er appel au potager
		Garden lPotager = pConnector.getGarden();
		if (lPotager.isTeam_enabled()) {
			// Pour chaque équipe
			final TeamComposition[] lCompos = lTeamCompos.getCompositions();
			List<FightWrapper> lFights = new ArrayList<FightWrapper>(20 * lCompos.length); 
			for (TeamComposition lCompo : lCompos) {
				LWUtils.sleep(1);
				// positionne l'id de l'équipe
				lCompo.setTeamId(lTeamCompos.getId());
				// lance les combats
				lFights.addAll(fastGardenForTeamCompo(pConnector, lCompo, pVisitor));
			}
			if (!lFights.isEmpty()) {
				// Statistiques
				computeStats(pConnector, lFights, pVisitor, true);
			}
		} else {
			pVisitor.onMessage(new MessageWrapper(lTeamCompos, MessageWrapper.MESSAGE_TYPE_INFO, "Aucun combat possible pour l'équipe : plus de combats possibles", "No fight possible for the team: no more fights"));
		}
		/*
		 * ON_END
		 */
		pVisitor.onEnd();
	}
	
	private static List<FightWrapper> fastGardenForTeamCompo(final AbstractLeekWarsConnector pConnector, final TeamComposition pCompo, final FastGardenVisitor pVisitor) throws LWException {
		final List<FightWrapper> lFights = new ArrayList<FightWrapper>(20);
		Garden lPotager = pConnector.getGarden();
		Map<String, GardenEnemyTeamComposition[]> lEnemies = lPotager.getEnemies_compositions();
		GardenEnemyTeamComposition[] lCompoEnemies = lEnemies.get(String.valueOf(pCompo.getId()));
		GardenFarmerTeamComposition myCompo = lPotager.getMyComposition(pCompo.getId());
		LOGGER.info("-------------------------------------------------------------");
		LOGGER.info(" COMBATS POUR LA COMPO " + pCompo.getId() + " - " +  pCompo.getName());
		if (myCompo == null || lCompoEnemies == null) {
			LOGGER.info(" CETTE COMPO N'EST PAS ACCESSIBLE POUR L'ELEVEUR " + pConnector.getFarmer().getName());
			LOGGER.info("-------------------------------------------------------------");
			pVisitor.onMessage(new MessageWrapper(pCompo, "Le potager de cette compo n'est pas accessible depuis l'éleveur " + pConnector.getFarmer().getName(), "Garden is not accessible for farmer " + pConnector.getFarmer().getName()));
			return lFights;
		}
		final int lInitialFightCount = myCompo.getFights();
		LOGGER.info(" NB DE COMBATS A LANCER : " + lInitialFightCount);
		if (lInitialFightCount == 0) {
			LOGGER.info(" PLUS DE COMBAT POSSIBLE POUR CETTE COMPO");
			LOGGER.info("-------------------------------------------------------------");
			pVisitor.onMessage(new MessageWrapper(pCompo, MessageWrapper.MESSAGE_TYPE_INFO, "Pas de combat possible pour cette compo", "No fight possible for the team composition: already launched"));
			return lFights;
		}
		LOGGER.info("-------------------------------------------------------------");
		long fightId;
		String label;
		int lFightCount = 0;
		int lErrorCount = 0;
		FightWrapper lFightInfos;
		while ((lCompoEnemies != null) && (lCompoEnemies.length > 0) && (lErrorCount < PARAMS.getMaxStartFightErrors()) && (lFightCount < lInitialFightCount)) {
			// on prend le 1er pour lancer le combat
			try {
				fightId = pConnector.startTeamFight(pCompo.getId(), lCompoEnemies[0].getId());
				label = "COMBAT TEAM "+ fightId + " [" + pCompo.getName() + " vs " + lCompoEnemies[0].getTeam().getName() + "]";
				LOGGER.info(">> " + label + " lancé");
				lFightInfos = new FightWrapper(pCompo, "TEAM", fightId, lCompoEnemies[0].getTeam().getName());
				lFightInfos.setEntityType(EntityType.TEAMP_COMPO);
				lFights.add(lFightInfos);
				lFightCount++;
			} catch (LWException le) {
				pVisitor.onMessage(new MessageWrapper(pCompo, MessageWrapper.MESSAGE_TYPE_ERROR, "Impossible de lancer le combat contre " + lCompoEnemies[0] + ". Cause : " + le.getMessage(), "Cannot start a fight against " + lCompoEnemies[0] + ". Cause: " + le.getMessage()));
				fightId = -1;
				label = "COMBAT TEAM IMPOSSIBLE A LANCER POUR " + pCompo + " CONTRE " + lCompoEnemies[0] + " (Index=" + (lFightCount+1) + ")";
				LOGGER.info(">> "+ label + " : " + le.getMessage());
				lErrorCount++;
			}
			LWUtils.sleepMS(500);
			// appel au potager
			lPotager = pConnector.getGarden();
			// récupère les énemmis de la compo
			lEnemies = lPotager.getEnemies_compositions();
			lCompoEnemies = lEnemies.get(String.valueOf(pCompo.getId()));
		}
		LOGGER.info(String.valueOf(lFightCount) + " combats lancés sur " + lInitialFightCount + " possibles pour " + pCompo.getName());
		return lFights;
	}
	
	/* COMBATS POUR UN POIREAU
	 * @param pConnector
	 * @param pId
	 * @param pVisitor
	 * @return liste des combats lancés
	 * @throws LWException
	 */
	private static List<FightWrapper> fastGardenForLeek(final AbstractLeekWarsConnector pConnector, final long pId, final FastGardenVisitor pVisitor) throws LWException {
		final List<FightWrapper> lFights = new ArrayList<FightWrapper>(20);
		// appel au potager
		Garden lPotager = pConnector.getGarden();

		final int lInitialFightCount = lPotager.getLeek_fights().get(String.valueOf(pId));
		final LeekSummary leek = pConnector.getFarmer().getLeekFromId(pId);
		final String leekName = leek.getName();
		LOGGER.info("-------------------------------------------------------------");
		LOGGER.info(" COMBATS POUR LE POIREAU " + pId + " - " +  leekName);
		LOGGER.info(" NB DE COMBATS A LANCER : " + lInitialFightCount);
		LOGGER.info("-------------------------------------------------------------");
		
		// récupère les énemmis du poireau
		LeekSummary[] lTargets = lPotager.getSolo_enemies().get(String.valueOf(pId));
		long fightId;
		String label;
		int lFightCount = 0;
		int lErrorCount = 0;
		FightWrapper lFightInfos;
		while ((lTargets != null )&& (lTargets.length > 0) && (lErrorCount < PARAMS.getMaxStartFightErrors())) {
			// on prend le 1er pour lancer le combat
			try {
				fightId = pConnector.startSoloFight(pId, lTargets[0].getId());
				label = "COMBAT SOLO "+ fightId + " [" + leekName + " vs " + lTargets[0].getName() + "]";
				LOGGER.info(">> " + label + " lancé");
				lFightInfos = new FightWrapper(leek, "SOLO", fightId, lTargets[0].getName());
				lFightInfos.setEntityType(EntityType.LEEK);
				lFights.add(lFightInfos);
				lFightCount++;
			} catch (LWException le) {
				pVisitor.onMessage(new MessageWrapper(leek, MessageWrapper.MESSAGE_TYPE_ERROR, "Impossible de lancer le combat contre "+ lTargets[0] +". Cause : " + le.getMessage(), "Cannot start a fight against "+ lTargets[0] +". Cause : " + le.getMessage()));
				fightId = -1;
				label = "COMBAT SOLO IMPOSSIBLE A LANCER POUR " + leekName + " CONTRE " + lTargets[0] + " (Index=" + (lFightCount+1) + ")";
				LOGGER.info(">> "+ label + " : " + le.getMessage());
				lErrorCount++;
			}
			LWUtils.sleepMS(500);
			// appel au potager
			lPotager = pConnector.getGarden();
			// récupère les énemmis du poireau
			lTargets = lPotager.getSolo_enemies().get(String.valueOf(pId));
			LOGGER.info("il reste " + lPotager.getLeek_fights().get(String.valueOf(pId)) + " combat(s) pour ce poireau");
		}
		LOGGER.info(String.valueOf(lFightCount) + " combats lancés sur " + lInitialFightCount + " possibles pour " + leekName);
		return lFights;
	}
	
	/* COMBATS POUR L'ELEVEUR
	 * @param pConnector
	 * @param pVisitor
	 * @return liste des combats lancés
 	 * @throws LWException
	 */
	private static List<FightWrapper> fastGardenForFarmer(final AbstractLeekWarsConnector pConnector, final FastGardenVisitor pVisitor) throws LWException {
		List<FightWrapper> lFights = new ArrayList<FightWrapper>(20);
		Map<Long/* id farmer */, Integer /* nb de combats lancés */> lMapFights = new HashMap<Long, Integer>();
		// appel au potager
		Garden lPotager = pConnector.getGarden();
		final String farmerName = pConnector.getFarmer().getName();
		final int lInitialFightCount = lPotager.getFarmer_fights();
		LOGGER.info("-------------------------------------------------------------");
		LOGGER.info(" COMBATS POUR L'ELEVEUR " + pConnector.getFarmer().getId() + " - " + pConnector.getFarmer().getName());
		LOGGER.info(" NB DE COMBATS A LANCER : " + lInitialFightCount);
		LOGGER.info("-------------------------------------------------------------");
		
		List<FarmerSummary> lFamerEnemies = lPotager.getFarmer_enemies();
		int lFightCount = 0;
		long fightId;
		Integer countForTarget;
		String label;
		final int lRefTalent = pConnector.getFarmer().getTalent();
		boolean famine = false;
		int lastFightCount = 0;
		int lErrorCount = 0;
		while (!famine && (lPotager.getFarmer_fights() > 0) && (lErrorCount < PARAMS.getMaxStartFightErrors())) {
			logFarmerGarden(lPotager);
		
			// choix de la cible
			FightWrapper lFightInfos;
			for (FarmerSummary lTargetFamer : lFamerEnemies) {
				if (LWUtils.acceptTalent(lRefTalent, lTargetFamer.getTalent(), PARAMS.getTalentDiffAcceptance())) {
					countForTarget = lMapFights.get(lTargetFamer.getId());
					if (countForTarget == null) {
						countForTarget = Integer.valueOf(0);
					}
					if (countForTarget.intValue() < PARAMS.getMaxFarmerAttacks()) {
						try {
							fightId = pConnector.startFarmerFight(lTargetFamer.getId());
							label = "COMBAT FARMER "+ fightId + " [" + farmerName + " vs " + lTargetFamer.getName() + "]";
							LOGGER.info(">> "+ label + " lancé");
							lFightCount++;
							lFightInfos = new FightWrapper(pConnector.getFarmer(), "FARMER", fightId, lTargetFamer.getName());
							lFightInfos.setEntityType(EntityType.FARMER);
							lFights.add(lFightInfos);
							lMapFights.put(lTargetFamer.getId(), Integer.valueOf(countForTarget.intValue() + 1));
						} catch (LWException le) {
							pVisitor.onMessage(new MessageWrapper(pConnector.getFarmer(), MessageWrapper.MESSAGE_TYPE_ERROR, "Impossible de lancer le combat contre "+ lTargetFamer +". Cause : " + le.getMessage(), "Cannot start a fight against "+ lTargetFamer +". Cause: " + le.getMessage()));
							fightId = -1;
							label = "COMBAT FARMER IMPOSSIBLE A LANCER POUR " + farmerName + " CONTRE " + lTargetFamer + " (Index=" + (lFightCount+1) + ")";
							LOGGER.warn(">> "+ label + " : " + le.getMessage());
							lErrorCount++;
						}
						break;
					}
				}
			}
			LWUtils.sleepMS(500);
			// appel au potager
			lPotager = pConnector.getGarden();
			famine = lastFightCount == lPotager.getFarmer_fights();
			lastFightCount = lPotager.getFarmer_fights();
		}

		if (famine) {
			LOGGER.warn("**************************************************************");
			LOGGER.warn(" FAMINE");
			LOGGER.warn("**************************************************************");
			pVisitor.onMessage(new MessageWrapper(pConnector.getFarmer(), 
						"Famine : plus de combat à lancer : " + lFightCount + " combats lancés sur " + lInitialFightCount + " possibles",
						"Starvation: no more possible fights : " + lFightCount + " fights launched on " + lInitialFightCount));
		}
		
		LOGGER.info(String.valueOf(lFightCount) + " combats lancés sur " + lInitialFightCount + " possibles pour " + farmerName);

		return lFights;
	}
	
	private static void logFarmerGarden(final Garden pPotager) {
		List<FarmerSummary> lFamerEnemies = pPotager.getFarmer_enemies();
		LOGGER.info(">> POTAGER :");
		for (FarmerSummary lTargetFamer : lFamerEnemies) {
			LOGGER.info("\t" + lTargetFamer.getName() + " [" + lTargetFamer.getTalent() + "]");
		}
	}
	
	/*
	 * Recupere les resultats des combats et génére les statistiques.
	 */
	private static void computeStats(final AbstractLeekWarsConnector pConnector, final List<FightWrapper> pFights, final FastGardenVisitor pVisitor, final boolean pIsTeam) throws LWException {
		// attente de x secondes
		LOGGER.info("On patiente quelques secondes pour laisser les combats se terminer ...");
		LWUtils.waitFor(PARAMS.getWaitTimeToGetResults());
		final Farmer lFarmer = pConnector.getFarmer();
		LOGGER.info("-------------------------------------------------------------");
		LOGGER.info(" RESUME COMBATS ");
		LOGGER.info("-------------------------------------------------------------");
		final Map<String/*name*/, GardenStatsWrapper> lStats = new LinkedHashMap<>();
		GardenStatsWrapper lEntityStats;
		// Résumé - état des combats
		Fight lFightTmp;
		FightResult lResult;
		FightWrapper lFight;
		int retry = 0;
		String currentEntityName = "";
		for (int i = 0; i < pFights.size(); i++) {
			lFight = pFights.get(i);
			if (!currentEntityName.equals(lFight.getEntity().getName())) {
				currentEntityName = lFight.getEntity().getName();
				/*
				 * ON_ENTITY_CHANGE()
				 */
				pVisitor.onEntityChange(lFight.getEntityType(), lFight.getEntity());
			}
			
			lEntityStats = lStats.get(currentEntityName);
			if (lEntityStats == null) {
				lEntityStats = new GardenStatsWrapper(lFight.getEntityType(), lFight.getEntity());
				lStats.put(currentEntityName, lEntityStats);
			}
			
			// interroger pour connaitre le résultat
			lFightTmp = pConnector.getFight(lFight.getFightId());
			lResult = LWUtils.getFightResult(lFarmer, lFightTmp);
			switch (lResult) {
				case UNKNOWN :
					if (retry < PARAMS.getMaxRetryForFightResult()) {
						i--; // on reste sur le meme combat
						retry++;
						LWUtils.waitFor(2); // on attend pour retenter notre chance
					} else {
						pVisitor.onMessage(new MessageWrapper(lFight.getEntity(), 
								"Impossible de récupérer le résultat du combat " + lFight.getFightId(), 
								"Unable to retrieve result for fight " + lFight.getFightId()));
						retry = 0; // reinit
						/*
						 * ON_RESULT(UNKNOWN, lFight)
						 */
						pVisitor.onResult(lFightTmp, FightResult.UNKNOWN);
						lEntityStats.incTotalFights();
					}
					break;
				case DRAW : 
					retry = 0;
					lEntityStats.incDraws();
					lFight.setTurnCount(64);
					break;
				case VICTORY : 
					retry = 0;
					lEntityStats.incVictories();
					lFight.setTurnCount(lFightTmp.getReport().getDuration());
					break;
				case DEFEAT :
					retry = 0;
					lEntityStats.incDefeats();
					lFight.setTurnCount(lFightTmp.getReport().getDuration());
					break; 
				default: break;
			}
			
			if (lResult == FightResult.UNKNOWN) {
				LOGGER.info("\t" + lFight + " -- attente du résultat (" + retry + ") --");
			} else {
				LOGGER.info("\t" + lFight + " : " + lResult + " en " + lFight.getTurnCount() + " tours");
				/*
				 * ON_RESULT(lResult, lFight)
				 */
				pVisitor.onResult(lFightTmp, lResult);
				lEntityStats.incTotalFights();
				LWUtils.sleepMS(500);
			}
		}

		/*
		 * ON_BEFORESTAT()
		 */
		pVisitor.onBeforeStat();
		LOGGER.info("-------------------------------------------------------------");
		LOGGER.info(" STATISTIQUES ");
		LOGGER.info("-------------------------------------------------------------");
		// Mise à jour des infos (talent notamment) de l'eleveur et poireaux
		pConnector.updateFarmer();
		TeamPrivate lTeamCompos = null;
		if (pIsTeam) {
			lTeamCompos = pConnector.getTeamCompositions();
		}
		GardenStatsWrapper lStat;
		for (Map.Entry<String, GardenStatsWrapper> lEntry : lStats.entrySet()) {
			lStat = lEntry.getValue();
			LOGGER.info("\t" + lStat);
			// Récupération du talent et level final
			switch (lStat.getEntityType()) {
				case FARMER :
					lStat.setFinalTalent(pConnector.getFarmer().getTalent());
					// pas de level pour le farmer
					break;
				case LEEK :
					Entity leek = pConnector.getFarmer().getLeekFromId(lStat.getEntity().getId());
					lStat.setFinalTalent(leek.getTalent());
					lStat.getEntity().setLevel(leek.getLevel());
					break;
				case TEAMP_COMPO :
					TeamComposition lCompo = lTeamCompos.getComposition(lStat.getEntity().getId());
					lStat.setFinalTalent(lCompo.getTalent());
					lStat.getEntity().setLevel(lCompo.getLevel());
					break;
				default : 
					break;
			}
			
			/*
			 * ON_STAT(lStat)
			 */
			pVisitor.onStat(lStat);
		}
	}
}
