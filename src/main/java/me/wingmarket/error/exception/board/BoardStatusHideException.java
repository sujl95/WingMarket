package me.wingmarket.error.exception.board;

import me.wingmarket.error.exception.common.ExceptionStatus;
import me.wingmarket.error.exception.common.WingMarketException;

public class BoardStatusHideException extends WingMarketException {

	public BoardStatusHideException() {
		super(ExceptionStatus.BOARD_STATUS_HIDE_EXCEPTION);
	}
}
