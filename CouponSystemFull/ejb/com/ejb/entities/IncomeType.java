package com.ejb.entities;

public enum IncomeType {

	CUSTOMER_PURCHASE ("with this, customer can purchase NEW coupon"), 
	COMPANY_NEW_COUPON("creating NEW coupon by Company"),
	COMPANY_UPDATE_COUPON("updating an exist Coupon by the Company");
	private String description;
	
	IncomeType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
