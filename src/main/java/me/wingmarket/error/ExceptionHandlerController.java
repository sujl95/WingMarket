package me.wingmarket.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.extern.slf4j.Slf4j;
import me.wingmarket.controller.response.ErrorResponse;
import me.wingmarket.controller.response.ExceptionResponseInfo;
import me.wingmarket.error.exception.common.ExceptionStatus;
import me.wingmarket.error.exception.common.WingMarketException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

	/**
	 * @Valid,@Validated 검증으로 binding 못할 때
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	private ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		errorLogging(exception);
		return ErrorResponse.of(ExceptionStatus.INVALID_INPUT_VALUE,
			exception.getBindingResult());
	}

	/**
	 * @RequestParam enum type 불일치로 binding 못할 때
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	private ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
		errorLogging(exception);
		return ErrorResponse.of(exception);
	}

	/**
	 * @ModelAttribute 나 RequestBody 로 @Valid 로 Binding 못할 때
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	private ErrorResponse handleBindException(BindException exception) {
		errorLogging(exception);
		return ErrorResponse.of(ExceptionStatus.INVALID_TYPE_VALUE_EXCEPTION,
			exception.getBindingResult());
	}

	/**
	 * 지원하지 않는 HTTP METHOD 를 요청 했을때
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	private ErrorResponse handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException exception) {
		return ErrorResponse.of(exception);
	}

	@ExceptionHandler(WingMarketException.class)
	private ResponseEntity<ExceptionResponseInfo> handleStatusException(WingMarketException exception) {
		ExceptionResponseInfo response = ExceptionResponseInfo.of(exception);
		HttpStatus httpStatus = exception.getHttpStatus();
		errorLogging(exception);
		return new ResponseEntity<>(response, httpStatus);
	}

	/**
	 * @RequestBody 에 잘못된 타입을 보냈을 때
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidFormatException.class)
	private ErrorResponse handleInvalidFormatException(InvalidFormatException exception) {
		errorLogging(exception);
		return ErrorResponse.of(exception);
	}

	private void errorLogging(Exception ex) {
		log.error("Exception = {} , message = {}", ex.getClass().getSimpleName(),
			ex.getLocalizedMessage());
	}

}
