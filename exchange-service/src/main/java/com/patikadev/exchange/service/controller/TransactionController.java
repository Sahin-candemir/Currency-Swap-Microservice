package com.patikadev.exchange.service.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.patikadev.exchange.service.dto.TransactionDto;
import com.patikadev.exchange.service.dto.TransactionRequest;
import com.patikadev.exchange.service.dto.TransactionResponse;
import com.patikadev.exchange.service.service.TransactionService;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

	private TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	// @CrossOrigin
	@PostMapping //
	public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest transactionRequest,
			@RequestHeader HttpHeaders req) {

		return new ResponseEntity<>(
				transactionService.createTransaction(transactionRequest, req.getFirst("Authorization")),
				HttpStatus.CREATED);
	}

	@GetMapping("/{userId}")
	public List<TransactionDto> getAllTransactionByUserId(@PathVariable Long userId,@RequestHeader HttpHeaders req) {
		return transactionService.getAllTransactionByUserId(userId,req.getFirst("Authorization"));
	}

	@GetMapping("/{accountType}/{userId}")
	public List<TransactionDto> getTransactionByAccountAndUserId(@PathVariable String accountType,
			@PathVariable Long userId,@RequestHeader HttpHeaders req) {
		return transactionService.getAllTransactionByUserId(accountType, userId,req.getFirst("Authorization"));
	}

}
