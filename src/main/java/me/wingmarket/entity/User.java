package me.wingmarket.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.wingmarket.dto.Position;
import me.wingmarket.dto.UserDate;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	public enum Role {
		USER, MANAGER
	}

	public enum Status {
		ACTIVE, DELETED
	}

	private Long id;
	private String userId;
	private String locationName;
	private Long locationId;
	private String password;
	private String nickname;
	private String phone;
	private String email;
	private UserDate userDate;
	private Role role;
	private Status status;
	private Position position;
}
