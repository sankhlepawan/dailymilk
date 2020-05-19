package com.dailyservice.whatsappbot.handler.impl;


import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

import com.dailyservice.whatsappbot.handler.ITwiloHandler;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;


@Component
public class TwiloHandlerImpl implements ITwiloHandler{

	@Override
	public String buildWhatsappResponse(String msg) throws UnsupportedEncodingException {
		
		// byte ptext[] = msg.getBytes("ISO-8859-1"); 
		// String value = new String(ptext, "UTF-8"); 
		Body messageBody = new Body.Builder(msg).build();
	    Message sms = new Message.Builder().body(messageBody).build();
	    MessagingResponse twiml = new MessagingResponse.Builder().message(sms).build();
	    return twiml.toXml();
	    
	}

	
}
