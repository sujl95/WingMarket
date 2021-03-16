package me.wingmarket.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import lombok.RequiredArgsConstructor;
import me.wingmarket.controller.request.BoardFindRequest;
import me.wingmarket.dto.BoardDetailDto;
import me.wingmarket.dto.BoardFindDto;
import me.wingmarket.dto.BoardModifyDto;
import me.wingmarket.dto.BoardSaveDto;
import me.wingmarket.entity.Board;
import me.wingmarket.service.BoardService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

	private final BoardService boardService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/boards")
	public Board register(@Valid @RequestBody BoardSaveDto boardSaveDto, @SessionAttribute("ID") Long userId) {
		return boardService.register(boardSaveDto, userId);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/boards/{id}")
	public BoardDetailDto findById(@PathVariable Long id, @SessionAttribute("ID") Long userId) {
		return boardService.findById(id, userId);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/boards")
	public List<BoardFindDto> findByCondition(@Valid BoardFindRequest boardFindRequest,
		@SessionAttribute(value = "ID") Long userId) {
		return boardService.findByCondition(boardFindRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/users/{userId}/boards")
	public List<BoardFindDto> findByUser(@PathVariable Long userId, @SessionAttribute("ID") Long sessionUserId) {
		return boardService.findByUser(userId, sessionUserId);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PatchMapping("/boards/{id}/status")
	public void updateStatus(@PathVariable Long id, @RequestBody Board.Status status,
		@SessionAttribute("ID") Long userId) {
		boardService.updateStatus(id, status, userId);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/boards/{id}")
	public void updateBoard(@PathVariable Long id, @Valid @RequestBody BoardModifyDto boardModifyDto,
		@SessionAttribute("ID") Long userId) {
		boardService.updateBoard(id, boardModifyDto, userId);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/boards/{id}")
	public void deleteBoard(@PathVariable Long id, @SessionAttribute("ID") Long userId) {
		boardService.deleteById(id, userId);
	}
}
