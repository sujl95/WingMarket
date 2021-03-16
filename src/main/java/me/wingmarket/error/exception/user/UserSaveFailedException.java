package me.wingmarket.error.exception.user;

import me.wingmarket.error.exception.common.ExceptionStatus;
import me.wingmarket.error.exception.common.WingMarketException;

public class UserSaveFailedException extends WingMarketException {
	public UserSaveFailedException() {
		super(ExceptionStatus.USER_SAVE_FAILED_EXCEPTION);
	}
}
