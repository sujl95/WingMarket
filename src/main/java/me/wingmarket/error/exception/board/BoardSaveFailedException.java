package me.wingmarket.error.exception.board;

import me.wingmarket.error.exception.common.ExceptionStatus;
import me.wingmarket.error.exception.common.WingMarketException;

public class BoardSaveFailedException extends WingMarketException {

	public BoardSaveFailedException() {
		super(ExceptionStatus.BOARD_SAVE_FAILED_EXCEPTION);
	}

}
