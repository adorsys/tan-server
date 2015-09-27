package de.adorsys.tanserver.service;

public class UnknownAccountException extends Exception {
	
	private String accountId;

	public UnknownAccountException(String accountId) {
		super();
		this.accountId = accountId;
	}

}
