package com.dailyservice.whatsappbot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.dailyservice.whatsappbot.dto.ResponseDto;
import com.google.maps.GeoApiContext;
import com.twilio.Twilio;

@SpringBootApplication
public class WhatsappbotApplication {
	
	
	 final private static String ACCOUNT_SID = "AC721e2e4c9625f45edd94b2e45de0b25c";
	 final private static String AUTH_ID = "3900a95c06d20e8e9fba0cce6427368e";
	 
	 
	 @Value("${GOOGLE_APIKEY}")
	 String apiKey;

	
	
	static {
		try {
			Twilio.init(ACCOUNT_SID, AUTH_ID);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(WhatsappbotApplication.class, args);
	}
	
	
	@Bean
    public ResourceBundleMessageSource messageSource() {

		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("lang/messages");
        // source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }
	
	
	@Bean
    public GeoApiContext getGoogleApiContext() {

		GeoApiContext context = 
				new GeoApiContext
				.Builder()
			    .apiKey(apiKey)
			    .maxRetries(2)
			    .build();
		return context;
    }
}
