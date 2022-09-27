package com.patikadev.account.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patikadev.account.service.dto.AccountDto;
import com.patikadev.account.service.dto.ExchageRate;
import com.patikadev.account.service.dto.UpdateAccountDto;
import com.patikadev.account.service.dto.ValidateTokenDto;
import com.patikadev.account.service.exception.APIException;
import com.patikadev.account.service.service.AccountService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@RestController
@RequestMapping("/a-service/v1/accounts")
public class AccountController {
	// String a = SecurityContextHolder.getContext().getAuthentication().getName();
	@Autowired
	private AccountService accountService;

	@PostMapping("/{userId}")
	public ResponseEntity<AccountDto> createAccount(@PathVariable Long userId, @RequestBody AccountDto accountDto) {
		return new ResponseEntity<>(accountService.createAccount(userId, accountDto), HttpStatus.CREATED);
	}

	@GetMapping("/getAllAccount/{userId}")
	public List<AccountDto> getAllAccountByUserId(@PathVariable Long userId) {
		return accountService.getAllAccountByUserId(userId);
	}

	@GetMapping("/{userId}/{accountType}")
	public ResponseEntity<AccountDto> getAccountByAccountType(@PathVariable Long userId,
			@PathVariable String accountType) {
		return new ResponseEntity<>(accountService.getAccountByAccountTypeAndUserId(userId, accountType),
				HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> updateAccount(@RequestBody UpdateAccountDto updateAccountDto) {
		accountService.updateAccount(updateAccountDto);
		return new ResponseEntity<>("Updated successfully.", HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<ExchageRate> generateExchangeRate() {

		return new ResponseEntity<>(accountService.generateExchangeRate(), HttpStatus.OK);
	}
	
}
