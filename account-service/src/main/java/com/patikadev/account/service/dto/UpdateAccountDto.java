package com.patikadev.account.service.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccountDto {

	private String receivedAccountType;

	private String soldAccountType;

	private BigDecimal soldAmount;

	private BigDecimal exchangeRate;

	private Long userId;
}
