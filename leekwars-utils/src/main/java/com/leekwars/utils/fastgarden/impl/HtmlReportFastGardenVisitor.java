package com.leekwars.utils.fastgarden.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.leekwars.utils.LWUtils;
import com.leekwars.utils.enums.EntityType;
import com.leekwars.utils.enums.FightResult;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.fastgarden.FastGardenVisitor;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.Fight;
import com.leekwars.utils.wrappers.GardenStatsWrapper;

/**
 * Implementation de FastGardenVisitor qui génère un rapport HTML complet.
 * @author Bezout
 */
public class HtmlReportFastGardenVisitor implements FastGardenVisitor {
	private static final String VERSION = "1.0";
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	// PARAMETRAGE HTML
	private static final Map<String, String> MAP_ICONS = initIconsMap();
	private static final int TABLE_PERCENT_WIDTH = 60;
	// MEMBRES
	final File mTemplateFile;
	final File mOutputFile;
	
	private boolean canGenerate;
	private String mTitle;
	private StringBuilder mBody;
	private Farmer mFarmer;
	private List<String> mWarnings = new LinkedList<>();
	
	// membres pour la gestion interne
	private boolean tableOpened;
	private int mCount;
	
	/**
	 * Constructeur avec files
	 * @param pTemplate le template HTML
	 * @param pOutput le fichier à générer
	 */
	public HtmlReportFastGardenVisitor(final File pTemplate, final File pOutput) {
		mTemplateFile = pTemplate;
		mOutputFile = pOutput;
	}
	
	private HtmlReportFastGardenVisitor addBodyLine(final String pLine) {
		mBody.append(pLine).append(LINE_SEPARATOR);
		return this;
	}
	private static String toString(final EntityType pEntityType) {
		switch (pEntityType) {
			case BULB :
				return "bulbe";
			case FARMER :
				return "éleveur";
			case LEEK :
				return "poireau";
			default:
				return "?";
		}
	}
	private static String toCSS(final FightResult pResult) {
		switch (pResult) {
			case DEFEAT : return "defeat";
			case DRAW : return "draw";
			case UNKNOWN : return "unknown";
			case VICTORY : return "win";
			default : return "";
		}
	}
	private static Map<String, String> initIconsMap() {
		Map<String, String> lMap = new HashMap<>();
		lMap.put("perfect", "<img src=\"http://leekwars.com/static/image/fight_flag/perfect\" width=\"%dpx\" height=\"%dpx\"/>");
		lMap.put("static", "<img src=\"http://leekwars.com/static/image/fight_flag/static\" width=\"%dpx\" height=\"%dpx\"/>");
		lMap.put("dead", "<img src=\"http://leekwars.com/static/image/cross.png\" width=\"%dpx\" height=\"%dpx\"/>");
		lMap.put("garden", "<img src=\"http://leekwars.com/static/image/icon/garden.png\" width=\"%dpx\" height=\"%dpx\"/>");
		lMap.put("fight", "<img src=\"http://leekwars.com/static/image/notif/fight.png\" width=\"%dpx\" height=\"%dpx\"/>");
		lMap.put("ranking", "<img src=\"http://leekwars.com/static/image/icon/ranking.png\" width=\"%dpx\" height=\"%dpx\"/>");
		lMap.put("gearing", "<img src=\"http://leekwars.com/static/image/gearing_small_white.png\" width=\"%dpx\" height=\"%dpx\"/>");
		return Collections.unmodifiableMap(lMap);
	}
	private static String getIcon(final String pKey, int width, int height) {
		String value = MAP_ICONS.get(pKey);
		return value == null ? pKey : String.format(value, width, height);
	}

	@Override
	public void onInit(Farmer pFarmer) {
		canGenerate = false;
		tableOpened = false;
		mTitle = "Rapport FastGarden pour " + pFarmer.getName();
		mBody = new StringBuilder(15 * 1024); // 15kio
		mFarmer = pFarmer;
		addBodyLine("<h1>Rapport de combats \"Fast Garden\"</h1>");
		addBodyLine("<br/>");
		addBodyLine("<div class=\"container\">");
	}

	@Override
	public void onWarning(String pEntityName, String pMessage) {
		mWarnings.add(pEntityName == null ? "GENERAL : " + pMessage : pEntityName + " : " + pMessage);
	}
	
