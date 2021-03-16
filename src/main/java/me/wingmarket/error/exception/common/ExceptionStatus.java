package me.wingmarket.error.exception.common;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionStatus {

	// User
	USER_NOT_FOUND_EXCEPTION(4001, "유저 검색 결과가 없습니다.", NOT_FOUND),
	USER_SAVE_FAILED_EXCEPTION(4002, "회원가입을 실패했습니다.", BAD_REQUEST),
	USER_DUPLICATE_USERID_EXCEPTION(4003, "중복된 아이디입니다.", BAD_REQUEST),
	USER_REGION_AUTH_FAILED_EXCEPTION(4004, "동네 인증을 실패했습니다.", BAD_REQUEST),

	// Board
	BOARD_NOT_FOUND_EXCEPTION(4101, "게시글이 존재하지 않습니다.", NOT_FOUND),
	BOARD_SAVE_FAILED_EXCEPTION(4102, "게시글 저장을 실패했습니다.", BAD_REQUEST),
	BOARD_STATUS_HIDE_EXCEPTION(4103, "게시글이 숨김 상태거나 본인이 작성한 글이 아닙니다.", BAD_REQUEST),
	BOARD_PULL_FAILED_EXCEPTION(4104, "끌어 올리기를 실패 했습니다.", BAD_REQUEST),

	BOARD_DATE_LESS_THAN_TWO_DAYS_EXCEPTION(4105, "게시글 게시일이 2일이 지나지 않았습니다.", BAD_REQUEST),
	BOARD_NOT_MATCH_USERID_EXCEPTION(4106, "게시글 작성자가 일치하지 않습니다.", BAD_REQUEST),
	BOARD_STATUS_FAILED_EXCEPTION(4107, "게시글 상태 변경을 실패했습니다.", BAD_REQUEST),
	BOARD_UPDATE_FAILED_EXCEPTION(4108, "게시글 수정을 실패했습니다", BAD_REQUEST),
	BOARD_DELETE_FAILED_EXCEPTION(4109, "게시글 삭제를 실패했습니다", BAD_REQUEST),

	// Location
	LOCATION_NOT_FOUND_EXCEPTION(4201, "일치하는 동네가 없습니다. 올바른 동네를 입력해주세요.", NOT_FOUND),
	LOCATION_NOT_MATCH_EXCEPTION(4202, "동네가 일치하지 않습니다. 글쓰기를 하려면 동네인증이 필요합니다.", BAD_REQUEST),

	// Common
	RUN_TIME_EXCEPTION(500, "런타임 에러", INTERNAL_SERVER_ERROR),
	NOT_FOUND_EXCEPTION(404, "요청한 리소스가 존재하지 않습니다.", NOT_FOUND),
	INVALID_TYPE_VALUE_EXCEPTION(400, "유효하지 않는 Type의 값입니다. 입력 값을 확인 해주세요.", BAD_REQUEST),
	INVALID_FORMAT_EXCEPTION(400, "유효하지 않는 Type 입니다. Type을 확인 해주세요.", BAD_REQUEST),
	INVALID_INPUT_VALUE(400, "유효하지 않는 입력 값입니다.", BAD_REQUEST),
	METHOD_NOT_SUPPORT(405, "지원하지 않은 HTTP Method 입니다.", METHOD_NOT_ALLOWED);

	private final int status;
	private final String message;
	private final HttpStatus httpStatus;

}
