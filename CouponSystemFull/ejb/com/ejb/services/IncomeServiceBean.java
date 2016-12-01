package com.ejb.services;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ejb.entities.Income;

@Stateless(name= "IncomeServiceEJB")
public class IncomeServiceBean implements IncomeService{

	@PersistenceContext(unitName = "couponSystem")
	private EntityManager incomeManager;
	
	public IncomeServiceBean() {}
	
	@Override
	public void storeIncome(Income income) {
		incomeManager.persist(income);
	}

	@Override
	public Collection<Income> viewAllIncome() {
		String sqlQuery = "SELECT i FROM income i";
//		return incomeManager.createQuery(sqlQuery, Income.class);
		return null;
	}

	@Override
	public Collection<Income> viewIncomeByCustomer(long customerID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Income> viewIncomeByCompany(long companyID) {
		// TODO Auto-generated method stub
		return null;
	}

}
