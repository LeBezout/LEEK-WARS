package com.leekwars.utils.wrappers;

import com.leekwars.utils.model.Entity;

/**
 * Information d'un message.<br/>
 * Permet de stocker un message pour un rapport (HTML,  log, ...) éventuel.
 * @author Bezout
 */
public class MessageWrapper {
	/** message de type informatif */
	public static final String MESSAGE_TYPE_INFO = "INFO";
	/** message de type avertissement */
	public static final String MESSAGE_TYPE_WARNING = "WARNING";
	/** message de type erreur */
	public static final String MESSAGE_TYPE_ERROR = "ERROR";
	
	private String mType = MESSAGE_TYPE_WARNING;
	private Entity mEntity;
	private String mMessageFR;
	private String mMessageEN;
	
	/**
	 * Constructeur par défaut
	 */
	public MessageWrapper() {
		super();
	}
	/**
	 * Constructeur avec entité
	 * @param pEntity
	 */
	public MessageWrapper(final Entity pEntity) {
		this();
		mEntity = pEntity;
	}
	/**
	 * Constructeur avec messages
	 * @param pMessageFR
	 * @param pMessageEN
	 */
	public MessageWrapper(final String pMessageFR, final String pMessageEN) {
		this(null, pMessageFR, pMessageEN);
	}
	/**
	 * Constructeur avec entité et messages
	 * @param pMessageFR
	 * @param pMessageEN
	 */
	public MessageWrapper(final Entity pEntity, final String pMessageFR, final String pMessageEN) {
		this();
		mEntity = pEntity;
		mMessageFR = pMessageFR;
		mMessageEN = pMessageEN;
	}
	/**
	 * Constructeur complet
	 * @param pMessageFR
	 * @param pMessageEN
	 */
	public MessageWrapper(final Entity pEntity, final String pType, final String pMessageFR, final String pMessageEN) {
		this();
		mEntity = pEntity;
		mType = pType;
		mMessageFR = pMessageFR;
		mMessageEN = pMessageEN;
	}

	/**
	 * Builder MESSAGE_TYPE_INFO sans entité
	 * @param pMessageFR
	 * @param pMessageEN
	 * @return new instance
	 */
	public static MessageWrapper info(final String pMessageFR, final String pMessageEN) {
		final MessageWrapper lMsg = new MessageWrapper(pMessageFR, pMessageEN);
		lMsg.setType(MESSAGE_TYPE_INFO);
		return lMsg;
	}
	/**
	 * Builder MESSAGE_TYPE_ERROR sans entité
	 * @param pMessageFR
	 * @param pMessageEN
	 * @return new instance
	 */
	public static MessageWrapper error(final String pMessageFR, final String pMessageEN) {
		final MessageWrapper lMsg = new MessageWrapper(pMessageFR, pMessageEN);
		lMsg.setType(MESSAGE_TYPE_ERROR);
		return lMsg;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return mType;
	}
	/**
	 * @param pType the type to set
	 */
	public void setType(String pType) {
		mType = pType;
	}
	/**
	 * @return the entity
	 */
	public Entity getEntity() {
		return mEntity;
	}
	/**
	 * @param pEntity the entity to set
	 */
	public void setEntity(Entity pEntity) {
		mEntity = pEntity;
	}
	/**
	 * @return the messageFR
	 */
	public String getMessageFR() {
		return mMessageFR;
	}
	/**
	 * @param pMessageFR the messageFR to set
	 */
	public void setMessageFR(String pMessageFR) {
		mMessageFR = pMessageFR;
	}
	/**
	 * @return the messageEN
	 */
	public String getMessageEN() {
		return mMessageEN;
	}
	/**
	 * @param pMessageEN the messageEN to set
	 */
	public void setMessageEN(String pMessageEN) {
		mMessageEN = pMessageEN;
	}
}
