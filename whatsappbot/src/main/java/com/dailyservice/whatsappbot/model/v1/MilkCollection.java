package com.dailyservice.whatsappbot.model.v1;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "wb_milk_collection")
public class MilkCollection implements Serializable{

	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@Column(name="animal_type")
	@Enumerated(EnumType.STRING)
	private AnimalType type;
	
	@Column(name="test_mode")
	@Enumerated(EnumType.STRING)
	private TestModeType testMode;
	
	@Column(name="collection_status")
	@Enumerated(EnumType.STRING)
	private CollectionStatusType status;
	
	@Column(name="fat")
	private double fat;
	
	@Column(name="snf")
	private double snf;
	
	@Column(name="qwt")
	private double qwt;
	
	@Column(name="rate")
	private double rate;
	
	@Column(name="amount")
	private double amount;
	
	@Column(name="density")
	private double density;
	
	@Column(name="lactose")
	private double lactose;
	
	@Column(name="salts")
	private double salts;
	
	@Column(name="protein")
	private double protein;
	
	@Column(name="temp")
	private double temp;
	
	@Column(name="water")
	private double water;
	
	@Column(name="created_on")
	private Date createdOn;
	
	
	
}
