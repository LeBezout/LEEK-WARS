package com.leekwars.utils.model;

import java.util.Map;

/**
 * Infos d'un éleveur
 * @author Bezout
 */
public class Farmer extends Identity {
	private String login;
	private TeamSummary team;
	private int talent;
	private Map<String, LeekSummary> leeks;
	private long avatar_changed;
	private int talent_more;
	private int victories;
	private FightSummary[] fights;
	private int draws;
	private int defeats;
	private double ratio;
	private boolean connected;
	private long last_connection;
	private long register_date;
	private Tournament[] tournaments;
	private boolean admin;
	private boolean moderator;
	private String country;
	private Identity godfather;
	private Identity[] godsons;
	private String color;
	private int banned;
	private int won_solo_tournaments;
	private int won_farmer_tournaments;
	private int won_team_tournaments;
	private long habs;
	private int crystals;
	private TemplatedItem[] weapons;
	private TemplatedItem[] chips;
	private AI[] ais;
	private Potion[] potions;
	private Hat[] hats;
	private DailyTournament tournament;
	// candidacy
	
	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param pLogin the login to set
	 */
	public void setLogin(String pLogin) {
		login = pLogin;
	}
	/**
	 * @return the team
	 */
	public TeamSummary getTeam() {
		return team;
	}
	/**
	 * @param pTeam the team to set
	 */
	public void setTeam(TeamSummary pTeam) {
		team = pTeam;
	}
	/**
	 * @return the talent
	 */
	public int getTalent() {
		return talent;
	}
	/**
	 * @param pTalent the talent to set
	 */
	public void setTalent(int pTalent) {
		talent = pTalent;
	}
	/**
	 * @return the avatar_changed
	 */
	public long getAvatar_changed() {
		return avatar_changed;
	}
	/**
	 * @param pAvatar_changed the avatar_changed to set
	 */
	public void setAvatar_changed(long pAvatar_changed) {
		avatar_changed = pAvatar_changed;
	}
	/**
	 * @return the talent_more
	 */
	public int getTalent_more() {
		return talent_more;
	}
	/**
	 * @param pTalent_more the talent_more to set
	 */
	public void setTalent_more(int pTalent_more) {
		talent_more = pTalent_more;
	}
	/**
	 * @return the victories
	 */
	public int getVictories() {
		return victories;
	}
	/**
	 * @param pVictories the victories to set
	 */
	public void setVictories(int pVictories) {
		victories = pVictories;
	}
	/**
	 * @return the draws
	 */
	public int getDraws() {
		return draws;
	}
	/**
	 * @param pDraws the draws to set
	 */
	public void setDraws(int pDraws) {
		draws = pDraws;
	}
	/**
	 * @return the defeats
	 */
	public int getDefeats() {
		return defeats;
	}
	/**
	 * @param pDefeats the defeats to set
	 */
	public void setDefeats(int pDefeats) {
		defeats = pDefeats;
	}
	/**
	 * @return the ratio
	 */
	public double getRatio() {
		return ratio;
	}
	/**
	 * @param pRatio the ratio to set
	 */
	public void setRatio(double pRatio) {
		ratio = pRatio;
	}
	/**
	 * @return the connected
	 */
	public boolean isConnected() {
		return connected;
	}
	/**
	 * @param pConnected the connected to set
	 */
	public void setConnected(boolean pConnected) {
		connected = pConnected;
	}
	/**
	 * @return the last_connection
	 */
	public long getLast_connection() {
		return last_connection;
	}
	/**
	 * @param pLast_connection the last_connection to set
	 */
	public void setLast_connection(long pLast_connection) {
		last_connection = pLast_connection;
	}
	/**
	 * @return the register_date
	 */
	public long getRegister_date() {
		return register_date;
	}
	/**
	 * @param pRegister_date the register_date to set
	 */
	public void setRegister_date(long pRegister_date) {
		register_date = pRegister_date;
	}
	/**
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}
	/**
	 * @param pAdmin the admin to set
	 */
	public void setAdmin(boolean pAdmin) {
		admin = pAdmin;
	}
	/**
	 * @return the moderator
	 */
	public boolean isModerator() {
		return moderator;
	}
	/**
	 * @param pModerator the moderator to set
	 */
	public void setModerator(boolean pModerator) {
		moderator = pModerator;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param pCountry the country to set
	 */
	public void setCountry(String pCountry) {
		country = pCountry;
	}
	/**
	 * @return the godfather
	 */
	public Identity getGodfather() {
		return godfather;
	}
	/**
	 * @param pGodfather the godfather to set
	 */
	public void setGodfather(Identity pGodfather) {
		godfather = pGodfather;
	}
	/**
	 * @return the godsons
	 */
	public Identity[] getGodsons() {
		return godsons;
	}
	/**
	 * @param pGodsons the godsons to set
	 */
	public void setGodsons(Identity[] pGodsons) {
		godsons = pGodsons;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param pColor the color to set
	 */
	public void setColor(String pColor) {
		color = pColor;
	}
	/**
	 * @return the banned
	 */
	public int getBanned() {
		return banned;
	}
	/**
	 * @param pBanned the banned to set
	 */
	public void setBanned(int pBanned) {
		banned = pBanned;
	}
	/**
	 * @return the won_solo_tournaments
	 */
	public int getWon_solo_tournaments() {
		return won_solo_tournaments;
	}
	/**
	 * @param pWon_solo_tournaments the won_solo_tournaments to set
	 */
	public void setWon_solo_tournaments(int pWon_solo_tournaments) {
		won_solo_tournaments = pWon_solo_tournaments;
	}
	/**
	 * @return the won_farmer_tournaments
	 */
	public int getWon_farmer_tournaments() {
		return won_farmer_tournaments;
	}
	/**
	 * @param pWon_farmer_tournaments the won_farmer_tournaments to set
	 */
	public void setWon_farmer_tournaments(int pWon_farmer_tournaments) {
		won_farmer_tournaments = pWon_farmer_tournaments;
	}
	/**
	 * @return the won_team_tournaments
	 */
	public int getWon_team_tournaments() {
		return won_team_tournaments;
	}
	/**
	 * @param pWon_team_tournaments the won_team_tournaments to set
	 */
	public void setWon_team_tournaments(int pWon_team_tournaments) {
		won_team_tournaments = pWon_team_tournaments;
	}
	/**
	 * @return the habs
	 */
	public long getHabs() {
		return habs;
	}
	/**
	 * @param pHabs the habs to set
	 */
	public void setHabs(long pHabs) {
		habs = pHabs;
	}
	/**
	 * @return the crystals
	 */
	public int getCrystals() {
		return crystals;
	}
	/**
	 * @param pCrystals the crystals to set
	 */
	public void setCrystals(int pCrystals) {
		crystals = pCrystals;
	}
	/**
	 * @return the weapons
	 */
	public TemplatedItem[] getWeapons() {
		return weapons;
	}
	/**
	 * @param pWeapons the weapons to set
	 */
	public void setWeapons(TemplatedItem[] pWeapons) {
		weapons = pWeapons;
	}
	/**
	 * @return the chips
	 */
	public TemplatedItem[] getChips() {
		return chips;
	}
	/**
	 * @param pChips the chips to set
	 */
	public void setChips(TemplatedItem[] pChips) {
		chips = pChips;
	}
	/**
	 * @return the ais
	 */
	public AI[] getAis() {
		return ais;
	}
	/**
	 * @param pAis the ais to set
	 */
	public void setAis(AI[] pAis) {
		ais = pAis;
	}
	/**
	 * @return the potions
	 */
	public Potion[] getPotions() {
		return potions;
	}
	/**
	 * @param pPotions the potions to set
	 */
	public void setPotions(Potion[] pPotions) {
		potions = pPotions;
	}
	/**
	 * @return the hats
	 */
	public Hat[] getHats() {
		return hats;
	}
	/**
	 * @param pHats the hats to set
	 */
	public void setHats(Hat[] pHats) {
		hats = pHats;
	}
	/**
	 * @return the tournament
	 */
	public DailyTournament getTournament() {
		return tournament;
	}
	/**
	 * @param pTournament the tournament to set
	 */
	public void setTournament(DailyTournament pTournament) {
		tournament = pTournament;
	}
	
	/**
	 * @return the tournaments
	 */
	public Tournament[] getTournaments() {
		return tournaments;
	}
	/**
	 * @param pTournaments the tournaments to set
	 */
	public void setTournaments(Tournament[] pTournaments) {
		tournaments = pTournaments;
	}
	/**
	 * @return the fights
	 */
	public FightSummary[] getFights() {
		return fights;
	}
	/**
	 * @param pFights the fights to set
	 */
	public void setFights(FightSummary[] pFights) {
		fights = pFights;
	}
	/**
	 * @return the leeks
	 */
	public Map<String, LeekSummary> getLeeks() {
		return leeks;
	}
	/**
	 * @param pLeeks the leeks to set
	 */
	public void setLeeks(Map<String, LeekSummary> pLeeks) {
		leeks = pLeeks;
	}	
	/** getLeekFromId
	 * @param pId
	 * @return LeekSummary
	 */
	public LeekSummary getLeekFromId(final long pId) {
		return leeks.get(String.valueOf(pId));
	}
}
