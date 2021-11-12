package io.mosip.websub.perf.utility.service.impl;


import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.mosip.websub.perf.utility.dto.PerformanceData;
import io.mosip.websub.perf.utility.dto.RequestDTO;
import io.mosip.websub.perf.utility.dto.ResultMetadata;
import io.mosip.websub.perf.utility.service.WebsubCallbackService;


@Service
public class WebsubCallbackServiceImpl implements WebsubCallbackService {

	
	//private static final Logger LOGGER = LoggerFactory.getLogger(WebsubCallbackServiceImpl.class);
	private static final String UTC_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	private Map<String, PerformanceData> cache = new HashMap<>();
	
	@Override
	public void compute(RequestDTO requestDTO,String subID) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(UTC_DATETIME_PATTERN);
		LocalDateTime timeStamp= LocalDateTime.parse(requestDTO.getTimestamp(), formatter);
		LocalDateTime timeNow= LocalDateTime.now(ZoneOffset.UTC);
		long millis=ChronoUnit.MILLIS.between(timeStamp,timeNow);
		// added sync block for thread safe alter of hashmap and perf data;
		synchronized (cache) {
			if(cache.containsKey(subID)) {
				PerformanceData performenceData = cache.get(subID);
				performenceData.getTurnAroundTime().add(millis);
			}else {
				PerformanceData performenceData = new PerformanceData();
				performenceData.getTurnAroundTime().add(millis);
				cache.put(subID, performenceData);
			}
		}
	}

	@Override
	public ResultMetadata getResult(String subID) {
		PerformanceData performenceData= cache.get(subID);
		ResultMetadata metadata= new ResultMetadata();
		List<Long> tat= performenceData.getTurnAroundTime();
		Collections.sort(tat);
	    long sum=tat.parallelStream().collect(Collectors.summingLong(Long::longValue));
		int noOfRequest = tat.size();
		metadata.setMessageCount(noOfRequest);
		metadata.setAvgTurnAroundTime(sum/noOfRequest);
		metadata.setSubID(subID);
		int index=(int) (0.90*noOfRequest);
		metadata.setNinetiethPercentile(tat.get(index));
		return metadata;	
	}

	@Override
	public void reset() {
		cache = new HashMap<>();
	}

}
