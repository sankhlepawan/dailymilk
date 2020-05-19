package com.dailyservice.whatsappbot.model.v1;

import java.io.Serializable;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wb_order_item")
public class OrderItem implements Serializable{

	private static final long serialVersionUID = 1L;

	   @Id
	   @GeneratedValue(strategy = GenerationType.AUTO)
	   private Long id;
	   
	   @Column(name="name")
	   private String name;
	   
	   @Column(name="quantity")
	   private int quantity;
	   
	   @Column(name="unit")
	   private String itemUnit;
	   
	   @Column(name="product_id")
	   private Long itemId;
	   
	   @Column(name="order_id")
	   private Long orderId;
}
