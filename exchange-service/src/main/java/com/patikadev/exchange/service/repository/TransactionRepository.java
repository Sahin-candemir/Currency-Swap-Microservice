package com.patikadev.exchange.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patikadev.exchange.service.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	List<Transaction> findAllByUserId(Long userId);

	List<Transaction> findAllByAccountTypeAndUserId(String accountType, Long userId);

}
