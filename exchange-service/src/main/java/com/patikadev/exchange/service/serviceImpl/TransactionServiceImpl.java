package com.patikadev.exchange.service.serviceImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.patikadev.exchange.service.dto.TokenValidateDto;
import com.patikadev.exchange.service.dto.TransactionDto;
import com.patikadev.exchange.service.dto.TransactionRequest;
import com.patikadev.exchange.service.dto.TransactionResponse;

import com.patikadev.exchange.service.entity.Transaction;

import com.patikadev.exchange.service.repository.TransactionRepository;
import com.patikadev.exchange.service.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Autowired
	private RestTemplate restTemplate;

	private TransactionRepository transactionRepository;

	private ModelMapper modelMapper;

	public TransactionServiceImpl(TransactionRepository transactionRepository, ModelMapper modelMapper) {
		this.transactionRepository = transactionRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public TransactionResponse createTransaction(TransactionRequest transactionRequest, String req) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", req);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<TransactionRequest> requestEntity = new HttpEntity<>(transactionRequest, headers);

		restTemplate.exchange("http://ACCOUNT-SERVICE/a-service/v1/accounts/", HttpMethod.POST, requestEntity,
				String.class);

		Transaction soldTransaction = new Transaction();
		Date date = new Date();
		soldTransaction.setTransactionDate(date);
		soldTransaction.setAccountType(transactionRequest.getSoldAccountType());
		soldTransaction.setAmount(transactionRequest.getSoldAmount().negate()); // Satılan hesab negative
		soldTransaction.setExchangeRate(transactionRequest.getExchangeRate());
		soldTransaction.setUserId(transactionRequest.getUserId());
		transactionRepository.save(soldTransaction);

		Transaction receivedTransaction = new Transaction();
		receivedTransaction.setTransactionDate(date);
		receivedTransaction.setAccountType(transactionRequest.getReceivedAccountType());
		receivedTransaction
				.setAmount(transactionRequest.getSoldAmount().multiply(BigDecimal.valueOf(transactionRequest.getExchangeRate()) ));
		receivedTransaction.setExchangeRate(1/(transactionRequest.getExchangeRate()));
		receivedTransaction.setUserId(transactionRequest.getUserId());
		transactionRepository.save(receivedTransaction);

		TransactionResponse transactionResponse = modelMapper.map(transactionRequest, TransactionResponse.class);
		transactionResponse.setTransactionDate(date);
		transactionResponse
				.setReceivedAmount(transactionRequest.getSoldAmount().multiply(BigDecimal.valueOf(transactionRequest.getExchangeRate())));
		return transactionResponse;
	}

	@Override
	public List<TransactionDto> getAllTransactionByUserId(Long userId,String req) {
		TokenValidateDto tokenValidateDto = new TokenValidateDto();
		tokenValidateDto.setJwtSecret(jwtSecret);
		tokenValidateDto.setToken(req);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<TokenValidateDto> requestEntity = new HttpEntity<>(tokenValidateDto, headers);
		
		boolean a = restTemplate.exchange("http://ACCOUNT-SERVICE/a-service/v1/auth/validate/",HttpMethod.POST,requestEntity, boolean.class) != null;
		
		if(a) {
			List<Transaction> transactions = transactionRepository.findAllByUserId(userId);
			return transactions.stream().map(transaction -> modelMapper.map(transaction, TransactionDto.class))
					.collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public List<TransactionDto> getAllTransactionByUserId(String accountType, Long userId,String req) {
		TokenValidateDto tokenValidateDto = new TokenValidateDto();
		tokenValidateDto.setJwtSecret(jwtSecret);
		tokenValidateDto.setToken(req);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<TokenValidateDto> requestEntity = new HttpEntity<>(tokenValidateDto, headers);
		
		boolean a = restTemplate.exchange("http://ACCOUNT-SERVICE/a-service/v1/auth/validate/",HttpMethod.POST,requestEntity, boolean.class) != null;
		if(a) {
		List<Transaction> transactions = transactionRepository.findAllByAccountTypeAndUserId(accountType, userId);
		return transactions.stream().map(transaction -> modelMapper.map(transaction, TransactionDto.class))
				.collect(Collectors.toList());
		}
		return null;
	}

}
