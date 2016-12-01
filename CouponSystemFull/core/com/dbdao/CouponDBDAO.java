package com.dbdao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.beans.Company;
import com.beans.Coupon;
import com.beans.CouponType;
import com.beans.Customer;
import com.dao.interfaces.CouponDAO;
import com.exceptionerrors.DaoException;
import com.exceptionerrors.FiledErrorException;
import com.facade.ClientType;
import com.task.and.singleton.DBconnector;
import com.task.and.singleton.DatabaseInfo;


/**
 * This is Coupon Database DAO Class.
 * Just implements the methods from CouponDAO in 'com.dao.intefaces' package. </br>
 * 
 * @author Raziel
 *
 */

public class CouponDBDAO implements CouponDAO{
	
	// Attributes
	private CouponFoundInDatabase existInDB = new CouponFoundInDatabase(); 
	
	@Override
	public Coupon createCoupon(Coupon coupon, Company company) throws DaoException{

		// We need to Check if the Company owns this coupon before.
		if(existInDB.couponFoundInJoinTables(coupon, company, CheckCouponBy.BY_ID) == true 
				|| existInDB.couponFoundInJoinTables(coupon, company, CheckCouponBy.BY_NAME) == true) {
			throw new DaoException("Error: Creating Coupon By Company - FAILED (You can create only ONE coupon with the same name!)");
		} // if
		else {
			try{	
				String sqlAddCoupon = "INSERT INTO " + DatabaseInfo.getDBname() + ".coupon (Title, Start_Date, End_Date, " + 
						"Amount, Category, Message, Price, Image, Owner_ID)" + "VALUES(?,?,?,?,?,?,?,?,?)";	
				// set the owner ID into the coupon (is the company id)
				coupon.setOwnerID(company.getId());
				
				PreparedStatement prep = DBconnector.getConnection().prepareStatement(sqlAddCoupon, Statement.RETURN_GENERATED_KEYS);
				prep.setString(1, coupon.getTitle());
				prep.setDate(2, Date.valueOf(coupon.getStartDate()));
				prep.setDate(3, Date.valueOf(coupon.getEndDate()));
				prep.setInt(4, coupon.getAmount());
				prep.setString(5, coupon.getCategory().toString());
				prep.setString(6, coupon.getMessage());
				prep.setDouble(7, coupon.getPrice());
				prep.setString(8, coupon.getImage());
				prep.setLong(9, coupon.getOwnerID());
				
				prep.executeUpdate();
				ResultSet rs = prep.getGeneratedKeys();
				rs.next();
				long id = rs.getLong(1);
				coupon.setId(id);
				
				prep.clearBatch();
			} // try
			catch (SQLException | NullPointerException | FiledErrorException e) {
				e.printStackTrace();
				throw new DaoException("Error: Creating Coupon By Company- FAILED (something went wrong..)");
			} // catch
			return coupon;
		} // else - isExistInJoinTables
	} // createCoupon 
	
	@Override
	public void removeCoupon(Coupon coupon, long clientID ,ClientType client) throws DaoException{
		// check if the coupon exist
		
		if (existInDB.couponExistByID(coupon) == true) {
			if(client == ClientType.ADMIN) {
				removeCouponMethod(coupon, ClientType.ADMIN);
			} // if - ADMIN
			else if(client == ClientType.COMPANY) {
				Company company = new Company();
				company.setId(clientID);
				if(existInDB.couponFoundInJoinTables(coupon, company, CheckCouponBy.BY_ID) == true) {
					removeCouponMethod(coupon, ClientType.COMPANY);
				}
				else {
					throw new DaoException("Error: Removing Coupon - FAILED (Coupon is not exist in the DataBase)");
				}
			} // else if - COMPANY
		} // if - existOrNotByID
		else {
				throw new DaoException("Error: Removing Coupon - FAILED (Coupon is not exist in the DataBase)");
		} // else - existOrNotByID
	} // removeCoupon
	
