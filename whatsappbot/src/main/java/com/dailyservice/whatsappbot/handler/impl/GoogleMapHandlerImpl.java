package com.dailyservice.whatsappbot.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dailyservice.whatsappbot.handler.IGoogleMapHandler;
import com.dailyservice.whatsappbot.handler.IRedisHandler;
import com.dailyservice.whatsappbot.model.v1.inputs.RedisTemplateInput;
import com.dailyservice.whatsappbot.service.v1.impl.MenuProcessServiceImpl;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class GoogleMapHandlerImpl implements IGoogleMapHandler {

	@Autowired
	GeoApiContext geoApiContext;
	
	@Autowired
	IRedisHandler redisHandler;
	
	
	
	@Override
	public void setUerAddressByLatLong(Float lat, Float lng, RedisTemplateInput user) {
		String defaultAddress="tejaji mohalla kodariya, Kodriya, Dr. Ambedkar Nagar, Madhya Pradesh 453441, India";
		user.setShippingAddress(defaultAddress);
	    redisHandler.set(user.getWhtsappNumber(), user);
		
		// @uncommnet this code in production
	    // @make sure to enable *Geocoding API,Places API* in google console
	    
		/*LatLng latlng = new LatLng(lat, lng);
		GeocodingApiRequest req = GeocodingApi.newRequest(geoApiContext).latlng(latlng);
		req.setCallback(new PendingResult.Callback<GeocodingResult[]>() {
			  @Override
			  public void onResult(GeocodingResult[] result) {
			    log.info("user {} address fetched succefully => {}", user.getWhtsappNumber(),result[0].formattedAddress.toString());
			    
			    user.setShippingAddress(result[0].formattedAddress.toString());
			    redisHandler.set(user.getWhtsappNumber(), user);
			  }

			  @Override
			  public void onFailure(Throwable e) {
			   log.error("error in fetch user address ==> {}", e.getLocalizedMessage());
			   e.printStackTrace();
			  }
			}); */ 
	}

}
