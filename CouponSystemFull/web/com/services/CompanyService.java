package com.services;

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
import javax.xml.bind.annotation.XmlRootElement;

import com.beans.Company;
import com.beans.Coupon;
import com.exceptionerrors.ConnectorException;
import com.exceptionerrors.DaoException;
import com.exceptionerrors.FiledErrorException;
import com.exceptionerrors.LoginException;
import com.facade.ClientType;
import com.facade.CompanyFacade;
import com.helpers.Message;
import com.helpers.ServerLogPrint;

@XmlRootElement

@Path("/companyService")
@Produces(MediaType.APPLICATION_JSON)
public class CompanyService {
	
	// Attributes
	private static final String FACADE =  "FACADE";
	private Message customResponse = new Message();
	//private CompanyFacade compF;
	
	@Context HttpServletRequest req;
	
	// Constructor
	public CompanyService() throws ConnectorException {}

	@POST
	@Path("/companyLogin/{compName}/{password}")
	public Response login(
			@PathParam("compName") String compName, 
			@PathParam("password") String password) throws LoginException, DaoException, ConnectorException {
		// Setting the clientType Enum
				ClientType client = ClientType.COMPANY;
				// create new company facade
				CompanyFacade compF = new CompanyFacade();
				// send login parameters
				compF = compF.login(compName, password, client);
				
				// Check if CompanyFacade object is not null before running the rest of the code
				if(compF == null) {
					// if Company login fail, put null in the as the Company facade.
					req.getSession().setAttribute(FACADE, null);
					return customResponse.getStatusUNCustom("login", "Access Denied [Company] - FAILED)");
				}
				// pushing the facade into the session
				req.getSession().setAttribute(FACADE, compF);

				// if evreything good, return status OK.
				return customResponse.getStatusOKCustom("login", "Access Granted - Wellcom " + compName + " :)");
				
	} // login
	
	@POST
	@Path("/createCoupon/{title}/{stDay}/{stMonth}/{stYear}/{enDay}/{enMonth}/{enYear}/{amount}/{category}/{message}/{price}/{image}")
	public Response createCoupon(
			@PathParam("title") String title, 
			// Insert Start Date Coupon
			@PathParam("stDay") int stDay,
			@PathParam("stMonth") int stMonth,
			@PathParam("stYear") int stYear,
			// Insert End Date Coupon
			@PathParam("enDay") int enDay,
			@PathParam("enMonth") int enMonth,
			@PathParam("enYear") int enYear,
			// Insert the other values of the coupon
			@PathParam("amount") int amount,
			@PathParam("category") String category,
			@PathParam("message") String message,
			@PathParam("price") double price,
			@PathParam("image") String image
			) throws FiledErrorException, DaoException {
		
		// Take the facade from the session
		CompanyFacade compF = (CompanyFacade)req.getSession().getAttribute(FACADE);
		Coupon coupon = new Coupon();
		
		coupon.setId(0);
		coupon.setTitle(title);
		coupon.setStartDate(LocalDate.of(stYear, stMonth, stDay));
		coupon.setEndDate(LocalDate.of(enYear, enMonth, enDay));
		coupon.setAmount(amount);
		coupon.setCategory(category.toUpperCase());
		coupon.setMessage(message);
		coupon.setPrice(price);
		coupon.setImage(image);
		coupon.setOwnerID(0);
		compF.addCoupon(coupon);
		
		return customResponse.getStatusOKPureBulild("createCoupon", coupon);
	} // addCoupon
	
	@GET
	@Path("/viewCompany")
	public Response viewCompany() throws DaoException {
		
		// Getting the facade from the session
				CompanyFacade compF = (CompanyFacade)req.getSession().getAttribute(FACADE);
				// Check if CompanyFacade object is not null before running the rest of the code
				Company company = new Company();
				company = compF.viewCompay();
				return customResponse.getStatusOKPureBulild("viewCompany", company);
	} // viewCompany
	
