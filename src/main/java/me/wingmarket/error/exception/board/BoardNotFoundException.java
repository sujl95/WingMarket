package me.wingmarket.error.exception.board;

import me.wingmarket.error.exception.common.ExceptionStatus;
import me.wingmarket.error.exception.common.WingMarketException;

public class BoardNotFoundException extends WingMarketException {

	public BoardNotFoundException() {
		super(ExceptionStatus.BOARD_NOT_FOUND_EXCEPTION);
	}
}
