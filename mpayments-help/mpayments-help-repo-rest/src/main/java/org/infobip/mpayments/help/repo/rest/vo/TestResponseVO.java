package org.infobip.mpayments.help.repo.rest.vo;

import java.io.Serializable;

public class TestResponseVO implements Serializable {

	/**
	 * Serialization support
	 */
	private static final long serialVersionUID = 6203806362919681628L;
	
	private String errorMessage = null;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "TestResponseVO [errorMessage=" + errorMessage + "]";
	}
	
}
