package me.wingmarket.error.exception.board;

import me.wingmarket.error.exception.common.ExceptionStatus;
import me.wingmarket.error.exception.common.WingMarketException;

public class BoardDateLessThanTwoDaysException extends WingMarketException {

	public BoardDateLessThanTwoDaysException(String message) {
		super(ExceptionStatus.BOARD_DATE_LESS_THAN_TWO_DAYS_EXCEPTION,
			"게시일이 2일이 지나지 않았습니다. 경과시간 = " + message);
	}
}
