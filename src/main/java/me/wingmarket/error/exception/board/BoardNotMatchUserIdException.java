package me.wingmarket.error.exception.board;

import me.wingmarket.error.exception.common.ExceptionStatus;
import me.wingmarket.error.exception.common.WingMarketException;

public class BoardNotMatchUserIdException extends WingMarketException {

	public BoardNotMatchUserIdException() {
		super(ExceptionStatus.BOARD_NOT_MATCH_USERID_EXCEPTION);
	}
}
