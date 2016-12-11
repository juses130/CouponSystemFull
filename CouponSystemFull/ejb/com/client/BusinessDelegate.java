package com.client;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Properties;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.ejb.entities.Income;
import com.ejb.services.IncomeService;

public class BusinessDelegate {

	private QueueSession qsession;
	private QueueSender qsender;
	private IncomeService incomeService;
	
	public BusinessDelegate(){
		// Prepare initial context
		
//		Hashtable<String,String> table = new Hashtable<String,String>();
		Properties jndiProperties = new Properties();
	    jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
	    jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
	    jndiProperties.put(Context.PROVIDER_URL,"jnp://127.0.0.1:1089");
//	    jndiProperties.put(Context.PROVIDER_URL,"http://0.0.0.0");
//		jndiProperties.put("jboss.naming.client.ejb.context", true);
		try
		{
			// Set initial context
			InitialContext ctx = new InitialContext(jndiProperties);
			// Set the rest of connection properties
			QueueConnectionFactory f = (QueueConnectionFactory)ctx.lookup("ConnectionFactory");
			Queue q = (Queue)ctx.lookup("queue/couponQueue");
			incomeService = (IncomeService)ctx.lookup("incomeService/local");
			QueueConnection qcon = f.createQueueConnection();
			qsession = qcon.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
			qsender = qsession.createSender(q);
			ctx.close();
		}catch(Exception e){
			// for testing..
			System.out.println("Something went wrong! in + [" + this.getClass().getSimpleName() + "] Class-Construrctor");
			e.printStackTrace();
		}	
	}
	
	public synchronized void storeIncome(Income i){
		try{
			qsender.send(qsession.createObjectMessage(i));
		}catch(Exception e){
			System.out.println("Income persistance failed");
			e.printStackTrace();
		}
	}
	
	public Collection<Income> viewAllIncome(){
		return incomeService.viewAllIncome();
	}
	
	public Collection<Income> viewIncomeByCompany(long id){
		return incomeService.viewIncomeByCompany(id);
	}
	
	public Collection<Income> viewIncomeByCustomer(long id){
		return incomeService.viewIncomeByCustomer(id);
	}
}
