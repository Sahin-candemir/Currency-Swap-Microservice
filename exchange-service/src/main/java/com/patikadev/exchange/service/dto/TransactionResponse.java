package com.patikadev.exchange.service.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponse {

	private Date transactionDate;
	
	private String receivedAccountType;
	
	private String soldAccountType;
	
	private BigDecimal receivedAmount;
	
	private BigDecimal soldAmount;
	
	private double exchangeRate;
	private Long userId;
}
