package me.wingmarket.error.exception.board;

import me.wingmarket.error.exception.common.ExceptionStatus;
import me.wingmarket.error.exception.common.WingMarketException;

public class BoardPullFailedException extends WingMarketException {

	public BoardPullFailedException() {
		super(ExceptionStatus.BOARD_PULL_FAILED_EXCEPTION);
	}

}
