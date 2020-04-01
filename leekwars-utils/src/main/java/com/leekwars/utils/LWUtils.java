package com.leekwars.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.leekwars.utils.enums.FightContext;
import com.leekwars.utils.enums.FightResult;
import com.leekwars.utils.enums.FightType;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.model.Entity;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.Fight;
import com.leekwars.utils.model.Identity;
import com.leekwars.utils.model.LeekSummary;

/**
 * Utilitaires divers
 * @author Bezout
 */
public final class LWUtils {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private LWUtils() {}

    /**
     * Retourne une entité inconnu
     * @return Entity
     * @since 1.6.0
     */
	public static Entity getUnknonwEntity() {
        Entity unknown = new Entity();
        unknown.setName("Unknown");
        return unknown;
    }

    /**
     * Formatte une chaine JSON
     * @param pJSON chaine JSON
     * @return JSON formatté
     */
	public static String formatJsonString(final String pJSON) {
	    return GSON.toJson(new JsonParser().parse(pJSON));
    }

    /**
     * Convertit une chaine JSON en objet Java
     * @param pJSON chaine JSON
     * @param pType type du wrapper de réponse
     * @param <T>
     * @return
     */
    public static  <T> T parseJson(final String pJSON, final Class<T> pType) {
	    return GSON.fromJson(pJSON, pType);
    }

