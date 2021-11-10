package io.mosip.websub.perf.utility.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;


@Data
public class RequestDTO {

	@NotBlank
	private String timestamp;
	
	@NotBlank
	private String messageID;

}
