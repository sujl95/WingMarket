package me.wingmarket.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.wingmarket.entity.User;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailDto {
	private Long id;
	private String locationName;
	private String nickname;
	private LocalDateTime regDate;

	public static UserDetailDto userToDetailDto(User user) {
		return UserDetailDto.builder()
			.id(user.getId())
			.locationName(user.getLocationName())
			.nickname(user.getNickname())
			.regDate(user.getUserDate().getRegDate())
			.build();
	}
}
