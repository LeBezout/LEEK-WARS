package com.leekwars.utils.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.leekwars.utils.LWConst;
import org.apache.log4j.Logger;

import com.leekwars.utils.LWUtils;
import com.leekwars.utils.exceptions.LWException;

/**
 * Outillage pour appel de l'API LW en HTTP
 * @author Bezout
 */
public final class HttpUtils {
	private static final Logger LOGGER = Logger.getLogger(HttpUtils.class.getName());
	/** Timeout de connexion en secondes */
	private static final int HTTP_CONNECT_TIMEOUT = 10;
	/** Timeout de lecture de réponse en secondes */
	private static final int HTTP_READ_TIMEOUT = 20;
	/** Valeur du header Http Accept-Language */
	private static final String HTTP_ACCEPT_LANGUAGE = "fr,fr-FR";
	/** Taille initiale du buffer de lecture de la réponse Http */
	private static final int HTTP_READ_BUFFER_CAPACITY = 1024;

	private HttpUtils() {}
	
	//TODO peut être lever des exceptions agnostiques ! (HttpException)
	
	/**
	 * Encode un paramètre ou élément de path d'URL
	 * @param pValue
	 * @return encoded value
	 * @throws LWException
	 */
	public static String encodeUrlParam(final String pValue) throws LWException {
		try {
			return java.net.URLEncoder.encode(pValue, LWConst.DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			 throw new LWException(e);
		}
	}
	
	/** ------- GET DATA ---------
	 * @param pURL url to call
	 * @param pPHPSESSID cookie value
     * @param pToken token JWT
	 * @return response wrapper
	 * @throws LWException
	 */
	public static HttpResponseWrapper get(final String pURL, final String pPHPSESSID, final String pToken) throws LWException {
		final HttpURLConnection lConnection = getHttpConnection(pURL, "GET", pPHPSESSID,  pToken);
		int lCode = 0;
		try {
			lConnection.connect();
			LOGGER.debug("Connected at " + pURL);
			lCode = lConnection.getResponseCode();
		} catch (IOException e) {
			throw new LWException(e);
		}
		LOGGER.debug("HEADERS="+lConnection.getHeaderFields());
		final String lResponse = readHttpResponse(lConnection);
		LOGGER.debug("Receiving " + lResponse);
		return new HttpResponseWrapper(pURL, lCode, lConnection.getHeaderFields(), lResponse.trim());
	}
	
	/** ------- POST DATA ---------
	 * @param pURL url to call
	 * @param pData data to send
	 * @param pPHPSESSID cookie value
     * @param pToken token JWT
	 * @return response wrapper
	 * @throws LWException
	 */
	public static HttpResponseWrapper post(final String pURL, final String pData, final String pPHPSESSID, final String pToken) throws LWException {
		final HttpURLConnection lConnection = getHttpConnection(pURL, "POST", pPHPSESSID, pToken);
		int lCode;
		if (pData != null && pData.trim().length() > 0) {
			try {
				lConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				final byte[] lBytes = pData.getBytes(LWConst.DEFAULT_ENCODING);
				lConnection.setRequestProperty("Content-Encoding", LWConst.DEFAULT_ENCODING);
				lConnection.setRequestProperty("Content-Length", String.valueOf(lBytes.length));
				LOGGER.debug("Content-Length : " + lBytes.length);
				lConnection.connect();
				LOGGER.debug("Connected at " + pURL);
				LOGGER.debug("Sending " + pData);
				lConnection.getOutputStream().write(lBytes);
				lConnection.getOutputStream().flush();
				lCode = lConnection.getResponseCode();
			} catch (IOException e) {
				throw new LWException(e);
			}
		} else {
			try {
				lConnection.connect();
				LOGGER.debug("Connected at " + pURL);
				lCode = lConnection.getResponseCode();
			} catch (IOException e) {
				throw new LWException(e);
			}
		}
		LOGGER.debug("HEADERS="+lConnection.getHeaderFields());
		final String lResponse = readHttpResponse(lConnection);
		// exemple d'erreur : {"success":false,"error":"missing_parameter","parameter":"login","module":"farmer","function":"login-token"}
		LOGGER.debug("Receiving " + lResponse);
		return new HttpResponseWrapper(pURL, lCode, lConnection.getHeaderFields(), lResponse.trim());
	}

	/**
	 * Initialisation d'une connexion HTTP de type HttpURLConnection
	 * @param pURl url to call
	 * @param pMethod (GET/POST)
	 * @param pPHPSESSID valeur du cookie ou null
     * @param pToken token JWT
	 * @return HttpURLConnection
	 * @throws LWException
	 */
	public static HttpURLConnection getHttpConnection(final String pURl, final String pMethod, final String pPHPSESSID, final String pToken) throws LWException {
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection)new URL(pURl).openConnection();
			connection.setRequestMethod(pMethod);
			connection.setConnectTimeout(1000 * HTTP_CONNECT_TIMEOUT);
			connection.setReadTimeout(1000 * HTTP_READ_TIMEOUT);
			connection.setUseCaches(false);
			connection.setAllowUserInteraction(false);
			connection.setInstanceFollowRedirects(true);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("Accept-Language", HTTP_ACCEPT_LANGUAGE);
			if (pPHPSESSID != null) {
				connection.addRequestProperty("Cookie", "PHPSESSID=" + pPHPSESSID);// + "; path=/; domain=leekwars.com; HttpOnly");
			}
            if (pToken != null) {
                connection.addRequestProperty("Authorization", "Bearer " + pToken);
            }
			return connection;
		} catch (IOException e) {
			throw new LWException(e);
		}
	}
	
	/**
	 * Lecture du flux de réponse HTTP
	 * @param connection
	 * @return réponse texte
	 * @throws LWException
	 */
	public static String readHttpResponse(final HttpURLConnection connection) throws LWException {
		try {
			final int retCode = connection.getResponseCode();
			LOGGER.debug("HTTP RETURN CODE " + retCode);
			if (retCode >= HttpURLConnection.HTTP_OK && retCode < HttpURLConnection.HTTP_MULT_CHOICE) {
				StringBuilder lResponseStr = new StringBuilder(HTTP_READ_BUFFER_CAPACITY);
				final Scanner lScanner = new Scanner(connection.getInputStream(), LWUtils.defaultIfNull(connection.getContentEncoding(), LWConst.DEFAULT_ENCODING));
				try {
					while (lScanner.hasNextLine()) {
						lResponseStr.append(lScanner.nextLine());
					}
					return lResponseStr.toString();
				} finally {
					lScanner.close();
				}
			} else {
				throw new LWException("HTTP ERROR : " + retCode); // on ne lit même pas l'errorStream !
			}
		} catch (IOException e) {
			throw new LWException(e);
		}
	}
}