	@Override
	public Coupon updateCoupon(Coupon coupon, Company company) throws DaoException{
		
		if(existInDB.couponExistByID(coupon) == true 
				  // check if the company owns this coupon
				&& existInDB.couponFoundInJoinTables(coupon, company, CheckCouponBy.BY_ID) == true) {
		       try {

					String sql = "UPDATE " + DatabaseInfo.getDBname() + ".coupon SET end_Date=?, amount=?, message=?, price=? WHERE coup_id=?";
					PreparedStatement prep = DBconnector.getConnection().prepareStatement (sql);
					
					// set all the Coupon to the new one.
					coupon.setEndDate(coupon.getEndDate());
					coupon.setAmount(coupon.getAmount());
					coupon.setMessage(coupon.getMessage());
					coupon.setPrice(coupon.getPrice());
					
					prep.setDate(1, Date.valueOf(coupon.getEndDate()));
					prep.setLong(2, coupon.getAmount());
					prep.setString(3, coupon.getMessage());
					prep.setDouble(4, coupon.getPrice());
					prep.setLong(5, coupon.getId());
					
					prep.executeUpdate();
					
					} catch (SQLException | FiledErrorException e) {
						throw new DaoException("Error: Updating Coupon - FAILED (something went wrong)");
					} // catch

		       return coupon;
		} // if - exist
		else {
			throw new DaoException("Error: Updating Coupon - FAILED (Coupon dosen't exist in the DataBase)");
		} // else - exist

		} // updateCoupon
	
	@Override
	public Coupon getCoupon(long coupID, long clientID, ClientType client) throws DaoException, FiledErrorException{
		
		Coupon coupon = getCouponMethod(coupID, clientID, client);
		return coupon;
	} // getCoupon - Function
	
	@Override
	public Collection<Coupon> getAllCoupons() throws DaoException{

		Collection<Coupon> coupons = new HashSet<>(); 
		
        try {
			String sql = "SELECT * FROM " + DatabaseInfo.getDBname() + ".coupon";
			Statement stat = DBconnector.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(sql);
			
			while (rs.next()) {				
				Coupon coupon = new Coupon(
						rs.getLong("coup_id"),
						rs.getString("Title"),
						rs.getDate("start_date").toLocalDate(),
						rs.getDate("end_date").toLocalDate(),
						rs.getInt("amount"),
						rs.getString("Category"),
						rs.getString("Message"),
						rs.getDouble("Price"),
						rs.getString("image"),
						rs.getLong("owner_ID"));
				
				// adding the current coupon to the collection
				coupons.add(coupon);
			} // while
		} catch (SQLException | FiledErrorException e) {
			throw new DaoException("Error: Getting All Coupons By ADMIN - FAILED (something went wrong)");
		} // catch
		if(!coupons.isEmpty()) {
			return coupons;
		} // if - empty
		else {
			throw new DaoException("Error: No Coupons Found");
		}  // else - Set<> is empty
	} // getAllCoupons

