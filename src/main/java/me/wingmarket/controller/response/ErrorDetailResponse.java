package me.wingmarket.controller.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorDetailResponse {

	private final String field;
	private final String value;
	private final String reason;

	public static List<ErrorDetailResponse> of(String field, String value, String reason) {
		List<ErrorDetailResponse> fieldErrors = new ArrayList<>();
		fieldErrors.add(new ErrorDetailResponse(field, value, reason));
		return fieldErrors;
	}

	public static List<ErrorDetailResponse> of(BindingResult bindingResult) {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		return fieldErrors.stream()
			.map(error -> new ErrorDetailResponse(
				error.getField(),
				error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
				error.getDefaultMessage() == null ? "" : toDefaultMessage(error)))
			.collect(Collectors.toList());
	}

	public static String toDefaultMessage(FieldError error) {
		String message = Objects.requireNonNull(error.getDefaultMessage());
		String resultMessage;
		if (message.contains("No enum")) {
			resultMessage = "Enum Type 이 일치하지 않습니다.";
		} else if (message.contains("^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")) {
			resultMessage = "전화번호 형식이 일치하지 않습니다";
		} else if (message.contains("^[가-힣ㄱ-ㅎa-zA-Z0-9._ -]{2,20}$")) {
			resultMessage = "닉네임 형식이 일치하지 않습니다";
		} else if (message.contains("^[a-zA-Z][a-zA-Z0-9_]{4,20}$")) {
			resultMessage = "아이디 형식이 일치하지 않습니다";
		} else {
			resultMessage = error.getDefaultMessage();
		}
		return resultMessage;
	}
}

