package me.wingmarket.service;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.wingmarket.common.utils.Sha256Util;
import me.wingmarket.controller.request.UserLoginRequest;
import me.wingmarket.controller.response.UserLoginResponse;
import me.wingmarket.entity.User;
import me.wingmarket.repository.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserLoginService {

	private final UserMapper userMapper;

	public ResponseEntity<UserLoginResponse> login(UserLoginRequest loginRequest, HttpSession session) {
		String id = loginRequest.getId();
		String encryptPassword = Sha256Util.encode(loginRequest.getPassword());
		User userInfo = userMapper.findByIdAndPassword(id, encryptPassword);
		ResponseEntity<UserLoginResponse> responseEntity = null;
		UserLoginResponse userLoginResponse;
		if (ObjectUtils.isEmpty(userInfo)) {
			userLoginResponse = UserLoginResponse.FAIL;
			responseEntity = new ResponseEntity<>(userLoginResponse, HttpStatus.UNAUTHORIZED);
		} else if (User.Status.ACTIVE.equals(userInfo.getStatus())) {
			userLoginResponse = UserLoginResponse.SUCCESS;
			setSessions(userInfo, session);
			responseEntity = new ResponseEntity<>(userLoginResponse, HttpStatus.OK);
		}
		return responseEntity;
	}

	public void logout(HttpSession session) {
		session.invalidate();
	}

	private void setSessions(User user, HttpSession session) {
		session.setAttribute("ID", user.getId());
		session.setAttribute("ROLE", user.getRole());
		session.setMaxInactiveInterval(60 * 30);
	}
}
