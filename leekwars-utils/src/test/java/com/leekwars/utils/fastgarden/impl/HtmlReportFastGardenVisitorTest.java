package com.leekwars.utils.fastgarden.impl;

import java.io.File;

import org.junit.Test;

import com.leekwars.utils.LWUtils;
import com.leekwars.utils.enums.EntityType;
import com.leekwars.utils.enums.FightResult;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.model.Entity;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.Fight;
import com.leekwars.utils.test.FightBuilder;
import com.leekwars.utils.wrappers.GardenStatsWrapper;
import com.leekwars.utils.wrappers.MessageWrapper;

public class HtmlReportFastGardenVisitorTest {

	@Test
	@org.junit.Ignore
	public void testGenerate() throws LWException {
		File output = new File("target/generated_junit.html");
		File template = new File("src/main/resources/report_template.html");
		HtmlReportFastGardenVisitor lVisitor = new HtmlReportFastGardenVisitor(template, output);
		lVisitor.setLang("en");
		
		Farmer lFarmerEntity = new Farmer();
		lFarmerEntity.setId(16748);
		lFarmerEntity.setName("Bezout_JUNIT");
		lFarmerEntity.setTalent(2000);
		
		Entity lLeekEntity = new Entity();
		lLeekEntity.setName("CapitaineFlirt_JUNIT");
		lLeekEntity.setTalent(2000);
		lLeekEntity.setLevel(301);
		
		lVisitor.onInit(lFarmerEntity);
		
		lVisitor.onEntityChange(EntityType.LEEK, lLeekEntity);
		lVisitor.onResult(getFightFromLW(15592973), FightResult.DRAW);
		lVisitor.onResult(getFightFromLW(15566926), FightResult.VICTORY);
		lVisitor.onResult(getFightFromLW(15580334), FightResult.DEFEAT);
		lVisitor.onResult(getFightFromLW(15594489), FightResult.UNKNOWN);
		
		lVisitor.onEntityChange(EntityType.FARMER, lFarmerEntity);
		lVisitor.onResult(getFightFromLW(15594446), FightResult.VICTORY);
		lVisitor.onResult(getFightFromLW(15594447), FightResult.DEFEAT);
		lVisitor.onResult(getFightFromLW(15592522), FightResult.DRAW);
		
		lVisitor.onBeforeStat();
		
		GardenStatsWrapper lStat = new GardenStatsWrapper(EntityType.LEEK, lLeekEntity);
		lStat.setFinalTalent(2045);
		lStat.setTotalFight(10);
		lStat.setDraws(4);
		lStat.incDefeats();
		lStat.setVictories(5);
		lVisitor.onStat(lStat);
		
		lStat = new GardenStatsWrapper(EntityType.FARMER, lFarmerEntity);
		lStat.setFinalTalent(1966);
		lStat.setTotalFight(10);
		lStat.setDraws(1);
		lStat.setVictories(4);
		lStat.incDefeats().incDefeats().incDefeats();
		lVisitor.onStat(lStat);
		
		lVisitor.onMessage(new MessageWrapper(lFarmerEntity, "test fr", "test en"));
		lVisitor.onMessage(new MessageWrapper(lLeekEntity, "test fr", "test en"));
		lVisitor.onMessage(new MessageWrapper(lFarmerEntity, "test 3 fr", "test 3 en"));
		lVisitor.onMessage(new MessageWrapper("test 4 fr", "test 4 en"));
		
		lVisitor.onEnd();
		lVisitor.generate();
		System.out.println(output.getAbsolutePath() + " generated.");
		LWUtils.openFileInDefaultBrowser(output);
	}
	
	private static Fight getFightFromLW(long pId) throws LWException {
		Fight lFight = FightBuilder.fromId(pId);
		String str = LWUtils.fightToString(lFight);
		System.out.println(str);
		return lFight;
	}
	
	@Test
	public void testGetFightFromLW() throws LWException {
		getFightFromLW(15594444);
	}
	
	@Test
	public void testFormat() {
		int initialTalent = 2000;
		int finalTalent = 2035;
		int diffTalent = finalTalent - initialTalent;
		
		String lStr = ((diffTalent > 0 ? "+" : "") + diffTalent + String.format("(%d -> %d)", initialTalent, finalTalent));
		System.out.println(lStr);
		
		initialTalent = 2000;
		finalTalent = 1966;
		diffTalent = finalTalent - initialTalent;
		lStr = String.format("%+d (%d -> %d)", diffTalent, initialTalent, finalTalent);
		System.out.println(lStr);
	}
}
