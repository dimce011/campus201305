package org.infobip.mplatform.help.it.repo.vo;

import java.io.Serializable;

public class TestResponseVO implements Serializable {

	/**
	 * Serialization support
	 */
	private static final long serialVersionUID = -7226757046411365028L;
	
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
