package com.patikadev.account.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.patikadev.account.service.dto.AccountDto;
import com.patikadev.account.service.dto.ExchageRate;
import com.patikadev.account.service.dto.UpdateAccountDto;
import com.patikadev.account.service.entity.Account;
import com.patikadev.account.service.entity.User;
import com.patikadev.account.service.exception.AlreadyExistUserAccountException;
import com.patikadev.account.service.exception.InsufficientBalanceException;
import com.patikadev.account.service.exception.ResourseNotFoundException;
import com.patikadev.account.service.repository.AccountRepository;
import com.patikadev.account.service.service.AccountService;
import com.patikadev.account.service.service.UserService;

@Service
public class AccountServiceImpl implements AccountService{
 
	private AccountRepository accountRepository;
	
	private ModelMapper modelMapper;
	
	private UserService userService;

	public AccountServiceImpl(AccountRepository accountRepository,UserService userService,ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
		this.accountRepository = accountRepository;
	}

	@Override
	public AccountDto createAccount(Long userId, AccountDto accountDto) {
		User user = userService.getUserById(userId);

		if(accountRepository.existsByAccountTypeAndUserId(accountDto.getAccountType(),userId)) {
			throw new AlreadyExistUserAccountException();
		}
		Account account = modelMapper.map(accountDto, Account.class);
		account.setUser(user);
		accountRepository.save(account);
		return modelMapper.map(account, AccountDto.class);
	}

	@Override
	public List<AccountDto> getAllAccountByUserId(Long userId) {
		User user = userService.getUserById(userId);
		if(!user.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
			throw new ResourseNotFoundException("User account not found.");
		
		List<Account> accounts = accountRepository.findAllAccountByUserId(userId);
		return accounts.stream().map(account->modelMapper.map(account, AccountDto.class)).collect(Collectors.toList());
	}

	@Override
	public AccountDto getAccountByAccountTypeAndUserId(Long userId,String accountType) {
		User user = userService.getUserById(userId);
		if(!user.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
			throw new ResourseNotFoundException("User account not found.");
		
		return modelMapper.map(accountRepository.findByUserIdAndAccountType(userId, accountType), AccountDto.class) ;
	}

	@Override
	public void updateAccount(UpdateAccountDto updateAccountDto) {
		
		User user = userService.getUserById(updateAccountDto.getUserId());
		if(!user.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
			throw new ResourseNotFoundException("User account not found.");
		
		Account soldAccount = accountRepository.findByUserIdAndAccountType(updateAccountDto.getUserId(),updateAccountDto.getSoldAccountType());
		
		if(soldAccount.getBalance().compareTo(updateAccountDto.getSoldAmount())==-1 ) {
			throw new InsufficientBalanceException("Insuffiecient balance.");
		}
		soldAccount.setBalance(soldAccount.getBalance().subtract(updateAccountDto.getSoldAmount()));
		accountRepository.save(soldAccount);
		
		Account receivedAccount = accountRepository.findByUserIdAndAccountType(updateAccountDto.getUserId(),updateAccountDto.getReceivedAccountType());
								  //
		receivedAccount.setBalance(receivedAccount.getBalance().add(updateAccountDto.getExchangeRate().multiply(updateAccountDto.getSoldAmount()))  );
		accountRepository.save(receivedAccount);
	}

	@Override
	public ExchageRate generateExchangeRate() {
		ExchageRate exchageRate = new ExchageRate();
		exchageRate.setRateAltınToEuroDolar(Math.random()*(0.05)+0.2);
		exchageRate.setRateAltınToTl(Math.random()*(0.000001)+0.000002);
		exchageRate.setRateDolarEuroToAltın(Math.random()*(4)+52);
		exchageRate.setRateDolarEuroToTl(Math.random()*(0.01)+0.05);
		exchageRate.setRateTlToAltın(Math.random()*(100)+900);
		exchageRate.setRateTlToDolarEuro(Math.random()*(6)+15);

		return exchageRate;
	}

}
