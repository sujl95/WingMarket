package me.wingmarket.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import me.wingmarket.dto.LocationFindDto;
import me.wingmarket.dto.Position;
import me.wingmarket.entity.Location;

@Mapper
public interface LocationMapper {

	Location findByLocationName(String locationName);

	List<LocationFindDto> findAll(@Param("region") String region,
		@Param("position") Position position);
}
