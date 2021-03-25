package me.wingmarket.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import lombok.RequiredArgsConstructor;
import me.wingmarket.controller.request.UserLoginRequest;
import me.wingmarket.controller.response.UserLoginResponse;
import me.wingmarket.dto.UserDetailDto;
import me.wingmarket.dto.UserFindDto;
import me.wingmarket.dto.UserSaveDto;
import me.wingmarket.service.UserLoginService;
import me.wingmarket.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	private final UserLoginService userLoginService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/register")
	public String register(@Valid @RequestBody UserSaveDto userSaveDto) {
		userService.save(userSaveDto);
		return "회원가입을 성공했습니다";
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public UserDetailDto findById(@PathVariable Long id) {
		return userService.findById(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/profile")
	public UserDetailDto getUserProfile(@SessionAttribute("ID") Long id) {
		return userService.findById(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<UserFindDto> findByNickName(@RequestParam String nickname) {
		return userService.findByNickName(nickname);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/duplicate/{id}")
	public boolean idCheck(@PathVariable String id) {
		return userService.isDuplicatedUserId(id);
	}

	@PostMapping("/login")
	public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest,
		HttpSession httpSession) {
		return userLoginService.login(userLoginRequest, httpSession);
	}

	@GetMapping("/logout")
	public void logout(HttpSession session) {
		userLoginService.logout(session);
	}


}
