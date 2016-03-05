package com.leekwars.utils.fastgarden;

/**
 * Paramétrage du Fast Garden
 * @author Bezout
 */
public class FastGardenParam {

	/**
	 * Nombre de tentatives maximales pour récupérer le résultat d'un combat
	 */
	private int maxRetryForFightResult = 10;
	/**
	 * Nombre de secondes à attendre pour laisser les combats se terminer 
	 */
	private int waitTimeToGetResults = 10;
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
	 * Constructeur
	 */
	public FastGardenParam() {
		super();
	}
	
	/**
	 * @return the maxRetryForFightResult
	 */
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
	 * @return the maxFarmerAttacks
	 */
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
	public int getMaxStartFightErrors() {
		return maxStartFightErrors;
	}
	/**
	 * @param pMaxStartFightErrors the maxStartFightErrors to set
	 */
	public void setMaxStartFightErrors(int pMaxStartFightErrors) {
		maxStartFightErrors = pMaxStartFightErrors;
	}
	
}
