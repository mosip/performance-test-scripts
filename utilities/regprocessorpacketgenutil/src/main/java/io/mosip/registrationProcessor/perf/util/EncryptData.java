package io.mosip.registrationProcessor.perf.util;

import java.io.File;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;

import io.mosip.dbdto.CryptomanagerDto;
import io.mosip.dbdto.DecrypterDto;
import io.mosip.dbdto.RegistrationPacketSyncDTO;
import io.mosip.dbdto.SyncRegistrationDto;

public class EncryptData {
//	private String applicationId = "REGISTRATION";
//	ObjectMapper objectMapper = new ObjectMapper();
	private static Logger logger = Logger.getLogger(EncryptData.class);

	@SuppressWarnings("unchecked")
	public JSONObject encodeData(RegistrationPacketSyncDTO registrationPacketSyncDto) {
		String applicationId = "REGISTRATION";
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		String outputJson = "";
		try {
			outputJson = objectMapper.writeValueAsString(registrationPacketSyncDto);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] byteArray = outputJson.getBytes();
		String encryptedString = Base64.encodeBase64URLSafeString(byteArray);
		JSONObject encryptRequest = new JSONObject();
		CryptomanagerDto cryptoReq = new CryptomanagerDto();
		JSONObject cryptographicRequest = new JSONObject();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmssSSS");

		DecrypterDto decrypterDto = new DecrypterDto();

		String registrationId = registrationPacketSyncDto.getSyncRegistrationDTOs().get(0).getRegistrationId()
				.toString();

		String referenceId = registrationId.substring(0, 5) + "_" + registrationId.substring(5, 10);

		try {

			decrypterDto.setApplicationId(applicationId);
			decrypterDto.setReferenceId(referenceId);
			decrypterDto.setData(encryptedString);
			decrypterDto.setTimeStamp(getTime(registrationId));
			cryptoReq.setRequesttime(getTime(registrationId));
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			cryptographicRequest.put("applicationId", applicationId);
			cryptographicRequest.put("data", encryptedString);
			cryptographicRequest.put("referenceId", referenceId);
			cryptographicRequest.put("timeStamp", decrypterDto.getTimeStamp().atOffset(ZoneOffset.UTC).toString());
			encryptRequest.put("id", "mosip.registration.sync");
			encryptRequest.put("metadata", "");
			encryptRequest.put("request", cryptographicRequest);
			encryptRequest.put("requesttime", cryptoReq.getRequesttime().atOffset(ZoneOffset.UTC).toString());
			encryptRequest.put("version", "1.0");
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptRequest;
	}

	/*
	 * public static void main(String[] args) { File f=new File(
	 * "D:\\sprint_10\\mosip\\automationtests\\src\\test\\resources\\regProc\\Packets\\ValidPackets\\packteForInvalidPackets\\10011100110001920190514120310.zip"
	 * ); EncryptData e=new EncryptData(); RegistrationPacketSyncDTO
	 * dto=e.createSyncRequest(f); System.out.println(dto.toString());
	 * System.out.println(e.encryptData(dto)); }
	 */
	public RegistrationPacketSyncDTO createSyncRequest(File f, String regType) throws ParseException {
		String regId = f.getName().substring(0, f.getName().lastIndexOf("."));
		HashSequenceUtil hashSeqUtil = new HashSequenceUtil();
		String packetHash = hashSeqUtil.getPacketHashSequence(f);
		SyncRegistrationDto syncRegistrationDto = new SyncRegistrationDto();
		syncRegistrationDto.setLangCode("eng");
		syncRegistrationDto.setPacketHashValue(packetHash);
		syncRegistrationDto.setPacketSize(BigInteger.valueOf(f.length()));
		syncRegistrationDto.setRegistrationId(regId);
		syncRegistrationDto.setRegistrationType(regType);
		syncRegistrationDto.setSupervisorComment("APPROVED");
		syncRegistrationDto.setSupervisorStatus("APPROVED");
		List<SyncRegistrationDto> syncRegistrationList = new ArrayList<SyncRegistrationDto>();
		syncRegistrationList.add(syncRegistrationDto);
		RegistrationPacketSyncDTO registrationPacketSyncDto = new RegistrationPacketSyncDTO();
		registrationPacketSyncDto.setId("mosip.registration.sync");

		registrationPacketSyncDto.setRequesttime(getTime(regId).toString() + "Z");
		// registrationPacketSyncDto.setRequesttime(getCurrTime());
		registrationPacketSyncDto.setVersion("1.0");
		registrationPacketSyncDto.setSyncRegistrationDTOs(syncRegistrationList);
		logger.info("Sync JSON is " + new Gson().toJson(registrationPacketSyncDto));
		return registrationPacketSyncDto;
	}

	public String getCurrTime(PropertiesUtil prop) {
		LocalDateTime ldt = null;
		// String srcTimeStr = "2019-07-22T13:22:30.000";
		String srcTimeStr = prop.PACKET_UPLOAD_TIME;
		String timestamp = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		Random r = new Random();
		Date currDate = new Date();
		try {
			currDate = formatter.parse(srcTimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDate);
		int randomMinute = 0;
		int randMin_min = 30;
		int randMin_max = 155;
		randomMinute = r.nextInt(randMin_max - randMin_min) + randMin_min;

		cal.add(Calendar.MINUTE, -randomMinute);
		Date date = cal.getTime();
		// SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		timestamp = formatter.format(date);
		timestamp = timestamp + "Z";
		logger.info("Time added to sync request is " + timestamp);
		return timestamp;
	}

	public LocalDateTime getTime(String registrationId) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmssSSS");
		String packetCreatedDateTime = registrationId.substring(registrationId.length() - 14);
		int n = 100 + new Random().nextInt(900);
		String milliseconds = String.valueOf(n);

		Date date = formatter.parse(packetCreatedDateTime.substring(0, 8) + "T"
				+ packetCreatedDateTime.substring(packetCreatedDateTime.length() - 6) + milliseconds);
		LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		logger.info("Time added to sync request is " + ldt.toString() + "Z");
		return ldt;
	}

	public RegistrationPacketSyncDTO createSyncRequest(JSONObject jsonRequest) throws ParseException {
		String regId = null;
		String packetHash = null;
		long packetSize = 0;
		String langCode = null;
		String registrationType = null;
		String superVisiorStatus = null;
		JSONArray request = (JSONArray) jsonRequest.get("request");
		for (int j = 0; j < request.size(); j++) {
			JSONObject obj = (JSONObject) request.get(j);
			regId = obj.get("registrationId").toString();
			packetHash = obj.get("packetHashValue").toString();
			packetSize = (long) obj.get("packetSize");
			langCode = obj.get("langCode").toString();
			registrationType = obj.get("registrationType").toString();
			superVisiorStatus = obj.get("supervisorStatus").toString();
		}
		String id = jsonRequest.get("id").toString();
		String version = jsonRequest.get("version").toString();
		String requesttime = jsonRequest.get("requesttime").toString();

		SyncRegistrationDto syncRegistrationDto = new SyncRegistrationDto();
		syncRegistrationDto.setLangCode(langCode);
		syncRegistrationDto.setPacketHashValue(packetHash);
		syncRegistrationDto.setPacketSize(BigInteger.valueOf(packetSize));
		syncRegistrationDto.setRegistrationId(regId);
		syncRegistrationDto.setRegistrationType(registrationType);
		syncRegistrationDto.setSupervisorComment("APPROVED");
		syncRegistrationDto.setSupervisorStatus(superVisiorStatus);
		List<SyncRegistrationDto> syncRegistrationList = new ArrayList<SyncRegistrationDto>();
		syncRegistrationList.add(syncRegistrationDto);
		RegistrationPacketSyncDTO registrationPacketSyncDto = new RegistrationPacketSyncDTO();
		registrationPacketSyncDto.setId(id);

		// LocalDateTime requestTime=LocalDateTime.ofInstant(currentDate.toInstant(),
		// ZoneId.systemDefault());
		registrationPacketSyncDto.setRequesttime(requesttime);
		registrationPacketSyncDto.setVersion(version);
		registrationPacketSyncDto.setSyncRegistrationDTOs(syncRegistrationList);
		return registrationPacketSyncDto;
	}
}
