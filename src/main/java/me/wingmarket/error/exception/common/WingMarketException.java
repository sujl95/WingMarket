package me.wingmarket.error.exception.common;

import org.springframework.http.HttpStatus;

public class WingMarketException extends RuntimeException {

	private final ExceptionStatus responseStatus;

	public WingMarketException(ExceptionStatus responseStatus) {
		super(responseStatus.getMessage());
		this.responseStatus = responseStatus;
	}

	public WingMarketException(ExceptionStatus responseStatus, String message) {
		super(message);
		this.responseStatus = responseStatus;
	}

	public HttpStatus getHttpStatus() {
		return responseStatus.getHttpStatus();
	}

	public String getStatus() {
		return String.valueOf(responseStatus.getStatus());
	}
}
