package com.dailyservice.whatsappbot.model.v1;

import java.io.Serializable;
import java.util.Date;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wb_order")
public class Order implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderID;
	
	
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="order_number")
	private String orderNumber;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="isWhtsappNumber")
	private boolean whtsappNumber = false;
	
	
	@Column(name="payment_id")
	private Payment paymentId;
	
	@Column(name="order_date")
	private Date orderDate;
	
	@Column(name="ship_date")
	private Date shipDate;
	
	
	@Column(name="isFulfilled")
	private boolean fulfilled;
	
	@Column(name="isDeleted")
	private boolean deleted;
	
	@Column(name="isPaid")
	private boolean paid;
	
	@Column(name="payment_date")
	private Date paymentDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private OrderStatusType status;
	
	@OneToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name="order_id")
	private List<OrderItem> items;
	
	
	@Column(name="shipping_address")
	private String shippingAddress;
	
	@Column(name="total_price")
	private double totalPrice;
	
}
