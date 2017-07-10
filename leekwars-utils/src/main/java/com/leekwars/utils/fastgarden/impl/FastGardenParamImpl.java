package com.leekwars.utils.fastgarden.impl;

import com.leekwars.utils.fastgarden.FastGardenParam;
import com.leekwars.utils.fastgarden.FightingDistributionStrategy;

/**
 * Paramétrage du Fast Garden sous forme de POJO
 * @author Bezout
 */
public class FastGardenParamImpl implements FastGardenParam {
	/**
	 * Nombre de tentatives maximales pour récupérer le résultat d'un combat
	 */
	private int maxRetryForFightResult = 10;
	/**
	 * Nombre de secondes à attendre pour laisser les combats se terminer 
	 */
	private int waitTimeToGetResults = 10;
    /**
     * Nombre de secondes à attendre entre 2 tentatives de récupération du résultat d'un combat
     */
    private int waitTimeBeforeRetry = 2;
	/**
	 * Nombre de secondes à attendre entre 2 combats
	 */
	private int sleepTimeBetweenFights = 2;
	/**
	 * Nombre d'agressions maximales d'un seul éleveur
	 */
	private int maxFarmerAttacks = 2;
	/**
	 * Seuil d'acception en pourcentage d'un talent pour déterminer si l'on peut combattre l'adversaire
	 * => pourcentage accepté d'écart entre 2 talents
	 */
	private int talentDiffAcceptance = 20;
	/**
	 * Pour eviter les boucles infinies, nombres d'erreurs maximum tolérées (par entité)
	 */
	private int maxStartFightErrors = 20;
	/**
	 * Depuis LW 1.94 on peut répartir le nombre total de combats journaliers comme bon nous semble
	 * avant il y avait un nombre max par poireau et un nombre max pour l'éleveur.<br/>
	 * Ce paramètre permet de limiter le nombre de combats maximum par entité.
	 */
	private int maxStartFights = 10;
    /**
     * Stratégie de répartition des combats entre les différentes entités
     * @since 1.4.1
     */
	private FightingDistributionStrategy fightingDistributionStrategy = FightingDistributionStrategy.BALANCED_PARAM_LIMITED;

	/**
	 * @return the maxStartFights
	 */
	@Override
	public int getMaxStartFights() {
		return maxStartFights;
	}
	/**
	 * @param pMaxStartFights How many fights to start in total if available
	 */
	public void setMaxStartFights(int pMaxStartFights) {
		maxStartFights = pMaxStartFights;
	}
	/**
	 * @return the maxRetryForFightResult
	 */
	@Override
	public int getMaxRetryForFightResult() {
		return maxRetryForFightResult;
	}
	/**
	 * @param pMaxRetryForFightResult the maxRetryForFightResult to set. Defaults to 10.
	 */
	public void setMaxRetryForFightResult(int pMaxRetryForFightResult) {
		maxRetryForFightResult = pMaxRetryForFightResult;
	}
	/**
	 * @return the waitTimeToGetResults
	 */
	@Override
	public int getWaitTimeToGetResults() {
		return waitTimeToGetResults;
	}
	/**
	 * @param pWaitTimeToGetResults the waitTimeToGetResults to set. Defaults to 10.
	 */
	public void setWaitTimeToGetResults(int pWaitTimeToGetResults) {
		waitTimeToGetResults = pWaitTimeToGetResults;
	}
    /**
     * @return the sleepTimeBetweenFights
     */
    @Override
    public int getSleepTimeBetweenFights() {
        return sleepTimeBetweenFights;
    }
    /**
     * @param pSleepTimeBetweenFights the sleepTimeBetweenFights to set. Defaults to 2.
     */
    public void setSleepTimeBetweenFights(int pSleepTimeBetweenFights) {
        sleepTimeBetweenFights = pSleepTimeBetweenFights;
    }
    /**
     * @return the waitTimeBeforeRetry [en secondes]
     */
    public int getWaitTimeBeforeRetry() {
        return waitTimeBeforeRetry;
    }
    /**
     * @param pWaitTimeBeforeRetry the waitTimeBeforeRetry to set. Defaults to 2.
     */
    public void setWaitTimeBeforeRetry(int pWaitTimeBeforeRetry) {
        waitTimeBeforeRetry = pWaitTimeBeforeRetry;
    }
	/**
	 * @return the maxFarmerAttacks
	 */
	@Override
	public int getMaxFarmerAttacks() {
		return maxFarmerAttacks;
	}
	/**
	 * @param pMaxFarmerAttacks the maxFarmerAttacks to set. Defaults to 2.
	 */
	public void setMaxFarmerAttacks(int pMaxFarmerAttacks) {
		maxFarmerAttacks = pMaxFarmerAttacks;
	}
	/**
	 * @return the talentDiffAcceptance
	 */
	@Override
	public int getTalentDiffAcceptance() {
		return talentDiffAcceptance;
	}
	/**
	 * @param pTalentDiffAcceptance the talentDiffAcceptance to set. Defaults to 20.
	 */
	public void setTalentDiffAcceptance(int pTalentDiffAcceptance) {
		talentDiffAcceptance = pTalentDiffAcceptance;
	}
	/**
	 * @return the maxStartFightErrors
	 */
	@Override
	public int getMaxStartFightErrors() {
		return maxStartFightErrors;
	}
	/**
	 * @param pMaxStartFightErrors the maxStartFightErrors to set
	 */
	public void setMaxStartFightErrors(int pMaxStartFightErrors) {
		maxStartFightErrors = pMaxStartFightErrors;
	}
    /**
     * @return strategie de répartition
     */
	@Override
	public FightingDistributionStrategy getFightingDistributionStrategy() {
	    return fightingDistributionStrategy;
    }
    /**
     * @param pFightingDistributionStrategy strategie de répartition. Default to FightingDistributionStrategy#BALANCED_PARAM_LIMITED
     */
    public void setFightingDistributionStrategy(final FightingDistributionStrategy pFightingDistributionStrategy) {
        fightingDistributionStrategy = pFightingDistributionStrategy;
    }
}
