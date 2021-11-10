package io.mosip.websub.perf.utility.service;


import org.springframework.stereotype.Service;

import io.mosip.websub.perf.utility.dto.RequestDTO;
import io.mosip.websub.perf.utility.dto.ResultMetadata;


@Service
public interface WebsubCallbackService {

	 void compute(RequestDTO requestDTO, String subID);

	 ResultMetadata getResult(String subID);

	void reset();

}
