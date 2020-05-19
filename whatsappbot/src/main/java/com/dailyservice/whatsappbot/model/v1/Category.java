package com.dailyservice.whatsappbot.model.v1;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wb_category")
public class Category implements Serializable{
   
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private int id;
   
   @Column(name = "name")
   private String name;
   
   @Column(name = "active")
   private boolean active;
   
   @Column(name = "description")
   private String description;
   
   @Column(name = "category_unit")
   @Enumerated(EnumType.STRING)
   private UnitType categoryUnit;
   
   @Column(name = "menu_order")
   private Integer menuOrder;  // this field is defined to show category in menu by order and identify user input
   
   @OneToMany(cascade=CascadeType.ALL)
   @JoinColumn(name="category_id")
   private List<SubCategory> subCategories;
   
}
