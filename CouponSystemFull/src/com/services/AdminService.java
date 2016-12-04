package com.services;

import java.util.Collection;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

import com.beans.Company;
import com.beans.Coupon;
import com.beans.Customer;
import com.exceptionerrors.*;
import com.exceptions.messeges.ServiceException;
import com.facade.AdminFacade;
import com.helpers.Message;
import com.helpers.ServerLogPrint;
import com.facade.*;

@XmlRootElement

@Path("/adminService")
@Produces(MediaType.APPLICATION_JSON)
public class AdminService {

	// Attributes
	private static final String FACADE = "FACADE";
	private Message customResponse = new Message();

	@Context HttpServletRequest req;
	
	// Constructor
	public AdminService() {}
	
	@POST
	@Path("/adminLogin/{adminName}/{password}")
	public Response login(
			@PathParam("adminName") String adminName, 
			@PathParam("password") String password) throws DaoException, ConnectorException {
		
		// Setting the clientType Enum
		ClientType client = ClientType.ADMIN;
		// create new admin facade
		AdminFacade admF = new AdminFacade();
		// send login parameters
		admF = admF.login(adminName, password, client);
		
		// Check if adminfacade object is not null before running the rest of the code
		if(admF == null) {
			// if admin login fail, put null in the as the admin facade.
			req.getSession().setAttribute(FACADE, null);
			return customResponse.getStatusUNCustom("login", "Admin Login - FAILED (Incorrect password or username)");
		}
		// pushing the facade into the session
		req.getSession().setAttribute(FACADE, admF);
		
		// if evreything good, return status OK.
		return customResponse.getStatusOKCustom("login", "Access Granted - Wellcon Admin :)");
		
	} // login

	/*
	 * Company Section
	 */
	
	@POST
	@Path("/createCompany/{compName}/{password}/{email}")
	public Response createCompany(
			@PathParam("compName") String compName, 
			@PathParam("password") String password, 
			@PathParam("email") String email) throws ServiceException, DaoException, FiledErrorException {
		
		// getting the facade from the current session
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		// Check if adminfacade object is not null before running the rest of the code		
		if(admF == null) {
			return customResponse.getStatusUNCustom("createComnay", "Access Denied [Admin] - FAILED");
		} // if
		
		// create new company object
		Company company = new Company(compName, password, email);
		// if the create-part failed, we want to know way.
		try {
			admF.createCompany(company);
		} catch (Exception e) {
			String message = e.getMessage();
			return customResponse.getStatusUNCustom("createCompany", message);
		} // catch
		// if evreything good, return status OK and Object.
		return customResponse.getStatusOKPureBulild("createCompany", company);
	} // createCompany
	
	@POST
	@Path("/removeCompanyByID/{compID}")
	public Response removeCompanyByID(
			@PathParam("compID") long compID) throws DaoException {
		
		Company company = new Company();
		company.setId(compID);
		
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		if(admF == null) {
			return customResponse.getStatusUNCustom("removeCompanyByName", "Access Denied [Admin] - FAILED");
		}
		
		// saving the company date before deleting it (for return it as info to the client side)
		Company companyCopy = admF.getCompany(compID);
		admF.removeCompanyByID(company);

		return customResponse.getStatusOKPureBulild("removeCompanyByID", companyCopy);
	} // removeCompany
	
	@POST 
	@Path("/removeCompanyByName/{compName}")
	public Response removeCompanyByName(
			@PathParam("compName") String compName) throws DaoException, FiledErrorException {
		
		Company company = new Company();
		company.setCompName(compName);
		
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		if(admF == null) {
			return customResponse.getStatusUNCustom("removeCompanyByName", "Access Denied [Admin] - FAILED");
		}
		
		// saving the company date before deleting it (for return it as info to the client side)
		Company companyCopy = admF.getCompany(compName);
		admF.removeCompanyByName(company);

		return customResponse.getStatusOKPureBulild("removeCompanyByName", companyCopy);
	} // removeCompany
	
	@POST 
	@Path("/updateCompany/{compID}/{password}/{email}")
	public Response updateCompany(	
			@PathParam("compID") long compID, 
			@PathParam("password") String password, 
			@PathParam("email") String email) throws ServiceException, DaoException, FiledErrorException {
		
		Company company = new Company();
		company.setId(compID);
		company.setPassword(password);
		company.setEmail(email);
		
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		if(admF == null) {
			return customResponse.getStatusUNCustom("updateCompany", "Access Denied [Admin] - FAILED");
		}
		
			admF.updateCompany(company);
			// get the full info of the company after update - and before sending it as json to the client
			company = admF.getCompany(compID);
			return customResponse.getStatusOKPureBulild("updateCompany", company);

	} // updateCompany
	
