package me.wingmarket.error.exception.location;

import me.wingmarket.error.exception.common.ExceptionStatus;
import me.wingmarket.error.exception.common.WingMarketException;

public class TownNotMatchException extends WingMarketException {

	public TownNotMatchException(String message) {
		super(ExceptionStatus.LOCATION_NOT_MATCH_EXCEPTION, "동네가 일치하지 않습니다. 글쓰기를 하려면 " + message + " 동네인증이 필요합니다 ");
	}
}
