package com.leekwars.utils.fastgarden;

import com.leekwars.utils.enums.EntityType;
import com.leekwars.utils.enums.FightResult;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.Fight;
import com.leekwars.utils.wrappers.GardenStatsWrapper;

/**
 * Interface décrivant un visiteur pour le fast garden.
 * @author Bezout
 */
public interface FastGardenVisitor {

	/**
	 * Déclenché au début du "fast garden" 
	 * @param pFarmer données de l'éleveur
	 */
	void onInit(final Farmer pFarmer);
	
	/**
	 * Déclenché à chaque détection d'un message de warning
	 * @param pEntityName nom de l'entité concerné (poireau ou éleveur) ou null
	 * @param pMessage message
	 */
	void onWarning(final String pEntityName, final String pMessage);
	
	/**
	 * Déclenché lorsque l'on change d'entité
	 * @param pEntityType le type (poireau ou éleveur)
	 * @param pEntityName le nom
	 */
	void onEntityChange(final EntityType pEntityType, final String pEntityName);
	
	/**
	 * Déclenché lors de la récupération d'un résultat d'un combat
	 * @param pFight infos du combat
	 * @param pResult résultat
	 */
	void onResult(final Fight pFight, final FightResult pResult);
	
	/**
	 * Déclenché avant le calcul des statistiques
	 */
	public void onBeforeStat();
	
	/**
	 * Déclenché lors du calcul d'une statistique
	 * @param pStat infos
	 */
	void onStat(final GardenStatsWrapper pStat);
	
	/**
	 * Déclenché à la fin du traitement
	 */
	void onEnd();
}
