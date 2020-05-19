package com.dailyservice.whatsappbot.handler;

import java.io.UnsupportedEncodingException;

public interface ITwiloHandler {

	String  buildWhatsappResponse(String msg) throws UnsupportedEncodingException;
}
