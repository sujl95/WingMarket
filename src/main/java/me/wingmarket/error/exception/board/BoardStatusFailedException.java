package me.wingmarket.error.exception.board;

import me.wingmarket.entity.Board;
import me.wingmarket.error.exception.common.ExceptionStatus;
import me.wingmarket.error.exception.common.WingMarketException;

public class BoardStatusFailedException extends WingMarketException {

	public BoardStatusFailedException(Board.Status message) {
		super(ExceptionStatus.BOARD_STATUS_FAILED_EXCEPTION, message + " 상태 변경을 실패 했습니다");
	}
}