	@POST
	@Path("/getCompanyByID/{compID}")
	public Response getCompanyByID(
			@PathParam("compID") long compID) throws DaoException {
		
		Company company = new Company();
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		if(admF == null) {
			return customResponse.getStatusUNCustom("getCompanyByID", "Access Denied [Admin] - FAILED");
		}		
		
		company = admF.getCompany(compID);
		return customResponse.getStatusOKPureBulild("getCompanyByID", company);

	} // getCompany
	
	@POST
	@Path("/getCompanyByName/{compName}")
	public Response getCompanyByName(
			@PathParam("compName") String compName) throws DaoException {
		Company company = new Company();
		
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		if(admF == null) {
			return customResponse.getStatusUNCustom("getCompanyByName", "Access Denied [Admin] - FAILED");
		}
		
		company =admF.getCompany(compName);	
		return customResponse.getStatusOKPureBulild("getCompanyByName", company);
	} // getCompany
	
	@GET
	@Path("/getAllCompanies")
	public Response getAllCompanies() throws DaoException {
			
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		// Check if adminfacade object is not null before running the rest of the code		
		if(admF == null) {
			return customResponse.getStatusUNCustom("getCompanyByName", "Access Denied [Admin] - FAILED");
		} // if
		
		Collection<Company> companies = new HashSet<>();
		// get the companies from the DB
		try {
			companies = admF.getAllCompanies();
		} catch (Exception e) {
			String message = e.getMessage();
			e.printStackTrace();
			return customResponse.getStatusUNCustom("getAllCompanies", message);
		} // catch
		ServerLogPrint.messegeLog("getAllCompanies", companies.toString());
		return Response.status(Status.OK).entity(companies.toArray(new Company[]{})).build();
			
	} // getAllCompanies
		
	/*
	 * Customer Section
	 */

	@POST
	@Path("/createCustomer/{custName}/{password}")
	public Response createCustomer(
			@PathParam("custName") String custName, 
			@PathParam("password") String password) throws ServiceException, DaoException, FiledErrorException {
		
		Customer customer = new Customer(custName,password);
		
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		if(admF == null) {
			return customResponse.getStatusUNCustom("createCustomer", "Access Denied [Admin] - FAILED");
		}
		
		admF.createCustomer(customer);
		return customResponse.getStatusOKPureBulild("createCustomer", customer);
	} // createCompany
	
	@POST
	@Path("/removeCustomerByID/{custID}")
	public Response removeCustomerByID(
			@PathParam("custID") long custID) throws DaoException, FiledErrorException {
		
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		if(admF == null) {
			return customResponse.getStatusUNCustom("removeCompanyByName", "Access Denied [Admin] - FAILED");
		}
		
		Customer customer = new Customer();
		customer.setId(custID);
		// saving the company date before deleting it (for return it as info to the client side)
		Customer customerCopy = admF.getCustomer(custID);
		admF.removeCustomer(customer);
		return customResponse.getStatusOKPureBulild("removeCustomerByID", customerCopy);
		
	} // removeCustomerByID
	
	@POST
	@Path("/removeCustomerByName/{custName}")
	public Response removeCustomerByName(
			@PathParam("custName") String custName) throws DaoException, FiledErrorException {
		
		Customer customer = new Customer();
		customer.setCustName(custName);
		
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		if(admF == null) {
			return customResponse.getStatusUNCustom("removeCustomerByName", "Access Denied [Admin] - FAILED");
		}
		
		// saving the company date before deleting it (for return it as info to the client side)
		Customer customerCopy = admF.getCustomer(custName);
		admF.removeCustomer(customer);
		return customResponse.getStatusOKPureBulild("removeCustomerByName", customerCopy);
	} // removeCustomerByName
	
	@POST 
	@Path("/updateCustomer/{custID}/{password}/{custName}")
	public Response updateCustomer (
			@PathParam("custID") long custID, 
			@PathParam("password") String password, 
			@PathParam("custName") String custName) throws DaoException, FiledErrorException {
		
		Customer customer = new Customer();
		customer.setId(custID);
		customer.setPassword(password);
		customer.setCustName(custName);
		
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		if(admF == null) {
			return customResponse.getStatusUNCustom("removeCustomerByName", "Access Denied [Admin] - FAILED");
		}
		
		admF.updateCustomer(customer);
		customer = admF.getCustomer(custID);
		return customResponse.getStatusOKPureBulild("updateCustomer", customer);
	} // updateCustomer

