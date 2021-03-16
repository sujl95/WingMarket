package me.wingmarket.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import me.wingmarket.dto.LocationFindDto;
import me.wingmarket.entity.Location;
import me.wingmarket.entity.User;
import me.wingmarket.error.exception.location.LocationNotFoundException;
import me.wingmarket.error.exception.user.UserRegionAuthFailedException;
import me.wingmarket.repository.mapper.LocationMapper;
import me.wingmarket.repository.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class LocationService {

	private final LocationMapper locationMapper;
	private final UserMapper userMapper;

	public static void isEmptyLocation(Location location) {
		if (ObjectUtils.isEmpty(location)) {
			throw new LocationNotFoundException();
		}
	}

	public boolean isNotReflected(int result) {
		return result < 1;
	}

	@Transactional(readOnly = true)
	public Location findByLocationName(String locationName) {
		Location locationInfo = locationMapper.findByLocationName(locationName);
		isEmptyLocation(locationInfo);
		return locationInfo;
	}

	@Transactional(readOnly = true)
	public List<LocationFindDto> findAll(String region, Long userId) {
		User userInfo = userMapper.findById(userId);
		return locationMapper.findAll(region, userInfo.getPosition());
	}

	public String regionAuth(String locationName, Long userId) {
		Location locationInfo = findByLocationName(locationName);
		System.out.println("locationInfo = " + locationInfo);
		int result = userMapper.updateLocation(locationInfo, userId);
		if (isNotReflected(result)) {
			throw new UserRegionAuthFailedException();
		}
		return locationInfo.getTown() + " 동네 인증 성공";
	}
}
