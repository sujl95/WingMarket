package me.wingmarket.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.wingmarket.dto.Position;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

	private Long id;
	private String locationName;
	private String province;
	private String city;
	private String town;
	private Position position;
	private String relatedLocation;
}
