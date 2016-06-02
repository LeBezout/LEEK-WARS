package com.leekwars.utils;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by ble on 02/06/16.
 */
public class PropertiesLeekWarsConnectorTest {

    @Test
    public void encodeB64() {
        Base64 b64 = new Base64();
        String encoded = b64.encodeAsString("mot_dé_passe".getBytes());
        System.out.println(encoded);
        System.out.println(new String(b64.decode(encoded)));
    }

    @Test
    public void testRaw() throws IOException {
        PropertiesLeekWarsConnector lConnector = new PropertiesLeekWarsConnector("/connector-RAW.properties");
        System.out.println(lConnector.getUsername());
        System.out.println(lConnector.getPassword());
    }

    @Test
    public void testBase64() throws IOException {
        PropertiesLeekWarsConnector lConnector = new PropertiesLeekWarsConnector("/connector-B64.properties");
        System.out.println(lConnector.getUsername());
        System.out.println(lConnector.getPassword());
    }

    @Test
    public void testDefaultMode() throws IOException {
        PropertiesLeekWarsConnector lConnector = new PropertiesLeekWarsConnector("/connector-DEFAULT.properties");
        System.out.println(lConnector.getUsername());
        System.out.println(lConnector.getPassword());
    }
}
