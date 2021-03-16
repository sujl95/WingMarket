package me.wingmarket.repository.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import me.wingmarket.controller.request.BoardFindRequest;
import me.wingmarket.dto.BoardDetailDto;
import me.wingmarket.dto.BoardFindDto;
import me.wingmarket.dto.BoardModifyDto;
import me.wingmarket.dto.Position;
import me.wingmarket.entity.Board;

@Mapper
public interface BoardMapper {

	int save(Board saveBoard);

	BoardDetailDto findById(Long id);

	List<BoardFindDto> findByUser(@Param("userId") Long userId, @Param("userIdResult") Long userIdResult);

	List<BoardFindDto> findByCondition(@Param("boardFindRequest") BoardFindRequest boardFindRequest,
		@Param("position") Position position);

	int updateStatus(@Param("id") Long id, @Param("userId") Long userId, @Param("status") Board.Status status,
		@Param("updateTime") LocalDateTime updateTime);

	int updateBoard(@Param("id") Long id, @Param("boardModifyDto") BoardModifyDto boardModifyDto,
		@Param("updateTime") LocalDateTime updateTime);

	int deleteById(Long id);
}
