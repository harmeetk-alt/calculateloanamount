package com.example.loanamount.entity;

import org.springframework.stereotype.Component;

@Component
public class LoanResult {

	
	private String valid;
	private double sannctionedLoanAmt;
	
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public double getSannctionedLoanAmt() {
		return sannctionedLoanAmt;
	}
	public void setSannctionedLoanAmt(double sannctionedLoanAmt) {
		this.sannctionedLoanAmt = sannctionedLoanAmt;
	}
	
	
}
