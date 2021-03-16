package me.wingmarket.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.wingmarket.error.exception.common.WingMarketException;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionResponseInfo {
	private final String status;
	private final String message;

	public static ExceptionResponseInfo of(WingMarketException wingMarketException) {
		return new ExceptionResponseInfo(wingMarketException.getStatus(), wingMarketException.getMessage());
	}
}
