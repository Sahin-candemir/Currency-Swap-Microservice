package com.patikadev.account.service.service;

import java.util.List;

import com.patikadev.account.service.dto.AccountDto;
import com.patikadev.account.service.dto.ExchageRate;
import com.patikadev.account.service.dto.UpdateAccountDto;

public interface AccountService {

	AccountDto createAccount(Long userId, AccountDto accountDto);

	List<AccountDto> getAllAccountByUserId(Long userId);

	AccountDto getAccountByAccountTypeAndUserId(Long userId,String accountType);

	void updateAccount(UpdateAccountDto updateAccountDto);

	ExchageRate generateExchangeRate();

}
