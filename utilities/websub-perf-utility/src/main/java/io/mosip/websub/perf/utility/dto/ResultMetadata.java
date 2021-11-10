package io.mosip.websub.perf.utility.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;


@Data
public class ResultMetadata {

	
	private long messageCount;
	
	private double avgTurnAroundTime;
	
	private long ninetiethPercentile;

}
