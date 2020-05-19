package com.dailyservice.whatsappbot.service.v1.impl;

import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dailyservice.whatsappbot.config.LocaleConfig;
import com.dailyservice.whatsappbot.handler.IRedisHandler;
import com.dailyservice.whatsappbot.handler.ITwiloHandler;
import com.dailyservice.whatsappbot.model.v1.LastSelectedMenu;
import com.dailyservice.whatsappbot.model.v1.WhatsappRequestBody;
import com.dailyservice.whatsappbot.model.v1.inputs.RedisTemplateInput;
import com.dailyservice.whatsappbot.repo.v1.IUserRepository;
import com.dailyservice.whatsappbot.service.v1.IMenuProcessService;
import com.dailyservice.whatsappbot.service.v1.IOrderService;
import com.dailyservice.whatsappbot.service.v1.IWhatsappHandlerService;
import com.dailyservice.whatsappbot.utils.Constants;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class WhatsappHandlerServiceImpl implements IWhatsappHandlerService{

	
	
	@Autowired
	private LocaleConfig localeConfig;
	
	@Autowired
	IUserRepository userRepo;
	
	@Autowired
	ITwiloHandler twiloHandler;
	
	@Autowired
	IRedisHandler redisHandler;
	
	@Autowired
	IMenuProcessService menuProcessService;
	
	@Autowired
	IOrderService orderService;
	
	
	
	
	@Override
	public String requestHandler(WhatsappRequestBody body) {
		log.info("inside @class WahtsappHandler @method requestHandler @param from => {}", body.getFrom());
		String from = body.getFrom();
		String res = localeConfig.get(Locale.US, Constants.GREETING);
		System.out.println("==================================");
		System.out.println("| body: " + body.getBody());
		System.out.println("| from: " + from);
		System.out.println("| to: " + body.getTo());
		System.out.println("| status:" + body.getSmsStatus());
		System.out.println("==================================");
		try {
				// User user = userRepo.findByWhatsappNumber(from);
				RedisTemplateInput userTemplate = redisHandler.get(from);
				if(userTemplate != null) {
					log.info("user name is: {} and lastMenu is: {} locals is: {}",userTemplate.getWhtsappNumber(), userTemplate.getSelectedMenu(), userTemplate.getLocale());
					
					LastSelectedMenu lastMenu = userTemplate.getSelectedMenu();
					boolean isMainMenuRequest = isMainMenuRequest(body.getBody());
					if(isMainMenuRequest) {
						userTemplate.setSelectedMenu(LastSelectedMenu.MAIN);
						redisHandler.set(from, userTemplate);
						res = localeConfig.get(Locale.US, Constants.MAIN_MENU_MSG); 
						return twiloHandler.buildWhatsappResponse(res);
					}
				    switch(lastMenu) {
				    	case MAIN:
				    		res = menuProcessService.processMainMenuRequest(userTemplate, body);
				    		break;
				    	case MENU_RESET:
				    		res = localeConfig.get(userTemplate.getLocale(), Constants.MAIN_MENU_MSG);
				    		break;
				    	case ORDER_DETAILS:
				    		res = localeConfig.get(userTemplate.getLocale(), Constants.MAIN_MENU_MSG);
				    		break;
				    	case SUBSCRIPTION_DETAILS:
				    		return "subscription details menu";
				    	case PLACE_ORDER:
				    		res = menuProcessService.processPlaceOrderRequest(userTemplate, body);
				    		break;
				    	case PLACE_ORDER_MONTHLY:
				    		res = menuProcessService.processPlaceOrderMonthlyRequestMenu(userTemplate, body);
				    		break;			    	
				    	case PLACE_ORDER_ONE_TIME: // show item category to be select
				    		res = menuProcessService.processOrderOneTimeRequest(userTemplate, body);
				    		break;
				    	case CONFIRM_QUANTITY: // get item sub category
				    		res = menuProcessService.processOrderConfirmQuantityRequest(userTemplate, body);
				    		break;
				    	case PLACE_ORDER_ITEM_CONFIRMED:
				    		res = menuProcessService.processItemQunatityRequest(userTemplate, body);
				    		break;
				    	case PLACE_ORDER_QWT_CONFIRMED: // collect user address
				    		res = menuProcessService.processSelectAddressRequest(userTemplate, body);
				    		break;
				    	case PLACE_ORDER_ADDRESS_CONFIRMED: // collect user address
				    		res = menuProcessService.processAddressConfirmedRequest(userTemplate, body);
				    		break;
				    	case USER_CONFIRMED_ORDER:
				    		if(body.getBody() != null && body.getBody().equalsIgnoreCase(Constants.CONFIRM_ORDER)) {
				    			res = orderService.processAndPlaceOrderByWhatsapp(userTemplate); 
				    		}else {
				    			res = localeConfig.get(userTemplate.getLocale(), Constants.INVALID_INPUT);
				    		}
				    		break;
				    	case LAST_ORDER_FAILED:
				    		res = localeConfig.get(Locale.US, Constants.MAIN_MENU_MSG);
				    		userTemplate.setSelectedMenu(LastSelectedMenu.MAIN);
							redisHandler.set(from, userTemplate);
				    		break;
				    	default:
				    		System.out.println("no last menu");
				    }
				} else {
					log.error("user not found with number goind to create one...");
					addUserToRedisStore(from);
					// System.out.println("user lang is==>" + u.getUserPreference().getLanguage());
					// res = localeConfig.get(Locale.US, Constants.MAIN_MENU_MSG);
				}
				return twiloHandler.buildWhatsappResponse(res);
		}catch(Exception e) {
			e.printStackTrace();
			log.error("error inside @class WhatsappHandlerService @method requestHandler => {}", e.getLocalizedMessage());
			
		}
		return null;
	}
	
	
	
	
	
	
	private boolean isMainMenuRequest(String body) {
		return body.equals("#");
	}

	public void addUserToRedisStore(String number) {
//		User newUser = new User();
//		UserPreference up = new UserPreference();
//		up.setLanguage(Locale.US);
//		up.setLastSelectedMenu(LastSelectedMenu.MAIN);
//		newUser.setUserPreference(up);
//		newUser.setWhatsappNumber(number);
		
		RedisTemplateInput redistTemp = new RedisTemplateInput();
		redistTemp.setLocale(Locale.US);
		redistTemp.setSelectedMenu(LastSelectedMenu.MAIN);
		redistTemp.setUpdateOn(new Date());
		redistTemp.setWhtsappNumber(number);
		redisHandler.set(number, redistTemp);
		//return userRepo.save(newUser);
	}

}
