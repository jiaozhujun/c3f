package com.youcan.core.security;

/**
 * EncryptException is an exception class from exception, handles any exceptions
 * that may happen in the encryption/descryption.
 */
class EncryptException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4641885419300327371L;

	/**
	 * Construct a new EncryptException
	 */
	public EncryptException() {
		super();
	}

	/**
	 * Construct a new EncryptException
	 * 
	 * @param msg
	 *            exception message
	 */
	public EncryptException(String msg) {
		super(msg);
	}

	/**
	 * Construct a new EncryptException
	 * 
	 * @ex exception
	 */
	public EncryptException(Throwable ex) {
		super(ex);
	}
}

/**
 * RSAUtil is a util class for implementing RSA algorithm, which needs
 * bcprov-jdk1.4.jar being installed into CLASSPATH
 * 
 * @author quickpoint
 * @version 1.0 06/22/2005
 */
