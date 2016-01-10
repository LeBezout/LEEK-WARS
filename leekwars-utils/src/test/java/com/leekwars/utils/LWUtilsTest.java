package com.leekwars.utils;

import java.util.Calendar;

import org.junit.Test;

import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.model.Fight;

public class LWUtilsTest {
	
	@Test
	public void testLongToDate() {
		//System.out.println(System.currentTimeMillis());
		System.out.println(LWUtils.longToDate(System.currentTimeMillis() / 1000));
		System.out.println(LWUtils.longToDate(1434816754));
	}
	@Test
	public void testLongToCalendar() {
//		System.out.println(System.currentTimeMillis());
//		System.out.println(System.currentTimeMillis() - 1410895414);
		Calendar lCal = LWUtils.longToCalendar(System.currentTimeMillis() / 1000);
		System.out.println(LWUtils.formatCalendar(lCal));
		
		lCal = LWUtils.longToCalendar(1410895414);
		System.out.println(LWUtils.formatCalendar(lCal));
	}

	@Test
	public void testAcceptTalent() {
		System.out.println(LWUtils.acceptTalent(2462, 2100));
		System.out.println(LWUtils.acceptTalent(2462, 2666));
		System.out.println(LWUtils.acceptTalent(2462, 3005));
	}
	
	@Test
	@org.junit.Ignore
	public void testIsFarmer1() throws LWException {
		DefaultLeekWarsConnector lConnector = new DefaultLeekWarsConnector("TODO", "TODO");
		lConnector.connect();
		
		long lID = 15585179;
		Fight lFight = lConnector.getFight(lID);
		boolean result = LWUtils.isFarmer1(lConnector.getFarmer(), lFight);
		System.out.println("Combat " + lID + " Farmer1 : " + result + ", Result = " + LWUtils.getFightResult(lConnector.getFarmer(), lFight));
	}
}
