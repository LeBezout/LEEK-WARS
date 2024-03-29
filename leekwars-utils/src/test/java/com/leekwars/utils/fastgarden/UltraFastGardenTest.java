package com.leekwars.utils.fastgarden;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.leekwars.utils.DefaultLeekWarsConnector;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.fastgarden.impl.HtmlReportFastGardenVisitor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class UltraFastGardenTest {
	private static final File HTML_TEMPLATE_FILE = new File("src/main/resources/report_template.html");
	private static final String TIMESTAMP = new SimpleDateFormat("yyyyMMdd").format(new Date());

	@Test
	@Disabled
	void testUltraFastGarden() throws LWException {
		final File output = new File("target/reports", TIMESTAMP + "_TODO.html");
		HtmlReportFastGardenVisitor lReport = new HtmlReportFastGardenVisitor(HTML_TEMPLATE_FILE, output);
		UltraFastGarden.forAll(new DefaultLeekWarsConnector("TODO", "TODO"), lReport);
		lReport.generate();
	}

	@Test
    @Disabled
	void testTeamFastGarden() throws LWException {
		final File output = new File("target/reports", TIMESTAMP + "_TEAM.html");
		HtmlReportFastGardenVisitor lReport = new HtmlReportFastGardenVisitor(HTML_TEMPLATE_FILE, output);
		DefaultLeekWarsConnector lConnector = new DefaultLeekWarsConnector("TODO", "TODO");
		//lConnector.setTrace(true);
		UltraFastGarden.forTeam(lConnector, lReport);
		lReport.generate();
	}
}
