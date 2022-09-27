package com.patikadev.exchange.service.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
	
	private String receivedAccountType;
	
	private String soldAccountType;
	
	private BigDecimal soldAmount;
	
	private double exchangeRate;
	
	private Long userId;

}
