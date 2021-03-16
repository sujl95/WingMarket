package me.wingmarket.board;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import me.wingmarket.dto.BoardDate;
import me.wingmarket.dto.Position;
import me.wingmarket.entity.Board;
import me.wingmarket.repository.mapper.BoardMapper;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
	@Mock
	private BoardMapper boardMapper;

	private Board board;
	private Board failedBoard;

	@BeforeEach
	void initBoard() {
		//given
		final LocalDateTime saveTime = LocalDateTime.now();
		BoardDate boardDate = BoardDate.builder()
			.regDate(saveTime)
			.modDate(saveTime)
			.boardDate(saveTime)
			.build();
		Position position = Position.builder()
			.latitude(37.587111)
			.longitude(126.969069)
			.build();
		board = Board.builder()
			.userId(1L)
			.locationId(1111010100L)
			.locationName("서울 종로구 청운동")
			.title("제목 청운동")
			.content("내용 청운동")
			.price(1_000_000L)
			.categoryId(1L)
			.status(Board.Status.SALE)
			.boardDate(boardDate)
			.isPull(false)
			.position(position)
			.build();

		failedBoard = Board.builder().build();
	}

	@Test
	@DisplayName("게시글 생성 테스트 - 성공")
	void saveBoard() {
		//when
		when(boardMapper.save(board)).thenReturn(1);
		int saveCount = boardMapper.save(board);
		//then
		assertEquals(saveCount, 1);
	}

	@Test
	@DisplayName("게시글 생성 테스트 - 실패")
	void saveFailedBoard() {
		//when
		when(boardMapper.save(failedBoard)).thenThrow(new RuntimeException("save failed"));
		//then
		assertThrows(RuntimeException.class, () -> boardMapper.save(failedBoard));
	}

}
