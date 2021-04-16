package me.wingmarket.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.wingmarket.controller.request.UserLoginRequest;
import me.wingmarket.dto.UserSaveDto;
import me.wingmarket.error.exception.user.UserDuplicateUserIdException;
import me.wingmarket.error.exception.user.UserNotFoundException;

@ActiveProfiles("local")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	UserSaveDto userSaveDto;
	UserSaveDto duplicateUserSaveDto;
	UserLoginRequest userLoginRequest;
	UserLoginRequest badUserLoginRequest;

	@BeforeEach
	void setUp() {
		userSaveDto = UserSaveDto.builder()
			.userId("thewingtest")
			.locationName("서울 종로구 청운동")
			.phone("010-1234-1234")
			.password("thewingpassword")
			.nickname("thewingNickName")
			.email("thewing@gmail.com")
			.build();

		duplicateUserSaveDto = UserSaveDto.builder()
			.userId("thewingtest")
			.locationName("서울 종로구 청운동")
			.phone("010-1234-1234")
			.password("testpassword")
			.nickname("testNickName")
			.email("test@gmail.com")
			.build();

		userLoginRequest = UserLoginRequest.builder()
			.id("thewingtest")
			.password("thewingpassword")
			.build();

		badUserLoginRequest = UserLoginRequest.builder()
			.id("thewingtest1")
			.password("thewingpassword1")
			.build();
	}

	@Test
	@Order(0)
	@Rollback(false)
	@DisplayName("유저 저장 - 성공")
	void registerSuccessTest() throws Exception {
		System.out.println(0);
		mockMvc.perform(post("/api/users/register")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(userSaveDto)))
			.andDo(print())
			.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("유저 저장 - 중복 아이디 입력시 - 실패 ")
	void registerFailedTest() throws Exception {
		mockMvc.perform(post("/api/users/register")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(duplicateUserSaveDto)))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(
				result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getClass().isAssignableFrom(
					UserDuplicateUserIdException.class)));
	}

	@Test
	@DisplayName("유저 번호 조회 - 성공")
	void findByIdSuccessTest() throws Exception {
		mockMvc.perform(get("/api/users/{id}", 1L))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("유저 번호 조회 - 실패")
	void findByIdFailedTest() throws Exception {
		mockMvc.perform(get("/api/users/{id}", 0L))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(
				result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getClass().isAssignableFrom(
					UserNotFoundException.class)));
	}

	@Test
	@DisplayName("유저 프로필 조회 - 성공")
	void getUserProfileSuccessTest() throws Exception {
		mockMvc.perform(get("/api/users/profile")
			.sessionAttr("ID", 1L))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("유저 프로필 조회 - 로그인 안했을때 - 실패")
	void getUserProfileFailedTest() throws Exception {
		mockMvc.perform(get("/api/users/profile"))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("유저 조회 - 성공")
	void findByNickNameSuccessTest() throws Exception {
		mockMvc.perform(get("/api/users")
			.queryParam("nickname", "NICKNAME"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@Order(1)
	@DisplayName("유저 중복 아이디 - 중복 일때 - 성공")
	void isDuplicateSuccessTest() throws Exception {
		mockMvc.perform(get("/api/users/duplicate/{id}", "thewingtest"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(result -> assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString())));
	}

	@Test
	@DisplayName("유저 중복 아이디 - 중복 아닐때 - 성공")
	void isNotDuplicateSuccessTest() throws Exception {
		mockMvc.perform(get("/api/users/duplicate/{id}", "thewing1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(result -> assertFalse(Boolean.parseBoolean(result.getResponse().getContentAsString())));
	}

	@Test
	@Order(10)
	@DisplayName("유저 로그인 - 성공")
	void loginSuccessTest() throws Exception {
		mockMvc.perform(post("/api/users/login")
			.content(objectMapper.writeValueAsString(userLoginRequest))
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("유저 로그인 - 실패")
	void loginFailedTest() throws Exception {
		mockMvc.perform(post("/api/users/login")
			.content(objectMapper.writeValueAsString(badUserLoginRequest))
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isUnauthorized());
	}

	@Test
	@DisplayName("유저 로그아웃 - 성공")
	void logoutTest() throws Exception {
		mockMvc.perform(get("/api/users/logout")
			.sessionAttr("ID", 1L))
			.andDo(print())
			.andExpect(status().isOk());
	}
}