	@Override
	public void onEntityChange(EntityType pEntityType, String pEntityName) {
		mCount = 0;
		if (tableOpened) {
			tableOpened = false;
			addBodyLine("</table>"); 
		}
		addBodyLine("<br/>");
		addBodyLine(String.format("<h2>"+getIcon("garden", 22, 22)+" Combats %s %s</h2>", 
				toString(pEntityType),
				pEntityName
				));
		addBodyLine(String.format("<table style=\"width:%d%%\">", TABLE_PERCENT_WIDTH));
		addBodyLine("\t<tr class=\"header\">");
		addBodyLine("\t\t<th>Numéro</th><th>Id</th><th>Visualisation</th><th>Flag</th><th>Contre</th><th>Nombre de tours</th>");
		addBodyLine("\t</tr>");
		tableOpened = true;
	}

	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onResult(com.leekwars.utils.model.Fight, com.leekwars.utils.enums.FightResult)
	 */
	@Override
	public void onResult(final Fight pFight, final FightResult pResult) {
		mCount++;
		addBodyLine(String.format("\t<tr class=\"%s\">", toCSS(pResult)));
		addBodyLine(String.format("\t\t<td>%d</td>", mCount));
		addBodyLine(String.format("\t\t<td>%d</td>", pFight.getId()));
		addBodyLine(String.format("\t\t<td><a href=\"http://leekwars.com/fight/%d\">Combat</a> | <a href=\"http://leekwars.com/report/%d\">Rapport</a></td>", pFight.getId(), pFight.getId()));
		mBody.append("\t\t<td>");
		final boolean isFamer1 = LWUtils.isFarmer1(mFarmer, pFight);
		if (pResult != FightResult.UNKNOWN) {
			String[] lFlags = isFamer1 ? pFight.getReport().getFlags1() : pFight.getReport().getFlags2();
			if (lFlags != null && lFlags.length > 0) {
				for (String lFlag : lFlags) {
					mBody.append(getIcon(lFlag, 20, 20));
				}
			}
	//		if (pResult == FightResult.DEFEAT) {
	//			mBody.append(getIcon("dead"));
	//		}
			mBody.append(" | ");
			lFlags = isFamer1 ? pFight.getReport().getFlags2() : pFight.getReport().getFlags1();
			if (lFlags != null && lFlags.length > 0) {
				for (String lFlag : lFlags) {
					mBody.append(getIcon(lFlag, 20, 20));
				}
			}
		}
		addBodyLine("</td>");
		final String lEnemyName = LWUtils.getTargetEnemyName(mFarmer, pFight);
		addBodyLine(String.format("\t\t<td>%s</td>", lEnemyName));
		String lDuration = pFight.getReport() == null ? "?" : String.valueOf(pFight.getReport().getDuration());
		addBodyLine(String.format("\t\t<td>%s</td>", lDuration));
		addBodyLine("\t</tr>");
	}

	@Override
	public void onBeforeStat() {
		if (tableOpened) {
			tableOpened = false;
			addBodyLine("</table>"); 
		}
		addBodyLine("<br/>");
		addBodyLine("<h2>"+getIcon("ranking", 22, 22)+" Statistiques</h2>");
		addBodyLine(String.format("<table style=\"width:%d%%\">", TABLE_PERCENT_WIDTH));
		addBodyLine("\t<tr class=\"header\">");
		addBodyLine("\t\t<th>Entité</th><th>Taux de réussite</th><th>Ratio</th><th>Nb combats</th><th>Nb victoires</th><th>Nb nuls</th><th>Nb défaites</th>");
		addBodyLine("\t</tr>");
	}

	@Override
	public void onStat(GardenStatsWrapper pStat) {
		addBodyLine("\t<tr>");
		addBodyLine(String.format("\t\t<td>%s</td><td>%d%%</td><td>%.2f</td><td>%d</td><td>%d</td><td>%d</td><td>%d</td>",
				pStat.getEntityName(),
				pStat.getVictoriesPercent(),
				pStat.getRatio(),
				pStat.getTotalFight(),
				pStat.getVictories(),
				pStat.getDraws(),
				pStat.getDefeats()
				));
		addBodyLine("\t</tr>");
	}

	@Override
	public void onEnd() {
		addBodyLine("</table>"); // table des stats
		canGenerate = true;
		addBodyLine("<br/>");
		if (!mWarnings.isEmpty()) {
			addBodyLine("<h2>"+getIcon("gearing", 22, 22)+" Messages</h2>");
			addBodyLine(String.format("<table style=\"width:%d%%\">", TABLE_PERCENT_WIDTH));
			addBodyLine("\t<tr class=\"header\">");
			addBodyLine("\t\t<th width=\"10%\">Numéro</th><th width=\"20%\">Type</th><th width=\"70%\" style=\"text-align:left\">Message</th>");
			addBodyLine("\t</tr>");
			int num = 1;
			for (String lMsg : mWarnings) {
				addBodyLine("\t<tr>");
				addBodyLine(String.format("\t\t<td>%d</td><td>%s</td><td style=\"text-align:left\">%s</td>", num, "WARNING", lMsg));
				addBodyLine("\t</tr>");
				num++;
			}
			addBodyLine("</table>");
		}
		addBodyLine("<br/>");
		addBodyLine("</div>");
		addBodyLine("<div class=\"copyright\">"
				+ getIcon("fight", 12, 12)
				+ " Version " + VERSION 
				+ " - <a href=\"http://leekwars.com/farmer/16748\">Bezout</a> (c) 2016 - Généré en Java depuis <a href=\"http://leekwars.com/help/api\">l'API Leek Wars</a> "
				+ getIcon("fight", 12, 12)
				+ "</div>");
	}

	/**
	 * Génère le rapport à partir de toute les données collectées
	 * @throws LWException en cas d'erreur
	 */
	public void generate() throws LWException {
		if (canGenerate) {
			try {
				// Load template
				String lTemplateHTML = new String(Files.readAllBytes(mTemplateFile.toPath()), "UTF-8");
				// replace token TITLE
				lTemplateHTML = lTemplateHTML.replace("{TITLE}", mTitle);
				// replace token BODY
				lTemplateHTML = lTemplateHTML.replace("{BODY}", mBody.toString());
				System.out.println("---DEBUG-- Taille body : " + mBody.length());
				// write report
				mOutputFile.getParentFile().mkdirs();
				FileOutputStream fout = new FileOutputStream(mOutputFile);
				try {
					fout.write(lTemplateHTML.getBytes("UTF-8"));
				} finally {
					fout.close();
				}
			} catch (IOException ioe) {
				throw new LWException("Cannot generate report, I/O error : " + ioe.getMessage(), ioe);
			}
		} else {
			throw new LWException("Cannot generate report, not enough data");
		}
	}
}
