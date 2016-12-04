package com.ejb.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.ejb.EJB;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity              // Declaring this class as Entity bean
@Table(name="income")		// Declaring this class as a TABLE in the DataBase	
public class Income implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long id;
	@Column
	private String name;
	@Column
	private LocalDate date;
	@Column
	private IncomeType description;
	@Column
	private double amount;
	
	// Constructor
	public Income(String name, LocalDate date, IncomeType description, double amount) {
		super();
		this.name = name;
		this.date = date;
		this.description = description;
		this.amount = amount;
	}
	
	// default Constructor
	public Income(){}

	public long getId() {
		return id;
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

	public void setDate(LocalDate date) {
		this.date = date;
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
