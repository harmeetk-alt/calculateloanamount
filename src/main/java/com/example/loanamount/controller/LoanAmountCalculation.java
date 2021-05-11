package com.example.loanamount.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.loanamount.entity.CreditInfo;
import com.example.loanamount.entity.LoanResult;

@RestController
public class LoanAmountCalculation {
	
	@Autowired
	RestTemplate template;
	
	@Autowired
	LoanResult loan;
	
	private Map<String, Date> loanSanctionedDate = new HashMap<>();
	
	@PostMapping("/loanAmount")
	public ResponseEntity<LoanResult> calculateLoanAmount(@RequestBody CreditInfo creditInfo)
	{
		
		if(loanSanctionedDate.containsKey(creditInfo.getSsnNumber())) {
			Instant fromInstant = loanSanctionedDate.get(creditInfo.getSsnNumber()).toInstant();
			Instant toInstant = new Date().toInstant();
			Duration duration = Duration.between(fromInstant, toInstant);
			final Duration THIRTY_DAYS = Duration.ofDays(30);

			if(duration.compareTo(THIRTY_DAYS) < 0) {
				loan.setValid("Invalid: Cannot apply again");
			} 
			
		}else {
		    String uri = "http://localhost:8181/calculateCreditScore/{ssnNumber}";
		 
		    RestTemplate restTemplate = new RestTemplate();
		 
		    Integer result = restTemplate.getForObject(uri, Integer.class, creditInfo.getSsnNumber());
		    
		    if(result > 700) {
		    	loan.setValid("Valid");
		    	loan.setSannctionedLoanAmt(creditInfo.getAnnualIncome()/2);
		    }else {
		    	loan.setValid("Please check  your credit score.");
		    	loan.setSannctionedLoanAmt(0);
		    }
		    loanSanctionedDate.put(creditInfo.getSsnNumber(), new Date());
		}
	    return new ResponseEntity<>(loan, HttpStatus.OK);
	}

}
