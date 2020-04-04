package com.leekwars.utils.test;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leekwars.utils.DefaultLeekWarsConnector;
import com.leekwars.utils.enums.FightContext;
import com.leekwars.utils.enums.FightStatus;
import com.leekwars.utils.enums.FightType;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.model.Fight;
import com.leekwars.utils.model.FightReport;
import com.leekwars.utils.model.Identity;
import com.leekwars.utils.model.LeekSummary;

/**
 * Builder pour construire des combats de test.
 * @author Bezout
 */
public class FightBuilder {
	private static final Random RANDOM = new Random();
	private static final Gson gson = new GsonBuilder().create();
	private final Fight mFight = new Fight();
	private final List<LeekSummary> leeks1 = new LinkedList<>();
	private final List<LeekSummary> leeks2 = new LinkedList<>();
	private Map<String, Identity> farmers1 = new LinkedHashMap<>();
	private Map<String, Identity> farmers2 = new LinkedHashMap<>();
	private final FightReport mReport = new FightReport();
	private final List<String> flags1 = new LinkedList<>();
	private final List<String> flags2 = new LinkedList<>();
	
	/**
	 * Constructeur
	 */
	public FightBuilder() {
		mFight.setId(RANDOM.nextLong());
		mFight.setType(FightType.SOLO.ordinal());
		mFight.setContext(FightContext.GARDEN.ordinal());
		mFight.setContext(FightStatus.FINISHED.ordinal());
		mFight.setDate(System.currentTimeMillis() / 1000);
		mFight.setTournament(-1);
		mFight.setWinner(-1); // Unknown
		mReport.setDuration(-1);// Unknown
	}
	
	public FightBuilder setType(FightType pType) {
		mFight.setType(pType.ordinal());
		return this;
	}
	public FightBuilder setContext(FightContext pCtx) {
		mFight.setContext(pCtx.ordinal());
		return this;
	}
	public FightBuilder setContext(FightStatus pType) {
		mFight.setContext(pType.ordinal());
		return this;
	}
	public FightBuilder setDate(long pDate) {
		mFight.setDate(pDate);
		return this;
	}
	public FightBuilder setDate(Date pDate) {
		mFight.setDate(pDate.getTime() / 1000);
		return this;
	}
	public FightBuilder setWinner(int pWinner) {
		switch (pWinner) {
			case -1 : 
			case 0 :
			case 1 :
			case 2 :
				mFight.setWinner(pWinner);
				mReport.setWin(pWinner);
				break;
			default:
				mFight.setWinner(-1); // Unknown;
				break;
		}
		return this;
	}
	public FightBuilder addLeek1(LeekSummary pLeek1) {
		leeks1.add(pLeek1);
		return this;
	}
	public FightBuilder addLeek1(long pId, String pName) {
		LeekSummary leek = new LeekSummary();
		leek.setId(pId);
		leek.setName(pName);
		leeks1.add(leek);
		return this;
	}
	public FightBuilder addLeek2(LeekSummary pLeek2) {
		leeks1.add(pLeek2);
		return this;
	}
	public FightBuilder addLeek2(long pId, String pName) {
		LeekSummary leek = new LeekSummary();
		leek.setId(pId);
		leek.setName(pName);
		leeks2.add(leek);
		return this;
	}
	public FightBuilder setTeam1(long pId, String pName) {
		mFight.setTeam1(pId);
		mFight.setTeam1_name(pName);
		return this;
	}
	public FightBuilder setTeam2(long pId, String pName) {
		mFight.setTeam2(pId);
		mFight.setTeam2_name(pName);
		return this;
	}
	public FightBuilder setDuration(int pDuration) {
		mReport.setDuration(pDuration);
		return this;
	}
	public FightBuilder setBonus(int pBonus) {
		mReport.setBonus(pBonus);
		return this;
	}
	public FightBuilder addFlag1(String pFlag) {
		flags1.add(pFlag);
		return this;
	}
	public FightBuilder addFlag2(String pFlag) {
		flags2.add(pFlag);
		return this;
	}
	public FightBuilder addFarmer1(Identity pFamer) {
		farmers1.put(String.valueOf(pFamer.getId()), pFamer);
		return this;
	}
	public FightBuilder addFarmer1(long pFamerId, String pFarmerName) {
		return addFarmer1(new Identity(pFamerId, pFarmerName));
	}
	public FightBuilder addFarmer2(Identity pFamer) {
		farmers2.put(String.valueOf(pFamer.getId()), pFamer);
		return this;
	}
	public FightBuilder addFarmer2(long pFamerId, String pFarmerName) {
		return addFarmer2(new Identity(pFamerId, pFarmerName));
	}
	
	/**
	 * @return Fight
	 */
	public Fight build() {
		mFight.setLeeks1(leeks1.toArray(new LeekSummary[leeks1.size()]));
		mFight.setLeeks2(leeks2.toArray(new LeekSummary[leeks2.size()]));
		mFight.setFarmers1(farmers1);
		mFight.setFarmers2(farmers2);
		if (mReport.getDuration() > 0) {
			mReport.setFlags1(flags1.toArray(new String[flags1.size()]));
			mReport.setFlags2(flags2.toArray(new String[flags2.size()]));
			mFight.setReport(mReport);
		}
		return mFight;
	}
	
	// - STATIC --------------------------------------------------------------------------
	
	/**
	 * @param pJSON flux fight uniquement
	 * @return instance
	 * @throws LWException
	 */
	public static Fight fromJSON(final String pJSON) throws LWException {
		try {
			return gson.fromJson(pJSON, Fight.class);
		} catch (Exception e) {
			throw new LWException(e);
		}
	}
	
	/**
	 * @param pJSON flux complet (avec champs sucess + fight)
	 * @return instance
	 * @throws LWException
	 */
	public static Fight fromJSONAPIResponse(final String pJSON) throws LWException {
		try {
			//return gson.fromJson(pJSON, GetFightJSONResponse.class).getFight();
            return gson.fromJson(pJSON, Fight.class);
		} catch (Exception e) {
			throw new LWException(e);
		}
	}
	
	/**
	 * Se connecte à LW et récupère les infos d'un combat (fight/get)
	 * @param pId du combat
	 * @return instance
	 * @throws LWException
	 */
	public static Fight fromId(long pId) throws LWException {
		DefaultLeekWarsConnector lConnector = new DefaultLeekWarsConnector();
		return lConnector.getFight(pId); // l'appel de ce service ne necessite pas de connexion
	}
}
