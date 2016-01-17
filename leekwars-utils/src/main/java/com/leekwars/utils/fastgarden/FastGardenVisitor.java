package com.leekwars.utils.fastgarden;

import com.leekwars.utils.enums.EntityType;
import com.leekwars.utils.enums.FightResult;
import com.leekwars.utils.model.Entity;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.Fight;
import com.leekwars.utils.wrappers.GardenStatsWrapper;
import com.leekwars.utils.wrappers.MessageWrapper;

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
	 * Déclenché à chaque détection d'un message
	 * @param pMessage message
	 */
	void onMessage(final MessageWrapper pMessage);
	
	/**
	 * Déclenché lorsque l'on change d'entité
	 * @param pEntityType le type (poireau ou éleveur)
	 * @param pEntity l'entité concerné
	 */
	void onEntityChange(final EntityType pEntityType, final Entity pEntity);
	
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
