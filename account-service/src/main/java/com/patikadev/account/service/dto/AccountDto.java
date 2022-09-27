package com.patikadev.account.service.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
	
	private String accountType;
	
	private BigDecimal balance;
}
