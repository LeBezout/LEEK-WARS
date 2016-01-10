package com.leekwars.utils.fastgarden.impl;

import java.io.File;

import org.junit.Test;

import com.leekwars.utils.LWUtils;
import com.leekwars.utils.enums.EntityType;
import com.leekwars.utils.enums.FightResult;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.Fight;
import com.leekwars.utils.test.FightBuilder;
import com.leekwars.utils.wrappers.GardenStatsWrapper;

public class HtmlReportFastGardenVisitorTest {

	@Test
	@org.junit.Ignore
	public void testGenerate() throws LWException {
		File output = new File("target/generated_junit.html");
		File template = new File("src/main/resources/report_template.html");
		HtmlReportFastGardenVisitor lVisitor = new HtmlReportFastGardenVisitor(template, output);
		Farmer farmer = new Farmer();
		farmer.setId(16748);
		farmer.setName("Bezout_JUNIT");
		lVisitor.onInit(farmer);
		
		lVisitor.onEntityChange(EntityType.LEEK, "CapitaineFlirt_JUNIT");
		lVisitor.onResult(getFightFromLW(15592973), FightResult.DRAW);
		lVisitor.onResult(getFightFromLW(15566926), FightResult.VICTORY);
		lVisitor.onResult(getFightFromLW(15580334), FightResult.DEFEAT);
		lVisitor.onResult(getFightFromLW(15594489), FightResult.UNKNOWN);
		
		lVisitor.onEntityChange(EntityType.FARMER, "Bezout_JUNIT");
		lVisitor.onResult(getFightFromLW(15594446), FightResult.VICTORY);
		lVisitor.onResult(getFightFromLW(15594447), FightResult.DEFEAT);
		lVisitor.onResult(getFightFromLW(15592522), FightResult.DRAW);
		
		lVisitor.onBeforeStat();
		GardenStatsWrapper lStat = new GardenStatsWrapper("CapitaineFlirt_JUNIT");
		lStat.setTotalFight(10);
		lStat.setDraws(4);
		lStat.incDefeats();
		lStat.setVictories(5);
		lVisitor.onStat(lStat);
		lStat = new GardenStatsWrapper("Bezout_JUNIT");
		lStat.setTotalFight(10);
		lStat.setDraws(1);
		lStat.setVictories(4);
		lStat.incDefeats().incDefeats().incDefeats();
		lVisitor.onStat(lStat);
		
		lVisitor.onWarning("CapitaineFlirt_JUNIT", "test 1");
		lVisitor.onWarning(null, "test 2");
		lVisitor.onWarning("Bezout_JUNIT", "test 3");
		
		lVisitor.onEnd();
		lVisitor.generate();
	}
	
	private static Fight getFightFromLW(long pId) throws LWException {
		Fight lFight = FightBuilder.fromId(pId);
		String str = LWUtils.fightToString(lFight);
		System.out.println(str);
		return lFight;
	}
	
	@Test
	@org.junit.Ignore
	public void testGetFightFromLW() throws LWException {
		getFightFromLW(15594444);
	}
}
