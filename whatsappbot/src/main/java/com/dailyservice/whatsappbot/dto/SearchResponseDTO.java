package com.dailyservice.whatsappbot.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponseDTO<T> {
	
	
	private List<T> items = new ArrayList<T>();
	private Long count;
	
 }
