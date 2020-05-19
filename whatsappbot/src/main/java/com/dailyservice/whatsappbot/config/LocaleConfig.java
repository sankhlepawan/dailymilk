package com.dailyservice.whatsappbot.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LocaleConfig implements WebMvcConfigurer {
	
	
	
	@Autowired
    MessageSource messageSource;
	
	public String get(Locale locale, String code) {
		
		// System.out.println("localte in contesxt =>" + LocaleContextHolder.getLocale());
		// log.info("{}", messageSource.getMessage("choose_lang",null, new Locale("hi", "IN")));
		String msg = messageSource.getMessage(code,null,locale);
		log.info("lang =>{} @msg => {}",locale, code);
		return msg;
		
	}
}