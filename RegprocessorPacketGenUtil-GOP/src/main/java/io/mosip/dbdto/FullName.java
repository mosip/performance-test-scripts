package io.mosip.dbdto;

import java.util.List;

import io.mosip.registrationProcessor.perf.regPacket.dto.FieldData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullName {

	List<FieldData> firstName;
	List<FieldData> middleName;
	List<FieldData> lastName;
	List<FieldData> suffix;
}