	@Override
	public Set<Coupon> getCouponByType(long clientID, CouponType category, ClientType client) throws DaoException {
		
		Set<Coupon> coupons = new HashSet<>();
		PreparedStatement prep = null;
		ResultSet rs = null;

		try {
		
		if(client == ClientType.ADMIN) {	
			
				String sql = "SELECT * FROM " + DatabaseInfo.getDBname() + ".coupon WHERE Category='"+ category.toString() + "'";
				prep = DBconnector.getConnection().prepareStatement(sql);
				rs = prep.executeQuery();

		} // if - ADMIN
		else if(client == ClientType.COMPANY) {
		
				String sql = "SELECT " + DatabaseInfo.getDBname() + ".coupon.* "
						+ "FROM " + DatabaseInfo.getDBname() + ".company_coupon "
						+ "LEFT JOIN " + DatabaseInfo.getDBname() + ".coupon USING (coup_id)"
						+ "WHERE " + DatabaseInfo.getDBname() + ".company_coupon.Comp_ID=" + clientID
						+ " AND " + DatabaseInfo.getDBname() + ".company_coupon.Comp_ID IS NOT NULL "
						+ "AND " + DatabaseInfo.getDBname() + ".company_coupon.Coup_ID = " + DatabaseInfo.getDBname() + ".coupon.Coup_id "
						+ "AND " + DatabaseInfo.getDBname() + ".coupon.Category='" +  category.toString() + "'";
				prep = DBconnector.getConnection().prepareStatement(sql);
				rs = prep.executeQuery();
				
		} // else if - COMPANY
		else if (client == ClientType.CUSTOMER) {

				String sql = "SELECT " + DatabaseInfo.getDBname() + ".coupon.* "
						+ "FROM " + DatabaseInfo.getDBname() + ".customer_coupon "
						+ "LEFT JOIN " + DatabaseInfo.getDBname() + ".coupon USING (coup_id)"
						+ "WHERE " + DatabaseInfo.getDBname() + ".customer_coupon.Cust_ID=" + clientID
						+ " AND " + DatabaseInfo.getDBname() + ".customer_coupon.Cust_ID IS NOT NULL "
						+ "AND " + DatabaseInfo.getDBname() + ".customer_coupon.coup_id = " + DatabaseInfo.getDBname() + ".coupon.Coup_id "
						+ "AND " + DatabaseInfo.getDBname() + ".coupon.Category='" +  category.toString() + "'";
				prep = DBconnector.getConnection().prepareStatement(sql);
				rs = prep.executeQuery();
	
		} // else if - CUSTOMER
		else {
			throw new DaoException("Error: Getting Coupons by Type (Category) - FAILD (Unidentified user)");
		} // else
		
		while(rs.next()) {
			Coupon coupon = new Coupon();
			
			coupon.setId(rs.getLong("coup_id"));
			coupon.setTitle(rs.getString("Title"));
			coupon.setStartDate(rs.getDate("start_date").toLocalDate());
			coupon.setEndDate(rs.getDate("end_date").toLocalDate());
			coupon.setAmount(rs.getInt("amount"));
			coupon.setMessage(rs.getString("Message"));
			coupon.setPrice(rs.getDouble("Price"));
			coupon.setCategory((rs.getString("Category")));
			coupon.setImage(rs.getString("image"));
			coupon.setOwnerID(rs.getLong("owner_ID"));
			
			coupons.add(coupon);
		} // while 
		
	if(!coupons.isEmpty()) {
		return coupons;
	} // if
	else {
		throw new DaoException("Error: No Coupons Found");
	}  // else - Set<> is empty
		
		} catch (FiledErrorException | SQLException e) {
			throw new DaoException("Error: Get Coupons By Type - FAILED (something went wrong)");
		} // catch
	}// getCouponByType
    
