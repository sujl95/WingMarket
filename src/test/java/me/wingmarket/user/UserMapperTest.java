package me.wingmarket.user;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import me.wingmarket.common.utils.Sha256Util;
import me.wingmarket.dto.Position;
import me.wingmarket.dto.UserDate;
import me.wingmarket.dto.UserFindDto;
import me.wingmarket.entity.User;
import me.wingmarket.repository.mapper.UserMapper;

@SpringBootTest
@ActiveProfiles("local")
public class UserMapperTest {

	@Autowired
	private UserMapper userMapper;

	@Test
	@DisplayName("유저 번호 조회")
	void findByIdUserTest() {
		// given
		User user = userMapper.findById(1L);
		// then
		assertNotNull(user);
	}

	@Test
	@DisplayName("유저 저장")
	void saveUserTest() {
		// given
		LocalDateTime saveTime = LocalDateTime.now();
		Position position = Position.builder()
			.latitude(37.587111)
			.longitude(126.969069)
			.build();
		UserDate userDate = UserDate.builder()
			.regDate(saveTime)
			.modDate(saveTime)
			.loginDate(saveTime)
			.build();
		User user = User.builder()
			.userId("testId")
			.locationName("서울 종로구 청운동")
			.locationId(1111010100L)
			.password(Sha256Util.encode("testpassword"))
			.nickname("testNickName")
			.phone("010-1234-1234")
			.email("test@gmail.com")
			.userDate(userDate)
			.role(User.Role.USER)
			.status(User.Status.ACTIVE)
			.position(position)
			.build();
		int saveCount = userMapper.save(user);
		// then
		assertEquals(1, saveCount);
	}

	@Test
	@DisplayName("유저 중복 체크 - 중복 일때")
	void isDuplicateUserSuccessTest() {
		// given
		boolean result = userMapper.idCheck("devking2106");
		// then
		assertTrue(result);
	}

	@Test
	@DisplayName("유저 중복 체크 - 중복이지 않을때")
	void isDuplicateUserFailedTest() {
		// given
		boolean result = userMapper.idCheck("testId3");
		// then
		assertFalse(result);
	}

	@Test
	@DisplayName("유저 검색")
	void findByNicknameUserTest() {
		// given
		List<UserFindDto> users = userMapper.findByNickName("NICKNAME");
		// then
		assertNotEquals(new ArrayList<UserFindDto>(), users);
	}

	@Test
	@DisplayName("유저 로그인 - 비밀번호 일치할 때")
	void loginFindByUserIdAndPasswordUserSuccessTest() {
		// given
		User result = userMapper.findByIdAndPassword("devking2106", Sha256Util.encode("devkingpassword"));
		// then
		assertNotNull(result);
	}

	@Test
	@DisplayName("유저 로그인 - 비밀번호 일치 하지 않을때")
	void loginFindByUserIdAndPasswordUserSuccessNotMatchPasswordTest() {
		// given
		User result = userMapper.findByIdAndPassword("devking2106", Sha256Util.encode("devkingpassword1"));
		// then
		assertNull(result);
	}
}
