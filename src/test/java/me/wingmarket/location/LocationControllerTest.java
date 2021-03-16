package me.wingmarket.location;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.wingmarket.error.exception.location.LocationNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class LocationControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	@DisplayName("동네 조회 테스트 - 동네 조회 - 성공")
	void findSuccessByLocation() throws Exception {
		mockMvc.perform(get("/api/locations")
			.queryParam("region", "서울 종로구 청운동")
			.sessionAttr("ID", 1L))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("동네 조회 테스트 - 동네 미조회 - 성공")
	void findSuccessNoLocation() throws Exception {
		mockMvc.perform(get("/api/locations")
			.sessionAttr("ID", 1L))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("동네 조회 테스트 - 없는 동네 조회 - 성공(데이터0개)")
	void findSuccessNotByLocation() throws Exception {
		mockMvc.perform(get("/api/locations")
			.queryParam("region", "서울 종로구 청운1동")
			.sessionAttr("ID", 1L))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("[]"));
	}

	@Test
	@DisplayName("동네 인증 테스트 - 성공")
	void findSuccessRegionAuth() throws Exception {
		mockMvc.perform(get("/api/locations/region-auth")
			.queryParam("location", "서울 종로구 청운동")
			.sessionAttr("ID", 1L))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("청운동 동네 인증 성공"));
	}

	@Test
	@DisplayName("동네 인증 테스트 - 없는 동네 조회 - 성공(데이터0개)")
	void findFailedRegionAuth() throws Exception {
		mockMvc.perform(get("/api/locations/region-auth")
			.queryParam("location", "서울 종로구 청운동1")
			.sessionAttr("ID", 1L))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(
				result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getClass().isAssignableFrom(
					LocationNotFoundException.class)));
	}

}
