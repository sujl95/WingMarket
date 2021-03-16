package me.wingmarket.error.exception.board;

import me.wingmarket.error.exception.common.ExceptionStatus;
import me.wingmarket.error.exception.common.WingMarketException;

public class BoardDeleteFailedException extends WingMarketException {
	public BoardDeleteFailedException() {
		super(ExceptionStatus.BOARD_DELETE_FAILED_EXCEPTION);
	}
}
