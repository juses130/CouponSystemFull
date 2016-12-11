package com.messageDriven;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.ejb.entities.Income;
import com.ejb.services.IncomeService;

@MessageDriven(activationConfig={
		@ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
		@ActivationConfigProperty(propertyName="destination", propertyValue="queue/couponQueue")
})
public class IncomeConsumerBean implements MessageListener
{	
	@EJB 
	private IncomeService incomeServiceStub;

	public void onMessage(Message msg) {
		try
		{
			ObjectMessage om=(ObjectMessage)msg;
			Income data=(Income)om.getObject();
			incomeServiceStub.storeIncome(data);
			System.out.println("in Queue!");
		
		}catch(JMSException e){
			e.printStackTrace();
		}
	}
}