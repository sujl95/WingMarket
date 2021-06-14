package me.wingmarket.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import lombok.RequiredArgsConstructor;
import me.wingmarket.dto.LocationFindDto;
import me.wingmarket.service.LocationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LocationController {

	private final LocationService locationService;

	@GetMapping("/locations")
	public List<LocationFindDto> findAll(
		@RequestParam(value = "region", required = false) String region, @SessionAttribute("ID") Long userId) {
		return locationService.findAll(region, userId);
	}

	@GetMapping("/locations/region-auth")
	public String regionAuth(@RequestParam(value = "location") String location, @SessionAttribute("ID") Long userId) {
		return locationService.regionAuth(location, userId);
	}
}