    @Override
	public Set<Coupon> getCouponByPrice(long id, double price,ClientType client) throws DaoException {
    
    	Set<Coupon> coupons = new HashSet<>();
		if(client == ClientType.ADMIN) {
			
			try {
				String sql = "SELECT * FROM " + DatabaseInfo.getDBname() + ".coupon WHERE Price <= " + price;
				PreparedStatement prep = DBconnector.getConnection().prepareStatement(sql);
				ResultSet rs = prep.executeQuery();
				
				while(rs.next()) {
					Coupon coupon = new Coupon();
					
					coupon.setId(rs.getLong("coup_id"));
					coupon.setTitle(rs.getString("Title"));
					coupon.setStartDate(rs.getDate("start_date").toLocalDate());
					coupon.setEndDate(rs.getDate("end_date").toLocalDate());
					coupon.setAmount(rs.getInt("amount"));
					coupon.setMessage(rs.getString("Message"));
					coupon.setPrice(rs.getDouble("Price"));
					coupon.setCategory((rs.getString("Category")));
					coupon.setImage(rs.getString("image"));
					coupon.setOwnerID(rs.getLong("owner_ID"));
					
					coupons.add(coupon);
				} // while 
				
			} catch (SQLException | FiledErrorException e) {
				throw new DaoException("Error: Get Coupons By Max Price (Admin) - FAILED (something went wrong)");
			}
			if(!coupons.isEmpty()) {
				return coupons;
			} // if
			else {
				throw new DaoException("Error: No Coupons Found");
			}  // else - Set<> is empty
		} // if - ClientType.ADMIN
		else if(client == ClientType.COMPANY) {
			try {
				
				String sql = "SELECT " + DatabaseInfo.getDBname() + ".coupon.* "
						+ "FROM " + DatabaseInfo.getDBname() + ".company_coupon "
						+ "LEFT JOIN " + DatabaseInfo.getDBname() + ".coupon USING (coup_id) "
						+ "WHERE " + DatabaseInfo.getDBname() + ".company_coupon.comp_ID =" + id + " "
						+ "AND " + DatabaseInfo.getDBname() + ".company_coupon.Coup_ID IS NOT NULL "
						+ "AND " + DatabaseInfo.getDBname() + ".coupon.Price <= " + price;
				
				PreparedStatement prep = DBconnector.getConnection().prepareStatement(sql);
				ResultSet rs = prep.executeQuery();
				
				while(rs.next()) {
					Coupon coupon = new Coupon();
					
					coupon.setId(rs.getLong("coup_id"));
					coupon.setTitle(rs.getString("Title"));
					coupon.setStartDate(rs.getDate("start_date").toLocalDate());
					coupon.setEndDate(rs.getDate("end_date").toLocalDate());
					coupon.setAmount(rs.getInt("amount"));
					coupon.setMessage(rs.getString("Message"));
					coupon.setPrice(rs.getDouble("Price"));
					coupon.setCategory((rs.getString("Category")));
					coupon.setImage(rs.getString("image"));
					coupon.setOwnerID(rs.getLong("owner_ID"));
					
					coupons.add(coupon);
				} // while 
				
			} catch (SQLException | FiledErrorException e) {
				e.printStackTrace();
				throw new DaoException("Error: Get Coupons By Max Price (Company) - FAILED (something went wrong)");
			} // catch
			if(!coupons.isEmpty()) {
				return coupons;
			} // if
			else {
				throw new DaoException("Error: No Coupons Found");
			}  // else - Set<> is empty
		} // if - ClientType.COMPANY
		else if (client == ClientType.CUSTOMER) {
 try {
				
				String sql = "SELECT " + DatabaseInfo.getDBname() + ".coupon.* "
						+ "FROM " + DatabaseInfo.getDBname() + ".customer_coupon "
						+ "LEFT JOIN " + DatabaseInfo.getDBname() + ".coupon USING (coup_id) "
						+ "WHERE " + DatabaseInfo.getDBname() + ".customer_coupon.cust_id =" + id + " "
						+ "AND " + DatabaseInfo.getDBname() + ".customer_coupon.coup_id IS NOT NULL "
						+ "AND " + DatabaseInfo.getDBname() + ".coupon.Price <= " + price;
				
				PreparedStatement prep = DBconnector.getConnection().prepareStatement(sql);
				ResultSet rs = prep.executeQuery();
				
				while(rs.next()) {
					Coupon coupon = new Coupon();
					
					coupon.setId(rs.getLong("coup_id"));
					coupon.setTitle(rs.getString("Title"));
					coupon.setStartDate(rs.getDate("start_date").toLocalDate());
					coupon.setEndDate(rs.getDate("end_date").toLocalDate());
					coupon.setAmount(rs.getInt("amount"));
					coupon.setMessage(rs.getString("Message"));
					coupon.setPrice(rs.getDouble("Price"));
					coupon.setCategory((rs.getString("Category")));
					coupon.setImage(rs.getString("image"));
					coupon.setOwnerID(rs.getLong("owner_ID"));
					
					coupons.add(coupon);
				} // while 
				
			} catch (SQLException | FiledErrorException e) {
				e.printStackTrace();
				throw new DaoException("Error: Get Coupons By Max Price (Company) - FAILED (something went wrong)");
			} // catch
			if(!coupons.isEmpty()) {
				return coupons;
			} // if
			else {
				throw new DaoException("Error: No Coupons Found");
			}  // else - Set<> is empty
		} // if - ClientType.CUSTOMER
		else {
			throw new DaoException("Error: Getting all Coupons by Max Price (Customer) - FAILD");
		} // else
    }  // getCouponByType
    
