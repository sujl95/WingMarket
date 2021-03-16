package me.wingmarket.controller.request;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.wingmarket.entity.Board;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardFindRequest {

	private Board.FilterType filterType;

	private String query;

	@NotBlank
	private String location;

	@Range(min = 0, max = 10)
	private int range;

}
