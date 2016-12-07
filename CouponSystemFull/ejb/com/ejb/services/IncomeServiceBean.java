package com.ejb.services;

import java.sql.SQLClientInfoException;
import java.util.Collection;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.eclipse.persistence.internal.jpa.EntityManagerFactoryProvider;
import org.osgi.service.jpa.EntityManagerFactoryBuilder;

import com.ejb.entities.Income;
import com.messageDriven.EntityManagerSetter;

@Stateless(name= "IncomeServiceEJB")
public class IncomeServiceBean implements IncomeService{
	
//	@PersistenceContext(unitName = "couponSystem")
	private EntityManager eManager;
	private final static EntityManagerFactory entityManagerFactory  = Persistence.createEntityManagerFactory("couponSystem");
//	private EntityManagerSetter ems;
	
	public IncomeServiceBean() {
		setEntityManager(eManager);
	}
	
	@Asynchronous
	@Override
	public void storeIncome(Income income) {

		boolean flag = false;
		// start the transaction
		eManager.getTransaction().begin();
		
		try {
			// Persist the object
			eManager.persist(income);
			flag = true;
		} catch (EntityExistsException e) {
			flag = false;
			e.printStackTrace();
		}
		
		if(flag = true) {
			// commit the transaction
			eManager.getTransaction().commit();
			System.out.println("persist method - storeIncome!");
		} // if
		else {
			System.out.println("pesist error - storeIncome");
		}
		
		
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
	
//	@PersistenceContext(unitName = "couponSystem")
	private void setEntityManager(EntityManager eManager) {
		this.eManager = entityManagerFactory.createEntityManager();
	}

}
