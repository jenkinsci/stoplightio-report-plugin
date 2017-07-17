package com.arkea.satd.stoplightio.model;

/**
 * Internal model
 * @author Nicolas TISSERAND
 *
 */
public class Assertion {

	private boolean success;
	private String message;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(final boolean result) {
		this.success = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(final String message) {
		this.message = message;
	}

	public String toString() {
		return success + "|" + message;
	}	
	
}
