package com.dailyservice.whatsappbot.model.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhatsappRequestBody {

	String SmsMessageSid;
	String NumMedia;
    String SmsSid;
	String SmsStatus;
	String Body;
	String To;
	String NumSegments;
	String MessageSid;
	String AccountSid;
	String From;
	String ApiVersion;
	String Latitude;
	String Longitude;
}
