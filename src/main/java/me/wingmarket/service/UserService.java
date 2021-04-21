package me.wingmarket.service;

import static me.wingmarket.service.LocationService.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.wingmarket.dto.UserDetailDto;
import me.wingmarket.dto.UserFindDto;
import me.wingmarket.dto.UserSaveDto;
import me.wingmarket.entity.Location;
import me.wingmarket.entity.User;
import me.wingmarket.error.exception.user.UserDuplicateUserIdException;
import me.wingmarket.error.exception.user.UserNotFoundException;
import me.wingmarket.error.exception.user.UserSaveFailedException;
import me.wingmarket.repository.mapper.LocationMapper;
import me.wingmarket.repository.mapper.UserMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	private final LocationMapper locationMapper;

	public static void isEmptyUser(User user) {
		if (ObjectUtils.isEmpty(user)) {
			throw new UserNotFoundException();
		}
	}

	public boolean isNotReflected(int result) {
		return result < 1;
	}

	public long save(UserSaveDto userSaveDto) {
		boolean duplicatedUserIdResult = isDuplicatedUserId(userSaveDto.getUserId());
		if (duplicatedUserIdResult) {
			throw new UserDuplicateUserIdException();
		}
		String locationName = userSaveDto.getLocationName();
		Location locationInfo = locationMapper.findByLocationName(locationName);
		isEmptyLocation(locationInfo);
		User userInfo = userSaveDto.toEntity(locationInfo);
		int result = userMapper.save(userInfo);
		if (isNotReflected(result)) {
			log.info("유저 등록 실패 : userid = {}", userSaveDto.getUserId());
			throw new UserSaveFailedException();
		}
		return userInfo.getId();
	}

	@Transactional(readOnly = true)
	public boolean isDuplicatedUserId(String userId) {
		return userMapper.idCheck(userId);
	}

	@Transactional(readOnly = true)
	public UserDetailDto findById(Long id) {
		User userInfo = userMapper.findById(id);
		isEmptyUser(userInfo);
		return UserDetailDto.userToDetailDto(userInfo);
	}

	@Transactional(readOnly = true)
	public List<UserFindDto> findByNickName(String nickname) {
		return userMapper.findByNickName(nickname);
	}

}
