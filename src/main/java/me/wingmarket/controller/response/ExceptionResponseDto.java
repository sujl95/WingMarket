// package me.wingmarket.controller.response;
//
// import lombok.AccessLevel;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import me.wingmarket.error.exception.common.StatusException;
//
// @Getter
// @Builder
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
// @AllArgsConstructor(access = AccessLevel.PRIVATE)
// public class ExceptionResponseDto<T> {
// 	private String error;
// 	private T message;
//
// 	public static ExceptionResponseDto statusExceptionToExceptionResponse(StatusException exception) {
// 		return ExceptionResponseDto.builder()
// 			.error(exception.getStatus())
// 			.message(exception.getDetailMessage())
// 			.build();
// 	}
// }
