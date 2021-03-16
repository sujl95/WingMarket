package me.wingmarket.error.exception.location;

import me.wingmarket.error.exception.common.ExceptionStatus;
import me.wingmarket.error.exception.common.WingMarketException;

public class LocationNotFoundException extends WingMarketException {

	public LocationNotFoundException() {
		super(ExceptionStatus.LOCATION_NOT_FOUND_EXCEPTION);
	}
}
