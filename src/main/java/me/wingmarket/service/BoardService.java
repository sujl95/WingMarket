package me.wingmarket.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.wingmarket.controller.request.BoardFindRequest;
import me.wingmarket.dto.BoardDetailDto;
import me.wingmarket.dto.BoardFindDto;
import me.wingmarket.dto.BoardModifyDto;
import me.wingmarket.dto.BoardSaveDto;
import me.wingmarket.dto.Position;
import me.wingmarket.entity.Board;
import me.wingmarket.entity.Location;
import me.wingmarket.entity.User;
import me.wingmarket.error.exception.board.BoardDateLessThanTwoDaysException;
import me.wingmarket.error.exception.board.BoardDeleteFailedException;
import me.wingmarket.error.exception.board.BoardNotFoundException;
import me.wingmarket.error.exception.board.BoardNotMatchUserIdException;
import me.wingmarket.error.exception.board.BoardPullFailedException;
import me.wingmarket.error.exception.board.BoardSaveFailedException;
import me.wingmarket.error.exception.board.BoardStatusFailedException;
import me.wingmarket.error.exception.board.BoardStatusHideException;
import me.wingmarket.error.exception.board.BoardUpdateFailedException;
import me.wingmarket.repository.mapper.BoardMapper;
import me.wingmarket.repository.mapper.LocationMapper;
import me.wingmarket.repository.mapper.UserMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardMapper boardMapper;
	private final LocationMapper locationMapper;
	private final UserMapper userMapper;


	public boolean isNotReflected(int result) {
		return result < 1;
	}

	public static void isEmptyBoard(BoardDetailDto board) {
		if (ObjectUtils.isEmpty(board)) {
			throw new BoardNotFoundException();
		}
	}

	private boolean isLessThanTwoDays(long boardDateSeconds, long twoDaysSeconds) {
		return boardDateSeconds < twoDaysSeconds;
	}

	public String register(BoardSaveDto boardSaveDto, Long userId) {
		User userInfo = userMapper.findById(userId);
		UserService.isEmptyUser(userInfo);
		Board boardInfo = boardSaveDto.toEntity(userId, userInfo);
		int result = boardMapper.save(boardInfo);
		if (isNotReflected(result)) {
			log.info("게시글 등록 실패 : user id = {}", userId);
			throw new BoardSaveFailedException();
		}
		return "게시글 등록 성공";
	}

	@Transactional(readOnly = true)
	public BoardDetailDto findById(Long boardId, Long userId) {
		BoardDetailDto boardInfo = boardMapper.findById(boardId);
		isEmptyBoard(boardInfo);
		if (boardInfo.isBoardNotHideAndMyBoard(userId)) {
			throw new BoardStatusHideException();
		}
		return boardInfo;
	}

	@Transactional(readOnly = true)
	public List<BoardFindDto> findByUser(Long userId, Long sessionUserId) {
		User userInfo = userMapper.findById(userId);
		UserService.isEmptyUser(userInfo);
		return boardMapper.findByUser(userId, sessionUserId);
	}

	@Transactional(readOnly = true)
	public List<BoardFindDto> findByCondition(BoardFindRequest boardFindRequest) {
		String locationName = boardFindRequest.getLocation();
		Location locationInfo = locationMapper.findByLocationName(locationName);
		LocationService.isEmptyLocation(locationInfo);
		Position position = locationInfo.getPosition();
		return boardMapper.findByCondition(boardFindRequest, position);
	}

	public void updatePull(Long boardId, Board.Status status, Long userId,
		BoardDetailDto boardDetailDto, LocalDateTime updateTime) {
		LocalDateTime boardDate = boardDetailDto.getBoardDate();
		long boardDateSeconds = Duration.between(boardDate, updateTime).getSeconds();
		long twoDaysSeconds = Duration.ofDays(2).getSeconds();
		if (isLessThanTwoDays(boardDateSeconds, twoDaysSeconds)) {
			throw new BoardDateLessThanTwoDaysException(String.valueOf(boardDateSeconds));
		}
		int result = boardMapper.updateStatus(boardId, userId, status, updateTime);
		if (isNotReflected(result)) {
			log.info("게시글 끌어올리기 실패 : user id = {} , board id = {}", userId, boardId);
			throw new BoardPullFailedException();
		}
	}

	public void updateStatus(Long boardId, Board.Status status, Long userId) {
		BoardDetailDto boardDetailDto = boardMapper.findById(boardId);
		isEmptyBoard(boardDetailDto);
		if (!boardDetailDto.isOwnerTo(userId)) {
			throw new BoardNotMatchUserIdException();
		}
		if (!boardDetailDto.isStatusUpdatable(status)) {
			return;
		}
		LocalDateTime updateTime = LocalDateTime.now();
		if (status == Board.Status.PULL) {
			updatePull(boardId, status, userId, boardDetailDto, updateTime);
		} else {
			updateStatus(boardId, status, userId, updateTime);
		}
	}

	private void updateStatus(Long boardId, Board.Status status, Long userId, LocalDateTime updateTime) {
		int result = boardMapper.updateStatus(boardId, userId, status, updateTime);
		if (isNotReflected(result)) {
			log.info("게시글 상태 변경 실패 : user id = {} , board id = {}", userId, boardId);
			throw new BoardStatusFailedException(status);
		}
	}

	public void updateBoard(Long boardId, BoardModifyDto boardModifyDto, Long userId) {
		BoardDetailDto boardDetailDto = boardMapper.findById(boardId);
		isEmptyBoard(boardDetailDto);
		if (!boardDetailDto.isOwnerTo(userId)) {
			throw new BoardNotMatchUserIdException();
		}
		LocalDateTime updateTime = LocalDateTime.now();
		int result = boardMapper.updateBoard(boardId, boardModifyDto, updateTime);
		if (isNotReflected(result)) {
			log.info("게시글 수정 실패 : user id = {} , board id = {}", userId, boardId);
			throw new BoardUpdateFailedException();
		}
	}

	public void deleteById(Long boardId, Long userId) {
		BoardDetailDto boardDetailDto = boardMapper.findById(boardId);
		isEmptyBoard(boardDetailDto);
		if (!boardDetailDto.isOwnerTo(userId)) {
			throw new BoardNotMatchUserIdException();
		}
		int result = boardMapper.deleteById(boardId);
		if (isNotReflected(result)) {
			log.info("게시글 삭제 실패 : user id = {} , board id = {}", userId, boardId);
			throw new BoardDeleteFailedException();
		}
	}

}
