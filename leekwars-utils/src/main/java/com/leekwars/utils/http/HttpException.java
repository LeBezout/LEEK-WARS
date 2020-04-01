package com.leekwars.utils.http;

import com.leekwars.utils.model.ErrorResponse;

/**
 * Exception suite à un appel HTTP de type 4xx ou 5xx
 * @author Bezout
 */
public class HttpException extends Exception {
    private static final long serialVersionUID = 1L;
    private final ErrorResponse errorResponse;
    private final int statusCode;
    private final String statusMessage;

    /** Constructeur 1
     * @param pMessage message d'erreur
     * @param pStatusCode status HTTP (ex: 400)
     * @param pStatusMsg message de status (ex: Bad Request)
     */
    public HttpException(final String pMessage, final int pStatusCode, final String pStatusMsg) {
        super(pMessage);
        errorResponse = null;
        statusCode = pStatusCode;
        statusMessage = pStatusMsg;
    }

    /** Constructeur 2
     * @param pResponse reponse JSON
     * @param pStatusCode status HTTP (ex: 400)
     * @param pStatusMsg message de status (ex: Bad Request)
     */
    public HttpException(final ErrorResponse pResponse, final int pStatusCode, final String pStatusMsg) {
        super(pResponse.getError());
        errorResponse = pResponse;
        statusCode = pStatusCode;
        statusMessage = pStatusMsg;
    }

    /**
     * Message d'erreur retourné par l'API ou le message par défaut si vide.
     * @param pDefault le message par défaut
     * @return le message d'erreur
     */
    public String getErrorMessage(final String pDefault) {
        if (errorResponse == null || errorResponse.getError() == null || errorResponse.getError().isEmpty()) {
            return pDefault == null ? statusMessage : pDefault;
        }
        return errorResponse.getError();
    }

    @Override
    public String toString() {
        return String.format("[%d][%s][%s]", statusCode, statusMessage, errorResponse);
    }
}
