package com.patikadev.account.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchageRate {
		
	private double rateTlToDolarEuro;
	private double rateDolarEuroToTl;
	private double rateTlToAltın;
	private double rateAltınToEuroDolar;
	private double rateDolarEuroToAltın;
	private double rateAltınToTl;
}
