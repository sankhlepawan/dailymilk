package com.dailyservice.whatsappbot.service.v1;

import java.util.Locale;

import com.dailyservice.whatsappbot.model.v1.SubCategory;
import com.dailyservice.whatsappbot.model.v1.inputs.RedisTemplateInput;

public interface IWhatsappMenuService {

	String createOrderMenu(Locale locale);

	String createSelectPaymentMenu(Locale locale, RedisTemplateInput user);

	String createSubCategoryMenu(String name, Locale locale);

	String getSelectQwtMenu(SubCategory subCategory, Locale locale);

	String getAddressConfirmMenu(Locale locale);

	String getUserOrders(RedisTemplateInput user, Locale locale);

}
