package me.wingmarket.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import me.wingmarket.dto.UserFindDto;
import me.wingmarket.entity.Location;
import me.wingmarket.entity.User;

@Mapper
public interface UserMapper {

	User findById(long userId);

	int save(User user);

	boolean idCheck(String userId);

	List<UserFindDto> findByNickName(String nickname);

	User findByIdAndPassword(@Param("id") String id, @Param("password") String password);

	int updateLocation(@Param("location") Location location, @Param("userId") Long userId);
}