    /**
     * Remove Coupon {@code Coupon} from the underlying database 
     *(or any other persistence storage) with the 3 Access Points. 
     * </br>
     * Admin {@code ClientType}, Company {@code ClientType}, 
     * Customer {@code ClientType}.
     * </p>
     * This is a Private method for removing Coupons by all the access points.</br>
     * By Admin {@code ClientType}: Removing from ALL Tables.</br>
     * By Company {@code ClientType}: Removing from Company Table ONLY. </br>
     * 
     * @param coupon {@code Coupon} Object
     * @param client {@code ClientType} Enum
     * @throws DaoException
     */
	private void removeCouponMethod(Coupon coupon, ClientType client) throws DaoException{
		
	if(client == ClientType.ADMIN) {
		/*
		 * NOTE! Deleting coupon By the admin will delete the coupons from ALL Tables.
		 */
		String sqlDELid = "DELETE " + DatabaseInfo.getDBname() + ".coupon.*, "
				+ DatabaseInfo.getDBname() + ".company_coupon.*, "
				+ DatabaseInfo.getDBname() + ".customer_coupon.* "
				+ "FROM " + DatabaseInfo.getDBname() + ".coupon "
				+ "LEFT JOIN " + DatabaseInfo.getDBname() + ".company_coupon USING (coup_id) "
				+ "LEFT JOIN " + DatabaseInfo.getDBname() + ".customer_coupon USING (coup_id) "
				+ "WHERE " + DatabaseInfo.getDBname() + ".coupon.coup_id=" + coupon.getId()
				+ " AND " + DatabaseInfo.getDBname() + ".coupon.coup_id IS NOT NULL";
		
		PreparedStatement prep;
		try {
			prep = DBconnector.getConnection().prepareStatement(sqlDELid);
			prep.executeUpdate();
			prep.clearBatch();

		} catch (SQLException e) {
			throw new DaoException("Error: Removing Coupon - FAILED");
		} // catch
		
	} // if - ADMIN
	else if(client == ClientType.COMPANY) {
			/*
			 * NOTE! we are not deleting coupons from customer_coupon.
			 * The business logic says that the Customer will lose his coupon
			 * ONLY when the coupon END-DATE will Expired.
			 */
			
			String sqlDELid = "DELETE " + DatabaseInfo.getDBname() + ".coupon.*, "
					+ DatabaseInfo.getDBname() + ".company_coupon.*  "
					+ "FROM " + DatabaseInfo.getDBname() + ".company_coupon "
					+ "LEFT JOIN " + DatabaseInfo.getDBname() + ".coupon USING (coup_id) "
					+ "WHERE " + DatabaseInfo.getDBname() + ".company_coupon.Comp_ID=" + coupon.getOwnerID()
					+ " AND " + DatabaseInfo.getDBname() + ".company_coupon.coup_id=" + coupon.getId()
					+ " AND " + DatabaseInfo.getDBname() + ".company_coupon.Comp_ID IS NOT NULL";
			
			PreparedStatement prep;
			try {
				prep = DBconnector.getConnection().prepareStatement(sqlDELid);
				prep.executeUpdate();
				prep.clearBatch();

			} catch (SQLException e) {
				throw new DaoException("Error: Removing Coupon - FAILED");
			} // catch
		} // if - COMPANY
	} // removeMethod

