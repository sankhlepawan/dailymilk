package com.dailyservice.whatsappbot.model.v1;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wb_sub_category")
public class SubCategory {

	   @Id
	   @GeneratedValue(strategy = GenerationType.AUTO)
	   private int id;
	   
	   @Column(name = "name")
	   @Enumerated(EnumType.STRING)
	   private SubCategoryType name;
	   
	   
	   @Column(name = "active")
	   private boolean active;
	   
	   @Column(name = "description")
	   private String description;
	   
	   @Column(name = "menu_order")
	   private Integer menuOrder;  // this field is defined to show category in menu by order and identify user input
	   
	   @Column(name = "category_id")
	   private int categoryid;
}
