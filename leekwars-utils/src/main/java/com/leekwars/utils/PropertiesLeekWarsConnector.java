package com.leekwars.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe permettant de définir Login et MDP dans un fichier properties
 * Permet aussi de stocker la version attendue de LW et l'indicateur d'activation des traces des flux.
 * @author Bezout
 */
public class PropertiesLeekWarsConnector extends AbstractLeekWarsConnector {

    private static final String MODE_RAW = "RAW";
    private static final String MODE_B64 = "BASE64";
    private int expectedVersion;

    /**
     * Constructeur
     * @param pProps properties
     */
    public PropertiesLeekWarsConnector(final Properties pProps) {
        internalLoad(pProps);
    }

    /**
     * Constructeur depuis une resource du classpath
     * @param pResourceName
     * @throws IOException
     */
    public PropertiesLeekWarsConnector(final String pResourceName) throws IOException {
        final Properties lProps = new Properties();
        lProps.load(getClass().getResourceAsStream(pResourceName));
        internalLoad(lProps);
    }

    /**
     * Constructeur depuis un fichier
     * @param pFile
     * @throws IOException
     */
    public PropertiesLeekWarsConnector(final File pFile) throws IOException {
        final Properties lProps = new Properties();
        lProps.load(new FileInputStream(pFile));
        internalLoad(lProps);
    }

    private void internalLoad(final Properties pProps) {
        setUsername(pProps.getProperty("lw.login"));
        // suivant le mode
        String mode = pProps.getProperty("lw.mode");
        String password = pProps.getProperty("lw.password");
        String lVersion = pProps.getProperty("lw.expectedVersion");
        String lTrace = pProps.getProperty("lw.trace");
        if (lVersion != null && !lVersion.isEmpty()) {
            expectedVersion = Integer.parseInt(lVersion);
        }
        if (mode == null || mode.isEmpty()) {
            mode = MODE_RAW;
        }
        if (MODE_RAW.equals(mode)) {
            setPassword(password);
        } else if (MODE_B64.equals(mode)) {
            // décodage base 64
            // JAVA8 : (java.util.)Base64.getDecoder().decode(password)
            String lPwd = new String(new org.apache.commons.codec.binary.Base64().decode(password));
            setPassword(lPwd);
        }
        // since 1.6
        if (Boolean.parseBoolean(lTrace)) {
            setTrace(true);
        }
    }

    /**
     * @return expectedVersion
     */
    public int getExpectedVersion() {
        return expectedVersion;
    }
}
