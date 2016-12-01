package com.facade;

import java.util.Collection;
import java.util.HashSet;

import com.beans.*;
import com.dao.interfaces.*;
import com.exceptionerrors.*;
import com.task.and.singleton.CouponClientFacade;
import com.task.and.singleton.CouponSystem;

public class CustomerFacade implements CouponClientFacade {

	private Customer customer;
//	private CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO coupDao = null;
	// Instance of Security Access Check
	private static boolean customerIsConnected = false;
	
	// Constructor
	public CustomerFacade() throws ConnectorException{
		
//		compDao = CouponSystem.getInstance().getCompDao();
		custDao = CouponSystem.getInstance().getCustDao();
		coupDao = CouponSystem.getInstance().getCouponDao();
	} // Constructor
	
    public CustomerFacade login(String custName, String password, ClientType client) throws LoginException, DaoException {
    	
    	boolean loginSuccessful  = false;
    	loginSuccessful  = custDao.login(custName, password);
    	if(loginSuccessful == true) {
			// If the login was Successful, save it in the private Customer instance.
    		this.customer = custDao.getCustomer(custName);
    		customerIsConnected = true;
    		return this;
    	} // if - true
    	else {
    		customerIsConnected = false;
    		return null;
		} // else
	} // login 
	
	public Coupon purchaseCoupon(Coupon coupon) throws DaoException, FiledErrorException {
		
			// Security Access Check	
			if(customerIsConnected != false) {
				coupon = coupDao.getCoupon(coupon.getId(), customer.getId(), ClientType.CUSTOMER);
				custDao.addCoupon(coupon, customer.getId());
				return coupon;
				} // if - customerIsConnected
				else {
					throw new DaoException("Error: Access Denied [Customer] - FAILED (Unidentified user)");
				} // else - customerIsConnected
		
		
	} // purchaseCoupon
	
	public Customer viewCustomer() throws DaoException {
		
		// Security Access Check	
		if(customerIsConnected != false) {
			Customer customer = new Customer();
			customer = custDao.getCustomer(this.customer.getId());
			return customer;
			} // if - customerIsConnected
			else {
				throw new DaoException("Error: Access Denied [Customer] - FAILED (Unidentified user)");
			} // else - customerIsConnected
	} // getCustomer
	
    public Customer getCustomerAndCoupons() throws DaoException {
    	
		// Security Access Check	
		if(customerIsConnected != false) {
	    	Collection<Coupon> coupons = getCoupons();
	    	Customer customer = this.customer;
	    	customer.setCoupons(coupons);
	    	return customer;
			} // if - customerIsConnected
			else {
				throw new DaoException("Error: Access Denied [Customer] - FAILED (Unidentified user)");
			} // else - customerIsConnected
	} // getCustomerAndCoupons
	
	public Collection<Coupon> getCoupons() throws DaoException {
		
		// Security Access Check	
		if(customerIsConnected != false) {
			Collection<Coupon> coupons = custDao.getCoupons(customer.getId());
			return coupons;
			} // if - customerIsConnected
			else {
				throw new DaoException("Error: Access Denied [Customer] - FAILED (Unidentified user)");
			} // else - customerIsConnected
	} // getAllCoupons
	
	public Collection<Coupon> getCouponForPurchase() throws DaoException {
		
		// Security Access Check	
		if(customerIsConnected != false) {
			Collection<Coupon> coupons = custDao.getCouponForPurchase(customer.getId());
			return coupons;
			} // if - customerIsConnected
			else {
				throw new DaoException("Error: Access Denied [Customer] - FAILED (Unidentified user)");
			} // else - customerIsConnected
	} // getCouponForPurchase
	
	
	public Collection<Coupon> getAllCouponsByPrice(double maxPrice) throws DaoException {
		
		// Security Access Check	
		if(customerIsConnected != false) {
			Collection<Coupon> coupons = new HashSet<>();
			coupons = coupDao.getCouponByPrice(customer.getId(), maxPrice, ClientType.CUSTOMER);
			return coupons;	
			} // if - customerIsConnected
			else {
				throw new DaoException("Error: Access Denied [Customer] - FAILED (Unidentified user)");
			} // else - customerIsConnected
	} // getAllCouponsByPrice
	
	public Collection<Coupon> getAllCouponsByType(String category) throws DaoException, FiledErrorException {
		
		// Security Access Check	
		if(customerIsConnected != false) {
			Collection<Coupon> coupons = new HashSet<>();
			Coupon coupon = new Coupon();
			coupon.setCategory(category);
			coupons = coupDao.getCouponByType(customer.getId(), coupon.getCategory(), ClientType.CUSTOMER);
			return coupons;
			} // if - customerIsConnected
			else {
				throw new DaoException("Error: Access Denied [Customer] - FAILED (Unidentified user)");
			} // else - customerIsConnected
	} // getAllCouponsByType
	
} // Class
