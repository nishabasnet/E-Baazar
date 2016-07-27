package business.externalinterfaces;

import middleware.exceptions.DatabaseException;

public interface DbClassCustomerProfileForTest {
	public CustomerProfile readCustomerProfile(Integer custId) throws DatabaseException;
	public CustomerProfile getCustomerProfileForTest();

}
