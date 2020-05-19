package com.dailyservice.whatsappbot.model.v1;

import java.io.Serializable;

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
@Table(name = "wb_item_detail")
public class ItemDetail implements Serializable{

	private static final long serialVersionUID = 1L;

	   @Id
	   @GeneratedValue(strategy = GenerationType.AUTO)
	   private long id;
	   
	   @Enumerated(EnumType.STRING)
	   @Column(name = "item_detail_name")
	   private ItemDetailType name;
	   
	   @Column(name = "item_detail_value")
	   private String value;
}
