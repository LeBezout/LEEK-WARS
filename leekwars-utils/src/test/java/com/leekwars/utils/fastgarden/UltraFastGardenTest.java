package com.leekwars.utils.fastgarden;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.leekwars.utils.DefaultLeekWarsConnector;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.fastgarden.impl.HtmlReportFastGardenVisitor;

public class UltraFastGardenTest {
	private static final File HTML_TEMPLATE_FILE = new File("src/main/resources/report_template.html");
	private static final String TIMESTAMP = new SimpleDateFormat("yyyyMMdd").format(new Date());
	
	@Test
	@org.junit.Ignore
	public void testUltraFastGarden() throws LWException {
		final File output = new File("target/reports", TIMESTAMP + "_TODO.html");
		HtmlReportFastGardenVisitor lReport = new HtmlReportFastGardenVisitor(HTML_TEMPLATE_FILE, output);
		UltraFastGarden.fastGarden(new DefaultLeekWarsConnector("TODO", "TODO"), lReport);
		lReport.generate();
	}
}
