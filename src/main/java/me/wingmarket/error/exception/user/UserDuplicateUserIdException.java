package me.wingmarket.error.exception.user;

import me.wingmarket.error.exception.common.ExceptionStatus;
import me.wingmarket.error.exception.common.WingMarketException;

public class UserDuplicateUserIdException extends WingMarketException {
	public UserDuplicateUserIdException() {
		super(ExceptionStatus.USER_DUPLICATE_USERID_EXCEPTION);
	}
}
