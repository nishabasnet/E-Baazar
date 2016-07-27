package business.usecasecontrol;

import java.util.List;
import java.util.logging.Logger;

import business.BusinessConstants;
import business.SessionCache;
import business.customersubsystem.CustomerSubsystemFacade;
import business.customersubsystem.RulesPayment;
import business.exceptions.BackendException;
import business.exceptions.BusinessException;
import business.exceptions.RuleException;
import business.externalinterfaces.Address;
import business.externalinterfaces.CreditCard;
import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.CustomerSubsystem;
import business.externalinterfaces.Rules;
import business.externalinterfaces.ShoppingCart;
import business.externalinterfaces.ShoppingCartSubsystem;
import business.shoppingcartsubsystem.ShoppingCartSubsystemFacade;
import presentation.data.CartItemPres;
import presentation.util.Util;

public enum CheckoutController  {
	INSTANCE;
	
	private static final Logger LOG = Logger.getLogger(CheckoutController.class
			.getPackage().getName());
	
	
	public void runShoppingCartRules(List<CartItemPres> cartItems) throws RuleException, BusinessException {
		//implement
		ShoppingCart sc=ShoppingCartSubsystemFacade.INSTANCE.createShoppingCart(Util.cartItemPresToCartItemList(cartItems));
		ShoppingCartSubsystemFacade.INSTANCE.runShippingRules(sc);
		
		
	}
	public List<Address> getShippingAddresses(CustomerProfile custProf) throws BackendException {
		//implement
		//LOG.warning("Method CheckoutController.getShippingAddresses has not been implemented");
		CustomerSubsystem cust=new CustomerSubsystemFacade();
		cust.initializeCustomer(custProf.getCustId(),(custProf.isAdmin())?1:0);
		return cust.getAllAddresses();
	}
	
	//implement
	public Address getDefaultShippingAddress(CustomerProfile custProf) throws BackendException {
		CustomerSubsystem cust=new CustomerSubsystemFacade();
		cust.initializeCustomer(custProf.getCustId(),(custProf.isAdmin())?1:0);
		Address add=cust.getDefaultShippingAddress();
		System.out.println("checkout controlleer ="+add.getCity());
		return cust.getDefaultShippingAddress();
	}
	
	public Address getDefaultBillingAddress(CustomerProfile custProf) throws BackendException {
		CustomerSubsystem cust=new CustomerSubsystemFacade();
		cust.initializeCustomer(custProf.getCustId(),(custProf.isAdmin())?1:0);
		return cust.getDefaultBillingAddress();
	}
	public CreditCard getDefaultCreditCardInfo(CustomerProfile custProf) throws BackendException{
		CustomerSubsystem cust=new CustomerSubsystemFacade();
		cust.initializeCustomer(custProf.getCustId(),(custProf.isAdmin())?1:0);
		return cust.getDefaultPaymentInfo();
	}
	
	public void runPaymentRules(Address addr, CreditCard cc) throws RuleException, BusinessException {
		//implement
		Rules transferObject = new RulesPayment(addr, cc);//
		transferObject.runRules();
	}
	
	public Address runAddressRules(Address addr) throws RuleException, BusinessException {
		CustomerSubsystem cust = 
			(CustomerSubsystem)SessionCache.getInstance().get(BusinessConstants.CUSTOMER);
		return cust.runAddressRules(addr);
	}
	
	/** Asks the ShoppingCart Subsystem to run final order rules */
	public void runFinalOrderRules() throws RuleException, BusinessException {
		//implement
		ShoppingCartSubsystemFacade.INSTANCE.runFinalOrderRules(ShoppingCartSubsystemFacade.INSTANCE.getLiveCart());
	}
	
	/** Asks Customer Subsystem to check credit card against 
	 *  Credit Verification System 
	 */
	public void verifyCreditCard(CreditCard cc) throws BusinessException {
		//implement
		CustomerSubsystem cust = 
				(CustomerSubsystem)SessionCache.getInstance().get(BusinessConstants.CUSTOMER);	
		cust.checkCreditCard(cc);
	}
	
	public void saveNewAddress(Address addr) throws BackendException {
		CustomerSubsystem cust = 
			(CustomerSubsystem)SessionCache.getInstance().get(BusinessConstants.CUSTOMER);		
		cust.saveNewAddress(addr);
	}
	
	/** Asks Customer Subsystem to submit final order */
	public void submitFinalOrder() throws BackendException {
		//implement
		CustomerSubsystem cust = 
				(CustomerSubsystem)SessionCache.getInstance().get(BusinessConstants.CUSTOMER);		
		cust.submitOrder();
	}
	
	public void clearLiveCart(){
		ShoppingCartSubsystemFacade.INSTANCE.clearLiveCart();
	}
	public void setShippingAddress(Address userShipAdd) {
		CustomerSubsystem cust = 
				(CustomerSubsystem)SessionCache.getInstance().get(BusinessConstants.CUSTOMER);		
		cust.setShippingAddressInCart(userShipAdd);
	}
	public void setBillingAddress(Address userBillAdd) {
		CustomerSubsystem cust = 
				(CustomerSubsystem)SessionCache.getInstance().get(BusinessConstants.CUSTOMER);		
		cust.setBillingAddressInCart(userBillAdd);
	}
	public void setPaymentInfoInCart(CreditCard cc) {
		CustomerSubsystem cust = 
				(CustomerSubsystem)SessionCache.getInstance().get(BusinessConstants.CUSTOMER);	
		cust.setPaymentInfoInCart(cc);
	}
	public void deleteAddress(int addressId)throws BackendException{
		CustomerSubsystem cust = 
				(CustomerSubsystem)SessionCache.getInstance().get(BusinessConstants.CUSTOMER);	
		cust.deleteAddress(addressId);
	}


}
