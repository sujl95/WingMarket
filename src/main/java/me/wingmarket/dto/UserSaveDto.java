package me.wingmarket.dto;

import static me.wingmarket.common.utils.Sha256Util.*;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.wingmarket.entity.Location;
import me.wingmarket.entity.User;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSaveDto {

	@NotBlank
	@Size(min = 4, max = 20)
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{4,20}$")
	private String userId;
	@NotBlank
	private String locationName;
	@NotBlank
	@Size(min = 8, max = 20)
	private String password;
	@NotBlank
	@Size(min = 2, max = 20)
	@Pattern(regexp = "^[가-힣ㄱ-ㅎa-zA-Z0-9._ -]{2,20}$")
	private String nickname;
	@Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
	private String phone;
	@Email
	private String email;

	public User toEntity(Location location) {
		LocalDateTime saveTime = LocalDateTime.now();
		UserDate userDate = UserDate.builder()
			.loginDate(saveTime)
			.modDate(saveTime)
			.regDate(saveTime)
			.build();
		return User.builder()
			.userId(userId)
			.locationName(locationName)
			.locationId(location.getId())
			.password(encode(password))
			.nickname(nickname)
			.phone(phone)
			.email(email)
			.userDate(userDate)
			.role(User.Role.USER)
			.status(User.Status.ACTIVE)
			.position(location.getPosition())
			.build();
	}
}
