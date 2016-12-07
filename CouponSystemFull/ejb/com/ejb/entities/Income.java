package com.ejb.entities;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// Declaring this class as Entity bean
@Table(name="income")		// Declaring this class as a TABLE in the DataBase	
@Entity
public class Income implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Id
	@GeneratedValue
	private long id;
    @Column(name="name", nullable=false, length=50)
	private String name;
    @Column(name="date", nullable=false, length=50)
	private LocalDate date;
    @Column(name="description", nullable=false, length=100)
	private IncomeType description;
    @Column(name="amount", nullable=false, length=50)
	private double amount;
	
    public Income(String name, LocalDate localDate, IncomeType customerPurchase, double amount) {
//    	this.setId(id);
//    	this.setDate(localDate);
//    	this.setName(name);
//    	this.setDescription(customerPurchase);
//    	this.setAmount(d);
    	
//    	this.id = id;
    	this.date = localDate;
    	this.name = name;
    	this.description = customerPurchase.CUSTOMER_PURCHASE;
    	this.amount = amount;
    }
    
    @SuppressWarnings("unused")
	private Income() {}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate localDate) {
		this.date = localDate;
	}
	
	public IncomeType getDescription() {
		return description;
	}
	public void setDescription(IncomeType description) {
		this.description = description;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "Income [id=" + id + ", name=" + name + ", date=" + date + ", description=" + description + ", amount="
				+ amount + "]";
	}

}
