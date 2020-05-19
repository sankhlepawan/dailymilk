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
@Table(name = "wb_payment")
public class Payment implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long paymentID;
	
	@Column(name="transection_status")
	private TransectionStatusType transectionStatus;
	
	
	@Column(name="allowed")
	private boolean allowed;
	
	@Column(name="type")
	@Enumerated(EnumType.STRING)
	private PaymentType type;
	

}
