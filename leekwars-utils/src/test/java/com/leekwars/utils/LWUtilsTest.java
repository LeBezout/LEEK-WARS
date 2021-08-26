package com.leekwars.utils;

import java.util.Calendar;

import com.google.gson.JsonSyntaxException;

import com.leekwars.utils.enums.FightResult;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.model.Fight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class LWUtilsTest {

	@Test
	void testLongToDate() {
		//System.out.println(System.currentTimeMillis());
		System.out.println(LWUtils.longToDate(System.currentTimeMillis() / 1000));
		System.out.println(LWUtils.longToDate(1434816754));
	}
	@Test
	void testLongToCalendar() {
//		System.out.println(System.currentTimeMillis());
//		System.out.println(System.currentTimeMillis() - 1410895414);
		Calendar lCal = LWUtils.longToCalendar(System.currentTimeMillis() / 1000);
		System.out.println(LWUtils.formatCalendar(lCal));

		lCal = LWUtils.longToCalendar(1410895414);
		System.out.println(LWUtils.formatCalendar(lCal));
	}

	@Test
	void testAcceptTalent() {
		System.out.println(LWUtils.acceptTalent(2462, 2100, 20));
		System.out.println(LWUtils.acceptTalent(2462, 2666, 20));
		System.out.println(LWUtils.acceptTalent(2462, 3005, 20));
	}

	@Test
    void test_formatJsonString_OK() {
       String json =  LWUtils.formatJsonString("{\"A\": \"B\"}");
       System.out.println(json);
    }
    @Test
    public void test_formatJsonString_Error() {
        Assertions.assertThrows(JsonSyntaxException.class,
            () -> LWUtils.formatJsonString("}non_json_string{")
        );
    }

	@Test
    @Disabled
	void testIsFarmer1() throws LWException {
		DefaultLeekWarsConnector lConnector = new DefaultLeekWarsConnector("TODO", "TODO");
		lConnector.connect();

		long lID = 15585179;
		Fight lFight = lConnector.getFight(lID);
		boolean result = LWUtils.isFarmer1(lConnector.getFarmer(), lFight);
		System.out.println("Combat " + lID + " Farmer1 : " + result + ", Result = " + LWUtils.getFightResult(lConnector.getFarmer(), lFight));
	}

	@Test
	@Disabled
	void testGetFightResult_Team() throws LWException {
		DefaultLeekWarsConnector lConnector = new DefaultLeekWarsConnector("TODO", "TODO");
		lConnector.connect();
		final long lFightId = 16311083;
		Fight lFight = lConnector.getFight(lFightId);
		FightResult lResult = LWUtils.getFightResult(lConnector.getFarmer(), lFight);
		System.out.println(lResult);
	}
}
