package me.wingmarket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class LocationFindDto {
	private final Long id;
	private final String locationName;
	private final String relatedLocation;
}
