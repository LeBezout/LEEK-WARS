package com.leekwars.utils.fastgarden.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.leekwars.utils.fastgarden.FastGardenParam;
import com.leekwars.utils.fastgarden.FightingDistributionStrategy;

/**
 * Paramétrage du Fast Garden sous forme de properties
 * @author Bezout
 */
public class PropertiesFastGardenParamImpl implements FastGardenParam {
    private final Properties mProps;

    /**
     * Constructeur depuis une instance de Properties
     * @param pProps instance de Properties
     * @throws IOException
     */
    public PropertiesFastGardenParamImpl(final Properties pProps) {
        mProps = pProps;
    }
    /**
     * Constructeur depuis une ressource du classpath
     * @param pResourceName ressource du classpath
     * @throws IOException
     */
    public PropertiesFastGardenParamImpl(final String pResourceName) throws IOException {
        mProps = new Properties();
        mProps.load(getClass().getResourceAsStream(pResourceName));
    }
    /**
     * Constructeur depuis un fichier
     * @param pFile fichier
     * @throws IOException
     */
    public PropertiesFastGardenParamImpl(final File pFile) throws IOException {
        mProps = new Properties();
        mProps.load(new FileInputStream(pFile));
    }

    @Override
	public String toString() {
    	return "PropertiesFastGardenParamImpl ["
				+ "\nmaxStartFights=" + getMaxStartFights()
                + "\nmaxRetryForFightResult=" + getMaxRetryForFightResult()
                + "\nmaxRetryForFightResult=" + getMaxRetryForFightResult()
                + "\nwaitTimeToGetResults=" + getWaitTimeToGetResults()
                + "\nwaitTimeBeforeRetry=" + getWaitTimeBeforeRetry()
                + "\nsleepTimeBetweenFights=" + getSleepTimeBetweenFights()
                + "\nmaxFarmerAttacks=" + getMaxFarmerAttacks()
                + "\ntalentDiffAcceptance=" + getTalentDiffAcceptance()
                + "\nmaxStartFightErrors=" + getMaxStartFightErrors()
                + "\nfightingDistributionStrategy=" + getFightingDistributionStrategy()
				+ "]";
	}

    /**
	 * @return the maxStartFights
	 */
	@Override
	public int getMaxStartFights() {
		return Integer.parseInt(mProps.getProperty("fastGarden.maxStartFights", "10"));
	}
	
	/**
	 * @return the maxRetryForFightResult
	 */
	@Override
	public int getMaxRetryForFightResult() {
		return Integer.parseInt(mProps.getProperty("fastGarden.maxRetryForFightResult", "10"));
	}

	/**
	 * @return the waitTimeToGetResults
	 */
	@Override
	public int getWaitTimeToGetResults() {
		return Integer.parseInt(mProps.getProperty("fastGarden.waitTimeToGetResults", "10"));
	}

    /**
     * @return the waitTimeBeforeRetry [en secondes]
     */
    public int getWaitTimeBeforeRetry() {
        return Integer.parseInt(mProps.getProperty("fastGarden.waitTimeBeforeRetry", "2"));
    }

	/**
	 * @return the sleepTimeBetweenFights
	 */
	@Override
	public int getSleepTimeBetweenFights() {
		return Integer.parseInt(mProps.getProperty("fastGarden.sleepTimeBetweenFights", "2"));
	}

	/**
	 * @return the maxFarmerAttacks
	 */
	@Override
	public int getMaxFarmerAttacks() {
		return Integer.parseInt(mProps.getProperty("fastGarden.maxFarmerAttacks", "2"));
	}

	/**
	 * @return the talentDiffAcceptance
	 */
	@Override
	public int getTalentDiffAcceptance() {
		return Integer.parseInt(mProps.getProperty("fastGarden.talentDiffAcceptance", "20"));
	}

	/**
	 * @return the maxStartFightErrors
	 */
	@Override
	public int getMaxStartFightErrors() {
		return Integer.parseInt(mProps.getProperty("fastGarden.maxStartFightErrors", "20"));
	}

    /**
     * @return strategie de répartition des combats entre entités
     */
	@Override
	public FightingDistributionStrategy getFightingDistributionStrategy() {
	    return FightingDistributionStrategy.valueOf(mProps.getProperty("fastGarden.fightingDistributionStrategy", "BALANCED_PARAM_LIMITED"));
    }
}