package com.dailyservice.whatsappbot.model.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhatsappResponseBody {

	String from;
	String to;
	String message;
}
