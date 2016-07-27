package business.customersubsystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Logger;

import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.DbClassCustomerProfileForTest;
import middleware.DbConfigProperties;
import middleware.dataaccess.DataAccessSubsystemFacade;
import middleware.exceptions.DatabaseException;
import middleware.externalinterfaces.DataAccessSubsystem;
import middleware.externalinterfaces.DbClass;
import middleware.externalinterfaces.DbConfigKey;


class DbClassCustomerProfile implements DbClass,DbClassCustomerProfileForTest{
	enum Type {READ,READ_DEFAULT_CREDITCARD};
	@SuppressWarnings("unused")
	private static final Logger LOG = 
		Logger.getLogger(DbClassCustomerProfile.class.getPackage().getName());
	private DataAccessSubsystem dataAccessSS = 
    	new DataAccessSubsystemFacade();
    
    private Type queryType;
    
    private String readQuery 
        = "SELECT custid,fname,lname FROM Customer WHERE custid = ?";
    private String readDefaultCreditQuery 
    = "SELECT * FROM accountsdb.altpayment where custid = ?";
    private Object[] readParams,paymentParams;
    private int[] readTypes,paymentTypes;
    
    /** Used for reading in values from the database */
    private CustomerProfileImpl customerProfile;
    private CreditCardImpl defaultCreditCard;
   private CustomerProfile tempProfile;
    public CustomerProfileImpl readCustomerProfile(Integer custId) throws DatabaseException {
        queryType = Type.READ;
        readParams = new Object[]{custId};
        readTypes = new int[]{Types.INTEGER};
        dataAccessSS.atomicRead(this); 
        return customerProfile;
    }
    public CreditCardImpl readDefaultPaymentInfo(CustomerProfile custProfile) throws DatabaseException{
    	tempProfile=custProfile;
    	queryType = Type.READ_DEFAULT_CREDITCARD;
    	paymentParams = new Object[]{custProfile.getCustId()};
    	paymentTypes = new int[]{Types.INTEGER};
        dataAccessSS.atomicRead(this); 
        System.out.println("card numbver= "+defaultCreditCard.getCardNum());
        return defaultCreditCard;
    }
    public void populateEntity(ResultSet rs) throws DatabaseException  {
    	switch(queryType) {
    	case READ:
    		populateCustomerProfile(rs);
    	case READ_DEFAULT_CREDITCARD:
    		populatePaymentInfo(rs);
    	default:
    		//do nothing
    	}
    }
    public void populatePaymentInfo(ResultSet resultSet) throws DatabaseException{
    	try {   
            //we take the first returned row
            if(resultSet.next()){
            	defaultCreditCard 
                  = new CreditCardImpl("test",resultSet.getString("expdate"),
                							resultSet.getString("cardnum"),
                                            resultSet.getString("cardtype"));
            }
        }
        catch(SQLException e){
            throw new DatabaseException(e);
        }
    }
    public void populateCustomerProfile(ResultSet resultSet)throws DatabaseException{
    	 try {   
             //we take the first returned row
             if(resultSet.next()){
                 customerProfile 
                   = new CustomerProfileImpl(resultSet.getInt("custid"),
                 							resultSet.getString("fname"),
                                             resultSet.getString("lname"));
             
             }
         }
         catch(SQLException e){
             throw new DatabaseException(e);
         }
    }
    public String getDbUrl() {
    	DbConfigProperties props = new DbConfigProperties();	
    	return props.getProperty(DbConfigKey.ACCOUNT_DB_URL.getVal());
    }

    @Override
    public String getQuery() {
    	switch(queryType) {
	    	case READ :
	    		return readQuery;
	    	case READ_DEFAULT_CREDITCARD:
	    		return readDefaultCreditQuery;
	    	default :
	    		return null;
    	}
    }

	@Override
	public Object[] getQueryParams() {
		switch(queryType) {
	    	case READ :
	    		return readParams;
	    	case READ_DEFAULT_CREDITCARD:
	    		return paymentParams;
	    	default :
	    		return null;
		}
	}
	@Override
	public int[] getParamTypes() {
		switch(queryType) {
	    	case READ :
	    		return readTypes;
	    	case READ_DEFAULT_CREDITCARD:
	    		return paymentTypes;
	    	default :
	    		return null;
		}
	}
	
	@Override
	public CustomerProfile getCustomerProfileForTest() {
		// TODO Auto-generated method stub
		return customerProfile;
	}
}
