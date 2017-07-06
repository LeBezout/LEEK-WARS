package com.leekwars.utils.fastgarden;

/**
 * Interface décrivant le paramétrage du Fast Garden
 * @author Bezout
 */
public interface FastGardenParam {
	/**
	 * @return the maxStartFights
	 */
	public int getMaxStartFights();
	/**
	 * @return the maxRetryForFightResult
	 */
	public int getMaxRetryForFightResult();
	/**
	 * @return the waitTimeBeforeRetry [en secondes]
	 */
	public int getWaitTimeBeforeRetry();
	/**
	 * @return the waitTimeToGetResults [en secondes]
	 */
	public int getWaitTimeToGetResults();
	/**
	 * @return the maxFarmerAttacks
	 */
	public int getMaxFarmerAttacks();
	/**
	 * @return the talentDiffAcceptance [en %]
	 */
	public int getTalentDiffAcceptance();
	/**
	 * @return the maxStartFightErrors
	 */
	public int getMaxStartFightErrors();
	/**
	 * @return the sleepTimeBetweenFights [en secondes]
	 */
	public int getSleepTimeBetweenFights();
    /**
     * @return strategie de répartition
     */
	public FightingDistributionStrategy getFightingDistributionStrategy();
}