    /**
	 * Attente en secondes
	 * @param pSeconds
	 */
	public static void sleep(final int pSeconds) {
		try {
			Thread.sleep((long) pSeconds * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * Attente en millisecondes
	 * @param pMs nb ms
	 */
	public static void sleepMS(final int pMs) {
		try {
			Thread.sleep(pMs);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * Attente en secondes avec affichage d'un décompte sur la console
	 * @param pSeconds nb sec
	 */
	public static void waitFor(final int pSeconds) {
		if (pSeconds < 1) return; 
		for (int i = pSeconds; i >= 0; i--) {
			sleep(1);
			System.out.println("...waiting.. " + i);
		}
	}
	
	/**
	 * Permet d'ouvrir un fichier dans un browser
	 * @param pFile fichier à ouvrir
	 * @throws LWException
	 */
	public static void openFileInDefaultBrowser(final File pFile) throws LWException {
		if (!Desktop.isDesktopSupported()) {
			throw new LWException("Desktop not Supported");
		}
		try {
			Desktop.getDesktop().browse(pFile.toURI());
		} catch (IOException e) {
			throw new LWException(e);
		}
	}
	
	/**
	 * Conversion d'une date LW exprimé en nb de secondes depuis le 1/1/1970
	 * @param pValue
	 * @return Date
	 */
	public static Date longToDate(final long pValue) {
		return new Date(1000 * pValue);
	}
	/**
	 * Conversion d'une date LW exprimé en nb de secondes depuis le 1/1/1970
	 * @param pValue
	 * @return Calendar
	 */
	public static Calendar longToCalendar(final long pValue) {
		Calendar lCal = new GregorianCalendar();
		lCal.setTimeInMillis(1000 * pValue);
		return lCal;
	}
	
	/**
	 * Formatte une date
	 * @param pCal
	 * @return chaine
	 */
	public static String formatCalendar(final Calendar pCal) {
		if (pCal == null) return "null";
		return new SimpleDateFormat("dd/MM/yyyy").format(pCal.getTime());
	}
	/**
	 * Retourne une valeur par défaut si le paramètre est null
	 * @param pValue
	 * @param pDefault
	 * @return chaine
	 */
	public static String defaultIfNull(final String pValue, final String pDefault) {
		return pValue == null ? pDefault : pValue;
	}
	
	/**
	 * Détermine si le talent de l'adversaire n'est pas trop élevé
	 * @param pRefTalent
	 * @param pOtherTalent
	 * @param pAcceptance pourcentage accepté d'écart entre 2 talents
	 * @return true si ok
	 */
	public static boolean acceptTalent(final int pRefTalent, final int pOtherTalent, final int pAcceptance) {
		// calcul borne max (pas de min ;) )
		final long diff = Math.round((float)pRefTalent * pAcceptance / 100f);
		final long lMax = pRefTalent + diff;
		// regarde si le talent adverse est en dessous
		return pOtherTalent <= lMax;
	}
	
	/**
	 * Récupère les infos d'un poireau depuis son nom
	 * @param pFarmer eleveur récupéré lors du login
	 * @param pLeekName nom dui poireau recherché
	 * @return LeekSummary
	 * @throws LWException si non trouvé ou paramètres invalides
	 */
	public static LeekSummary getLeekFromName(final Farmer pFarmer, final String pLeekName) throws LWException {
		if (pFarmer == null || pLeekName == null) {
			throw new LWException("Parameters are mandatory");
		}
		for (LeekSummary lLeek : pFarmer.getLeeks().values()) {
			if (pLeekName.equals(lLeek.getName())) {
				return lLeek;
			}
		}
		throw new LWException(String.format("Leek %s doesn't exists for farmer %s.", pLeekName, pFarmer.getName()));
	}
	
	
	/**
	 * Détermine le résultat d'un combat par rapport à un éléveur donné 
	 * NE FONCTIONNE QUE POUR LES POIREAUX DU FARMER EN PARAMETRE
	 * @param pFarmer eleveur récupéré lors du login
	 * @param pFight
	 * @return FightResult
	 */
	public static FightResult getFightResult(final Farmer pFarmer, final Fight pFight) {
		switch (pFight.getWinner()) {
			case -1 /* status==0 */ : return FightResult.UNKNOWN;
			case 0 : return FightResult.DRAW;
			case 1 : 
				switch (getFightType(pFight)) {
					case SOLO : 
						final LeekSummary[] leeks1 = pFight.getLeeks1();
						for (LeekSummary lLeek : leeks1) {
							if (pFarmer.getLeekFromId(lLeek.getId()) != null) return FightResult.VICTORY;
						}
						return FightResult.DEFEAT;
					case FARMER : 
						return pFight.getFarmers1().get(String.valueOf(pFarmer.getId())) != null ? FightResult.VICTORY : FightResult.DEFEAT;
					case TEAM : 
						return pFight.getTeam1() == pFarmer.getTeam().getId() ? FightResult.VICTORY : FightResult.DEFEAT;
					default : 
						return FightResult.UNKNOWN;
				}
			case 2 : 
				switch (getFightType(pFight)) {
					case SOLO : 
						final LeekSummary[] leeks2 = pFight.getLeeks2();
						for (LeekSummary lLeek : leeks2) {
							if (pFarmer.getLeekFromId(lLeek.getId()) != null) return FightResult.VICTORY;
						}
						return FightResult.DEFEAT;
					case FARMER : 
						return pFight.getFarmers2().get(String.valueOf(pFarmer.getId())) != null ? FightResult.VICTORY : FightResult.DEFEAT;
					case TEAM : 
						return pFight.getTeam2() == pFarmer.getTeam().getId() ? FightResult.VICTORY : FightResult.DEFEAT;
					default : 
						return FightResult.UNKNOWN;
				}
			default : 
				return FightResult.UNKNOWN;
		}	
	}
	
	/**
	 * Détermine si dans un combat on est en position 1 ou 2
	 * @param pFarmer
	 * @param pFight
	 * @return true si en position 1
	 */
	public static boolean isFarmer1(final Farmer pFarmer, final Fight pFight) {
		return pFight.getFarmers1().get(String.valueOf(pFarmer.getId())) != null;
	}
	
	/**
	 * Détermine pour un combat le nom de l'ennemi
	 * @param pFarmer
	 * @param pFight
	 * @return nom de l'ennemi
	 */
	public static String getTargetEnemyName(final Farmer pFarmer, final Fight pFight) {
		switch (getFightType(pFight)) {
			case SOLO : 
				if (isFarmer1(pFarmer, pFight)) { // si on est farmer1 alors ennemi = leek2
					return pFight.getLeeks2().length > 0 ? pFight.getLeeks2()[0].getName() : "?";
				} else {
					return pFight.getLeeks1().length > 0 ? pFight.getLeeks1()[0].getName() : "?";
				}
			case FARMER :
				if (isFarmer1(pFarmer, pFight)) { // si on est farmer1 alors ennemi = farmer2
					Identity farmer = pFight.getFarmers2().values().isEmpty() ? null : pFight.getFarmers2().values().iterator().next(); 
					return farmer == null ? "?" : farmer.getName();
				} else {
					Identity farmer = pFight.getFarmers1().values().isEmpty() ? null : pFight.getFarmers1().values().iterator().next(); 
					return farmer == null ? "?" : farmer.getName();
				}
			case TEAM : // si on est farmer1 alors ennemi = Team2
				return isFarmer1(pFarmer, pFight) ? pFight.getTeam2_name() : pFight.getTeam1_name();
			default : 
				return "?";
		}
	}
	
	/**
	 * Conversion d'un code en FightContext
	 * @param pCode
	 * @return FightContext
	 */
	public static FightContext getFightContext(final int pCode) {
		switch (pCode) {
			case 0 : return FightContext.TEST;
			case 1 : return FightContext.CHALLENGE;
			case 2 : return FightContext.GARDEN;
			case 3 : return FightContext.TOURNAMENT;
			default : return null;
		}
	}
	/**
	 * Détermine le contexte d'un combat
	 * @param pFight
	 * @return FightContext
	 */
	public static FightContext getFightContext(final Fight pFight) {
		return pFight == null ? null : getFightContext(pFight.getContext());
	}
	
	/**
	 * Conversion d'un code en FightType
	 * @param pCode
	 * @return FightType
	 */
	public static FightType getFightType(final int pCode) {
		switch (pCode) {
			case 0 : return FightType.SOLO;
			case 1 : return FightType.FARMER;
			case 2 : return FightType.TEAM;
			default : return FightType.UNKNOWN;
		}
	}
	/**
	 * Détermine le type d'un combat
	 * @param pFight
	 * @return FightType
	 */
	public static FightType getFightType(final Fight pFight) {
		return pFight == null ? FightType.UNKNOWN : getFightType(pFight.getType());
	}
	
	/**
	 * Utilisable comme un toString sur un objet Fight.
	 * @param pFight
	 * @return string
	 */
	public static String fightToString(final Fight pFight) {
		if (pFight != null) {
			switch (getFightType(pFight)) {
				case SOLO:
					String leek1 = pFight.getLeeks1() == null || pFight.getLeeks1().length == 0 ? "?" : pFight.getLeeks1()[0].getName();
					String leek2 = pFight.getLeeks2() == null || pFight.getLeeks2().length == 0 ? "?" : pFight.getLeeks2()[0].getName();
					return "COMBAT SOLO " + pFight.getId() + " [" + leek1 + " vs " + leek2 + "]";
				case FARMER:
					String farmer1 = pFight.getFarmers1().isEmpty() ? "?" : pFight.getFarmers1().values().iterator().next().getName();
					String farmer2 = pFight.getFarmers2().isEmpty() ? "?" : pFight.getFarmers2().values().iterator().next().getName();
					return "COMBAT ELEVEUR " + pFight.getId() + " [" + farmer1 + " vs " + farmer2 + "]";
				case TEAM:
					return "COMBAT EQUIPE " + pFight.getId() + " [" + pFight.getTeam1_name() + " vs " + pFight.getTeam2_name() + "]";
				default:
			}
		}
		return "?";
	}
}
