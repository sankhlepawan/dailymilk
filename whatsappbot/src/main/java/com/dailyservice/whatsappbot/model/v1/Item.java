package com.dailyservice.whatsappbot.model.v1;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NamedQueries({
	@NamedQuery(name="findDistinctItemByAvailable",query="select DISTINCT i.name from Item i where i.available= :available"),
	@NamedQuery(name="findBySubCategoriesAndAvailableAndQwt",query="select i from Item i where i.subCategory.name = :subCategoryName and i.available = :available and i.qwt > :qwt")
})

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wb_item")
public class Item implements Serializable{
	   
	   private static final long serialVersionUID = 1L;

	   @Id
	   @GeneratedValue(strategy = GenerationType.AUTO)
	   private long id;
	   
	   @Column(name="name")
	   private String name;
	   
	   @Column(name="qwt")
	   private int qwt;
	   
	   @Column(name="available")
	   private boolean available;
	   
	   @Column(name="price")
	   private double price;
	   
	   private transient String subCategoryName;
	   
	   @ManyToOne
	   @JoinColumn(name ="sub_category_id")
	   private SubCategory subCategory;
	   
	   @ManyToMany(targetEntity=ItemDetail.class, fetch=FetchType.EAGER,cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
	   @JoinTable(name="item_detail", joinColumns=@JoinColumn(name="item_id"), inverseJoinColumns=@JoinColumn(name="detail_id"))
	   private Set<ItemDetail> details;

		@JsonIgnore
		public SubCategory getSubCategory() {
			return subCategory;
		}

		@JsonProperty
		public void setSubCategory(SubCategory subCategory) {
			this.subCategory = subCategory;
		}

		public SubCategoryType getSubCategoryName() {
			return this.subCategory.getName();
		}

}
