package com.dailyservice.whatsappbot.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include. NON_NULL)
public class ResponseDto<T> {
	
	Iterable<T> items;
	List<T> data;
	long count;
	String status;
	
	ResponseDto(Iterable<T> obj1) 
    { 
        this.items = obj1; 
    }
	
	ResponseDto(List<T> obj1) 
    { 
        this.data = obj1; 
    }
	
	public ResponseDto(Iterable<T> obj1, long l) 
    { 
        this.items = obj1; 
        this.count = l;
    }
	
	public ResponseDto(List<T> obj1, long count) 
    { 
        this.items = obj1; 
        this.count = count;
    }

}	