	@GET
	@Path("/getAllCoupons")
	public Response getAllCoupons() throws DaoException{
		
		// Getting the facade from the session
		CompanyFacade compF = (CompanyFacade)req.getSession().getAttribute(FACADE);
		Collection<Coupon> coupons = compF.getAllCoupons();
		ServerLogPrint.messegeLog("getAllCoupons", coupons.toString());

		return	Response.status(Response.Status.OK).entity(coupons.toArray(new Coupon[]{})).build();
		
	} // getAllCoupons

	@POST
	@Path("/getCouponByID/{coupID}")
	public Response getCouponByID (
			@PathParam("coupID") long coupID) throws Exception  {
		
		CompanyFacade compF = (CompanyFacade)req.getSession().getAttribute(FACADE);	
		Coupon coupon = new Coupon();
		coupon = compF.getCoupon(coupID);
		return customResponse.getStatusOKPureBulild("getCouponByID", coupon);

	} // getCouponByID 
		
	@POST
	@Path("/removeCouponByID/{coupID}")
	public Response removeCouponByID(
			@PathParam("coupID") long coupID) throws DaoException, FiledErrorException {
		
		CompanyFacade compF = (CompanyFacade) req.getSession().getAttribute(FACADE);	
		Coupon coupon = new Coupon();
		coupon.setId(coupID);
		// saving the coupon date before deleting it (for return it as info to the client side)
		Coupon couponCopy = compF.getCoupon(coupID);
		compF.removeCoupon(coupon);
		return customResponse.getStatusOKPureBulild("removeCouponByID", couponCopy);
	} // removeCompany
	
	@POST
	@Path("/updateCoupon/{coupID}/{enDay}/{enMonth}/{enYear}/{amount}/{message}/{price}")
	public Response updateCoupon (
			@PathParam("coupID") long coupID,
			@PathParam("enDay") int enDay,
			@PathParam("enMonth") int enMonth,
			@PathParam("enYear") int enYear,
			@PathParam("amount") int amount,
			@PathParam("message") String message,
			@PathParam("price") double price) throws DaoException, FiledErrorException {

		CompanyFacade compF = (CompanyFacade) req.getSession().getAttribute(FACADE);
		Coupon coupon = new Coupon();
		coupon.setId(coupID);
		// set End dates
		coupon.setEndDate(LocalDate.of(enYear, enMonth, enDay));
		// set the other parameters
		coupon.setAmount(amount);
		coupon.setMessage(message);
		coupon.setPrice(price);
		coupon = compF.updateCoupon(coupon);
		// get all coupon data
		coupon = compF.getCoupon(coupID);
		
		return customResponse.getStatusOKPureBulild("updateCoupon", coupon);
	} // updateCustomer

	@POST
	@Path("/getCouponsByPrice/{maxPrice}")
	public Response getCouponsByPrice(
			@PathParam("maxPrice") double maxPrice) throws DaoException {
		
		CompanyFacade compF = (CompanyFacade)req.getSession().getAttribute(FACADE);
		Collection<Coupon> coupons = new HashSet<>();
		coupons= compF.getCouponsCompanyByPrice(maxPrice);
		
		ServerLogPrint.messegeLog("getCouponsByPrice", coupons.toString());
		return	Response.status(Response.Status.OK).entity(coupons.toArray(new Coupon[]{})).build();
	} // getCouponByPrice
	
	@POST
	@Path("/getCouponsByType/{category}")
	public Response getCouponsByType(
			@PathParam("category") String category) throws DaoException, FiledErrorException {
		
		CompanyFacade compF = (CompanyFacade)req.getSession().getAttribute(FACADE);	
		Collection<Coupon> coupons = new HashSet<>();
		coupons = compF.getCouponsCompanyByType(category);
		
		ServerLogPrint.messegeLog("getCouponsByType", coupons.toString());
		return	Response.status(Response.Status.OK).entity(coupons.toArray(new Coupon[]{})).build();
	} // getCouponByType
	
} // Class
