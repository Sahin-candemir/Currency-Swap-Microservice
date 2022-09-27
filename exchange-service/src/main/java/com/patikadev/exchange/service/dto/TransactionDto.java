package com.patikadev.exchange.service.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
public class TransactionDto {

	private Date transactionDate;

	private BigDecimal amount;
}
