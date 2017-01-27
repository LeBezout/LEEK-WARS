package com.leekwars.utils.fastgarden;

import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.*;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPasswordField;

import com.leekwars.utils.DefaultLeekWarsConnector;
import com.leekwars.utils.fastgarden.impl.HtmlReportFastGardenVisitor;

public class Main {
	private static final File HTML_TEMPLATE_FILE = new File("src/main/resources/report_template.html");
	private static final String TIMESTAMP = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());

	public static void main(String[] args) throws Exception {
		DefaultLeekWarsConnector connector = buildConnector();
		System.setProperty("javax.net.ssl.keyStore", "src/main/security/lw.jks");
		System.setProperty("javax.net.ssl.trustStore", "src/main/security/jssecacerts");

		final File output = new File("target/reports", TIMESTAMP + "_TODO.html");
		HtmlReportFastGardenVisitor lReport = new HtmlReportFastGardenVisitor(HTML_TEMPLATE_FILE, output);
		UltraFastGarden.setParams(configuration());
		UltraFastGarden.forFarmer(connector, lReport);
		lReport.generate();
		System.out.println("Report written to "+output.getAbsolutePath());
		Desktop.getDesktop().open(output);
	}

	private static FastGardenParam configuration() {
		FastGardenParam params = new FastGardenParam();
		params.setMaxFarmerAttacks(3);
		params.setWaitTimeToGetResults(20);
		params.setMaxRetryForFightResult(30);
		params.setMaxStartFights(40);
		return params;
	}

	private static DefaultLeekWarsConnector buildConnector() {
		String login = showInputDialog(null, "Enter your LeekWars login");

		JPasswordField passwordField = new JPasswordField();
		if (showConfirmDialog(null, passwordField, "Enter your LeekWars password for " + login, OK_OPTION) != OK_OPTION)
			System.exit(1);

		DefaultLeekWarsConnector connector = new DefaultLeekWarsConnector(login,
				String.valueOf(String.valueOf(passwordField.getPassword())));
		return connector;
	}
	
	
}
