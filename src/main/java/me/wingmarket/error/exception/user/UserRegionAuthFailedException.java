package me.wingmarket.error.exception.user;

import me.wingmarket.error.exception.common.ExceptionStatus;
import me.wingmarket.error.exception.common.WingMarketException;

public class UserRegionAuthFailedException extends WingMarketException {
	public UserRegionAuthFailedException() {
		super(ExceptionStatus.USER_REGION_AUTH_FAILED_EXCEPTION);
	}
}
