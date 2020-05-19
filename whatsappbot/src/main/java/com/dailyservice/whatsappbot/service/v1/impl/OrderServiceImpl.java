package com.dailyservice.whatsappbot.service.v1.impl;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dailyservice.whatsappbot.config.LocaleConfig;
import com.dailyservice.whatsappbot.dto.ResponseDto;
import com.dailyservice.whatsappbot.handler.IRedisHandler;
import com.dailyservice.whatsappbot.model.v1.Address;
import com.dailyservice.whatsappbot.model.v1.Item;
import com.dailyservice.whatsappbot.model.v1.LastSelectedMenu;
import com.dailyservice.whatsappbot.model.v1.Order;
import com.dailyservice.whatsappbot.model.v1.OrderItem;
import com.dailyservice.whatsappbot.model.v1.OrderStatusType;
import com.dailyservice.whatsappbot.model.v1.Subscription;
import com.dailyservice.whatsappbot.model.v1.User;
import com.dailyservice.whatsappbot.model.v1.UserPreference;
import com.dailyservice.whatsappbot.model.v1.inputs.RedisTemplateInput;
import com.dailyservice.whatsappbot.repo.v1.IItemRepository;
import com.dailyservice.whatsappbot.repo.v1.IOrderItemRepository;
import com.dailyservice.whatsappbot.repo.v1.IOrderRepo;
import com.dailyservice.whatsappbot.repo.v1.ISubscriptionRepository;
import com.dailyservice.whatsappbot.service.v1.IItemService;
import com.dailyservice.whatsappbot.service.v1.IOrderService;
import com.dailyservice.whatsappbot.service.v1.IUserService;
import com.dailyservice.whatsappbot.utils.Constants;
import com.dailyservice.whatsappbot.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements IOrderService{

	@Autowired
	IUserService userSrv;
	
	@Autowired
	IOrderRepo orderRepo;
	
	@Autowired
	IItemRepository ItemRepo;
	
	@Autowired
	IOrderItemRepository orderItemRepo;
	
	@Autowired
	private LocaleConfig localeConfig;
	
	@Autowired
	IRedisHandler redisHandler;
	
	@Autowired
	ISubscriptionRepository subscriptionRepo;
	
	
	@Override
	public Order create(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean isAlreadyPlaced(RedisTemplateInput user) {
		List<Order> oldOrder = orderRepo.findByWhtsappNumber(user.getWhtsappNumber());
		boolean placed = false;
		if (oldOrder != null && !oldOrder.isEmpty()) {
			
			for (Order o : oldOrder) {
				// System.out.println("o.getStatus() not equal OrderStatusType.COMPLETED =" + !o.getStatus().equals(OrderStatusType.COMPLETED));
				if (!o.getStatus().equals(OrderStatusType.COMPLETED)) {
					for (OrderItem i : o.getItems()) {
						// System.out.println(i.getName() + " " + user.getItemName());
						// System.out.println(i.getQuantity() + " " + user.getQwt());
						if (i.getName().equals(user.getItemName()) && i.getQuantity() == user.getQwt()) {
							placed = true;
						}
					}
				}
			}
		}else {
			System.out.println("no old order find by number "+user.getWhtsappNumber());
		}
		return placed;
	}
	@Override
	public String processAndPlaceOrderByWhatsapp(RedisTemplateInput user) {
		log.info("inside @class orderServiceImpl @method processAndPlaceOrderByWhatsapp going to place order for user {}", user.getWhtsappNumber());
		String res = localeConfig.get(user.getLocale(), Constants.INVALID_ORDER);
		String key = user.getWhtsappNumber();
		try {
			 
			  User newUser = createNewUser(user);
			  if(newUser != null) {
				  log.info("inside @class orderServiceImpl @method processAndPlaceOrderByWhatsapp new user create with id: {}", newUser.getId());
				  Order order = null;
				  if(isAlreadyPlaced(user)) {
					  return MessageFormat.format(localeConfig.get(user.getLocale(), Constants.ALREADY_PLACED_ORDER), user.getItemName(), user.getQwt(), user.getUnit());
				  }
				  if(!user.isSubscription()) { // plase order one time
					  order = createOrderByWhatsapp(user,newUser);
					  if(order != null) {
						  redisHandler.set(key, new RedisTemplateInput());
						  res = MessageFormat.format(localeConfig.get(user.getLocale(), Constants.ORDER_PLACED_CONFIRMED), order.getOrderNumber());
					  } else {
						  user.setSelectedMenu(LastSelectedMenu.LAST_ORDER_FAILED);
						  res = MessageFormat.format(localeConfig.get(user.getLocale(), Constants.ITEM_NOT_AVAILABLE), user.getItemName());
					  }
				  } else { // to subscribe item
					  res = subscribeItem(user,newUser);
				  }
				}else {
				  log.error("inside @class orderServiceImpl @method processAndPlaceOrderByWhatsapp could not create new user with number: {}", user.getWhtsappNumber());
				  user.setSelectedMenu(LastSelectedMenu.LAST_ORDER_FAILED);
			  }
		}catch(Exception e) {
			e.printStackTrace();
		}
		redisHandler.set(key, user);
		return res;
	}
	
	private String subscribeItem(RedisTemplateInput user, User newUser) {
		List<Item> items = ItemRepo.findBySubCategoriesAndAvailableAndQwt(user.getItemName(), true, 0);
		String res = localeConfig.get(user.getLocale(),Constants.SUBSCRIPTION_FAILED);
		if(items != null) {
			Item item = items.get(0);
			res = localeConfig.get(user.getLocale(),Constants.SUBSCRIPTION_SUCESS);
			Date startDate = getOrderDate();
			Date endDate = getSubscriptionEntDate();
			Subscription sub = new Subscription();	
			sub.setStartDate(startDate);
			sub.setEndData(endDate);
			sub.setQwt(user.getQwt());
			sub.setUnit(user.getUnit());
			sub.setItem(item);
			subscriptionRepo.save(sub);
			res += MessageFormat.format(localeConfig.get(user.getLocale(),
					Constants.SUBSCRIPTION_ITEM_DETAILS),
					item.getSubCategory().getName(),
					user.getQwt(),
					user.getUnit(),
					Utils.formatDate(startDate),
					Utils.formatDate(endDate));
			res += "\n\n";
			res += localeConfig.get(user.getLocale(),
					Constants.SELECT_MENU);
		} else {
			res = MessageFormat.format(localeConfig.get(user.getLocale(), Constants.ITEM_NOT_AVAILABLE), user.getItemName());
			log.error("inside @class orderServiceImpl @method createOrderItems item not found with name: {}", user.getItemName());
			
		}
		return res;
	}

	private String generateOrderId(User u) {
		return UUID.randomUUID().toString();
	}
	
	private Date getOrderDate() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 1);
		Date orderDate = c.getTime();
		return orderDate;
	}
	
	private Date getSubscriptionEntDate() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 30);
		Date orderDate = c.getTime();
		return orderDate;
	}

	private Order createOrderByWhatsapp(RedisTemplateInput redisUser, User dbUser) {
		log.info("inside @class orderServiceImpl @method createOrderByWhatsapp entry...");
		Order dbOrder = new Order();
		dbOrder.setDeleted(false);
		dbOrder.setFulfilled(false);
		dbOrder.setOrderDate(getOrderDate());
		dbOrder.setOrderNumber(generateOrderId(dbUser));
		dbOrder.setStatus(OrderStatusType.PLACED);
		dbOrder.setShippingAddress(redisUser.getShippingAddress());
		dbOrder.setPaid(false);
		dbOrder.setUserId(dbUser.getId());
		dbOrder.setMobile(dbUser.getMobile());
		dbOrder.setWhtsappNumber(true);
		dbOrder = createOrderItems(dbOrder,redisUser);
		if(dbOrder.getStatus().equals(OrderStatusType.ITEM_NOT_FOUND)) {
			 log.error("error while placing order item not found with name => {}", redisUser.getItemName());
			 dbOrder = null;
			 return null;
		}
		dbOrder = orderRepo.save(dbOrder);
		return dbOrder;
		
	}

	private Order createOrderItems(Order dbOrder, RedisTemplateInput redisUser) {
		log.info("inside @class orderServiceImpl @method createOrderItems entry...");
		OrderItem oi = new OrderItem();
		List<Item> items = ItemRepo.findBySubCategoriesAndAvailableAndQwt(redisUser.getItemName(), true,0);
		if(items != null) {
			Item item = items.get(0);
			oi.setName(item.getName());
			oi.setItemId(item.getId());
			oi.setOrderId(dbOrder.getOrderID());
			oi.setQuantity(redisUser.getQwt());
			oi.setItemUnit(redisUser.getUnit());
			double total = Utils.calculateItemTotal(redisUser.getQwt(), item.getPrice());
			dbOrder.setTotalPrice(total);
			dbOrder.setItems(Arrays.asList(oi));
			// orderItemRepo.save(oi);
		} else {
			log.error("inside @class orderServiceImpl @method createOrderItems item not found with name: {}", redisUser.getItemName());
			dbOrder.setStatus(OrderStatusType.ITEM_NOT_FOUND);
			// orderRepo.save(dbOrder);
		}
		return dbOrder;
	}

	private User createNewUser(RedisTemplateInput user) {
		log.info("inside @class orderServiceImpl @method createNewUser entry..");
		try {
				User dbUser = userSrv.findByWhatsappNumber(user.getWhtsappNumber());
				Address address = new Address();
				address.setLocation(user.getLatLng());
				address.setFullAddress(user.getShippingAddress());
				if(dbUser == null) {
					UserPreference pre = new UserPreference();
					pre.setLastSelectedMenu(user.getSelectedMenu() != null ? user.getSelectedMenu(): LastSelectedMenu.MAIN);
					pre.setLanguage(user.getLocale());
					
					dbUser = new User();
					dbUser.setAddress(address);
					dbUser.setUserPreference(pre);
					dbUser.setMobile(dbUser.getMobile());
					dbUser.setWhatsappNumber(true);
				} else {
					dbUser.setAddress(address);
				}
				return userSrv.save(dbUser);
		}catch(Exception ex) {
			ex.printStackTrace();
			log.info("Error inside @method createNewUser Error => {}",ex.getLocalizedMessage());
		}
		return null;
	}

	@Override
	public ResponseDto<Order> findAll() {
		// return orderRepo.findAll();
		return new ResponseDto<Order>(orderRepo.findAll(), orderRepo.count());
	}

	@Override
	public Order findById(Long orderId) {
		return orderRepo.findById(orderId).orElse(null);
	}

	@Override
	public Order update(Order order) {
		Order dbOrder = orderRepo.findById(order.getOrderID()).orElse(null);
		if(dbOrder != null) {
			
			dbOrder.setDeleted(order.isDeleted());
			dbOrder.setFulfilled(order.isFulfilled());
			dbOrder.setPaid(order.isPaid());
			dbOrder.setStatus(order.getStatus() != null ? order.getStatus() : dbOrder.getStatus());
			dbOrder.setPaymentDate(order.getPaymentDate() != null ? order.getPaymentDate() : dbOrder.getPaymentDate());
			dbOrder.setShipDate(order.getShipDate() != null ? order.getShipDate() : dbOrder.getShipDate());
			return orderRepo.save(dbOrder);
		}else {
			// thow error here
		}
		return order;
		
	}

}
