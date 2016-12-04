package com.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.beans.Coupon;
import com.beans.Customer;
import com.ejb.entities.Income;
import com.ejb.entities.IncomeType;
import com.ejb.services.IncomeServiceBean;
import com.exceptionerrors.ConnectorException;
import com.exceptionerrors.DaoException;
import com.exceptionerrors.FiledErrorException;
import com.exceptionerrors.LoginException;
import com.facade.ClientType;
import com.facade.CustomerFacade;
import com.helpers.Message;
import com.helpers.ServerLogPrint;


@Path("/customerService")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerService {

	private static final String FACADE =  "FACADE";
	private Message customResponse = new Message();
	
	// constructor
	public CustomerService() throws ConnectorException {}

	@Context HttpServletRequest req;
	
	@POST
	@Path("/customerLogin/{custName}/{password}")
	public Response login(
			@PathParam("custName") String custName, 
			@PathParam("password") String password) throws DaoException, ConnectorException, LoginException {
		// Setting the clientType Enum
		ClientType client = ClientType.CUSTOMER;
		// create new customer facade
		CustomerFacade custF = new CustomerFacade();
		// send login parameters
		custF = custF.login(custName, password, client);
		// Check if CustomerFacade object is not null before running the rest of the code
		if(custF == null) { 
			req.getSession().setAttribute(FACADE, null);
			// if admin login fail, put null in the as the admin facade.
			return customResponse.getStatusUNCustom("login", "Access Denied [Customer] - FAILED)");
		}
		// pushing the facade into the session
		req.getSession().setAttribute(FACADE, custF);
		// set session timeout
		
		// if evreything good, return status OK.
		return customResponse.getStatusOKCustom("login", "Access Granted - Wellcom " + custName + " :)");
	} // login

	@GET
	@Path("/loadAvailableCoupons")
	public Response loadAvailableCoupons() throws DaoException {
		
		CustomerFacade custF =  (CustomerFacade)req.getSession().getAttribute(FACADE);	
		Collection<Coupon> coupons = custF.getCouponForPurchase();
		ServerLogPrint.messegeLog("loadAvailableCoupons", coupons.toString());
		
		return	Response.status(Response.Status.OK).entity(coupons.toArray(new Coupon[]{})).build();
	} // loadAvailableCoupons
	
	@GET
	@Path("/getYourCoupons") 
	public Response getYourCoupons() throws DaoException {
		
		CustomerFacade custF =  (CustomerFacade)req.getSession().getAttribute(FACADE);
		Collection<Coupon> coupons = custF.getCoupons();
		ServerLogPrint.messegeLog("loadAvailableCoupons", coupons.toString());
		return	Response.status(Response.Status.OK).entity(coupons.toArray(new Coupon[]{})).build();

	} // getYourCoupons
	
	@POST
	@Path("/purchaseCoupon/{coupID}")
	public Response purchaseCoupon(
			@PathParam("coupID") long coupID) throws DaoException, FiledErrorException {
		
		CustomerFacade custF =  (CustomerFacade)req.getSession().getAttribute(FACADE);	
		Coupon coupon = new Coupon();
		coupon.setId(coupID);
		coupon = custF.purchaseCoupon(coupon);
		
		IncomeServiceBean incomeService = new IncomeServiceBean();
		Income income = new Income(coupon.getTitle(), LocalDate.now(), IncomeType.CUSTOMER_PURCHASE, coupon.getPrice());
//		System.out.println(income.toString());

		incomeService.storeIncome(income);
		return customResponse.getStatusOKPureBulild("purchaseCoupon", coupon);
	} // purchaseCoupon
	
	@GET
	@Path("/viewCustomer")
	public Response viewCustomer(
			@PathParam("coupID") long coupID) throws DaoException, FiledErrorException {
		
		CustomerFacade custF =  (CustomerFacade)req.getSession().getAttribute(FACADE);		
		Customer customer = new Customer();
		customer = custF.viewCustomer();
		
		return customResponse.getStatusOKPureBulild("viewCustomer", customer);
	} // viewCustomer	
	
	@POST
	@Path("/getCouponsByPrice/{maxPrice}")
	public Response getCouponsByPrice(
			@PathParam("maxPrice") double maxPrice) throws DaoException {
		
		CustomerFacade custF = (CustomerFacade)req.getSession().getAttribute(FACADE);
		Collection<Coupon> coupons = new HashSet<>();
		coupons= custF.getAllCouponsByPrice(maxPrice);
		
		ServerLogPrint.messegeLog("getCouponsByPrice", coupons.toString());
		return	Response.status(Response.Status.OK).entity(coupons.toArray(new Coupon[]{})).build();
	} // getCouponByPrice
	
	@POST
	@Path("/getCouponsByType/{category}")
	public Response getCouponsByType(
			@PathParam("category") String category) throws DaoException, FiledErrorException {
		
		CustomerFacade custF = (CustomerFacade)req.getSession().getAttribute(FACADE);
		Collection<Coupon> coupons = new HashSet<>();
		coupons = custF.getAllCouponsByType(category);
		
		ServerLogPrint.messegeLog("getCouponsByType", coupons.toString());
		return	Response.status(Response.Status.OK).entity(coupons.toArray(new Coupon[]{})).build();
	} // getCouponByType
	
} // class
