package com.ejb.services;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ejb.entities.Income;

@Stateless(name= "IncomeServiceEJB")
public class IncomeServiceBean implements IncomeService{
	
	@PersistenceContext(unitName = "couponSystem")
	private EntityManager eManager;
	
	public IncomeServiceBean() {}
	
	@Override
	public void storeIncome(Income income) {
		eManager.persist(income);
	}

	@Override
	public Collection<Income> viewAllIncome() {
		String sqlQuery = "SELECT i FROM income i";
		return eManager.createQuery(sqlQuery, Income.class).getResultList();
	}

	@Override
	public Collection<Income> viewIncomeByCustomer(long customerID) {
		return eManager.createQuery("SELECT i FROM income i WHERE i.id = " + customerID, Income.class).getResultList();
	}

	@Override
	public Collection<Income> viewIncomeByCompany(long companyID) {
		return eManager.createQuery("SELECT i FROM income i WHERE  i.id =" + companyID, Income.class).getResultList();
	}

}
