package me.wingmarket.error.exception.board;

import me.wingmarket.error.exception.common.ExceptionStatus;
import me.wingmarket.error.exception.common.WingMarketException;

public class BoardUpdateFailedException extends WingMarketException {
	public BoardUpdateFailedException() {
		super(ExceptionStatus.BOARD_UPDATE_FAILED_EXCEPTION, "게시글 수정을 실패했습니다");
	}
}
