package me.wingmarket.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.wingmarket.dto.BoardDate;
import me.wingmarket.dto.Position;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

	public enum Status {
		SALE, SALE_COMPLETED, TRADE, HIDE, RESERVATION, PULL
	}

	public enum FilterType {
		TITLE, CONTENT, TITLE_OR_CONTENT
	}

	private Long id;
	private Long userId;
	private Long locationId;
	private String locationName;
	private String title;
	private String content;
	private Long price;
	private Long categoryId;
	private Board.Status status;
	private boolean isPull;
	private BoardDate boardDate;
	private Position position;

}
