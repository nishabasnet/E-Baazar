package presentation.data;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import presentation.gui.GuiConstants;
import business.BusinessConstants;
import business.SessionCache;
import business.customersubsystem.CustomerSubsystemFacade;
import business.exceptions.BackendException;
import business.externalinterfaces.Address;
import business.externalinterfaces.CreditCard;
import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.CustomerSubsystem;
import business.usecasecontrol.BrowseAndSelectController;
import business.usecasecontrol.CheckoutController;

public enum CheckoutData {
	INSTANCE;
	
	public Address createAddress(String street, String city, String state,
			String zip, boolean isShip, boolean isBill) {
		return CustomerSubsystemFacade.createAddress(street, city, state, zip, isShip, isBill);
	}
	
	public CreditCard createCreditCard(String nameOnCard,String expirationDate,
               String cardNum, String cardType) {
		return CustomerSubsystemFacade.createCreditCard(nameOnCard, expirationDate, 
				cardNum, cardType);
	}
	
	//Customer Ship Address Data
		private ObservableList<CustomerPres> shipAddresses;
		
		//Customer Bill Address Data
		private ObservableList<CustomerPres> billAddresses;
		
		//Customer default payment info
		private CreditCardPres creditCardPres;
		//customer default ship and bill address
		private Address defaultShipAddress,defaultBillAddress;
		
	/**
	 * Precondition: Customer has logged in
	 */
	public void loadShipAddresses() throws BackendException {
		/*CustomerProfile custProf 
		  = ((CustomerSubsystem)SessionCache.getInstance().get(BusinessConstants.CUSTOMER)).getCustomerProfile();*/
		List<Address> shippingAddresses = CheckoutController.INSTANCE.getShippingAddresses(getCustomerProfile());
		System.out.println(shippingAddresses.get(0).getCity());
		List<CustomerPres> displayableCustList =
				shippingAddresses.stream()
				                 .map(addr -> new CustomerPres(getCustomerProfile(), addr))
				                 .collect(Collectors.toList());
		shipAddresses =  FXCollections.observableList(displayableCustList);				   
										   
	}
	
	public void loadDefaultPaymentInfo() throws BackendException{
		creditCardPres=new CreditCardPres(CheckoutController.INSTANCE.getDefaultCreditCardInfo(getCustomerProfile()));
		creditCardPres.setName(getCustomerProfile().getFirstName()+" "+getCustomerProfile().getLastName());
	}
	public void loadDefaultShipAddress() throws BackendException{
		defaultShipAddress=CheckoutController.INSTANCE.getDefaultShippingAddress(getCustomerProfile());
	}
	public void loadDefaultBillAddress() throws BackendException{
		defaultBillAddress=CheckoutController.INSTANCE.getDefaultBillingAddress(getCustomerProfile());
	}
		
	/**
	 * Precondition: Customer has logged in
	 */
	public void loadBillAddresses() throws BackendException  {
		/*CustomerProfile custProf 
		  = ((CustomerSubsystem)SessionCache.getInstance().get(BusinessConstants.CUSTOMER)).getCustomerProfile();*/
		List<Address> billingAddresses = CheckoutController.INSTANCE.getShippingAddresses(getCustomerProfile());
		List<CustomerPres> displayableCustList =
				billingAddresses.stream()
				                .map(addr -> new CustomerPres(getCustomerProfile(), addr))
				                .collect(Collectors.toList());
		billAddresses = FXCollections.observableList(displayableCustList);		
	}
	public ObservableList<CustomerPres> getCustomerShipAddresses() throws BackendException {
		if(shipAddresses == null) {
			loadShipAddresses();
		}
		return shipAddresses;
	}
	public ObservableList<CustomerPres> getCustomerBillAddresses() throws BackendException {
		if(billAddresses == null) {
			loadBillAddresses();
		}
		return billAddresses;
	}
	public ObservableList<CustomerPres> deleteCustomerAddressesAndUpdate(String title,Address address) throws BackendException {
		CheckoutController.INSTANCE.deleteAddress(address.getId());
		if (title.matches("Billing Address")) {
			
			List<CustomerPres> Addresses=billAddresses.stream()
					.filter(add->add.getId()!=address.getId()).collect(Collectors.toList());
			billAddresses=FXCollections.observableArrayList(Addresses);
			return billAddresses;
		}else{
			List<CustomerPres> Addresses=shipAddresses.stream()
					.filter(add->add.getId()!=address.getId()).collect(Collectors.toList());
			shipAddresses=FXCollections.observableArrayList(Addresses);
			return shipAddresses;
		}
	}
	public List<String> getDisplayAddressFields() {
		return GuiConstants.DISPLAY_ADDRESS_FIELDS;
	}
	public List<String> getDisplayCredCardFields() {
		return GuiConstants.DISPLAY_CREDIT_CARD_FIELDS;
	}
	public List<String> getCredCardTypes() {
		return GuiConstants.CREDIT_CARD_TYPES;
	}
	
	
	
	public CreditCardPres getDefaultPaymentInfo() throws BackendException {
		//CheckoutController.INSTANCE.getDefaultCreditCardInfo(getCustomerProfile());
		return creditCardPres;
	}
	
	
	public CustomerProfile getCustomerProfile() {
		
		return BrowseAndSelectController.INSTANCE.getCustomerProfile();
	}
	
		
	
	private class ShipAddressSynchronizer implements Synchronizer {
		public void refresh(ObservableList list) {
			shipAddresses = list;
		}
	}
	public ShipAddressSynchronizer getShipAddressSynchronizer() {
		return new ShipAddressSynchronizer();
	}
	
	private class BillAddressSynchronizer implements Synchronizer {
		public void refresh(ObservableList list) {
			billAddresses = list;
		}
	}
	public BillAddressSynchronizer getBillAddressSynchronizer() {
		return new BillAddressSynchronizer();
	}
	
	public static class ShipBill {
		public boolean isShipping;
		public String label;
		public Synchronizer synch;
		public ShipBill(boolean shipOrBill, String label, Synchronizer synch) {
			this.isShipping = shipOrBill;
			this.label = label;
			this.synch = synch;
		}
		
	}
	public Address getDefaultShippingData() throws BackendException {
		//implement
		return defaultShipAddress;
	}
	
	public Address getDefaultBillingData() throws BackendException {
		return defaultBillAddress;
	}
	public void setDefaultShippingData(Address add) throws BackendException {
		//implement
		 defaultShipAddress=add;
	}
	
	public void setDefaultBillingData(Address add) throws BackendException {
		 defaultBillAddress=add;
	}
	
	//called when user is successfully login
	

}
