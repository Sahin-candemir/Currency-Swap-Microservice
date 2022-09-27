package com.patikadev.exchange.service.service;

import java.util.List;

import com.patikadev.exchange.service.dto.TransactionDto;
import com.patikadev.exchange.service.dto.TransactionRequest;
import com.patikadev.exchange.service.dto.TransactionResponse;

public interface TransactionService {

	TransactionResponse createTransaction(TransactionRequest transactionRequest, String req);

	List<TransactionDto> getAllTransactionByUserId(Long userId,String req);

	List<TransactionDto> getAllTransactionByUserId(String accountType, Long userId,String req);

}