	/**
	 * This is my add-on. </br>
	 * Getting all coupons by the ID {@code long} of a Client {@code ClientType}.
	 * @param id {@code long}
	 * @param client {@code ClientType} Enum
	 * @return a {@code Coupon} Object by the client {@code ClientType}.
	 * @throws DaoException
	 * @throws FiledErrorException 
	 */
	private Coupon getCouponMethod(long coupID,  long clientID, ClientType client) throws DaoException, FiledErrorException {
		Coupon coupon = new Coupon();
		coupon.setId(coupID);
		/* We set the ID for the existOrNot check method.
		 * and if the coupon exist - we don't need to get the ID or setting it again. 
		 * we just gonna put it in the constructor below.
		 */
		// we have 3 access option of this get method.
		if(client == ClientType.ADMIN) {
			try {
				

			if(existInDB.couponExistByID(coupon) == true) {
				String title, message, image, category;
				Date stDate, enDate ;	
				int amount;
				double price;
				long ownerID = -1;
			
					String sqlSEL = "SELECT * FROM " + DatabaseInfo.getDBname() + ".coupon WHERE Coup_ID= ?" ;
					PreparedStatement prep = DBconnector.getConnection().prepareStatement(sqlSEL);
					prep.setLong(1, coupID);
					
					ResultSet rs = prep.executeQuery();
					
					while(rs.next()) {
						
						title = rs.getString("Title");
						stDate = rs.getDate("start_date");
						enDate = rs.getDate("end_date");
						amount = rs.getInt("amount");
						category = rs.getString("Category");
						message = rs.getString("Message");
						price = rs.getDouble("Price");
						image = rs.getString("image");
						ownerID = rs.getLong("Owner_ID");

						coupon =  new Coupon(coupID, title, stDate.toLocalDate(), enDate.toLocalDate(), amount, category, message, price, image, ownerID);
					} // while

				return coupon;
			} // if - exist
			else {
				throw new DaoException("Error: Getting Coupon By ID - FAILED (Coupon dosen't exist in the DataBase)");
			} // else - exist
			} catch (SQLException | FiledErrorException e) {
				throw new DaoException("Error: Getting Coupon By ID - FAILED (something went wrong)");
			} // catch
		} // if - admin
		else if (client == ClientType.COMPANY) {
			Company company = new Company();
			company.setId(clientID);
			// check if the company owns this coupon
			if(existInDB.couponFoundInJoinTables(coupon, company, CheckCouponBy.BY_ID) == true) {
				try {
				if(existInDB.couponExistByID(coupon) == true) {
					String title, message, image, category;
					Date stDate, enDate ;	
					int amount;
					double price;
					long ownerID = -1;
							
						String sqlSELCoupByCompany = "SELECT " + DatabaseInfo.getDBname() + ".coupon.Coup_ID, title, Start_Date, End_Date, Amount, category, message, price, image, Owner_ID "
								+ "FROM " + DatabaseInfo.getDBname() + ".coupon, " + DatabaseInfo.getDBname() + ".company_coupon "
								+ "WHERE " + DatabaseInfo.getDBname() + ".coupon.Coup_ID=? = " + DatabaseInfo.getDBname() + ".company_coupon.Coup_ID";
						PreparedStatement prep = DBconnector.getConnection().prepareStatement(sqlSELCoupByCompany);
						prep.setLong(1, coupID);
						ResultSet rs = prep.executeQuery();
						
						while(rs.next()) {
							long idcopy = rs.getLong("coup_id");
							title = rs.getString("Title");
							stDate = rs.getDate("start_date");
							enDate = rs.getDate("end_date");
							amount = rs.getInt("amount");
							category = rs.getString("Category");
							message = rs.getString("Message");
							price = rs.getDouble("Price");
							image = rs.getString("image");
							ownerID = rs.getLong("Owner_ID");
							
							coupon = new Coupon(idcopy, title, stDate.toLocalDate(), enDate.toLocalDate(), amount, category,  message, price, image, ownerID);
						} // while
					return coupon;
				} // if - exist
				else {
					throw new DaoException("Error: Getting Coupon By ID - FAILED (Coupon dosen't exist in the DataBase)");
				} // else - exist
				} catch (SQLException | FiledErrorException e) {
					throw new DaoException("Error: Getting Coupon By ID - FAILED (something went wrong)");
				} // catch
			} // if - existInDB.couponFoundInJoinTables
			else {
				throw new DaoException("Error: Getting Coupon By ID - FAILED (Coupon dosen't exist in the DataBase)");
			} // else - existInDB.couponFoundInJoinTables
		} // else - COMPANY
		else if (client == ClientType.CUSTOMER
				&& existInDB.purchasedBefore(coupID, clientID) == false) {
			
			try {
			if(existInDB.couponExistByID(coupon) == true) {
					
					String title, message, image, category;
					Date stDate, enDate ;	
					int amount;
					double price;
					long ownerID, couponID;
						
						String sqlSelCoupAfterCustomer = "SELECT * FROM " + DatabaseInfo.getDBname() + ".coupon WHERE coup_id= ?" ;
						PreparedStatement prep = DBconnector.getConnection().prepareStatement(sqlSelCoupAfterCustomer);
						prep.setLong(1, coupID);
						ResultSet rs = prep.executeQuery();
						rs.next();

						couponID = rs.getLong("coup_id");
						title = rs.getString("Title");
						stDate = rs.getDate("start_date");
						enDate = rs.getDate("end_date");
						amount = rs.getInt("amount");
						category = rs.getString("Category");
						message = rs.getString("Message");
						price = rs.getDouble("Price");
						image = rs.getString("image");
						ownerID = rs.getLong("owner_id");
						
						coupon = new Coupon(couponID, title, stDate.toLocalDate(), enDate.toLocalDate(), amount, category, message, price, image, ownerID);
					return coupon;
			} // if - exist
			else {
				throw new DaoException("Error: Getting Coupon By ID - FAILED (Coupon dosen't exist in the DataBase)");
			} // else - exist
			} catch (SQLException | FiledErrorException e) {
				e.printStackTrace();
				throw new DaoException("Error: Getting Coupon By ID - FAILED (something went wrong)");
			} // catch
		} // else if - ClientType.CUSTOMER
		else {
			throw new DaoException("Error: You can buy the coupon only once!");
		} // else
    } // getCouponMethod

} // Class
