package com.leekwars.utils.applets;

import com.leekwars.utils.DefaultLeekWarsConnector;
import com.leekwars.utils.exceptions.LWException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.applet.Applet;
import java.awt.*;

/**
 * @author Bezout
 * @version 1.0
 */
public class ControlCenterApplet extends Applet {
    private DefaultLeekWarsConnector mConnector;
    private String mUsername;
    private String mPassword;

    @Override
    public void init() {
        // on passe log4j en mode console et level error
        ConsoleAppender lAppender = new ConsoleAppender(new PatternLayout());
        lAppender.setThreshold(Level.ERROR);
        Logger.getRootLogger().addAppender(lAppender);
        Logger.getRootLogger().setLevel(Level.ERROR);
        // GUI
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());
        setSize(800, 600);
        final Label lTitle = new Label("Centre de contrôle LWUtils");
        lTitle.setAlignment(Label.CENTER);
        add(lTitle, BorderLayout.NORTH);
        Panel lPanel = new Panel();


        add(lPanel, BorderLayout.CENTER);
        lPanel.add(new Label("Login : "));
        //add(new TextField(25));
        add(new Label("Password : "));
        //add(new TextField(25));

        add(new Label("(c) Bezout"), BorderLayout.SOUTH);
    }
    @Override
    public void destroy() {
        mConnector = null;
    }

    private void login() throws LWException {
        DefaultLeekWarsConnector lConnector = new DefaultLeekWarsConnector(mUsername, mPassword);
        lConnector.connect();
        // OK
        mConnector = lConnector;
    }
    private boolean isConnected() {
        return mConnector != null;
    }
}
