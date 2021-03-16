package me.wingmarket.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.wingmarket.entity.Board;
import me.wingmarket.entity.User;

@Builder
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardSaveDto {

	@NotBlank
	private String title;
	@NotBlank
	private String content;
	@Min(0)
	private long price;
	@NotNull
	@Min(0)
	private Long categoryId;

	public Board toEntity(Long userId, User user) {
		LocalDateTime saveTime = LocalDateTime.now();
		BoardDate boardDate = BoardDate.builder()
			.regDate(saveTime)
			.modDate(saveTime)
			.boardDate(saveTime)
			.build();
		return Board.builder()
			.userId(userId)
			.locationName(user.getLocationName())
			.locationId(user.getId())
			.title(title)
			.content(content)
			.price(price)
			.categoryId(categoryId)
			.status(Board.Status.SALE)
			.isPull(false)
			.boardDate(boardDate)
			.position(user.getPosition())
			.build();
	}
}
