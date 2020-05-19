package com.dailyservice.whatsappbot.service.v1;

import com.dailyservice.whatsappbot.model.v1.WhatsappRequestBody;

public interface IWhatsappHandlerService {

	String requestHandler(WhatsappRequestBody body);
}
