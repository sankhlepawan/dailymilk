package com.dailyservice.whatsappbot.controller.v1;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dailyservice.whatsappbot.model.v1.WhatsappRequestBody;
import com.dailyservice.whatsappbot.service.v1.IWhatsappHandlerService;
import com.twilio.twiml.TwiMLException;

@Controller
@RequestMapping(value = { "/v1" })
public class WhatsappHandlerController {

	@Autowired
	IWhatsappHandlerService service; 
	
	

	@PostMapping(value="/incoming")
	public void handleIncoming(WhatsappRequestBody body, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws TwiMLException{
		
		String res =  service.requestHandler(body);
		httpServletResponse.setContentType("application/xml");
		httpServletResponse.setStatus(200);
		try {
			
			httpServletResponse.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
