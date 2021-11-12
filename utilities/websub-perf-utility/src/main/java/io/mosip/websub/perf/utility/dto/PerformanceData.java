package io.mosip.websub.perf.utility.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;


@Data
public class PerformanceData {

	
List<Long> turnAroundTime = new ArrayList<>();

}
