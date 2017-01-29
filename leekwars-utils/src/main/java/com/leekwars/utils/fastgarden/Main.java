package com.leekwars.utils.fastgarden;

import com.leekwars.utils.DefaultLeekWarsConnector;
import com.leekwars.utils.LWUtils;
import com.leekwars.utils.fastgarden.impl.HtmlReportFastGardenVisitor;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javax.swing.JOptionPane.OK_OPTION;

/**
 * Standalone sample main app
 * @author tyrcho
 * @version 1.0
 */
public class Main {
	private static final String TIMESTAMP = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());

	/**
	 *
	 * @param args empty
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		File lw = extractFromClassPath("lw.jks");
		File jssecacerts = extractFromClassPath("jssecacerts");
		File htmlTemplate = extractFromClassPath("report_template.html");
		System.setProperty("javax.net.ssl.keyStore", lw.getAbsolutePath());
		System.setProperty("javax.net.ssl.trustStore", jssecacerts.getAbsolutePath());

		DefaultLeekWarsConnector connector = buildConnector();
		File output = File.createTempFile("report" + connector.getUsername() + TIMESTAMP, ".html");
		HtmlReportFastGardenVisitor lReport = new HtmlReportFastGardenVisitor(htmlTemplate, output);
		UltraFastGarden.setParams(configuration());
		UltraFastGarden.forAll(connector, lReport);
		lReport.generate();
		System.out.println("Report written to " + output.getAbsolutePath());
		LWUtils.openFileInDefaultBrowser(output);
	}

	private static FastGardenParam configuration() {
		FastGardenParam params = new FastGardenParam();
		params.setMaxFarmerAttacks(3);
		params.setWaitTimeToGetResults(20);
		params.setMaxRetryForFightResult(30);
		params.setMaxStartFights(10);
		return params;
	}

	private static DefaultLeekWarsConnector buildConnector() {
		String login = JOptionPane.showInputDialog(null, "Enter your LeekWars login");

		JPasswordField passwordField = new JPasswordField();
		if (JOptionPane.showConfirmDialog(null, passwordField, "Enter your LeekWars password for " + login, OK_OPTION) != OK_OPTION) {
			System.exit(1);
		}

		return new DefaultLeekWarsConnector(login, String.valueOf(passwordField.getPassword()));
	}

	private static File extractFromClassPath(String name) throws IOException {
		File tempFile = File.createTempFile(name, name);
		try (
				InputStream inputStream = ClassLoader.getSystemResourceAsStream(name);
				FileOutputStream outputStream = new FileOutputStream(tempFile)) {
			int read;
			byte[] buffer = new byte[1024];
			while ((read = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, read);
			}
			outputStream.close();
			return tempFile;
		}
	}

}
