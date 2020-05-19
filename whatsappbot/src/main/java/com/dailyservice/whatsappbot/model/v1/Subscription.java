package com.dailyservice.whatsappbot.model.v1;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wb_subscription")
public class Subscription implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private User userid;
	
	@OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name = "item_id")
	private Item item;
	
	@Column(name="wb_qwt")
	private int qwt;
	
	@Column(name="wb_unit")
	private String unit;
	
	@Column(name="wb_start_date")
	private Date startDate;
	
	@Column(name="wb_end_date")
	private Date endData;
	   
	
}
