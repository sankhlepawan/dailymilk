package com.dailyservice.whatsappbot.service.v1.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dailyservice.whatsappbot.config.LocaleConfig;
import com.dailyservice.whatsappbot.handler.IGoogleMapHandler;
import com.dailyservice.whatsappbot.handler.IRedisHandler;
import com.dailyservice.whatsappbot.model.v1.Category;
import com.dailyservice.whatsappbot.model.v1.LastSelectedMenu;
import com.dailyservice.whatsappbot.model.v1.PaymentType;
import com.dailyservice.whatsappbot.model.v1.SubCategory;
import com.dailyservice.whatsappbot.model.v1.WhatsappRequestBody;
import com.dailyservice.whatsappbot.model.v1.inputs.AddressInput;
import com.dailyservice.whatsappbot.model.v1.inputs.NumberInput;
import com.dailyservice.whatsappbot.model.v1.inputs.PlaceOrderInput;
import com.dailyservice.whatsappbot.model.v1.inputs.PlaceOrderOneTimeInput;
import com.dailyservice.whatsappbot.model.v1.inputs.RedisTemplateInput;
import com.dailyservice.whatsappbot.repo.v1.ICategoryRepository;
import com.dailyservice.whatsappbot.repo.v1.IItemRepository;
import com.dailyservice.whatsappbot.repo.v1.ISubCategoryRepository;
import com.dailyservice.whatsappbot.repo.v1.IUserRepository;
import com.dailyservice.whatsappbot.service.v1.IItemService;
import com.dailyservice.whatsappbot.service.v1.IMenuProcessService;
import com.dailyservice.whatsappbot.service.v1.IWhatsappMenuService;
import com.dailyservice.whatsappbot.utils.Constants;
import com.dailyservice.whatsappbot.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MenuProcessServiceImpl implements IMenuProcessService{

	
	@Autowired
	private LocaleConfig localeConfig;
	
	@Autowired
	IUserRepository userRepo;
	
	@Autowired
	IItemRepository itemRepo;
	
	@Autowired
	IRedisHandler redisHandler;
	
	@Autowired
	IWhatsappMenuService whatsappMenuService;
	
	@Autowired
	ICategoryRepository categoryRepo;
	
	@Autowired
	ISubCategoryRepository subCategoryRepository;
	
	@Autowired 
	IGoogleMapHandler gMapHandler;
	
	@Autowired
	IItemService itemSrv;

	
	
	@Override
	public String processMainMenuRequest(RedisTemplateInput user, WhatsappRequestBody req) {
		Locale locale = user.getLocale();
		String res = localeConfig.get(locale, Constants.INVALID_INPUT);
		try {
				Integer msgBody = Integer.parseInt(req.getBody());
				NumberInput input = new NumberInput(localeConfig, msgBody);
				String error = input.validate(locale,1,2);
				
				if(error == null) {
					// log.error("no error in bean validation==> {}", error);
					switch(input.getInput()) {
						case 1:
							user.setSelectedMenu(LastSelectedMenu.PLACE_ORDER);
							user.setUpdateOn(new Date());
							res =  localeConfig.get(locale, Constants.PLACE_ORDER_MENU);
							break;
						case 2:
							res =  whatsappMenuService.getUserOrders(user, locale);
							user.setSelectedMenu(LastSelectedMenu.ORDER_DETAILS);
							break;
						case 3:
							res =  whatsappMenuService.getUserOrders(user, locale);
							user.setSelectedMenu(LastSelectedMenu.SUBSCRIPTION_DETAILS);
							break;
					}
				}
		} catch(NumberFormatException ex) {
			log.error("Error insie @class WhatsappHandlerClass @method processMainMenuRequest error => {}", ex.getLocalizedMessage());
		}
		//userRepo.save(user);
		redisHandler.set(req.getFrom(), user);
		return res;
	}


	@Override
	public String processPlaceOrderRequest(RedisTemplateInput user, WhatsappRequestBody req) {
		Locale locale = user.getLocale();
		String res = localeConfig.get(locale, Constants.INVALID_INPUT);
		try {
				Integer msgBody = Integer.parseInt(req.getBody());
				PlaceOrderInput input = new PlaceOrderInput(localeConfig, msgBody);
				String error = input.validate(locale);
				
				
				if(error == null) {
					switch(input.getInput()) {
						case 1:
							user.setSelectedMenu(LastSelectedMenu.PLACE_ORDER_ONE_TIME);
							// res =  localeConfig.get(locale, Constants.PLACE_ORDER_MENU);
							res = whatsappMenuService.createOrderMenu(locale);
							user.setSubscription(false);
							break;
						case 2:
							user.setSelectedMenu(LastSelectedMenu.PLACE_ORDER_MONTHLY);
							user.setUpdateOn(new Date());
							// res =  localeConfig.get(locale, Constants.PLACE_ORDER_MENU);
							res =  whatsappMenuService.createOrderMenu(locale); // here menu to ask subscriotion both time or one time only
							user.setSubscription(true);
							break;
						
					}
				}
		} catch(NumberFormatException ex) {
			log.error("Error insie @class WhatsappHandlerClass @method processMainMenuRequest error => {}", ex.getLocalizedMessage());
		}
		//userRepo.save(user);
		redisHandler.set(req.getFrom(), user);
		return res;
	}


	


	private String getSubCategoryMenu(RedisTemplateInput user, int menuOrder) {
		Category cat = categoryRepo.findByMenuOrder(menuOrder);
		return whatsappMenuService.createSubCategoryMenu(cat.getName(), user.getLocale());
	}
	
	@Override
	public String processOrderOneTimeRequest(RedisTemplateInput user, WhatsappRequestBody req) {
		Locale locale = user.getLocale();
		String res = localeConfig.get(locale, Constants.INVALID_INPUT);
		try {
				Integer msgBody = Integer.parseInt(req.getBody());
				PlaceOrderOneTimeInput input = new PlaceOrderOneTimeInput(localeConfig, msgBody);
				String error = input.validate(locale);
				if(error == null) {
					res = getSubCategoryMenu(user, input.getInput());
					user.setSelectedMenu(LastSelectedMenu.CONFIRM_QUANTITY);
					user.setUpdateOn(new Date());
				}
		} catch(NumberFormatException ex) {
			log.error("Error insie @class WhatsappHandlerClass @method processMainMenuRequest error => {}", ex.getLocalizedMessage());
		}
		redisHandler.set(req.getFrom(), user);
		return res;
	}


	@Override
	public String processOrderConfirmQuantityRequest(RedisTemplateInput user, WhatsappRequestBody req) {
		Locale locale = user.getLocale();
		String res = localeConfig.get(locale, Constants.INVALID_INPUT);
		try {
			    if(req.getBody() != null) {
			    	Integer msgBody = Integer.parseInt(req.getBody());
					SubCategory subCategory = subCategoryRepository.findByMenuOrder(msgBody);
					if(subCategory != null) {
						user.setSelectedMenu(LastSelectedMenu.PLACE_ORDER_ITEM_CONFIRMED);
						user.setItemName(subCategory.getName().toString());
// here have to put item cateogy	// user.setUnit(subCategory.getCategoryUnit().getName());
						user.setUpdateOn(new Date());
						res = whatsappMenuService.getSelectQwtMenu(subCategory, locale);
					}
			    }
		} catch(NumberFormatException ex) {
			log.error("Error insie @class WhatsappHandlerClass @method processMainMenuRequest error => {}", ex.getLocalizedMessage());
		}
		//userRepo.save(user);
		redisHandler.set(req.getFrom(), user);
		return res;
	}


	@Override
	public String processPlaceOrderMonthlyRequestMenu(RedisTemplateInput user, WhatsappRequestBody req) {
		Locale locale = user.getLocale();
		String res = localeConfig.get(locale, Constants.INVALID_INPUT);
		try {
			Integer msgBody = Integer.parseInt(req.getBody());
			NumberInput input = new NumberInput(localeConfig, msgBody);
			String error = input.validate(locale,1,100);
				if (error == null) {
						user.setSelectedMenu(LastSelectedMenu.CONFIRM_QUANTITY);
						user.setUpdateOn(new Date());
					   res = getSubCategoryMenu(user, input.getInput());
				}
		 } catch (NumberFormatException ex) {
			log.error("Error insie @class WhatsappHandlerClass @method processMainMenuRequest error => {}",
					ex.getLocalizedMessage());
		}
		// userRepo.save(user);
		redisHandler.set(req.getFrom(), user);
		return res;
	}
	
    @Override
	public String processSelectAddressRequest(RedisTemplateInput user, WhatsappRequestBody body) {
    	Locale locale = user.getLocale();
		StringBuilder res = new StringBuilder(localeConfig.get(locale, Constants.INVALID_INPUT_ONLY));
		try {
			
			if(body.getLatitude() != null && body.getLongitude() != null) {
				Float lat = Float.parseFloat(body.getLatitude());
				Float lng = Float.parseFloat(body.getLongitude());
				AddressInput input = new AddressInput(lat, lng, localeConfig);
				String error = input.validate(locale,lat, lng);
				if(error == null) {
					user.setSelectedMenu(LastSelectedMenu.PLACE_ORDER_ADDRESS_CONFIRMED);
					user.setLatLng(lat + ","+ lng);
					System.out.println("Location: "+ lat + ","+ lng);
					gMapHandler.setUerAddressByLatLong(lat, lng, user);
					user.setUpdateOn(new Date());
					res = new StringBuilder(whatsappMenuService.createSelectPaymentMenu(locale, user));
				}
			} else {
				res.append(localeConfig.get(locale,Constants.SELECT_ADDRESS_MENU));
			}
			res
			.append("\n\n")
			.append(localeConfig.get(locale,Constants.SELECT_MENU)); 
						
		} catch(NumberFormatException ex) {
		   log.error("Error insie @class WhatsappHandlerClass @method processMainMenuRequest error => {}", ex.getLocalizedMessage());
	}
	// userRepo.save(user);
	redisHandler.set(body.getFrom(), user);
	return res.toString();
	}


	@Override
	public String processAddressConfirmedRequest(RedisTemplateInput user, WhatsappRequestBody req) {
		
		Locale locale = user.getLocale();
		int minInput = 1;
		int maxInput = 2;
		
		String res = localeConfig.get(locale, Constants.INVALID_INPUT);
		try {
			    Integer msgBody = Integer.parseInt(req.getBody());
				NumberInput input = new NumberInput(localeConfig, msgBody);
				String paymentMethod = PaymentType.COD.name;
				String error = input.validate(locale,minInput,maxInput);
				if(error == null) {
					// res = "You are going to order: \n\nProduct : *" + user.getQwt() +" " + user.getUnit() + " " + user.getItemName();   
					switch(input.getInput()) {
						case 1:
							user.setPaymentMethod(PaymentType.COD);
							// res += "*\n\nPayment Method : *" +PaymentType.COD.name +"*.";
							paymentMethod = PaymentType.COD.name;
							break;
						case 2:
							user.setPaymentMethod(PaymentType.UPI);
							user.setSelectedMenu(LastSelectedMenu.PAYMENT_UPI_INIT);
							paymentMethod = PaymentType.UPI.name;
							return "response to make payment";
					}
					user.setSelectedMenu(LastSelectedMenu.USER_CONFIRMED_ORDER);
					user.setUpdateOn(new Date());
					
					
					double total = itemSrv.calculatePriceByQwt(user.getQwt(), user.getItemName());
					
					
					//res += "\n\nShipping Address : *" + user.getShippingAddress();
					//res += "*\n\nTotal : Rs *200 /-*";
					//res += "\n\n Please Type *Confirm* to place order.";
					res =  MessageFormat.format(localeConfig.get(locale, Constants.CONFIRM_ORDER_MENU),user.getQwt(),user.getUnit(),user.getItemName(),paymentMethod,user.getShippingAddress(), total, user.isSubscription() ? "subscribe": "order");
					
				}
		} catch(NumberFormatException ex) {
			log.error("Error insie @class WhatsappHandlerClass @method processMainMenuRequest error => {}", ex.getLocalizedMessage());
		}
		//userRepo.save(user);
		redisHandler.set(req.getFrom(), user);
		return res;
	}


	@Override
	public String processItemQunatityRequest(RedisTemplateInput user, WhatsappRequestBody req) {
		Locale locale = user.getLocale();
		int qwt = 0;
		String res = localeConfig.get(locale, Constants.INVALID_INPUT);
		try {
			 	qwt = Integer.parseInt(req.getBody());
			 	if(qwt > 0) {
			 		user.setQwt(qwt);
			 		user.setSelectedMenu(LastSelectedMenu.PLACE_ORDER_QWT_CONFIRMED);
			 		user.setUpdateOn(new Date());
			 		res = whatsappMenuService.getAddressConfirmMenu(locale);
			 		
			 	}
		}catch(NumberFormatException ex) {
			log.error("Error insie @class WhatsappHandlerClass @method processMainMenuRequest error NumberFormatException => {}", ex.getLocalizedMessage());
		}catch(Exception e) {
			e.printStackTrace();
		}
		redisHandler.set(req.getFrom(), user);
		return res;
	}
}
