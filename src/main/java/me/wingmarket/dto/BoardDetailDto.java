package me.wingmarket.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.wingmarket.entity.Board;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor()
public class BoardDetailDto {

	private Long id;
	private Long userId;
	private String nickname;
	private String title;
	private String content;
	private String locationName;
	private Long price;
	private Long categoryId;
	private Board.Status status;
	private LocalDateTime boardDate;
	private boolean isPull;

	public boolean isStatusUpdatable(Board.Status status) {
		return !(this.status == status);
	}

	public boolean isBoardNotHideAndMyBoard(long userId) {
		return this.userId != userId && this.status == Board.Status.HIDE;
	}

	public boolean isOwnerTo(long userId) {
		return this.userId == userId;
	}

}
