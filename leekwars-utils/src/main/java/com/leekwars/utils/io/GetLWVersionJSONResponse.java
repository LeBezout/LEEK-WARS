package com.leekwars.utils.io;

import com.leekwars.utils.model.SimpleJSONResponse;

/**
 * Informations sur la version courante
 * @since 1.4.1
 * @author Bezout
 */
public class GetLWVersionJSONResponse extends SimpleJSONResponse {
    private int version;

    /**
     * @return the version
     */
    public int getVersion() {
        return version;
    }
    /**
     * @param pVersion the version to set
     */
    public void setVersion(int pVersion) {
        version = pVersion;
    }
}
