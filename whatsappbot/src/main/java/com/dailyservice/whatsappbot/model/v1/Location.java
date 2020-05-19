package com.dailyservice.whatsappbot.model.v1;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wb_location")
public class Location {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private double id;
	
	@Column(name="lat")
	private String lat;
	
	@Column(name="lng")
	private String lng;

}
