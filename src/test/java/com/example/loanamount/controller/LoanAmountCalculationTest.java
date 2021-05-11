package com.example.loanamount.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.loanamount.entity.CreditInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoanAmountCalculationTest {
	
	private MockMvc mockmvc;
	
	@Autowired
	private WebApplicationContext context;
	
	ObjectMapper om = new ObjectMapper();
	
	@Before
	private void setup() {
		mockmvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void calculateLoanAmountTestValid() throws Exception {
		CreditInfo info = new CreditInfo();
		info.setSsnNumber("4673128");
		info.setAnnualIncome(889000);
		String jsonReq = om.writeValueAsString(info);
		MvcResult result = mockmvc.perform(post("/loanAmount").content(jsonReq).
				contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		String resContent = result.getResponse().getContentAsString();
		//compare results
	}

}