	@POST
	@Path("/getCustomerByID/{custID}")
	public Response getCustomerByID (
			@PathParam("custID") long custID) throws DaoException, FiledErrorException  {
		
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		if(admF == null) {
			return customResponse.getStatusUNCustom("getCustomerByID", "Access Denied [Admin] - FAILED");
		}		
		
		Customer customer = new Customer();
		customer = admF.getCustomer(custID);
		return customResponse.getStatusOKPureBulild("getCustomerByID", customer);

	} // getCustomer 
	
	@POST
	@Path("/getCustomerByName/{custName}")
	public Response getCustomerByName (
			@PathParam("custName") String custName) throws DaoException, FiledErrorException {
		
		
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		if(admF == null) {
			return customResponse.getStatusUNCustom("getCustomerByName", "Access Denied [Admin] - FAILED");
		}		
		
		Customer customer =  new Customer();
		customer = admF.getCustomer(custName);
		return customResponse.getStatusOKPureBulild("getCustomerByName", customer);
	} // getCustomer 

	@GET
	@Path("/getAllCustomers")
	public Response getAllCustomers() throws DaoException { 
		
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		// Check if adminfacade object is not null before running the rest of the code		
		if(admF == null) {
			return customResponse.getStatusUNCustom("getCompanyByName", "Access Denied [Admin] - FAILED");
		} // if
		
		Collection<Customer> customers = new HashSet<>();
		customers = admF.getAllCustomers();
		ServerLogPrint.messegeLog("getAllCustomers", customers.toString());
		
		return	Response.status(Response.Status.OK).entity(customers.toArray(new Customer[]{})).build();
	} // getAllCustomers

	/*
	 *  Coupon Section
	 */

	@POST
	@Path("/getCouponByID/{coupID}")
	public Response getCouponByID (
			@PathParam("coupID") long coupID) throws Exception  {
		
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		if(admF == null) {
			return customResponse.getStatusUNCustom("getCouponByID", "Access Denied [Admin] - FAILED");
		}		
		
		Coupon coupon = new Coupon();
		coupon = admF.getCoupon(coupID);
		return customResponse.getStatusOKPureBulild("getCouponByID", coupon);

	} // getCustomer 
		
	@POST
	@Path("/removeCouponByID/{coupID}")
	public Response removeCouponByID(
			@PathParam("coupID") long coupID) throws Exception  {
		
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);		
		Coupon coupon = new Coupon();
		coupon.setId(coupID);
		Coupon couponCopy = admF.getCoupon(coupID);
		admF.removeCoupon(coupon);
		
		return customResponse.getStatusOKPureBulild("removeCouponByID", couponCopy);
	} // removeCoupon
	
	@POST
	@Path("/getCouponsByPrice/{maxPrice}")
	public Response getCouponsByPrice(
			@PathParam("maxPrice") double maxPrice) throws DaoException {
		
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		Collection<Coupon> coupons = new HashSet<>();
		coupons= admF.getCouponByPrice(maxPrice);
	
		ServerLogPrint.messegeLog("getCouponsByPrice", coupons.toString());
		return	Response.status(Response.Status.OK).entity(coupons.toArray(new Coupon[]{})).build();
	} // getCouponByPrice
	
	@POST
	@Path("/getCouponsByType/{category}")
	public Response getCouponsByType(
			@PathParam("category") String category) throws DaoException, FiledErrorException {
		
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		Collection<Coupon> coupons = new HashSet<>();
		coupons = admF.getCouponByType(category);
		
		ServerLogPrint.messegeLog("getCouponsByType", coupons.toString());
		return	Response.status(Response.Status.OK).entity(coupons.toArray(new Coupon[]{})).build();
	} // getCouponByType
	
	@GET
	@Path("/getAllCoupons")
	public Response getAllCoupons() throws DaoException, FiledErrorException {
		
		AdminFacade admF = (AdminFacade) req.getSession().getAttribute(FACADE);
		Collection<Coupon> coupons = new HashSet<>();
		coupons = admF.getAllCoupons();
		ServerLogPrint.messegeLog("getAllCoupons", coupons.toString());

		return	Response.status(Response.Status.OK).entity(coupons.toArray(new Coupon[]{})).build();
	} // getAllCoupons

	
} // class