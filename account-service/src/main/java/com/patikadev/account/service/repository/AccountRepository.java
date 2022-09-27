package com.patikadev.account.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patikadev.account.service.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	List<Account> findAllAccountByUserId(Long userId);
	Account findByUserIdAndAccountType(Long userId,String AccountType);
	Boolean existsByAccountTypeAndUserId(String accountType,Long userId);
}
