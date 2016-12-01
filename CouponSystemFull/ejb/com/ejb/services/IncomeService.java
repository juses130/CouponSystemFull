package com.ejb.services;

import java.util.Collection;

import javax.ejb.Remote;

import com.ejb.entities.Income;

@Remote
public interface IncomeService {

	public void storeIncome(Income income);
	public Collection<Income> viewAllIncome();
	public Collection<Income> viewIncomeByCustomer(long customerID);
	public Collection<Income> viewIncomeByCompany(long companyID);
	  
	
}
