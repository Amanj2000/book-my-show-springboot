package com.bookmyshow.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShowRequestDTO {
	@NotNull @Future
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date date;

	@NotNull
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date startTime;

	@NotNull
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date endTime;

	@NotNull
	private Integer movieId;

	@NotNull @Positive
	private Integer price;
}
