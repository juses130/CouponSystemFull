package com.tests;

import java.time.LocalDate;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

import com.core.beans.Coupon;
import com.exceptionerrors.ConnectorException;
import com.exceptionerrors.DaoException;
import com.exceptionerrors.FiledErrorException;
import com.exceptionerrors.LoginException;
import com.facade.ClientType;
import com.facade.CompanyFacade;
import com.helpers.ServerLogPrint;

@XmlRootElement

@Path("/clientSideTest")
public class ClientSideTest {

	@GET
	@Path("/testunctions")
	@Produces(MediaType.TEXT_XML)
	public Response testFunctions() throws FiledErrorException, ConnectorException, LoginException, DaoException {
		
		Coupon coupon = new Coupon();
		coupon.setId(0);
		coupon.setTitle("testWeb");
		coupon.setStartDate(LocalDate.of(2016, 02, 1));
		coupon.setEndDate(LocalDate.of(2017, 02, 1));
		coupon.setAmount(5);
		coupon.setCategory("travel");
		coupon.setMessage("no");
		coupon.setPrice(9);
		coupon.setImage("no");
		coupon.setOwnerID(0);
		
		CompanyFacade compF  = new CompanyFacade();
		compF.login("test84", "1234", ClientType.COMPANY);
		compF.addCoupon(coupon);
		
		ServerLogPrint.objectLog("createCoupon", coupon);
		return Response.status(Status.OK).entity(coupon).build();
	}
	
} // class
