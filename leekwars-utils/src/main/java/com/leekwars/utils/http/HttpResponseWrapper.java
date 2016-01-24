package com.leekwars.utils.http;

import java.util.List;
import java.util.Map;

/**
 * Infos d'une réponse HTTP
 * @author Bezout
 */
public class HttpResponseWrapper {
	private String urlCalled;
	private int retCode;
	private String responseText;
	private Map<String, List<String>> headers;
	
	/** Constructeur
	 * @param pUrl url appelée (informatif)
	 * @param pCode
	 * @param pHeaders
	 * @param pResponse
	 */
	public HttpResponseWrapper(final String pUrl, final int pCode, final Map<String,List<String>> pHeaders, final String pResponse) {
		urlCalled = pUrl;
		retCode = pCode;
		responseText = pResponse;
		headers = pHeaders;
	}
	/** Constructeur
	 * @param pCode
	 * @param pHeaders
	 * @param pResponse
	 */
	public HttpResponseWrapper(final int pCode, final Map<String,List<String>> pHeaders, final String pResponse) {
		this(null, pCode, pHeaders, pResponse);
	}
	
	/**
	 * Retourne la valeur du cookie désiré (sans path, expires, ..)
	 * @param pCookieName
	 * @return cookie value
	 */
	public String getCookie(final String pCookieName) {
		List<String> lCookies = headers.get("Set-Cookie");
		if (lCookies == null || lCookies.isEmpty()) {
			return null;
		}
		for (String lCookie : lCookies) {
			if (lCookie.startsWith(pCookieName)) {
				return lCookie.split("=")[1].split(";")[0];
			}
		}
		return null;
	}
	
	/**
	 * @return encoding de la réponse
	 */
	public String getEncoding() {
		List<String> lHeader = headers.get("Content-Encoding");
		if (lHeader == null || lHeader.isEmpty()) {
			return null;
		}
		return lHeader.get(0);
	}

	/**
	 * @return the retCode
	 */
	public int getRetCode() {
		return retCode;
	}
	/**
	 * @param pRetCode the retCode to set
	 */
	public void setRetCode(int pRetCode) {
		retCode = pRetCode;
	}

	/**
	 * @return the responseText
	 */
	public String getResponseText() {
		return responseText;
	}
	/**
	 * @param pResponseText the responseText to set
	 */
	public void setResponseText(String pResponseText) {
		responseText = pResponseText;
	}

	/**
	 * @return the headers
	 */
	public Map<String, List<String>> getHeaders() {
		return headers;
	}
	/**
	 * @param pHeaders the headers to set
	 */
	public void setHeaders(Map<String, List<String>> pHeaders) {
		headers = pHeaders;
	}

	/**
	 * @return the urlCalled
	 */
	public String getUrlCalled() {
		return urlCalled;
	}
	/**
	 * @param pUrlCalled the urlCalled to set
	 */
	public void setUrlCalled(String pUrlCalled) {
		urlCalled = pUrlCalled;
	}
}
