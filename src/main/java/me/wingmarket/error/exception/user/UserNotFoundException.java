package me.wingmarket.error.exception.user;

import me.wingmarket.error.exception.common.ExceptionStatus;
import me.wingmarket.error.exception.common.WingMarketException;

public class UserNotFoundException extends WingMarketException {

	public UserNotFoundException() {
		super(ExceptionStatus.USER_NOT_FOUND_EXCEPTION);
	}
}
