package com.ejb.tests;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.eclipse.persistence.sessions.JNDIConnector;

import com.ejb.entities.Income;
import com.ejb.services.IncomeServiceBean;
import com.fasterxml.classmate.AnnotationConfiguration;
import com.sun.xml.bind.CycleRecoverable.Context;

public class TestTables {

	public TestTables() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
			
		try {
			InitialContext ctx = new InitialContext();
			Object objName = ctx.lookup("ConnectionFactory");
			System.out.println(objName.getClass().getSimpleName());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Income income = new Income(name, localDate, customerPurchase, amount)
//		IncomeServiceBean
		

	}

}
