
package com.dailyservice.whatsappbot.service.v1.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dailyservice.whatsappbot.config.LocaleConfig;
import com.dailyservice.whatsappbot.handler.IRedisHandler;
import com.dailyservice.whatsappbot.model.v1.Category;
import com.dailyservice.whatsappbot.model.v1.Item;
import com.dailyservice.whatsappbot.model.v1.Order;
import com.dailyservice.whatsappbot.model.v1.SubCategory;
import com.dailyservice.whatsappbot.model.v1.inputs.RedisTemplateInput;
import com.dailyservice.whatsappbot.repo.v1.ICategoryRepository;
import com.dailyservice.whatsappbot.repo.v1.IItemRepository;
import com.dailyservice.whatsappbot.repo.v1.IOrderRepo;
import com.dailyservice.whatsappbot.repo.v1.ISubCategoryRepository;
import com.dailyservice.whatsappbot.service.v1.IItemService;
import com.dailyservice.whatsappbot.service.v1.IWhatsappMenuService;
import com.dailyservice.whatsappbot.utils.Constants;
import com.dailyservice.whatsappbot.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WhatsappMenuServiceImpl implements IWhatsappMenuService {

	@Autowired
	private LocaleConfig localeConfig;

	@Autowired
	IRedisHandler redisHandler;

	@Autowired
	ICategoryRepository categoryRepo;
	
	@Autowired
	ISubCategoryRepository subCategoryRepository;
	
	@Autowired
	IOrderRepo orderRepo;

	@Autowired
	IItemRepository ItemRepo;
	
	@Autowired
	IItemService ItemSrv;
	
	@Override
	public String createOrderMenu(Locale locale) {
		List<Category> items  = categoryRepo.findAllOrderByMenuOrderAsc();
		StringBuilder sb = new StringBuilder(localeConfig.get(locale, Constants.PLACE_ORDER_ONE_TIME_MENU));
		if(!items.isEmpty()) {
			for(Category cat : items) {
				sb
				.append("Type *")
				.append(cat.getMenuOrder())
				.append("* : ")
				.append(cat.getName())
			    .append(".\n");
			}
			sb.append("\n\n").append(localeConfig.get(locale, Constants.SELECT_MENU));
			
		}
		return sb.toString();
	}

	@Override
	public String createSelectPaymentMenu(Locale locale, RedisTemplateInput user) {
		log.info("inside @class WhatsappMenuSerivceImpl @method createSelectPaymentMen entry...");
		double total = 0;
		if(user.getQwt() > 0) {
				total = ItemSrv.calculatePriceByQwt(user.getQwt(), user.getItemName());
				user.setTotal(total);
		}
		redisHandler.set(user.getWhtsappNumber(),user);
		StringBuilder sb = new StringBuilder(localeConfig.get(locale, Constants.PAYMENT_CONFIRM_MENU));
		return MessageFormat.format(sb.toString(),total);
	}

	@Override
	public String createSubCategoryMenu(String name, Locale locale) {
		List<SubCategory> items  = subCategoryRepository.findByCategoryName(name);
		StringBuilder sb = new StringBuilder(localeConfig.get(locale, Constants.INVALID_INPUT));
		
		if(!items.isEmpty()) {
			sb = new StringBuilder(localeConfig.get(locale, Constants.PLACE_ORDER_ONE_TIME_MENU));
			for(SubCategory cat : items) {
				sb
				.append("Type *")
				.append(cat.getMenuOrder())
				.append("* : ")
				.append(cat.getName())
			    .append(".\n");
			}
			sb.append("\n\n").append(localeConfig.get(locale, Constants.SELECT_MENU));
			
		}
		return sb.toString();
	}

	@Override
	public String getSelectQwtMenu(SubCategory subCategory, Locale locale) {
		String menu = localeConfig.get(locale, Constants.PLACE_ORDER_QWT_MENU);
		return MessageFormat.format(menu, "Lit");
	}

	@Override
	public String getAddressConfirmMenu(Locale locale) {
		return localeConfig.get(locale, Constants.SELECT_ADDRESS_MENU);
		
	}

	@Override
	public String getUserOrders(RedisTemplateInput user, Locale locale) {
		List<Order> orders = orderRepo.findByWhtsappNumber(user.getWhtsappNumber());
		StringBuilder msg = new StringBuilder(localeConfig.get(locale, Constants.NO_ORDER_FOUND));
		if(orders!=null && !orders.isEmpty()) {
			   msg = new StringBuilder("Your order details:\n\n");
				for (Order o : orders) {
					String items = "NA";
					if(o.getItems() != null && !o.getItems().isEmpty()) {
						items = o.getItems().stream().map(item -> item.getName() + "(" + item.getQuantity() + item.getItemUnit()  + ")").collect(Collectors.joining(","));
					}
					msg.append(MessageFormat.format(
								localeConfig.get(locale,Constants.USER_ORDER_MSG),
									o.getOrderNumber(),
									Utils.formatDate(o.getOrderDate()),
									o.getStatus(),
									items,
									o.getTotalPrice())
					);
					msg.append("\n\n");
				}
				
		}
		msg.append("\n\n" + localeConfig.get(locale, Constants.SELECT_MENU));
		return msg.toString();
	}

	
	
	}
